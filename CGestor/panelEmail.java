import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Choice;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;


public class panelEmail extends JPanel {

	/**
	 * Create the panel.
	 */
	public panelEmail() {
		setLayout(null);
		
		JLabel label = new JLabel("Criterio:");
		label.setBounds(724, 25, 82, 27);
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
		add(label);
		
		Choice choice = new Choice();
		choice.setBounds(553, 76, 130, 36);
		String[] columnas = Main.getColumnasTabla();
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
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(863, 76, 108, 36);
		add(formattedTextField);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(84, 25, 399, 433);
		add(editorPane);
		
		JLabel label_1 = new JLabel("Destinatarios a los que se va a enviar el correro:");
		label_1.setBounds(534, 183, 437, 38);
		label_1.setFont(new Font(label_1.getFont().getName(), Font.PLAIN, 18));
		add(label_1);
		
		List list = new List();
		list.setBounds(553, 227, 416, 203);
		add(list);
		
		JButton button = new JButton("Enviar");
		button.setBounds(752, 442, 82, 29);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Enviar\" pulsado");
				String email = ""; 
				for (String destinatario : list.getItems()) {
					Main.enviarEmail(email, destinatario);
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
				String[] listaDestinatarios = Main.getDestinatarios();
				for (String destinatario : listaDestinatarios){
					list.add(destinatario);
				}
			}
			
		});
		add(btnSeleccionar);
	}
}
