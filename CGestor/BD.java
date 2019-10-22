import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.logging.Level;
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
	public static Object[][] getDatosTabla(){
		Connection conexion;
		Statement stmt;
		ResultSet resultado;
		ArrayList<String[]> participantes;
		participantes = new ArrayList<String[]>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:familias.db");
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT * FROM datosFamilias");
	        while (resultado.next()) {
	        	String[] familia = {
	        			resultado.getString("id"),
	        			resultado.getString("nombre"),
	        			resultado.getString("participantes"),
	        			resultado.getString("tallas"),
	        			resultado.getString("telefono"),
	        			resultado.getString("email"),
	        			resultado.getString("pagado")
	        	};
	        	participantes.add(familia);
	        }
	        Main.log.log(Level.INFO, "Base de datos leida correctamente");
			stmt.close();
			conexion.close();
		}catch (Exception e) {
			Main.log.log(Level.WARNING, "La base de datos no se ha podido leer");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Conectado correctamente a la base de datos");
		
		String[][] arrayParticipantes = new String[participantes.size()][BD.getColumnasTabla().length];
		for (int x=0; x<participantes.size(); x++)
			arrayParticipantes[x] = participantes.get(x);

		return (arrayParticipantes);
	}

	/**
	 * Devuelve las columnas que va a llevar la tabla
	 * @return String[] de las columnas
	 */
	public static String[] getColumnasTabla(){
		String[] nombresColumnas = {"Numero de familia", "Nombre de la familia", "Participantes", "Tallas de los disfraces", "Telefono", "Email", "Pagado" };	
		return(nombresColumnas);
	}

	/** 
	 * Hace updates a la base de datos con los cambios de actualizaciones
	 */
	public static void actualizar() {
		
		    try {
		    	Connection conexion = DriverManager.getConnection("jdbc:sqlite:familias.db");
		    	Statement stmt = conexion.createStatement();
		    	stmt.executeUpdate("DELETE FROM datosFamilias");
		    	stmt.close();
		    	PreparedStatement pstmt = conexion.prepareStatement("INSERT INTO datosFamilias (id, nombre, participantes, tallas, telefono, email, pagado) VALUES (?,?,?,?,?,?,?);");
		    	for (String[] familia : PanelGeneral.getDatosTabla()) {
		    		pstmt.setString(1, familia[BD.ID]);
		    		pstmt.setString(2, familia[BD.NOMBRE_FAMILIA]);
		    		pstmt.setString(3, familia[BD.PARTICIPANTES]);
		    		pstmt.setString(4, familia[BD.TALLAS]);
		    		pstmt.setString(5, familia[BD.TELEFONO]);
		    		pstmt.setString(6, familia[BD.CORREO]);
		    		pstmt.setString(7, familia[BD.PAGADO]);
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
	
	/**
	 * Escribe un log de cada cambio que se ha hecho a la base de datos
	 */
	private static void logearCadaCambio() {
		for (String[] datos : actualizaciones) {
			Main.log.log(Level.INFO, "Cambiado(" + datos[0] + "," + datos[1] + "," + datos[2] + "," + datos[3] + ")");
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
			conexion = DriverManager.getConnection("jdbc:sqlite:familias.db");
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT * FROM datosFamilias WHERE email='" + email + "'");
	        System.out.println("Resultado: " + resultado);
			//stmt.executeUpdate(sql);
			stmt.close();
			conexion.close();
		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return(new String[]{"1","2"});
	}
}
