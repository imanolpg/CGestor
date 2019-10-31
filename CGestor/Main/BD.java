package Main;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import JPanels.PanelGeneral;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Clase que gestiona todas las interacciones con la base de datos
 * @author imanol
 *
 */
public class BD {
	
	public static final int NUMERO_FAMILIA = 0;
	public static final int NOMBRE_FAMILIA = 1;
	public static final int PARTICIPANTES = 2;
	public static final int TALLAS = 3;
	public static final int TELEFONO = 4;
	public static final int CORREO = 5;
	public static final int PAGADO = 6;
	
	public static final int ID = 0;
	public static final int CAMPO = 1;
	public static final int VALOR = 2;

	// {"id familia", "campo a cambiar", "nuevo valor", "viejo valor"}
	private static ArrayList<String[]> actualizaciones;
	
	public final static String pathBD = "jdbc:sqlite:privado/familias1 copia.db";
	
	/**
	 * Aniade una nueva actualizacion para guardarlo en la base de datos
	 * @param id localizador de la familia
	 * @param campo que queremos cambiar
	 * @param valor nuevo valor del campo
	 */
	public static void aniadirActualizacion(String id, String campo, String valorNuevo, String valorViejo) {
		actualizaciones.add(new String[] {id, campo, valorNuevo, valorViejo});
	}
	
	/** 
	 * Ordena los datos para mostrarlos en la tabla
	 * @return Object[][] de los datos
	 */
	public static String[][] getDatosTabla(){
		Connection conexion;
		Statement stmt;
		ResultSet resultado;
		ArrayList<String[]> datosTemporal;
		datosTemporal = new ArrayList<String[]>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(pathBD);
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT * FROM datos");
			String[] columnas = getColumnasTabla();
	        while (resultado.next()) {
	        	String[] record = new String[columnas.length];
	        	for (int index=0; index<columnas.length; index=index+1) {
	        		record[index] = resultado.getString(index+1);
	        	}
	        	datosTemporal.add(record);
	        }
	        Main.log.log(Level.INFO, "Base de datos leida correctamente");
			stmt.close();
			conexion.close();
		}catch (Exception e) {
			Main.log.log(Level.WARNING, "La base de datos no se ha podido leer");
			System.err.println("Error al leer los datos de la base de datos: " + e.getMessage());
		}
		
		String[][] datos = new String[datosTemporal.size()][BD.getColumnasTabla().length];
		for (int x=0; x<datosTemporal.size(); x++)
			datos[x] = datosTemporal.get(x);

		return (datos);
	}

	/**
	 * Devuelve las columnas de la tabla de la base de datos
	 * @return String[] de las columnas
	 */
	public static String[] getColumnasTabla(){
		
		String[] columnas;
		try {
			Connection conexion = DriverManager.getConnection(pathBD);
			Statement stmt = conexion.createStatement();
			ResultSet resultado = stmt.executeQuery("SELECT sql FROM sqlite_master\n WHERE tbl_name = 'datos'");
			columnas = resultado.getString(1).split(",");
			for (int index=0; index<columnas.length; index=index+1) {
				columnas[index] = columnas[index].substring(columnas[index].indexOf("`") + 1, columnas[index].lastIndexOf("`"));
			}
			conexion.close();
			return(columnas);
		} catch (Exception e) {
			System.err.println("Error al leer las columnas de la base de datos: " + e.getMessage());
		}
		return(null);
	}
	
//	/**
//	 * Guarda en la variable columnas las columnas de la base de datos
//	 * @param array list con los Strings de columnas
//	 */
//	public static void setColumnasTabla(ArrayList<String> arraylistColumnas) {
//		columnas = (String[]) arraylistColumnas.toArray();
//	}

	/** 
	 * Hace updates a la base de datos con los cambios de actualizaciones
	 */
	public static void actualizar() {
		
		    try {
		    	Connection conexion = DriverManager.getConnection(pathBD);
		    	Statement stmt = conexion.createStatement();
		    	stmt.executeUpdate("DELETE FROM datos");
		    	stmt.close();
		    	//Construyo el comando sqlite 
		    	String statement = "INSERT INTO datos (";
		    	String[] columnas  = PanelGeneral.tabla.getColumnas();
		    	for (String columna : columnas) {
		    		statement = statement + columna + ",";
		    	}
		    	statement = statement.substring(0, statement.length()-1);
		    	statement = statement + ") VALUES (";
		    	for (int x=1; x<=columnas.length;)
		    		statement = statement + "?" + ",";
		    	statement = statement.substring(0, statement.length()-1);
		    	statement = statement + ")";
		    	//preparo el statement para guardar en la base de datos
		    	PreparedStatement pstmt = conexion.prepareStatement(statement);
		    	for (String[] familia : PanelGeneral.tabla.getDatos()) {
		    		//TODO cambiar para que se haga con todas las columnas
		    		for(int index=0; index<columnas.length; index=index+1) {
		    			pstmt.setString(index + 1, familia[index]);
		    		}
		    		pstmt.executeUpdate();
		    		//stmt.executeUpdate("UPDATE familias SET " + orden[CAMPO] + "=" + orden[VALOR] + " WHERE id='" + orden[ID] + "'");
				}
		    	//logearCadaCambio(); TODO descomentar cuando se añadan en actualizaciones cada valor cambiado
		    	Main.log.log(Level.INFO, "Base de datos actualizada");
		    	pstmt.close();
		    	conexion.close();
	        }catch (SQLException e) {
	        	Main.log.log(Level.WARNING, "La base de datos no se ha podido actualizar");
	            System.err.println("ERROR al actualizar la base de datos: " + e.getMessage());
	        }
	}
	
