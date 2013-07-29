package br.ufrn.taskanalyser.framework.miner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.backhoe.ChangedAssetsMinerUtil;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.MethodLimit;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.MethodLimitBuilder;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedMethod;

public class ProjectUpdates {
	private List<ClassUpdates> classUpdates;
	
	public ProjectUpdates() {
		classUpdates = new ArrayList<ClassUpdates>();
	}
	
	public Collection<UpdatedMethod> getUpdatedMethods(long rev1, long rev2) {
		Collection<UpdatedMethod> updatedMethods = new ArrayList<UpdatedMethod>();
		for (ClassUpdates classUpdate : classUpdates) {
			List<UpdatedLine> removedLines = new ArrayList<UpdatedLine>(); 
			List<UpdatedLine> addedLines = new ArrayList<UpdatedLine>();
			for (UpdatedLine updatedLine : classUpdate.getChangedLines()) {
				if(updatedLine.getLineNumber()<0){
					updatedLine.setRevision(rev1);
					removedLines.add(updatedLine);
				}
				else{
					updatedLine.setRevision(rev2);
					addedLines.add(updatedLine);
				}
			}
			List<MethodLimit> oldLimits = (new MethodLimitBuilder(classUpdate.getClassSourceCode1())).getMethodLimits();
			List<MethodLimit> newLimits = (new MethodLimitBuilder(classUpdate.getClassSourceCode2())).getMethodLimits();
			updatedMethods.addAll(ChangedAssetsMinerUtil.filterChangedMethods(oldLimits, removedLines));
			updatedMethods.addAll(ChangedAssetsMinerUtil.filterChangedMethods(newLimits, addedLines));
		}
		return updatedMethods;
	}

	public List<ClassUpdates> getClassUpdates() {
		return classUpdates;
	}

	public void setClassUpdates(List<ClassUpdates> classUpdates) {
		this.classUpdates = classUpdates;
	}

}
