package JPanels;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Choice;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import Main.BD;
import Main.Main;

public class PanelEmail extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextField asuntoEmail;
	private static ArrayList<String[]> familiasDestinatarias = new ArrayList<String[]>();

	/**
	 * Create the panel.
	 */
	public PanelEmail() {
		setLayout(null);

		JLabel label = new JLabel("Criterio:");
		label.setBounds(724, 25, 82, 27);
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
		add(label);

		Choice choice = new Choice();
		choice.setBounds(553, 76, 130, 36);
		String[] columnas = BD.getColumnasTabla();
		for (String criterio : columnas) {
			choice.add(criterio);
		}
		add(choice);

		Choice opcionColumnas = new Choice();
		opcionColumnas.setBounds(724, 76, 102, 36);
		for (String nuevaOpcion : PanelGeneral.tabla.getColumnas())
			opcionColumnas.add(nuevaOpcion);
		add(opcionColumnas);

		JFormattedTextField comparador = new JFormattedTextField();
		comparador.setBounds(863, 76, 108, 36);
		add(comparador);

		JEditorPane cuerpoEmail = new JEditorPane();

		JScrollPane scrollPane = new JScrollPane(cuerpoEmail, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(84, 122, 399, 336);
		add(scrollPane);

		JLabel label_1 = new JLabel("Destinatarios a los que se va a enviar el correro:");
		label_1.setBounds(534, 183, 437, 38);
		label_1.setFont(new Font(label_1.getFont().getName(), Font.PLAIN, 18));
		add(label_1);

		List list = new List();
		list.setBounds(553, 227, 416, 203);
		add(list);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(752, 442, 82, 29);
		btnEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnEnviar.setText("Enviando mensaje..."); //no funciona
				System.out.println("Boton \"Enviar\" pulsado");
				System.out.println(familiasDestinatarias.size());
				for (String[] destinatario : familiasDestinatarias) {
					String asunto = reestructuraEmail(asuntoEmail.getText(), destinatario);
					String correo = reestructuraEmail(cuerpoEmail.getText(), destinatario);
					Main.enviarEmail(asunto, correo, destinatario[BD.CORREO]);
				}
				btnEnviar.setText("Enviar");
			}

		});
		add(btnEnviar);

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(704, 146, 117, 29);
		btnSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Seleccionar\" pulsado");
				list.removeAll();
				eliminarFamiliasDestinatarias();
				String[] listaDestinatarios = Main.getDestinatarios(choice.getSelectedIndex(), comparador.getText(),
						opcionColumnas.getSelectedItem());
				for (String destinatario : listaDestinatarios) {
					list.add(destinatario);
				}
			}

		});
		add(btnSeleccionar);

		asuntoEmail = new JTextField();
		asuntoEmail.setBounds(84, 40, 399, 43);
		add(asuntoEmail);
		asuntoEmail.setColumns(10);

		JLabel lblInfo = new JLabel("Asunto del correo:");
		lblInfo.setBounds(84, 6, 165, 33);
		lblInfo.setFont(new Font(lblInfo.getFont().getName(), Font.PLAIN, 15));
		add(lblInfo);

		JLabel lblInfo1 = new JLabel("Cuerpo del correo:");
		lblInfo1.setBounds(84, 85, 199, 36);
		lblInfo1.setFont(new Font(lblInfo1.getFont().getName(), Font.PLAIN, 15));
		add(lblInfo1);

		Choice choiceAniadir = new Choice();
		choiceAniadir.setBounds(84, 474, 199, 27);
		choiceAniadir.add("Numero Familia");
		choiceAniadir.add("Nombre Familia");
		choiceAniadir.add("Participantes");
		choiceAniadir.add("Tallas");
		choiceAniadir.add("Telefono");
		choiceAniadir.add("Correo");
		choiceAniadir.add("Pagado");
		add(choiceAniadir);

		JButton btnAniadir = new JButton("Insertar");
		btnAniadir.setBounds(335, 474, 108, 27);
		btnAniadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String aAniadir = "";
				System.out.println(choiceAniadir.getSelectedItem());
				switch (choiceAniadir.getSelectedItem()) {
				case "Numero Familia":
					aAniadir = "<<numeroFamilia>>";
					break;
				case "Nombre Familia":
					aAniadir = "<<nombreFamilia>>";
					break;
				case "Participantes":
					aAniadir = "<<participantes>>";
					break;
				case "Tallas":
					aAniadir = "<<tallas>>";
					break;
				case "Telefono":
					aAniadir = "<<telefono>>";
					break;
				case "Correo":
					aAniadir = "<<correo>>";
					break;
				case "Pagado":
					aAniadir = "<<pagado>>";
					break;
				default:
					aAniadir = "";
					break;
				}
				try {
					cuerpoEmail.getDocument().insertString(cuerpoEmail.getCaretPosition(), aAniadir, null);
				} catch (BadLocationException e1) {
					System.err.println("Error al añadir la variable al correo");
				}
			}

		});
		add(btnAniadir);
	}

	/**
	 * Reemplaza las variables del correo genérico con los datos de cada familia
	 * 
	 * @param correo que queremos enviar
	 * @param datos  que queremos utilizar
	 * @return el correo personalizado
	 */
	private static String reestructuraEmail(String correo, String[] datos) {
		correo = correo.replace("<<numeroFamilia>>", datos[BD.NUMERO_FAMILIA])
				.replace("<<nombreFamilia>>", datos[BD.NOMBRE_FAMILIA])
				.replace("<<participantes>>", datos[BD.PARTICIPANTES]).replace("<<tallas>>", datos[BD.TALLAS])
				.replace("<<telefono>>", datos[BD.TELEFONO]).replace("<<correo>>", datos[BD.CORREO])
				.replace("<<pagado>>", datos[BD.PAGADO]);
		return (correo);
	}

	/**
	 * Añade una nueva familia para enviar el email
	 * 
	 * @param nueva familia
	 */
	public static void aniadirFamiliaDestinataria(String[] familia) {
		familiasDestinatarias.add(familia);
	}

	/**
	 * Elimina todos los datos de la lista de familias destinatarios
	 */
	public static void eliminarFamiliasDestinatarias() {
		familiasDestinatarias = new ArrayList<String[]>();
	}
}
