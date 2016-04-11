package br.ufrn.dimap.rtquality.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.QualifiedName;

import sun.security.util.Length;

public class MethodLimitBuilder {
	
	private List<MethodLimit> methods;
	
	private String pack;
	
	public MethodLimitBuilder(String source) {
		methods = new ArrayList<MethodLimit>();
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		try {
			pack = cu.getPackage().getName().getFullyQualifiedName()+".";
		} catch(Exception e) {
			System.out.println("\t\t\t\tClasse mal formada. Ignorada...");
		}
		cu.accept(new ASTVisitor() {
			public boolean visit(TypeDeclaration node) { //TODO: Verificar se quando mais de uma classe é modificada se o pack está recebendo o nome correto de cada classe, sem misturá-los
				pack += (node.getName()+".");
				return true;
			}
 			public boolean visit(MethodDeclaration node) {
 				int startLine = cu.getLineNumber(node.getStartPosition());
 				int endLine = cu.getLineNumber(node.getStartPosition() + node.getLength());
 				String signature = (node.getReturnType2() != null ? (node.getReturnType2().toString().contains("<") ? node.getReturnType2().toString().substring(0,node.getReturnType2().toString().indexOf("<")) : node.getReturnType2().toString()) : "")+" "+pack+node.getName().getFullyQualifiedName()+"(";
 				
 				for(Object type : node.parameters()) {
 					String temp = ((SingleVariableDeclaration) type).getType().toString();
 					if(temp.contains("<"))
 						temp = temp.substring(0,temp.indexOf("<"));
 					signature += temp+", ";
 				}
 				if(node.parameters().size() > 0)
 					signature = signature.substring(0, signature.length()-2);
 				signature += ")";
 				methods.add(new MethodLimit(signature, startLine, endLine));
				
				return true;
			}
		});
	}
	
	public List<MethodLimit> getMethodLimits() {
		return Collections.unmodifiableList(methods);
	}
	
}
