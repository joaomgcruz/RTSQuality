package exemplo2;

import org.junit.Test;

public class Classe1 {

	@Test
	public void chamaClasse3() {
		Classe2 c2 = new Classe2();
		c2.metodo();
	}
}
