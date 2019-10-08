import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JEditorPane;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Choice;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;

public class panelEmail extends JPanel {

	/**
	 * Create the panel.
	 */
	public panelEmail() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JEditorPane editorPane = new JEditorPane();
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.insets = new Insets(0, 0, 0, 5);
		gbc_editorPane.gridheight = 7;
		gbc_editorPane.fill = GridBagConstraints.VERTICAL;
		gbc_editorPane.gridx = 1;
		gbc_editorPane.gridy = 0;
		add(editorPane, gbc_editorPane);
		
		JLabel lblNewLabel = new JLabel("Criterio");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 8;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		Choice choice = new Choice();
		GridBagConstraints gbc_choice = new GridBagConstraints();
		gbc_choice.insets = new Insets(0, 0, 5, 5);
		gbc_choice.gridx = 7;
		gbc_choice.gridy = 2;
		String[] columnas = Main.getColumnasTabla();
		for (String criterio : columnas) {
			choice.add(criterio);
		}
		add(choice, gbc_choice);
		
		Choice choice_1 = new Choice();
		GridBagConstraints gbc_choice_1 = new GridBagConstraints();
		gbc_choice_1.insets = new Insets(0, 0, 5, 5);
		gbc_choice_1.gridx = 8;
		gbc_choice_1.gridy = 2;
		choice_1.add("igual");
		choice_1.add("diferente");
		choice_1.add("menor que");
		choice_1.add("mayor que");
		add(choice_1, gbc_choice_1);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		GridBagConstraints gbc_formattedTextField = new GridBagConstraints();
		gbc_formattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField.gridx = 9;
		gbc_formattedTextField.gridy = 2;
		add(formattedTextField, gbc_formattedTextField);
		
		List list = new List();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridx = 8;
		gbc_list.gridy = 5;
		for (String destinatario : Main.getDestinatarios()) {
			list.add(destinatario);
		}
		
		Label label = new Label("Destinatarios a los que se va a enviar el correro:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 7;
		gbc_label.gridy = 5;
		add(label, gbc_label);
		add(list, gbc_list);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Enviar\" pulsado");
			}
			
		});
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviar.gridx = 8;
		gbc_btnEnviar.gridy = 6;
		add(btnEnviar, gbc_btnEnviar);
		
	}

}
