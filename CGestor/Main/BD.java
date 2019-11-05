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
	
	public static String pathBD = "jdbc:sqlite:privado/familias1 copia.db";
	private static String[][] datosBD;
	private static String[] columnasBD;

	/**
	 * Lee la base de datos y guarda los valores en las variables String[][] datosBD y String[] columnasBD
	 */
	public static void cargarTabla() {
		Connection conexion;
		Statement stmt;
		ResultSet resultado;
		ArrayList<String[]> datosTemporal;
		datosTemporal = new ArrayList<String[]>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(pathBD);
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT sql FROM sqlite_master\n WHERE tbl_name = 'datos'");
			columnasBD = resultado.getString(1).split(",");
			for (int index=0; index<columnasBD.length; index=index+1) 
				columnasBD[index] = columnasBD[index].substring(columnasBD[index].indexOf("`") + 1, columnasBD[index].lastIndexOf("`"));
			resultado = stmt.executeQuery("SELECT * FROM datos");
			//columnasBD = getColumnasTabla();
	        while (resultado.next()) {
	        	String[] record = new String[columnasBD.length];
	        	for (int index=0; index<columnasBD.length; index=index+1) {
	        		record[index] = resultado.getString(index+1);
	        	}
	        	datosTemporal.add(record);
	        }
			datosBD = new String[datosTemporal.size()][BD.getColumnasTabla().length];
			for (int x=0; x<datosTemporal.size(); x++)
				datosBD[x] = datosTemporal.get(x);
			
	        Main.log.log(Level.INFO, "Base de datos leida correctamente");
			stmt.close();
			conexion.close();
			
			//FIXME
			PanelGeneral.tabla.setDatos(datosBD);
			PanelGeneral.tabla.setColumas(columnasBD);
			
		}catch (Exception e) {
			Main.log.log(Level.WARNING, "La base de datos no se ha podido leer");
			System.err.println("Error al leer los datos de la base de datos: " + e.getMessage());
		}
	}
	/** 
	 * Devuelve los datos de la base de datos
	 * @return String[][] de los datos
	 */
	public static String[][] getDatosTabla(){
		return(datosBD);
	}

	/**
	 * Devuelve las columnas de la tabla de la base de datos
	 * @return String[] de las columnas
	 */
	public static String[] getColumnasTabla(){		
		return(columnasBD);
	}

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
		    	for (int x=1; x<=columnas.length; x=x+1)
		    		statement = statement + "?" + ",";
		    	statement = statement.substring(0, statement.length()-1);
		    	statement = statement + ")";
		    	//preparo el statement para guardar en la base de datos
		    	PreparedStatement pstmt = conexion.prepareStatement(statement);
		    	for (String[] familia : PanelGeneral.tabla.getDatos()) {
		    		for(int index=0; index<columnas.length; index=index+1) {
		    			pstmt.setString(index + 1, familia[index]);
		    		}
		    		pstmt.executeUpdate();
		    		//stmt.executeUpdate("UPDATE familias SET " + orden[CAMPO] + "=" + orden[VALOR] + " WHERE id='" + orden[ID] + "'");
				}
		    	Main.log.log(Level.INFO, "Base de datos actualizada");
		    	pstmt.close();
		    	conexion.close();
		    	
		    	cargarTabla();
		    	
	        }catch (SQLException e) {
	        	Main.log.log(Level.WARNING, "La base de datos no se ha podido actualizar");
	            System.err.println("ERROR al actualizar la base de datos: " + e.getMessage());
	        }
	}
	
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