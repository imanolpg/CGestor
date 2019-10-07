import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Main {
	
	//TODO personalizar la tabla
	

	static Canvas canvas;

	public static void main(String[] args) {
		createWindow();
		
	}

	public static void createWindow() {
		canvas = new Canvas("CGestor");

		JButton buttonGeneral, buttonEmail, buttonConfiguracion;
		buttonGeneral = new JButton("General");
		buttonGeneral.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"General\" pulsado");
				canvas.add(getGeneralPanel());
			}	
		});
		buttonEmail = new JButton("Email");
		buttonEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Email\" pulsado");
				canvas.add(getEmailPanel());
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
		buttonPanel.add(buttonGeneral);
		buttonPanel.add(buttonEmail);
		buttonPanel.add(buttonConfiguracion);
		

		canvas.add(getGeneralPanel(), BorderLayout.CENTER);

		canvas.add(buttonPanel, BorderLayout.NORTH);

		canvas.setVisible(true);
	}
	
	public static JPanel getGeneralPanel() {
		
		JPanel general;
		
		Object[][] datos = { { "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) }
		};

		String[] nombresColumnas = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };

		JTable tabla = new JTable(datos, nombresColumnas);
		
		JScrollPane scrollPanel = new JScrollPane(tabla);
		scrollPanel.setSize(canvas.getWidth() / 2, canvas.getHeight());
		tabla.setFillsViewportHeight(true);

		general = new JPanel();
		general.add(scrollPanel);
		
		
		JButton botonActualizar;
		botonActualizar = new JButton("Actualizar");
		
		general.add(botonActualizar);
		
		return(general);
		
	}

	public static JPanel getEmailPanel() {
		
		JPanel email;
		email = new JPanel();
		email.setLayout(new BorderLayout());
		
		JTextField texto;
		texto = new JTextField();
		
		JScrollPane scrollPanelTexto = new JScrollPane(texto);
		email.add(scrollPanelTexto, BorderLayout.WEST);
		
		JPanel panelDerecho;
		panelDerecho = new JPanel();
		
		JTextField titulo1;
		titulo1 = new JTextField("Criterio");
		panelDerecho.add(titulo1, BorderLayout.CENTER);
		
		JPanel organizadorCriterio;
		organizadorCriterio = new JPanel();
		Choice chooseCriterio;
		chooseCriterio = new Choice();
		organizadorCriterio.add(chooseCriterio);
		Choice chooseComparador;
		chooseComparador = new Choice();
		chooseComparador.add("igual");
		chooseComparador.add("diferente");
		chooseComparador.add("menor que");
		chooseComparador.add("mayor que");
		organizadorCriterio.add(chooseCriterio);
		JTextField textoCompara;
		textoCompara = new JTextField();
		organizadorCriterio.add(textoCompara);
		panelDerecho.add(organizadorCriterio);
		
		Object[][] datos = {};

		String[] nombresColumnas = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };

		JTable destinatarios = new JTable(datos, nombresColumnas);
		
		JScrollPane scrollPanelListaEnviado;
		scrollPanelListaEnviado= new JScrollPane(destinatarios);
		destinatarios.setFillsViewportHeight(true);
		panelDerecho.add(scrollPanelListaEnviado);
		
		email.add(panelDerecho, BorderLayout.WEST);
		
		JButton botonEnviar;
		botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Enviar\" pulsado");
			}
			
		});
		email.add(botonEnviar);
		
		return(email);
	}
	

}
