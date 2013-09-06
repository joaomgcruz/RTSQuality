package exemplo;

import org.junit.Assert;
import org.junit.Test;

public class Classe1_2 {
	
	@Test
	public void chamaMethodClasse2_2() {
		Classe2_2 c2_2 = new Classe2_2();
		c2_2.chamarMetodoClasse3();
		Classe2_3 c2_3 = new Classe2_3();
		c2_3.chamarMetodoClasse3();
		Assert.assertTrue(true);
	}

}
