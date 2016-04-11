package br.ufrn.dimap.rtquality.history;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.util.FileUtil;

public class ProjectUpdates {
	private List<ClassUpdates> classUpdates;
	
	public ProjectUpdates() {
		classUpdates = new ArrayList<ClassUpdates>();
	}
	
	public Collection<UpdatedMethod> getUpdatedMethods(Integer rev1, Integer rev2) {
		Collection<UpdatedMethod> updatedMethods = new ArrayList<UpdatedMethod>();
		for (ClassUpdates classUpdate : classUpdates) {
			List<UpdatedLine> removedLines = new ArrayList<UpdatedLine>(); 
			List<UpdatedLine> addedLines = new ArrayList<UpdatedLine>();
			for (UpdatedLine updatedLine : classUpdate.getChangedLines()) {
				if(updatedLine.getLineNumber()<0){
					updatedLine.setRevision(rev1);
					updatedLine.setLineNumber(updatedLine.getLineNumber()*-1);
					removedLines.add(updatedLine);
				}
				else{
					updatedLine.setRevision(rev2);
					addedLines.add(updatedLine);
				}
			}
			if(!classUpdate.getClassSourceCode1().equals("null")) {
				File oldFile = new File(classUpdate.getClassSourceCode1());
				MethodLimitBuilder mLBOld = new MethodLimitBuilder(FileUtil.loadTextFromFile(oldFile));
				List<MethodLimit> oldLimits = mLBOld.getMethodLimits(); //TODO: Tenho que verificar o que acontece
				//TODO: quando removemos um método por completo, pois se o método não existe mais na versão nova, não
				//TODO: há como ele ser coberto pelos testes, quando eu for procurar o método modificado dentro dos
				//TODO: métodos cobertos não o encontrarei então este método ficará na lista do MnC mas na verdade ele está na lista dos Excluidos (E)
				//TODO: de qualquer forma terei que montar a relação de todos os métodos da versão, se algum dos métodos ditos modificados não estiverem nesta relação, significa que foram E (Excluídos)
				updatedMethods.addAll(ChangedAssetsMinerUtil.filterChangedMethods(oldLimits, removedLines));
			}
			if(!classUpdate.getClassSourceCode2().equals("null")) {
				File newFile = new File(classUpdate.getClassSourceCode2());
				MethodLimitBuilder mLBNew = new MethodLimitBuilder(FileUtil.loadTextFromFile(newFile));
				List<MethodLimit> newLimits = mLBNew.getMethodLimits();
				updatedMethods.addAll(ChangedAssetsMinerUtil.filterChangedMethods(newLimits, addedLines));
			}
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
