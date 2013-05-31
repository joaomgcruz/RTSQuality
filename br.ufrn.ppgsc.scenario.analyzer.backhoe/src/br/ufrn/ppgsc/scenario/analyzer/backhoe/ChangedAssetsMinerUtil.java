package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;

import br.ufrn.backhoe.repminer.model.BackhoeClassCL;
import br.ufrn.backhoe.repminer.model.BackhoeFieldCL;
import br.ufrn.backhoe.repminer.model.BackhoeMethodCL;
import br.ufrn.backhoe.repminer.model.ChangeType;
import br.ufrn.backhoe.repminer.utils.ClassVisitor;

public class ChangedAssetsMinerUtil {
	
	private static final Logger logger = Logger.getLogger(ChangedAssetsMinerUtil.class);
	
	public static void retrieveChangedElements(SVNWCClient wcclient, String url, BackhoeClassCL sourceClass, BackhoeClassCL targetClass) {
		SVNRevision svnSourceRevision = SVNRevision.create(sourceClass.getRevision());
		SVNRevision svnTargetRevision = SVNRevision.create(targetClass.getRevision());
		
		ByteArrayOutputStream sourceStream = new ByteArrayOutputStream();
		ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
		
		try {
			SVNURL svnUrl = SVNURL.parseURIEncoded(url);
			
			wcclient.doGetFileContents(svnUrl, svnSourceRevision, svnSourceRevision, true, sourceStream);
			wcclient.doGetFileContents(svnUrl, svnTargetRevision, svnTargetRevision, true, targetStream);
		} catch (SVNException e) {
			logger.error(e.getMessage());
		}
		
		InputStream inSourceContent = new ByteArrayInputStream(sourceStream.toByteArray());
		InputStream inTargetContent = new ByteArrayInputStream(targetStream.toByteArray());
		
		CompilationUnit sourceCu = null;
		CompilationUnit targetCu = null;
		
		try {
			targetCu = JavaParser.parse(inTargetContent);
			sourceCu = JavaParser.parse(inSourceContent);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
		ClassVisitor sourceVisitor = new ClassVisitor();
		ClassVisitor targetVisitor = new ClassVisitor();
		
		sourceVisitor.visit(sourceCu, null);
		targetVisitor.visit(targetCu, null);
		
		/* 
		 * TODO: arrumar para pegar também o construtor
		 * atualmente não preciso pegar os campos
		 */
		retrieveChangedFields(targetClass,sourceVisitor,targetVisitor);
		retrieveChangedMethods(targetClass, sourceVisitor, targetVisitor);
		
	}
	
	/**
	 * Auxiliary method of "retrieveChangedElements"
	 * return the changedFields
	 * 
	 * @param classe
	 * @param sourceVisitor
	 * @param targetVisitor
	 */
	private static void retrieveChangedFields(BackhoeClassCL clazz, ClassVisitor sourceVisitor, ClassVisitor targetVisitor)
	{
		List<FieldDeclaration> sourceFields = sourceVisitor.getFields();
		List<FieldDeclaration> targetFields = targetVisitor.getFields();
		//das this class has fields?
		if(!sourceFields.isEmpty() || !targetFields.isEmpty())
		{
			//does the source revision added fields?
			List<FieldDeclaration> fields = (List<FieldDeclaration>) CollectionUtils.subtract(sourceFields, targetFields);
			if(fields.size() > 0)
			{
				for(FieldDeclaration field : fields)
				{
					for(VariableDeclarator variable : field.getVariables())
					{
						BackhoeFieldCL fieldChangeLog = buildField(field,variable,ChangeType.ADDED);
						clazz.getFields().add(fieldChangeLog);
					}
					
				}
			}
			//does the source revision removed fields?
			fields = (List<FieldDeclaration>) CollectionUtils.subtract(targetFields, sourceFields);
			if(fields.size() > 0)
			{
				for(FieldDeclaration field : fields)
				{
					for(VariableDeclarator variable : field.getVariables())
					{
						BackhoeFieldCL fieldChangeLog = buildField(field,variable,ChangeType.DELETE);
						clazz.getFields().add(fieldChangeLog);
					}
					
				}
			}
		}
	}
	
	/**
	 * Auxiliary method of "retrieveChangedElements"
	 * This method is responsible for returning the changed methods from source to target 
	 * 
	 * @param clazz
	 * @param sourceVisitor
	 * @param targetVisitor
	 */
	private static void retrieveChangedMethods(BackhoeClassCL clazz, ClassVisitor sourceVisitor, ClassVisitor targetVisitor)
	{
		List<MethodDeclaration> sourceMethods = sourceVisitor.getMethods();
		List<MethodDeclaration> targetMethods = targetVisitor.getMethods();
		
		Map<String, MethodDeclaration> keyValueSource = convertMethods(sourceMethods);
		Map<String, MethodDeclaration> keyValueTarget = convertMethods(targetMethods);
		
		for(Map.Entry<String, MethodDeclaration> sourceEntry : keyValueSource.entrySet())
		{
			String methodName = sourceEntry.getKey();
			if(!keyValueTarget.containsKey(methodName))
			{
				//this new name does not exists, does the change was only the name?
				//which would characterize an "update"
				MethodDeclaration sourceMethod = sourceEntry.getValue();
				boolean sameBody = false;
				for(Map.Entry<String, MethodDeclaration> targetEntry : keyValueTarget.entrySet())
				{
					MethodDeclaration targetMethod = targetEntry.getValue();
					if(sourceMethod.getBody() != null && targetMethod.getBody() != null)
					{
						if(sourceMethod.getBody().equals(targetMethod.getBody()))
						{
							BackhoeMethodCL mcl = buildMethod(sourceMethod, ChangeType.UPDATED);
							clazz.getMethods().add(mcl);
							sameBody = true;
						}
					}
				}
				if(!sameBody)
				{
					BackhoeMethodCL mcl = buildMethod(sourceMethod, ChangeType.ADDED);
					clazz.getMethods().add(mcl);
				}
			}
			else
			{
				//the name already exists, does the change was only the body?
				//which would characterize a update
				MethodDeclaration sourceMethod = sourceEntry.getValue();
				MethodDeclaration targetMethod = keyValueTarget.get(methodName);
				if(sourceMethod.getBody() != null && targetMethod.getBody() != null)
				{

					if(!sourceMethod.getBody().equals(targetMethod.getBody()))
					{
						BackhoeMethodCL mcl = buildMethod(sourceMethod, ChangeType.UPDATED);
						clazz.getMethods().add(mcl);
					}
				}
			}
		}
		
		for(Map.Entry<String, MethodDeclaration> targetEntry : keyValueTarget.entrySet())
		{
			String methodName = targetEntry.getKey();
			if(!keyValueSource.containsKey(methodName))
			{
				//does the method was removed, or does it has changed only the name?
				MethodDeclaration targetMethod = targetEntry.getValue();
				boolean sameBody = false;
				for(Map.Entry<String, MethodDeclaration> sourceEntry : keyValueSource.entrySet())
				{
					MethodDeclaration sourceMethod = sourceEntry.getValue();
					if(sourceMethod.getBody()!= null && targetMethod.getBody()!= null)
					{
						if(sourceMethod.getBody().equals(targetMethod.getBody()))
						{
							sameBody = true;
						}
					}
				}
				if(!sameBody)
				{
					BackhoeMethodCL mcl = buildMethod(targetMethod, ChangeType.DELETE);
					clazz.getMethods().add(mcl);
				}
			}
		}
	}
	
	/**
	 * 
	 * Auxiliary method of "retrieveChangedFields" and "retrieveChangedMethods"
	 * this method is responsible to build the FieldChangeLog object
	 * @param field
	 * @param variable
	 * @param changeType
	 * @return
	 */
	private static BackhoeFieldCL buildField(FieldDeclaration field, VariableDeclarator variable, String changeType)
	{
		BackhoeFieldCL fieldChangeLog = new BackhoeFieldCL();
		fieldChangeLog.setName(variable.getId().getName());
		fieldChangeLog.setChangeType(changeType);
		fieldChangeLog.setSignature(field.getType().toString() + ":" + fieldChangeLog.getName());
		return fieldChangeLog;
	}
	
	/**
	 * Auxiliary method of "retrieveChangedMethods"
	 * this method is responsible for building the MethodChangeLog object
	 * @param method
	 * @param changeType
	 * @return
	 */
	private static BackhoeMethodCL buildMethod(MethodDeclaration method, String changeType)
	{
		BackhoeMethodCL mcl = new BackhoeMethodCL();
		mcl.setChangeType(changeType);
		mcl.setName(method.getName());
		mcl.setSignature(
				method.getType().toString() + "#"
				+ method.getName()
				+ formatParameters(method));
		return mcl;
	}
	
	/**
	 * Auxiliary method of "buildMethod" 
	 * this method is responsible for formatting the 
	 * exhibition of the method's parameters 
	 * @param method
	 * @return
	 */
	private static String formatParameters(MethodDeclaration method)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		if(method.getParameters() != null)
		{
			for(int i = 0; i < method.getParameters().size(); i++)
			{
				Parameter p = method.getParameters().get(i);

				sb.append(p.getType().toString());
				sb.append(" ");
				sb.append(p.getId().getName());
			
				if(i != (method.getParameters().size()-1))
				{
					sb.append(", ");
				}
			}
			sb.append(" )");
		}
		else
		{
			sb.append(" )");
		}
		
		return sb.toString();
	}
	
	/**
	 * Auxiliary Method of "retrieveChangedMethods"
	 * This method converts a list of MethodDeclaration objects into
	 * a Map of the same objects. This facilitates the operation
	 * of the method "retrieveChangedMethods"
	 * 
	 * @param methods
	 * @return
	 */
	private static Map<String,MethodDeclaration> convertMethods(List<MethodDeclaration> methods)
	{
		Map<String,MethodDeclaration> keyValue = new HashMap<String,MethodDeclaration>();
		for(MethodDeclaration method : methods)
		{
			keyValue.put(method.getName(), method);
		}
		return keyValue;
	}

}
