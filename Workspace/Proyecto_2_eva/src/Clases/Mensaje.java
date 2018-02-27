package Clases;

import java.util.Date;

public class Mensaje {
	
	private Usuario usuario;
	private String[] palabras;
	private int numero;
	
	private Date fecha;
	
	/*
	 * CONSTRUCTORES
	 */
	public Mensaje(Usuario usuario, String cadena){
		this.usuario = usuario;
		palabras = cadena.split(" ");
		fecha = new Date();
		
	}
	
	/*
	 * GETERS AND SETERS
	 */

	protected Usuario getUsuario() {
		return usuario;
	}
	protected void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	protected String[] getPalabras() {
		return palabras;
	}
	protected void setPalabras(String[] palabras) {
		this.palabras = palabras;
	}
	protected Date getFecha() {
		return fecha;
	}
	protected void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	protected int getNumero() {
		return numero;
	}

	protected void setNumero(int numero) {
		this.numero = numero;
	}
	

}