//	/**
//	 * Escribe un log de cada cambio que se ha hecho a la base de datos
//	 */
//	private static void logearCadaCambio() {
//		for (String[] datos : actualizaciones) {
//			Main.log.log(Level.INFO, "Cambiado(" + datos[0] + "," + datos[1] + "," + datos[2] + "," + datos[3] + ")");
//		}
//	}
	
	/**
	 * Devuelve 
	 * @param email
	 * @return
	 */
	public static String[] getUsuarioEnBaseAEmail(String email){
		Connection conexion;
		Statement stmt;
		ResultSet resultado;
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(pathBD);
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT * FROM datos WHERE email='" + email + "'");
	        System.out.println("Resultado: " + resultado);
			//stmt.executeUpdate(sql);
			stmt.close();
			conexion.close();
		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return(new String[]{"1","2"});
	}
	
	/** Pasa el archivo .xls a una base de datos
	 * @param ruta del archivo
	 */
	public static void deXLSaBD(String rutaExcel) {
		//TODO testear
		File archivoXls;
		Workbook workbook;
		String[][] datosTabla;
		Connection conexion;
		Statement stmt;
		PreparedStatement pstmt;
		String comando;
		ArrayList<String> filas;
		ArrayList<String[]> datos;
		try {
			archivoXls = new File(rutaExcel);
			workbook = Workbook.getWorkbook(archivoXls);
			Sheet hoja = workbook.getSheet(0);
			filas = new ArrayList<String>();
			datos = new ArrayList<String[]>();
			conexion = DriverManager.getConnection(pathBD);
			stmt = conexion.createStatement();
			comando = "CREATE TABLE BaseDeDatos.datos(";
			for (int columna = 0; columna <= hoja.getColumns(); columna = columna + 1) {
				filas.add(hoja.getCell(0, columna).getContents());
				comando = comando + hoja.getCell(0, columna).getContents() + "string, ";
			}
			if (comando.contains("string, ")){
				comando = comando.substring(0, comando.length() - ("string, ").length());
			}
			stmt.execute(comando);
			stmt.close();
			//PanelGeneral.tabla.setColumas((String[]) filas.toArray());
			comando = "INSERT INTO datos (";
			for (String columna : filas) {
				comando = comando + columna + ", ";
			}
			if (comando.contains(", ")){
				comando = comando.substring(0, comando.length() - (", ").length());
			}
			comando = comando + ") VALUES (";
			for (int x=0; x<filas.size(); x = x + 1) {
				comando = comando + "?, ";
			}
			if (comando.contains(", ")){
				comando = comando.substring(0, comando.length() - (", ").length());
			}
			comando = comando + ")";
			
			pstmt = conexion.prepareStatement(comando);
			filas.clear();
			for (int fila = 1; fila <= hoja.getRows(); fila = fila + 1) {
				for (int columna = 0; columna <= hoja.getColumns(); columna = columna + 1) {
					filas.add(hoja.getCell(fila, columna).getContents());
				}
				datos.add((String[]) filas.toArray());
			}
			datosTabla = new String[filas.size()][datos.size()];
			for (int fila=0; fila<datos.size(); fila = fila + 1){
				for(int celda=0; celda<filas.size(); celda = celda + 1){
					datosTabla[fila][celda] = datos.get(fila)[celda];
				}
			}
			
		} catch (Exception e) {
			System.err.println("Error al crear la base de datos: " + e.getMessage());
		}
	}
}