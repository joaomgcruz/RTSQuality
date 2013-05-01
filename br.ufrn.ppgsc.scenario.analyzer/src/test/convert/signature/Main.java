package test.convert.signature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class Main {
	
	private ArrayList<Class<?>> getAllClasses() throws JavaModelException {

		 ArrayList<Class<?>> classList = new ArrayList<Class<?>>();

		 IWorkspace workspace = ResourcesPlugin.getWorkspace();
		 IWorkspaceRoot root = workspace.getRoot();
		 IProject[] projects = root.getProjects();

		 for (IProject project : projects) {
		  IJavaProject javaProject = JavaCore.create(project);
		  IPackageFragment[] packages = javaProject.getPackageFragments();

		  for (IPackageFragment myPackage : packages) {
		   IClassFile[] classes = myPackage.getClassFiles();

		   for (IClassFile myClass : classes) {
		    classList.add(myClass.getClass());
		   }
		  }
		 }

		    return classList;
		}
	
	public static String getStandartMethodSignature(Method method) {
		StringBuffer result = new StringBuffer();
		
		result.append(method.getDeclaringClass().getName());
		result.append(".");
		result.append(method.getName());
		result.append("(");
		
		for (Class<?> cls : method.getParameterTypes()) {
			result.append(cls.getCanonicalName());
			result.append(",");
		}
		
		if (result.charAt(result.length() - 1) == ',')
			result.deleteCharAt(result.length() - 1);
		
		return result + ")";
	}
	
	public void teste(Map<String, Integer> x[][], int[] a, double b[][][]) {
		
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("HelloWorld");
		project.open(null);

		IJavaProject javaProject = JavaCore.create(project);
		IType lwType = javaProject.findType("HelloWorld");
		ICompilationUnit iCu = lwType.getCompilationUnit();
		System.out.println(iCu.getSource()); 
		
		
		for (Class<?> cls : new Main().getAllClasses()) {
			System.out.println(cls.getName());
		}
		
		
		System.out.println(int[][].class.getCanonicalName());
		System.out.println(Object[].class.getCanonicalName());
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		for (Method m : Main.class.getDeclaredMethods()) {
			System.out.println("M: " + m.getName());
			
			for (Class<?> cls : m.getParameterTypes()) {
				System.out.println("\tP: " + cls.getCanonicalName() + " | " + cls.getName());
			}
		}
		
		System.out.println(map.getClass().getCanonicalName());
		
		System.out.println(int[][].class.getName());
		System.out.println(int[].class.getName());
		System.out.println(char[].class.getName());
		System.out.println(int.class.getName());
		System.out.println(Object[].class.getName());
		
	}
	
}
