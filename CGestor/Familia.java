import java.sql.Array;

public class Familia {
	/**
	id de familia
	nombre de la familia
	participantes
	tallas de los disfrazes que necesita
	numero de telefono
	corro electronico
	ha pagado
	*/
	
	private int id;
	private String nombre;
	private Array participantes;
	private Boolean haPagado;
	private Array tallasDisfraces;
	private int telefono;
	private String email;

	public Familia(int id, String nombre, Array participantes, Boolean haPagado, Array tallasDisfraces, int telefono, String email) {
		this.id = id;
		this.nombre = nombre;
		this.participantes = participantes;
		this.haPagado = haPagado;
		this.tallasDisfraces = tallasDisfraces;
		this.telefono = telefono;
		this.email = email;
	}
	
	public void updateInDatabase() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Array getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Array participantes) {
		this.participantes = participantes;
	}

	public Boolean getHaPagado() {
		return haPagado;
	}

	public void setHaPagado(Boolean haPagado) {
		this.haPagado = haPagado;
	}

	public Array getTallasDisfraces() {
		return tallasDisfraces;
	}

	public void setTallasDisfraces(Array tallasDisfraces) {
		this.tallasDisfraces = tallasDisfraces;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
