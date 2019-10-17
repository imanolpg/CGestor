import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JTextField;

public class PanelGeneral extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private JTextField textoABuscar;
	private DefaultTableModel modelo;

	public PanelGeneral() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(74, 6, 747, 462);
		add(scrollPane);
		
		modelo = new DefaultTableModel();
		for (String columna : BD.getColumnasTabla())
			modelo.addColumn(columna);
		for (Object[] familia : BD.getDatosTabla())
			modelo.addRow(familia);
		table = new JTable(modelo);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ajustarTamanioColumnas();
		table.setFillsViewportHeight(true);
		table.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("tableCellEditor".equals(evt.getPropertyName())) {
					ajustarTamanioColumnas();
					BD.actualizar();
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
		
		JButton btnAniadir = new JButton("Añadir");
		btnAniadir.setBounds(853, 357, 117, 29);
		btnAniadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.addRow(new Object[]{"", "", "", "", "", "", ""});
				System.out.println("Añadido un nuevo registro");
			}
			
		});
		add(btnAniadir);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(853, 389, 117, 29);
		btnEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.removeRow(table.getSelectedRow());
				System.out.println("Eliminado el registro seleccionado");
			}
			
		});
		add(btnEliminar);
		
		textoABuscar = new JTextField();
		textoABuscar.setBounds(843, 64, 139, 26);
		add(textoABuscar);
		textoABuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(843, 97, 139, 29);
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				Thread busqueda = new Thread() { 
					public void run() {
						for (int fila = 0; fila<table.getRowCount(); fila = fila + 1) {
							for (int columna = 0; columna<table.getColumnCount(); columna = columna + 1) {
								if(table.getValueAt(fila, columna).equals(textoABuscar.getText())) {
									btnBuscar.setText("Siguiente");
									table.setRowSelectionInterval(fila, fila);
									System.out.println("ENCONTRADO");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
						btnBuscar.setText("Buscar");
					}
				};
				busqueda.run();
			}
			
		});
		add(btnBuscar);
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