package Test;


import static org.junit.Assert.*;
import org.junit.Test;

import JPanels.PanelConfiguracion;

public class TestEncriptacion {

	@Test
	public void test() {
		PanelConfiguracion test = new PanelConfiguracion();
		String stringEncriptada = test.encriptar("prueba");
		String stringDesencriptada = test.desencriptar(stringEncriptada);
		assertEquals(stringDesencriptada, stringEncriptada);
	}
}