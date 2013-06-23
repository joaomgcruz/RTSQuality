package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodLimitBuilder {
	
	private List<MethodLimit> methods;
	
	public MethodLimitBuilder(String source) {
		methods = new ArrayList<MethodLimit>();
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		 
		cu.accept(new ASTVisitor() {
 			public boolean visit(MethodDeclaration node) {
 				int startLine = cu.getLineNumber(node.getStartPosition());
 				int endLine = cu.getLineNumber(node.getStartPosition() + node.getLength());
 				String signature = node.getName().getFullyQualifiedName();
 				
 				methods.add(new MethodLimit(signature, startLine, endLine));
				
				return true;
			}
		});
	}
	
	public List<MethodLimit> getMethodLimits() {
		return Collections.unmodifiableList(methods);
	}
	
}
