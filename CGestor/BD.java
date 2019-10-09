import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BD {
	
	public static final int FAMILIA = 0;
	public static final int NOMBRE = 1;
	public static final int PARTICIPANTES = 2;
	public static final int TALLAS = 3;
	public static final int TELEFONO = 4;
	public static final int CORREO = 5;
	public static final int PAGADO = 6;

	private static ArrayList<String[]> actualizaciones;
	
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
		System.out.println("Conectado correctamente");
		
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
		 
		 for (String[] orden : actualizaciones) {
		    try {
				 Connection conexion = DriverManager.getConnection("jdbc:sqlite:test.db");
				 Statement stmt = conexion.createStatement();
				 
		    	stmt.executeUpdate("");
		    	//stmt.executeUpdate();
		 
	        }catch (SQLException e) {
		            System.out.println(e.getMessage());
	        }
		}
	}
}
