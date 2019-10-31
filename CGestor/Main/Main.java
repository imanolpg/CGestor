package Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import JPanels.PanelConfiguracion;
import JPanels.PanelEmail;
import JPanels.PanelGeneral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Clase principal del programa
 * @author imanol
 *
 */
public class Main {

	// TODO buscar cosas en tabla
	// TODO encriptar credenciales
	// TODO log de cambios de la tabla

	static Canvas canvas;
	static PanelGeneral panelGeneral;
	static PanelEmail panelEmail;
	static PanelConfiguracion panelConfiguracion;
	static ArrayList<String[]> actualizaciones;
	
	static public Logger log;
	
	public static void main(String[] args) {
		log = Logger.getLogger("logger"); // creo el Logger
		try {
			log.addHandler(new FileHandler("privado/log.xml"));
		} catch (Exception e) {
			System.err.println("Error al crear el handler: " + e.getMessage());
		}
		panelGeneral = new PanelGeneral(); //creo el panel general
		panelEmail = new PanelEmail(); //creo el panel email
		panelConfiguracion = new PanelConfiguracion(); //creo el panel de configuracion
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
		System.out.println("D: " + datoAEstudiar + "\tValor: " + resultadoBuscado + "\tAccion: " + accion);
		for (String[] fila : PanelGeneral.tabla.getDatos()){
			if (accion.equals("todos")) {
				listaDestinatarios.add(fila[Arrays.asList(PanelGeneral.tabla.getColumnas()).indexOf("email")]);
			} else if (fila[datoAEstudiar].equals(resultadoBuscado) && accion.equals("igual") || !fila[datoAEstudiar].equals(resultadoBuscado) && accion.equals("diferente")) {
				listaDestinatarios.add(fila[Arrays.asList(PanelGeneral.tabla.getColumnas()).indexOf("email")]);
				PanelEmail.aniadirFamiliaDestinataria(fila);
			}
		}
		for (String g : listaDestinatarios)
			System.out.println(g);
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
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
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
			Transport.send(message);
			log.log(Level.INFO, "Mensaje enviado a: " + destinatario);
			System.out.println("Email enviado a: " + destinatario);
		} catch (MessagingException e) {
			System.err.println("Error al enviar el email: " + e.getMessage());
			log.log(Level.WARNING, "Error al enviar mensaje a: " + destinatario);
		}
	}
}
