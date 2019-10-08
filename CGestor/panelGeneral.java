import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class panelGeneral extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public panelGeneral() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Actualizar");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Actualizar\" pulsado");
			}
			
		});
		add(btnNewButton, BorderLayout.SOUTH);
		
		JTable table = new JTable(Main.getDatosTabla(), Main.getColumnasTabla());
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane, BorderLayout.CENTER);

	}

}
