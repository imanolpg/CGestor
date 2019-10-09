import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
		
		class TML implements TableModelListener{
			
			private int columna;
			private int fila;

			@Override
			public void tableChanged(TableModelEvent e) {
				
				columna = e.getColumn();
				fila = e.getLastRow();
			}
			
			public int getFila() {
				return fila;
			}
			
			public int getColumna() {
				return columna;
			}
			
		}
		
		TML tml = new TML();
		
		JTable table = new JTable(BD.getDatosTabla(), BD.getColumnasTabla());
		table.getModel().addTableModelListener(tml);
		table.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("tableCellEditor".equals(evt.getPropertyName())) {
		           System.out.println("Editando un dato..");
		           try {
		        	   Thread.sleep(1000);
		           } catch (Exception e) {
		        	   
		           }
		           System.out.println("Datod evt: " + tml.getFila());
		           BD.aniadirLog("", "", evt.getNewValue().toString(), evt.getOldValue().toString());
		           BD.aniadirActializacion("", "", evt.getNewValue().toString());
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(table,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		add(scrollPane, BorderLayout.CENTER);

	}

}
