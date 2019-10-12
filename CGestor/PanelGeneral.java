import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanelGeneral extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;

	public PanelGeneral() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(74, 6, 840, 462);
		add(scrollPane);

		table = new JTable(BD.getDatosTabla(), BD.getColumnasTabla());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ajustarTamanioColumnas();
		table.setFillsViewportHeight(true);
		table.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("tableCellEditor".equals(evt.getPropertyName())) {
					ajustarTamanioColumnas();
					System.out.println("Ajustando tamaño");
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

	/**
	 * Actualiza el tamaño de las columnas de la tabla
	 */
	private static void ajustarTamanioColumnas() {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int columna = 0; columna < table.getColumnCount(); columna++) {
			int width = BD.getColumnasTabla()[columna].length() * 6; //tamaño minimo
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, columna);
				Component comp = table.prepareRenderer(renderer, row, columna);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(columna).setPreferredWidth(width + 10);
		}
	}
	
	public static String[][] getDatosTabla(){
		String[][] aDevolver = new String[table.getRowCount()][table.getColumnCount()];
		for (int fila = 0; fila<table.getRowCount(); fila = fila + 1) {
			for (int columna = 0; columna<table.getColumnCount(); columna = columna + 1) {
				aDevolver[fila][columna] = (String) table.getValueAt(fila, columna);
			}
		}
		return (aDevolver);
	}
}