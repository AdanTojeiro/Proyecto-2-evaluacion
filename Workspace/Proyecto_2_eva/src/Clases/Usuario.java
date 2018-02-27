package Clases;

public class Usuario {
	private String nick, pass, nombre, apellidos, dni;
	
	/*
	 * CONSTRUCTORES
	 */
	
	public Usuario() {
		
	}
	
	public Usuario(String nick, String pass, String nombre, String apellidos, String dni) {
		super();
		this.nick = nick;
		this.pass = pass;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
	}
	
	/*
	 * GETERS AND SETERS
	 */

	protected String getNick() {
		return nick;
	}

	protected void setNick(String nick) {
		this.nick = nick;
	}

	protected String getPass() {
		return pass;
	}

	protected void setPass(String pass) {
		this.pass = pass;
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected String getApellidos() {
		return apellidos;
	}

	protected void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	protected String getDni() {
		return dni;
	}

	protected void setDni(String dni) {
		this.dni = dni;
	}
	
	/*
	 * METODOS
	 */
	
	protected int acceso() {
		return 0;
	}
	
	protected void marcarMensaje(Consulta consulta) {
		
	}

	
	
	
}
