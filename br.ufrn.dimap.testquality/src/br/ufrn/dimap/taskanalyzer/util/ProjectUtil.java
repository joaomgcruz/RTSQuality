package br.ufrn.dimap.taskanalyzer.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.JavaElementInfo;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Test;

import br.ufrn.dimap.taskanalyzer.history.SVNConfig;
import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.data.TestCoverageMapping;
import br.ufrn.dimap.testtracker.util.FileUtil;

public class ProjectUtil {

	public static ClassLoader getIProjectClassLoader(IProject iProject) {
		try {
			IJavaProject iJavaProject = JavaCore.create(iProject);
			String[] classPaths = JavaRuntime.computeDefaultRuntimeClassPath(iJavaProject);
			URL[] urls = new URL[classPaths.length];
			for (int i = 0; i < classPaths.length; i++) {
				urls[i] = (new File(classPaths[i])).toURL();
				//urls[classPaths.length] = (new File("<project path>"+"<build path>"+"<package path>"+"<class name>"+".class")).toURL(); //TODO: Estudar porque não foi possível executar o test recem adicionado, será que foi adicionado no classloader de forma inforreta ou incompleta?
			}
			return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static IProject getIProject(SVNConfig sVNConfig, Revision revision) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(sVNConfig.getProjectPath().substring(1)+"_"+revision.getId());
	}
	
	public static Set<String> getAllTestClasses(IProject iProject) throws JavaModelException {
		Set<String> testClasses = new HashSet<String>(0);
		for(IPackageFragment iPackageFragment : JavaCore.create(iProject).getPackageFragments()) {
			if(iPackageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				for(ICompilationUnit iCompilationUnit : iPackageFragment.getCompilationUnits()) {
					for(IType iType : iCompilationUnit.getTypes()) {
						if(isTestClass(iType))
							testClasses.add(iType.getFullyQualifiedName());
					}
				}
			}
		}
//		Set<String> testClasses = new HashSet<String>(0);
//		for(IPackageFragmentRoot iPackageFragmentRoot : JavaCore.create(iProject).getAllPackageFragmentRoots()) {
//			if(iPackageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE)
//				testClasses.addAll(getTestClasses(iPackageFragmentRoot));
//		}
		return testClasses;
	}
	
	private static Set<String> getTestClasses(IJavaElement iJavaElement) {
		Set<String> testClasses = new HashSet<String>();
		Object info = JavaModelManager.getJavaModelManager().getInfo(iJavaElement);
		if(info != null) {
			JavaElementInfo javaElementInfo = ((JavaElementInfo) info);
			for(IJavaElement child : javaElementInfo.getChildren()) {
				if(child instanceof ICompilationUnit){
					try {
						for(IType iType : ((ICompilationUnit) child).getTypes()) {
							if(isTestClass(iType))
								testClasses.add(iType.getFullyQualifiedName());
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
				}
				else
					testClasses.addAll(getTestClasses(child));
			}
		}
		return testClasses;
	}
	
	public static String getAClass(IProject iProject) throws JavaModelException {
		String aClass = null;
		for(IPackageFragmentRoot iPackageFragmentRoot : JavaCore.create(iProject).getAllPackageFragmentRoots()) {
			if(iPackageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
				aClass = getAClass(iPackageFragmentRoot);
				if(aClass != null)
					return aClass;
			}
		}
		return aClass;
	}
	
	private static String getAClass(IJavaElement iJavaElement) {
		String aClass = null;
		Object info = JavaModelManager.getJavaModelManager().getInfo(iJavaElement);
		if(info != null) {
			JavaElementInfo javaElementInfo = ((JavaElementInfo) info);
			for(IJavaElement child : javaElementInfo.getChildren()) {
				if(child instanceof ICompilationUnit){
					try {
						for(IType iType : ((ICompilationUnit) child).getTypes()) {
							aClass = iType.getFullyQualifiedName();
							if(aClass != null)
								return aClass;
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
				}
				else {
					aClass = getAClass(child);
					if(aClass != null)
						return aClass;
				}
			}
		}
		return aClass;
	}

	private static boolean isTestClass(IType iType) throws JavaModelException {
		for(IMethod iMethod : iType.getMethods()){
			for(IAnnotation iAnnotation : iMethod.getAnnotations()){
				IAnnotation teste = iMethod.getAnnotation(Test.class.getCanonicalName());
				IAnnotation arrobaTeste = iMethod.getAnnotation(Test.class.getSimpleName());
				if(iAnnotation.equals(arrobaTeste) || iAnnotation.equals(teste))
					return true;
			}
		}
		return false;
	}
	
	public static TestCoverageMapping getTestCoverageMapping(IProject iProject, String testCoverageMappingName) {
		try{
			String testClass = ProjectUtil.getAClass(iProject);
			ClassLoader iProjectClassLoader = ProjectUtil.getIProjectClassLoader(iProject);
			String loadFileDirectory = TestUtil.getSaveFileDirectory(iProjectClassLoader, testClass);
			return (TestCoverageMapping) FileUtil.loadFileToObject(loadFileDirectory, testCoverageMappingName, ".tcm");
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ClassCastException cce) {
			cce.printStackTrace();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

}
