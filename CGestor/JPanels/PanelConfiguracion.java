package JPanels;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.logging.Level;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Main.BD;
import Main.Main;
import Main.VentanaError;

import javax.swing.JButton;

public class PanelConfiguracion extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CLAVE_ENCRIPTACION = "2dede3dr";

	private static JTextField email;
	private static JPasswordField clave;
	
	private static final String pathCredenciales = "privado/credenciales.txt"; 
	private static JTextField rutaBaseDeDatos;
	private JTextField rutaCSV;

	/**
	 * Create the panel.
	 */
	public PanelConfiguracion() {
		setLayout(null);

		JLabel lblConfiguracin = new JLabel("Configuración");
		lblConfiguracin.setBounds(418, 34, 156, 42);
		lblConfiguracin.setFont(new Font(lblConfiguracin.getFont().getName(), Font.PLAIN, 21));
		add(lblConfiguracin);

		JLabel lblDireccinDeCorreo = new JLabel("Dirección de correo electrónico");
		lblDireccinDeCorreo.setBounds(124, 152, 249, 24);
		lblDireccinDeCorreo.setFont(new Font(lblDireccinDeCorreo.getFont().getName(), Font.PLAIN, 15));
		add(lblDireccinDeCorreo);

		email = new JTextField();
		email.setBounds(418, 149, 256, 33);
		add(email);
		email.setColumns(10);

		JLabel lblContraseaCorreoElectrnico = new JLabel("Contraseña del correo electrónico");
		lblContraseaCorreoElectrnico.setBounds(124, 215, 249, 16);
		lblContraseaCorreoElectrnico
				.setFont(new Font(lblContraseaCorreoElectrnico.getFont().getName(), Font.PLAIN, 15));
		add(lblContraseaCorreoElectrnico);

		clave = new JPasswordField();
		clave.setBounds(418, 208, 256, 33);
		add(clave);
		clave.setColumns(10);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(438, 429, 117, 33);
		btnGuardar.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCredenciales(email.getText(), clave.getText());
				BD.pathBD = "jdbc:sqlite:privado/" + rutaBaseDeDatos.getText();
				BD.cargarTabla();
				System.out.println("Credenciales guardadas");
			}

		});
		add(btnGuardar);
		
		JLabel lblArchivoExcell = new JLabel("Nombre de la base de datos");
		lblArchivoExcell.setBounds(124, 282, 249, 24);
		lblArchivoExcell.setFont(new Font(lblArchivoExcell.getFont().getName(), Font.PLAIN, 15));
		add(lblArchivoExcell);
		
		rutaBaseDeDatos = new JTextField();
		rutaBaseDeDatos.setBounds(418, 275, 256, 33);
		add(rutaBaseDeDatos);
		rutaBaseDeDatos.setColumns(10);
		rutaBaseDeDatos.setText("x.db");
		
		JLabel lblRutaArchivoCsv = new JLabel("Ruta archivo CSV");
		lblRutaArchivoCsv.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblRutaArchivoCsv.setBounds(124, 339, 197, 33);
		add(lblRutaArchivoCsv);
		
		rutaCSV = new JTextField();
		rutaCSV.setBounds(418, 339, 256, 29);
		add(rutaCSV);
		rutaCSV.setColumns(10);
		
		
		JButton btnConvertir = new JButton("Convertir");
		btnConvertir.setBounds(694, 345, 117, 24);
		add(btnConvertir);
		btnConvertir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BD.deCsvABd("privado/" + rutaCSV.getText());			
			}
			
		});
		
		cargarCredenciales();
	}

	/**
	 * Función que se llama al empezar el programa para actualizar las credenciales
	 */
	public void cargarCredenciales() {
		String[] credenciales = recuperarCredenciales();
		if (credenciales != null) {
			email.setText(credenciales[0]);
			clave.setText(credenciales[1]);
			rutaBaseDeDatos.setText(credenciales[2]);
		}
		Main.log.log(Level.INFO, "Credenciales cargadas");
		System.out.println("Credenciales cargadas");
	}

	/**
	 * Escribe las credenciales a un fichero "credenciales.txt"
	 * 
	 * @param correo
	 * @param clave
	 */
	public void guardarCredenciales(String correo, String clave) {
		try {
			PrintStream fs = new PrintStream(pathCredenciales);
			fs.println(correo);
			fs.println(clave);
			fs.print(rutaBaseDeDatos.getText());
			Main.log.log(Level.INFO, "Credenciales guardadas");
			fs.close();
		} catch (Exception e) {
			Main.log.log(Level.WARNING, "Error al cargar credenciales");
			System.err.println("Error al escribir las credenciales al fichero: " + e.getMessage());
			new VentanaError("Error al crear el archivo de credenciales: " + e.getMessage());
		}
	}

	/**
	 * Recupera los datos de acceso del correo
	 * 
	 * @return array con los datos de acceso. [0]: correo; [1]:contraseña
	 */
	public String[] recuperarCredenciales() {
		String[] credenciales = new String[3];
		String lineaActual;

		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(pathCredenciales));
			int x = 0;
			while ((lineaActual = br.readLine()) != null) {
				credenciales[x] = lineaActual;
				x = x + 1;
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
			new VentanaError("Error al leer el archivo de credenciales: " + e.getMessage());
			return null;
		}
		return credenciales;

	}

	/**
	 * Devuelve el correo
	 * 
	 * @return el correo
	 */
	public String getEmail() {
		return (email.getText());
	}

	/**
	 * Devuelve la contraseña del correo
	 * 
	 * @return contraseña del correo
	 */
	@SuppressWarnings("deprecation")
	public String getContrasenia() {
		return (clave.getText());
	}

	/**
	 * Desencripta el texto que le pasamos
	 * 
	 * @param texto encriptado
	 * @return texto desencriptado
	 */
	public String desencriptar(String textoADesencriptar) {
		String textoPlano = "";
		try {
			SecretKeySpec skeyspec = new SecretKeySpec(CLAVE_ENCRIPTACION.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted = cipher.doFinal(textoADesencriptar.getBytes());
			textoPlano = new String(decrypted);

		} catch (Exception e) {
			System.err.println("Error al desencriptar: " + e.getMessage());
			new VentanaError("Error al desencriptar: " + e.getMessage());
		}
		System.out.println("Desencriptado: " + textoPlano);
		return textoPlano;
	}

	/**
	 * Encripta el texto que le pasamos
	 * 
	 * @param texto sin encriptar
	 * @return texto encriptado
	 */
	public String encriptar(String textoAEncriptar) {
		String textoEncriptado = "";
		try {
			SecretKeySpec skeyspec = new SecretKeySpec(CLAVE_ENCRIPTACION.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted = cipher.doFinal(textoAEncriptar.getBytes());
			textoEncriptado = new String(encrypted);

		} catch (Exception e) {
			System.err.println("Error al encriptar: " + e.getMessage());
			new VentanaError("Error al encriptar: " + e.getMessage());
		}
		System.out.println("Encriptado: " + textoEncriptado);
		return textoEncriptado;
	}
	
	/**
	 * Devuelve la ruta de la base de datos
	 * @return ruta de la base de datos
	 */
	public static String getRutaBD() {
		return("jdbc:sqlite:privado/" + rutaBaseDeDatos.getText());
	}
}
