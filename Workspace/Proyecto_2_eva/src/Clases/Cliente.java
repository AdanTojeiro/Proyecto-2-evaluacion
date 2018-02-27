package Clases;

public class Cliente extends Usuario {

	public Cliente(String nick, String pass, String nombre, String apellidos, String dni) {
		super(nick, pass, nombre, apellidos, dni);
	}

	@Override
	protected int acceso() {
		return -1;
	}
	
	@Override
	protected void marcarMensaje(Consulta consulta) {
		consulta.setNuevoMensajeCliente(true);
	}
	
	
	

	

}
