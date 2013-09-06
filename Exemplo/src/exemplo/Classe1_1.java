package exemplo;

import org.junit.Assert;
import org.junit.Test;

public class Classe1_1 {
	
	@Test
	public void chamaMethodClasse2_1() {
		Classe2_1 c2_1 = new Classe2_1();
		c2_1.chamarMetodoClasse3();
		Assert.assertTrue(true);
	}
	
}
