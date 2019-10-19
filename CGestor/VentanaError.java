import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaError extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaError(String mensaje) {
		this.setTitle("ERROR");
		this.setSize(200, 100);
		this.setLocation(100, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.add(new JPanelVentanaError(mensaje));
		this.setVisible(true);
	}
}

class JPanelVentanaError extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanelVentanaError(String mensaje){
		JLabel texto = new JLabel(mensaje);
		this.add(texto);
	}
}