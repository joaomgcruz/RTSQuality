package br.ufrn.dimap.sample.tests;

import org.junit.Assert;
import org.junit.Test;

import br.ufrn.dimap.sample.M1;

public class T1 {

	@Test
	public void t1() {
		System.out.println("T1.t1()");
		M1.m1();
		Assert.assertTrue(true);
	}
	
}
