import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
	
	//TODO log
	//TODO email
	
	
	static Canvas canvas;
	static JPanel panelGeneral;
	static JPanel panelEmail;
	static ArrayList<String[]> actualizaciones;
	
	public static FileHandler fh;
	public static Logger logger;

	public static void main(String[] args) {
		panelGeneral = new panelGeneral();
		panelEmail = new panelEmail();
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
				panelEmail.setVisible(true);
			}	
		});
		buttonConfiguracion = new JButton("Configuracion");
		buttonConfiguracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Configuracion\" pulsado");	
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
	 * @return String[] con los destinatarios
	 */
	public static String[] getDestinatarios(){
		String[] destinatarios = {};
		return(destinatarios);
	}
	
	/**
	 * Envia un email al destinatario
	 * @param email Email a enviar
	 * @param destinatario Destinatario al que se le va a enviar
	 */
	public static void enviarEmail(String email, String destinatario) {
		//TODO hacer esta funcion
	}
	
	/**
	 * Crea un log de todos los cambios
	 */
	public static void crearLog() {
		try {
			fh = new FileHandler("ArchivoLog.log");
			logger.addHandler(fh);
			SimpleFormatter formateador = new SimpleFormatter();
			fh.setFormatter(formateador);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}	
}
