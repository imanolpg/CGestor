package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;

import JPanels.PanelError;

/**
 * Ventana de error que se lanza cuando hay un error
 * @author imanol
 *
 */
public class VentanaError extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaError(String mensaje) {
		this.setTitle("ERROR");
		JPanel panel = new PanelError(mensaje);
		this.add(panel);
		this.setSize(450, 300);
		this.setLocation(100, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
}