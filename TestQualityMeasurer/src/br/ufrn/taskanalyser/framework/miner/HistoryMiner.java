package br.ufrn.taskanalyser.framework.miner;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.tmatesoft.svn.core.SVNException;

import br.ufrn.testtracker.plugin.actions.SampleAction;

public class HistoryMiner {
	
	public static void main(String[] args){
		try {
			SVNConfig sVNConfig = new SVNConfig("http://scenario-analyzer.googlecode.com/svn","/trunk/Calculadora","","");
			for (String string : minerModifications(sVNConfig,93,94)) {
				System.out.println(string);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SVNException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkoutProject(SVNConfig sVNConfig, long revision) throws SVNException{
		History history = new History(sVNConfig,ResourcesPlugin.getWorkspace());
		try {
			history.checkoutProject(revision);
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File checkoutFile(SVNConfig sVNConfig, String fileName, long revision) throws SVNException{
		History history = new History(sVNConfig,ResourcesPlugin.getWorkspace());
		return history.checkoutFile(fileName, revision);
	}
	
	public static Set<String> minerModifications(SVNConfig sVNConfig, Integer startRevision, Integer endRevision) throws NumberFormatException, SVNException {
		History history = new History(sVNConfig,ResourcesPlugin.getWorkspace());
		return history.getChangedMethodsSignatures(startRevision,endRevision);
	}

}
