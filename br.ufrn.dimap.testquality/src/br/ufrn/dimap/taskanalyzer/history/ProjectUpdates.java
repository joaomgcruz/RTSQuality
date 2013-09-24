package br.ufrn.dimap.taskanalyzer.history;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.util.FileUtil;

public class ProjectUpdates {
	private List<ClassUpdates> classUpdates;
	
	public ProjectUpdates() {
		classUpdates = new ArrayList<ClassUpdates>();
	}
	
	public Collection<UpdatedMethod> getUpdatedMethods(Revision rev1, Revision rev2) {
		Collection<UpdatedMethod> updatedMethods = new ArrayList<UpdatedMethod>();
		for (ClassUpdates classUpdate : classUpdates) {
			List<UpdatedLine> removedLines = new ArrayList<UpdatedLine>(); 
			List<UpdatedLine> addedLines = new ArrayList<UpdatedLine>();
			for (UpdatedLine updatedLine : classUpdate.getChangedLines()) {
				if(updatedLine.getLineNumber()<0){
					updatedLine.setRevision(rev1.getId());
					updatedLine.setLineNumber(updatedLine.getLineNumber()*-1);
					removedLines.add(updatedLine);
				}
				else{
					updatedLine.setRevision(rev2.getId());
					addedLines.add(updatedLine);
				}
			}
			if(!classUpdate.getClassSourceCode1().equals("null")) {
				File oldFile = new File(classUpdate.getClassSourceCode1());
				MethodLimitBuilder mLBOld = new MethodLimitBuilder(FileUtil.loadTextFromFile(oldFile));
				List<MethodLimit> oldLimits = mLBOld.getMethodLimits();
				updatedMethods.addAll(ChangedAssetsMinerUtil.filterChangedMethods(oldLimits, removedLines));
			}
			File newFile = new File(classUpdate.getClassSourceCode2());
			MethodLimitBuilder mLBNew = new MethodLimitBuilder(FileUtil.loadTextFromFile(newFile));
			List<MethodLimit> newLimits = mLBNew.getMethodLimits();
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
