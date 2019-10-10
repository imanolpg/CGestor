import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class PanelGeneral extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public PanelGeneral() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(table,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(74, 6, 840, 462);
		add(scrollPane);
		
		JTable table = new JTable(BD.getDatosTabla(), BD.getColumnasTabla());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		table.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("tableCellEditor".equals(evt.getPropertyName())) {
		           System.out.println("Editando un dato..");
		           System.out.println("Datod evt: ");
		           //Main.logger.info("PruebaLOG");
		           //BD.aniadirActializacion("", "", evt.getNewValue().toString());
		        }
		    }
		});
		
		scrollPane.setViewportView(table);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Actualizar\" pulsado");
				BD.actualizar();
			}
			
		});
		btnActualizar.setBounds(428, 480, 117, 29);
		add(btnActualizar);
	}
	
}