import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.sql.*;
import java.util.ArrayList;

public class Main {
	
	//TODO personalizar la tabla
	
	static Canvas canvas;
	static JPanel panelGeneral;
	static JPanel panelEmail;
	static ArrayList<String[]> actualizaciones;

	public static void main(String[] args) {
		panelGeneral = new panelGeneral();
		panelEmail = new panelEmail();
		createWindow();
		
	}

	public static void createWindow() {
		canvas = new Canvas("CGestor");

		JButton buttongeneralPanelPanel, buttonemailPanel, buttonConfiguracion;
		buttongeneralPanelPanel = new JButton("General");
		buttongeneralPanelPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"General\" pulsado");
				canvas.add(panelGeneral, BorderLayout.CENTER);
				panelEmail.setVisible(false);
				panelGeneral.setVisible(true);
			}	
		});
		buttonemailPanel = new JButton("Email");
		buttonemailPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Email\" pulsado");
				canvas.add(panelEmail, BorderLayout.CENTER);
				panelGeneral.setVisible(false);
				panelEmail.setVisible(true);
			}	
		});
		buttonConfiguracion = new JButton("Configuracion");
		buttonConfiguracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Boton \"Configuracion\" pulsado");	
			}	
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buttongeneralPanelPanel);
		buttonPanel.add(buttonemailPanel);
		buttonPanel.add(buttonConfiguracion);
		

		canvas.add(panelGeneral, BorderLayout.CENTER);

		canvas.add(buttonPanel, BorderLayout.NORTH);

		canvas.setVisible(true);
	}
	
	public static void updateDatabase() {
		 String sql = "DELETE FROM warehouses WHERE id = ?";
		 
		for (String[] orden : actualizaciones) {
		    try (Connection conn = this.connect();
		    	PreparedStatement pstmt = conn.prepareStatement(sql)) {

		    	// set the corresponding param
		    	pstmt.setInt(1, id);
		    	// execute the delete statement
		    	pstmt.executeUpdate();
		 
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		}
	
	
	/** Ordena los datos para mostrarlos en la tabla
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
			conexion = DriverManager.getConnection("jdbc:sqlite:test.db");
			stmt = conexion.createStatement();
			resultado = stmt.executeQuery("SELECT * FROM familias");
	        while (resultado.next()) {
	        	String[] familia = {
	        			resultado.getString("id"),
	        			resultado.getString("nombre"),
	        			resultado.getString("participantes"),
	        			resultado.getString("haPagado"),
	        			resultado.getString("tallasDisfraces"),
	        			resultado.getString("telefono"),
	        			resultado.getString("email")
	        	};
	        	participantes.add(familia);
	        }
			//stmt.executeUpdate(sql);
			stmt.close();
		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Conectado correctamente");
		
		Object[][] datos = { 
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20), new Boolean(true), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Joe", "Brown", "Pool", new Integer(10), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false), "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) }
				
		};

		return (Object[][]) (participantes.toArray());
	}

	/** Devuelve las columnas que va a llevar la tabla
	 * @return String[] de las columnas
	 */
	public static String[] getColumnasTabla(){
		
		String[] nombresColumnas = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian", "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
		
		return(nombresColumnas);
	}
	
	/** Selecciona los destinatarios que cumplen el criterio
	 * @return String[] con los destinatarios
	 */
	public static String[] getDestinatarios(){
		String[] destinatarios = {};
		return(destinatarios);
	}
	
	/** Envia un email al destinatario
	 * @param email Email a enviar
	 * @param destinatario Destinatario al que se le va a enviar
	 */
	public static void enviarEmail(String email, String destinatario) {
		
	}
	
}
