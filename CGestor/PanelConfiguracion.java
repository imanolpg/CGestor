import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class PanelConfiguracion extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField email;
	private static JPasswordField clave;

	/**
	 * Create the panel.
	 */
	public PanelConfiguracion() {
		setLayout(null);
		
		JLabel lblConfiguracin = new JLabel("Configuración");
		lblConfiguracin.setBounds(392, 34, 156, 42);
		lblConfiguracin.setFont(new Font(lblConfiguracin.getFont().getName(), Font.PLAIN, 21));
		add(lblConfiguracin);
		
		JLabel lblDireccinDeCorreo = new JLabel("Dirección de correo electrónico ");
		lblDireccinDeCorreo.setBounds(124, 152, 249, 24);
		lblDireccinDeCorreo.setFont(new Font(lblDireccinDeCorreo.getFont().getName(), Font.PLAIN, 15));
		add(lblDireccinDeCorreo);
		
		email = new JTextField();
		email.setBounds(418, 149, 256, 33);
		add(email);
		email.setColumns(10);
		
		JLabel lblContraseaCorreoElectrnico = new JLabel("Contraseña del correo electrónico");
		lblContraseaCorreoElectrnico.setBounds(124, 256, 249, 16);
		lblContraseaCorreoElectrnico.setFont(new Font(lblContraseaCorreoElectrnico.getFont().getName(), Font.PLAIN, 15));
		add(lblContraseaCorreoElectrnico);
		
		clave = new JPasswordField();
		clave.setBounds(418, 249, 256, 33);
		add(clave);
		clave.setColumns(10);
	}
	
	 /**
	  * Devuelve el correo
	  * @return el correo
	  */
	public String getEmail() {
		return(email.getText());
	}
	
	/**
	 * Devuelve la contraseña del correo
	 * @return contraseña del correo
	 */
	@SuppressWarnings("deprecation")
	public String getContrasenia() {
		return(clave.getText());
	}

}
