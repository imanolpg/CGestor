import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class panelGeneral extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
				BD.actualizar();
			}
			
		});
		add(btnNewButton, BorderLayout.SOUTH);
		
		JTable table = new JTable(BD.getDatosTabla(), BD.getColumnasTabla());
		table.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("tableCellEditor".equals(evt.getPropertyName())) {
		           System.out.println("fdasfasdads");
		        }
		    }
			
		});
		
		JScrollPane scrollPane = new JScrollPane(table,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		add(scrollPane, BorderLayout.CENTER);

	}

}
