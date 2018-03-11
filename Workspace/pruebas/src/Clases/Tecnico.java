package Clases;

public class Tecnico extends Usuario {

	public Tecnico(String nick, String pass, String nombre, String apellidos, String dni) {
		super(nick, pass, nombre, apellidos, dni);
		
	}
	
	@Override
	protected int acceso() {
		return 1;
	}

	@Override
	protected void marcarMensaje(Consulta consulta) {
		consulta.setNuevoMensajeTecnico(true);
	}
	
	
	

}
