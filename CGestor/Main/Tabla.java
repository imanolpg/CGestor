package Main;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class Tabla extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel modelo;
	
	private String[] columnas;
	private String[][] datosTabla;
	
	public Tabla() {
		this.setColumas(BD.getColumnasTabla());
		this.setDatos(BD.getDatosTabla());
		this.creaModelo();
	}
	
	/**
	 * Crea el modelo en el cual se basa la tabla
	 */
	private void creaModelo() {
		modelo = new DefaultTableModel();
		for (String columna : columnas)
			modelo.addColumn(columna);
		for (String[] fila : datosTabla)
			modelo.addRow(fila);

//		modelo.addTableModelListener(new TableModelListener() {
//			
//			TableModelEvent cambio;
//			@Override
//			public void tableChanged(TableModelEvent e) {
//				cambio = e;
//			}
//			
//			public TableModelEvent getValorAntiguo() {
//				return(cambio);
//			}
//		});
		this.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("tableCellEditor".equals(evt.getPropertyName())) {
					ajustarTamanioColumnas();
					//añadir actualizaciones de los datos cambiados BD.aniadirDatoActualizado() y descomentar la linea de BD.actualizar
				}
			}
		});
		this.setModel(modelo);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ajustarTamanioColumnas();
		this.setFillsViewportHeight(true);
	}
	
	/**
	 * Inicia las columnas de la tabla
	 * @param columnas de la tabla
	 */
	public void setColumas(String[] argColumnas) {
		columnas = argColumnas;
	}
	
	/**
	 * Devuelve las columnas que hay en la tabla
	 * @return String[] con los nombres de las columnas
	 */
	public String[] getColumnas() {
		return(this.columnas);
	}

	/**
	 * Inicia los datos de la tabla
	 * @param datos de la tabla
	 */
	public void setDatos(String[][] argDatosTabla){
		datosTabla = argDatosTabla;
	}

	/**
	 * Devuelve un String[][] de los datos que hay en ese momento en la tabla
	 * @return datos de la tabla
	 */
	public String[][] getDatos(){
		return(datosTabla);
	}

	/**
	 * Actualiza el tamaño de las columnas de la tabla
	 */
	public void ajustarTamanioColumnas() {
		final TableColumnModel columnModel = this.getColumnModel();
		for (int columna = 0; columna < this.getColumnCount(); columna++) {
			int width = BD.getColumnasTabla()[columna].length() * 6; // tamaño minimo
			for (int row = 0; row < this.getRowCount(); row++) {
				TableCellRenderer renderer = this.getCellRenderer(row, columna);
				Component comp = this.prepareRenderer(renderer, row, columna);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(columna).setPreferredWidth(width + 10);
		}
	}
	
	/**
	 * Añade una nueva fila a la tabla
	 * @param nueva fila que se va a añadir
	 */
	public void aniadirFila(String[] filaNueva) {
		this.modelo.addRow((Object[]) filaNueva);
	}
	
	/**
	 * Elimina una fila de la tabla
	 * @param el número de la fila que hay que eliminar
	 */
	public void eliminarFila(int fila) {
		this.modelo.removeRow(fila);
	}
	
	/**
	 * Selecciona una fila de la tabla
	 * @param número de fila a seleccionar
	 */
	public void seleccionarFila(int fila) {
		this.setRowSelectionInterval(fila, fila);
	}
}
