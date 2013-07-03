package br.ufrn.taskanalyser.framework.miner;

import java.util.Set;

import org.tmatesoft.svn.core.SVNException;

public class HistoryMiner {
	
	public static void main(String[] args){
		try {
			for (String string : minerModifications("http://scenario-analyzer.googlecode.com/svn","/trunk/Calculadora","","","93","97")) {
				System.out.println(string);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SVNException e) {
			e.printStackTrace();
		}
		 
	}
	
	public static Set<String> minerModifications(String svnUrl, String projectPath, String username, String password, String startRevision, String endRevision) throws NumberFormatException, SVNException {
		History history = new History(svnUrl,projectPath,username,password,Long.parseLong(startRevision),Long.parseLong(endRevision));
		return history.getChangedMethodsSignatures();
	}

}
