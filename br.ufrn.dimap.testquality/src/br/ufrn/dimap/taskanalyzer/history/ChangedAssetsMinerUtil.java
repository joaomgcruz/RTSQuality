package br.ufrn.dimap.taskanalyzer.history;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangedAssetsMinerUtil {
	
	public static Collection<UpdatedMethod> filterChangedMethods(List<MethodLimit> limits, List<UpdatedLine> lines) {
		Map<String, UpdatedMethod> result = new HashMap<String, UpdatedMethod>();
		
		for (UpdatedLine l : lines) {
			for (MethodLimit m : limits) {
				if (l.getLineNumber() >= m.getStartLine() && l.getLineNumber() <= m.getEndLine()) {
					UpdatedMethod mu = result.get(m.getSignature());
					
					if (mu == null) {
						mu = new UpdatedMethod(m);
						result.put(m.getSignature(), mu);
					}
					
					mu.addUpdatedLine(l);
				}
			}
		}
		
		return Collections.unmodifiableCollection(result.values());
	}
	
}
