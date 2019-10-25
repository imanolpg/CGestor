package JPanels;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Main.BD;
import Main.Tabla;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Clase que crea el JPanel general
 * @author imanol
 *
 */
public class PanelGeneral extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static Tabla tabla;
	private static JTextField textoABuscar;
	private static DefaultTableModel modelo;
	private static Thread busqueda;
	
	BD.deXLSaBD("privado/familias.db");

	public PanelGeneral() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(74, 6, 747, 462);
		add(scrollPane);

		tabla = new Tabla();
		
		scrollPane.setViewportView(tabla);

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
				modelo.addRow(new Object[] { "", "", "", "", "", "", "" });
				System.out.println("Añadido un nuevo registro");
			}

		});
		add(btnAniadir);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(853, 389, 117, 29);
		btnEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.removeRow(tabla.getSelectedRow());
				System.out.println("Eliminado el registro seleccionado");
			}

		});
		add(btnEliminar);

		textoABuscar = new JTextField();
		textoABuscar.setBounds(843, 64, 139, 26);
		add(textoABuscar);
		textoABuscar.setColumns(10);

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(843, 97, 139, 29);
		btnSiguiente.setVisible(false);
		btnSiguiente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				busqueda.notify();
			}

		});
		add(btnSiguiente);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(843, 97, 139, 29);
		btnBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				busqueda = new Thread() {
					public void run() {
						btnSiguiente.setVisible(true);
						btnBuscar.setVisible(false);
						for (int fila = 0; fila < tabla.getRowCount(); fila = fila + 1) {
							for (int columna = 0; columna < tabla.getColumnCount(); columna = columna + 1) {
								if (tabla.getValueAt(fila, columna).equals(textoABuscar.getText())) {
									tabla.setRowSelectionInterval(fila, fila);
									System.out.println("hilo");
									try {
										synchronized (this) {
											this.wait();
										}
									} catch (InterruptedException e) {
										System.err.println("Error al esperar al hilo de busqueda: " + e.getMessage());
									}
									System.out.println("control");
								}
							}
						}
					}
				};
				busqueda.run();
			}
		});
		add(btnBuscar);
	}
}