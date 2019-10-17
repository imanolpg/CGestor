import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {

	// TODO log
	// TODO JUnit
	// TODO boton añadir/borrar nuevo elemento base de datos
	// TODO Cambiar texto boton a enviando
	// TODO hacer que en el .jar se guarden todos los archivos

	static Canvas canvas;
	static PanelGeneral panelGeneral;
	static PanelEmail panelEmail;
	static PanelConfiguracion panelConfiguracion;
	static ArrayList<String[]> actualizaciones;

	public static void main(String[] args) {
		panelGeneral = new PanelGeneral();
		panelEmail = new PanelEmail();
		panelConfiguracion = new PanelConfiguracion();
		panelConfiguracion.cargarCredenciales();
		createWindow();
	}

	/**
	 * Crea el JPanel principal y lanza la app
	 */
	public static void createWindow() {
		canvas = new Canvas("CGestor");

		JButton buttongeneralPanelPanel, buttonemailPanel, buttonConfiguracion;
		buttongeneralPanelPanel = new JButton("General");
		buttongeneralPanelPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"General\" pulsado");
				canvas.add(panelGeneral, BorderLayout.CENTER);
				panelEmail.setVisible(false);
				panelConfiguracion.setVisible(false);
				panelGeneral.setVisible(true);
			}
		});
		buttonemailPanel = new JButton("Email");
		buttonemailPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Email\" pulsado");
				canvas.add(panelEmail, BorderLayout.CENTER);
				panelGeneral.setVisible(false);
				panelConfiguracion.setVisible(false);
				panelEmail.setVisible(true);
			}
		});
		buttonConfiguracion = new JButton("Configuracion");
		buttonConfiguracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Configuracion\" pulsado");
				canvas.add(panelConfiguracion, BorderLayout.CENTER);
				panelGeneral.setVisible(false);
				panelEmail.setVisible(false);
				panelConfiguracion.setVisible(true);
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buttongeneralPanelPanel);
		buttonPanel.add(buttonemailPanel);
		buttonPanel.add(buttonConfiguracion);

		canvas.add(panelGeneral, BorderLayout.CENTER);

		canvas.add(buttonPanel, BorderLayout.NORTH);

		canvas.setVisible(true);
	}

	/**
	 * Selecciona los destinatarios que cumplen el criterio
	 * 
	 * @return String[] de los correos de los destinatarios
	 */
	public static String[] getDestinatarios(int datoAEstudiar, String resultadoBuscado, String accion) {
		ArrayList<String> listaDestinatarios = new ArrayList<String>();
		for (Object[] familia : BD.getDatosTabla()){
			if (familia[datoAEstudiar].toString().equals(resultadoBuscado) && accion.equals("igual") ||
				!familia[datoAEstudiar].toString().equals(resultadoBuscado) && accion.equals("diferente")) {
				listaDestinatarios.add(familia[BD.CORREO].toString());
				PanelEmail.aniadirFamiliaDestinataria((String[] )familia);
			}
		}
		String[] aDevolver = new String[listaDestinatarios.size()];
		for (int x=0; x<listaDestinatarios.size(); x++) {
			aDevolver[x] = listaDestinatarios.get(x);
			System.out.println(listaDestinatarios.get(x));
		}
		return (aDevolver);
	}

	/**
	 * Envia un email al destinatario
	 * 
	 * @param asunto       del correo
	 * @param email        a enviar
	 * @param destinatario al que se le va a enviar
	 */
	public static void enviarEmail(String asunto, String email, String destinatario) {

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(panelConfiguracion.getEmail(), panelConfiguracion.getContrasenia());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(asunto);
			message.setText(email);

			// send message
			Transport.send(message);
			System.out.println("Email enviado a: " + destinatario);
		} catch (MessagingException e) {
			System.err.println("Error al enviar el email: " + e.getMessage());
		}
	}
	
	/**
	 * Escribe un log de todos los cambios por seguridad
	 * @param id localizador de la familia
	 * @param campo a cambiar
	 * @param valorNuevo valor introducido
	 * @param valorViejo valor anterior
	 */
	public static void crearLog(String id, String campo, String valorNuevo, String valorViejo) {
		try {
			PrintStream fs = new PrintStream("Log.log");
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			fs.println("(" + formatoFecha.format(now) + ") CAMBIO id:" + id + ", campo:" + campo + ", valorViejo:" + valorViejo + ", valorNuevo:" + valorNuevo);
			fs.close();
			System.out.println("Log guardado correctamente");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR al escribir al fichero: " + e.getMessage());
		}
	}
}
