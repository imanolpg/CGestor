package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import JPanels.PanelConfiguracion;

public class XlsToDb {

	@Test
	public void test() {
		try{
		PanelConfiguracion.deXLSaBD("privado/exel.xls");
		} catch (Exception e) {
			System.out.println("Error: " + e.getStackTrace());
		}
	}

}
