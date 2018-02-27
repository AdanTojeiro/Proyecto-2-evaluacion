package Clases;

import java.util.*;

public class Consulta {
	
	private ArrayList <Mensaje> mensajes;
	private Cliente cliente;
	private String titulo;
	private int nMensajes;
	private boolean nuevoMensajeTecnico;
	private boolean nuevoMensajeCliente;
	
	/*
	 * CONSTRUCTORES
	 */
	public Consulta(Cliente cliente, String titulo) {
		this.cliente = cliente;
		this.titulo = titulo;
		mensajes = new ArrayList<Mensaje>();
		nMensajes = 0;
	}
	/*
	 * METODOS
	 */
	
	protected void addMensaje(Mensaje mensaje) {
		nMensajes++;
		mensaje.setNumero(nMensajes);
		mensaje.getUsuario().marcarMensaje(this);
		mensajes.add(mensaje);
	}
	
	/*
	 * GETERS AND SETERS
	 */

	protected ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}
	protected void setMensajes(ArrayList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	protected Cliente getCliente() {
		return cliente;
	}
	protected void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	protected boolean isNuevoMensajeTecnico() {
		return nuevoMensajeTecnico;
	}
	protected void setNuevoMensajeTecnico(boolean nuevoMensajeTecnico) {
		this.nuevoMensajeTecnico = nuevoMensajeTecnico;
	}
	protected boolean isNuevoMensajeCliente() {
		return nuevoMensajeCliente;
	}
	protected void setNuevoMensajeCliente(boolean nuevoMensajeCliente) {
		this.nuevoMensajeCliente = nuevoMensajeCliente;
	}
	protected String getTitulo() {
		return titulo;
	}
	protected void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	protected int getnMensajes() {
		return nMensajes;
	}

	protected void setnMensajes(int nMensajes) {
		this.nMensajes = nMensajes;
	}
	
	


}
