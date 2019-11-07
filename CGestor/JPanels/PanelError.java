package JPanels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;

public class PanelError extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelError(String mensaje) {
		setLayout(null);
		
		JLabel lblMensajeDeError = new JLabel("Mensaje de error:");
		lblMensajeDeError.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblMensajeDeError.setBounds(31, 27, 162, 19);
		add(lblMensajeDeError);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(41, 58, 357, 111);
		textPane.setText(mensaje);
		textPane.setEditable(false);
		add(textPane);

	}
}
