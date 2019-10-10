import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

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

	// {"id familia", "campo a cambiar", "nuevo valor"}
	private static ArrayList<String[]> actualizaciones;
	
	/**
	 * Aniade una nueva actualizacion para guardarlo en la base de datos
	 * @param id localizador de la familia
	 * @param campo que queremos cambiar
	 * @param valor nuevo valor del campo
	 */
	public static void aniadirActializacion(String id, String campo, String valor) {
		actualizaciones.add(new String[] {id, campo, valor});
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
			//stmt.executeUpdate(sql);
			stmt.close();
			conexion.close();
		}catch (Exception e) {
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
		    	Connection conexion = DriverManager.getConnection("jdbc:sqlite:test.db");
		    	Statement stmt = conexion.createStatement();

		    	for (String[] orden : actualizaciones) {
		    		stmt.executeUpdate("UPDATE familias SET " + orden[CAMPO] + "=" + orden[VALOR] + " WHERE id='" + orden[ID] + "'");
				}
		    	
		    	stmt.close();
		    	conexion.close();
	        }catch (SQLException e) {
		            System.err.println("ERROR al actualizar la base de datos: " + e.getMessage());
	        }
	}
}
