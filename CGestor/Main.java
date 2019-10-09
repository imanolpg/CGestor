import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.sql.*;
import java.util.ArrayList;

public class Main {
	
	//TODO personalizar la tabla
	
	static Canvas canvas;
	static JPanel panelGeneral;
	static JPanel panelEmail;
	static ArrayList<String[]> actualizaciones;

	public static void main(String[] args) {
		panelGeneral = new panelGeneral();
		panelEmail = new panelEmail();
		createWindow();
		
	}

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
		
	}
	
}
