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
import java.awt.Font;
import javax.swing.JTextField;


public class PanelEmail extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField asuntoEmail;

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
		
		Choice choice_1 = new Choice();
		choice_1.setBounds(724, 76, 102, 36);
		choice_1.add("igual");
		choice_1.add("diferente");
		choice_1.add("menor que");
		choice_1.add("mayor que");
		choice_1.add("menor o igual que");
		choice_1.add("mayor o igual que");
		add(choice_1);
		
		JFormattedTextField comparador = new JFormattedTextField();
		comparador.setBounds(863, 76, 108, 36);
		add(comparador);
		
		JEditorPane cuerpoEmail = new JEditorPane();
		//editorPane.setBounds(84, 122, 399, 336);
		//add(editorPane);
		
		JScrollPane scrollPane = new JScrollPane(cuerpoEmail,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(84, 122, 399, 336);
		add(scrollPane);
		
		JLabel label_1 = new JLabel("Destinatarios a los que se va a enviar el correro:");
		label_1.setBounds(534, 183, 437, 38);
		label_1.setFont(new Font(label_1.getFont().getName(), Font.PLAIN, 18));
		add(label_1);
		
		List list = new List();
		list.setBounds(553, 227, 416, 203);
		add(list);
		
		JButton button = new JButton("Enviar");
		button.setBounds(752, 442, 82, 29);
		//System.out.println("Correo: " + correo);
		//System.out.println("Correo nuevo: " + reestructuraEmail(correo, (String[]) BD.getDatosTabla()[0]));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Enviar\" pulsado");
				for (String destinatario : list.getItems()) {
					Main.enviarEmail(reestructuraEmail(asuntoEmail.getText(), BD.getUsuarioEnBaseAEmail(destinatario)) , reestructuraEmail(asuntoEmail.getText(), BD.getUsuarioEnBaseAEmail(destinatario)), destinatario);
					System.out.println("Email enviado a: " + destinatario);
				}
			}
			
		});
		add(button);	
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(704, 146, 117, 29);
		btnSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Seleccionar\" pulsado");
				list.removeAll();
				String[] listaDestinatarios = Main.getDestinatarios(choice.getSelectedIndex(), comparador.getText());
				for (String destinatario : listaDestinatarios){
					list.add(destinatario);
				}
			}
			
		});
		add(btnSeleccionar);
		
		asuntoEmail = new JTextField();
		asuntoEmail.setBounds(84, 40, 399, 43);
		add(asuntoEmail);
		asuntoEmail.setColumns(10);
		
		JLabel lblAsuntoDelCorreo = new JLabel("Asunto del correo:");
		lblAsuntoDelCorreo.setBounds(84, 6, 165, 33);
		lblAsuntoDelCorreo.setFont(new Font(lblAsuntoDelCorreo.getFont().getName(), Font.PLAIN, 15));
		add(lblAsuntoDelCorreo);
		
		JLabel lblNewLabel = new JLabel("Cuerpo del correo:");
		lblNewLabel.setBounds(84, 85, 199, 36);
		lblNewLabel.setFont(new Font(lblNewLabel.getFont().getName(), Font.PLAIN, 15));
		add(lblNewLabel);
	}
	
	/**
	 * Reemplaza las variables del correo genérico con los datos de cada familia
	 * @param correo que queremos enviar
	 * @param datos que queremos utilizar
	 * @return el correo personalizado
	 */
	private static String reestructuraEmail(String correo, String[] datos) {
		correo = correo.replace("<<numeroFamilia>>", datos[BD.NUMERO_FAMILIA])
			.replace("<<nombreFamilia>>", datos[BD.NOMBRE_FAMILIA])
			.replace("<<participantes>>", datos[BD.PARTICIPANTES])
			.replace("<<tallas>>", datos[BD.TALLAS])
			.replace("<<telefono>>", datos[BD.TELEFONO])
			.replace("<<correo>>", datos[BD.CORREO])
			.replace("<<pagado>>", datos[BD.PAGADO]);
		return(correo);
	}
	
	
}
