package br.ufrn.dimap.rtquality.util;

import java.util.HashSet;
import java.util.Set;

import br.ufrn.dimap.ttracker.data.TestCoverage;

public class MathUtil<T> {

	public static <T> Set<T> intersection(Set<T> A, Set<T> B) {
		if(A == null || B == null)
			return new HashSet<T>(0);
		Set<T> auxB = new HashSet<T>(B);
		Set<T> intersection = new HashSet<T>(A.size()+B.size());
		for (T testA : A) {
			T aux = null;
			for(T testB : auxB) {
				if(testA.equals(testB)) {
					aux = testB;
					intersection.add(aux);
					break;
				}
			}
			if(aux != null)
				auxB.remove(aux);
		}
		return new HashSet<T>(intersection);
	}
	
	public static <T> Set<T> union(Set<T> A, Set<T> B) {
		Set<T> union = new HashSet<T>();
		union.addAll(A);
		union.addAll(B);
		return union;
	}
	
}
