package Clases;

import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

	protected final int LONGITUD = 50;
	protected final int MAX_LENGHT = LONGITUD -2;

	protected String espacios(String s) {
		if (s.length() < LONGITUD) {
			while (s.length() < LONGITUD) {
				s = s + " ";
			}
		}
		return s;
	}
	
	protected String espacios(String s, int mayor) {
		if (s.length() < mayor) {
			while (s.length() < mayor) {
				s = s + " ";
			}
		}
		return s;
	}

	protected String centrar(String s, int LONGITUD) {
		while (s.length() < LONGITUD + 4) {
			s = " " + s + " ";
		}

		if (s.length() % 2 == 0) {
			s = s + " ";

		}

		return s;
	}

	protected void fila(String s) {
		if(checkLenght(s)) {
			s = recortar(s);
		}
		s = "|  " + espacios(s) + " |";
		System.out.println(s);
	}
	protected void filaCentrada(String s) {
		if(checkLenght(s)) {
			s = recortar(s);
		}
		if(s.length() % 2 == 0) {
			s = centrar(s, LONGITUD- 6 );
		} else {
			s = centrar(s, LONGITUD- 5 );
		}
		s = "|  " + espacios(s) + " |";
		System.out.println(s);
		
	}

	protected void cabezera(String s) {
		if(checkLenght(s)) {
			s = recortar(s);
		}
		s = "|-{ " + centrar(s, LONGITUD - 8) + " }-|";
		s = s + "\n|";
		for (int i = 0; i < LONGITUD + 3; i++) {
			s = s + "-";
		}
		s = s + "|";
		System.out.println(s);
	}

	protected void ultimaFila() {
		String s = "|";
		for (int i = 1; i < LONGITUD + 4; i++) {
			s = s + "-";

		}
		s = s + "|";
		System.out.println(s);

	}
	
	protected void interFila() {
		String s = "|";
		for (int i = 1; i < LONGITUD + 4; i++) {
			s = s + ".";

		}
		s = s + "|";
		System.out.println(s);

	}
	
	protected void subFila() {
		String s = "|";
		for (int i = 1; i < LONGITUD + 4; i++) {
			s = s + " ";

		}
		s = s + "|";
		System.out.println(s);

	}
	
	protected boolean checkLenght(String s) {
		
		if (s.length() > MAX_LENGHT) {
			return true;
		}
		return false;
	}
	
	protected String recortar(String s) {
		 s= s.substring(0, (MAX_LENGHT-4))+"...";
		return s;
	}
	
	protected void generarMenu(String cabezera, String[] array) {
		cabezera(cabezera);
		for(int i = 0; i < array.length ; i++ ) {
			String s = array[i];
			s = (i+1)+" - "+array[i];
			fila(s);
		}
		ultimaFila();
	}
	
	protected void generarMenu(String cabezera, String[] array, Consulta consulta) {
		cabezera(cabezera);
		mostrarMensajes(consulta);
		for(int i = 0; i < array.length ; i++ ) {
			String s = array[i];
			s = (i+1)+" - "+array[i];
			fila(s);
		}
		ultimaFila();
	}
	
	protected void generarNormas(String titulo, String[] array) {
		fila("["+titulo+"]");
		interFila();
		for(int i = 0; i < array.length ; i++ ) {
			String s = array[i];
			s = " * "+array[i];
			fila(s);
		}
		ultimaFila();
	}
	
	protected void generarConfirmacion(String pregunta, String dni, String nombre, String apellidos, String nick, String pass) {
		filaCentrada(pregunta);
		filaCentrada("1 -> Si | 2-> No");
		interFila();
		fila("DNI: "+dni);
		fila("NOMBRE: "+nombre);
		fila("APELLIDOS: "+apellidos);
		fila("NICK: "+nick);
		fila("CONTRASEÑA: "+pass);
		ultimaFila();
	}
	protected void generarConfirmacion(String pregunta){
		filaCentrada(pregunta);
		filaCentrada("1 -> Si | 2-> No");
		ultimaFila();
	}
	
	
	protected void generarLista(String cabezera, ArrayList<Usuario> lista) {
		Iterator<Usuario> it = lista.iterator();
		int i = 0;
		int [] mayor = mayoresEnLista(lista);
		String nick, pass;
		cabezera(cabezera);
		while(it.hasNext()) {
			Usuario user = it.next();
			nick = espacios(user.getNick(), mayor[0]);
			pass = espacios(user.getPass(), mayor[1]);
			fila(i+" -> | "+nick+" | "+pass);
			i++;
		}
		ultimaFila();
	}
	protected void mostrarConsultas(Cliente cliente, ArrayList<Consulta> consultas) {
		Iterator<Consulta> it = consultas.iterator();
		String titulo;
		ultimaFila();
		cabezera("CONSULTAS DE "+cliente.getNick().toUpperCase());
		while(it.hasNext()) {
			Consulta c = it.next();
			if(c.getCliente().equals(cliente)) {
				titulo = espacios(c.getTitulo(), mayorEnLista(consultas));
				fila(consultas.indexOf(c)+" - | "+titulo+" | "+(c.isNuevoMensajeTecnico()?"Nuevo":"Leido")+" | "+c.getnMensajes()+" Mensajes");
			}
		}
		interFila();
		fila("x - Salir");
		ultimaFila();
	}
	
	protected void mostrarMensajes(Consulta consulta) {
		Iterator<Mensaje> it = consulta.getMensajes().iterator();
		while(it.hasNext()) {
			Mensaje m = it.next();
			mostrarMensaje(m, consulta.getMensajes());
		}
	}
	
	protected void mostrarMensaje(Mensaje m, ArrayList<Mensaje> lista) {
		ArrayList<String> frases = frases(m.getPalabras());
		Iterator<String> it = frases.iterator();
		cabezeraMensaje(m, lista);
		while(it.hasNext()) {
			String s = it.next();
			fila(s);
		}
		interFila();	
	}
	
	protected void cabezeraMensaje(Mensaje m, ArrayList<Mensaje> lista) {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy hh:mm");
		fila("Autor: "+espacios(m.getUsuario().getNick(), mayorEnListaM(lista))+" | "+dt1.format(m.getFecha()));
		subFila();
		
		
	}
	
	protected ArrayList<String> frases( String[] palabras) {
		ArrayList<String> frases  = new ArrayList<String>();
		String s = "";
		int i = 0;
		int l = 0;
		while(i < palabras.length) {
			while((s.length()+l) <= MAX_LENGHT && i < palabras.length ) {
				s = s+" "+palabras[i];
				i++;
				if(i < palabras.length) {
					l = palabras[i].length()+1;
				}
			}
			frases.add(s);
			s ="";
		}
		return frases;
	}
	
	protected void pedir(String opcion) {
		System.out.print("    -> "+opcion+": ");
	}
	
	protected int[] mayoresEnLista(ArrayList<Usuario> lista) {
		Iterator<Usuario> it = lista.iterator();
		int [] mayor = {0, 0};
		while(it.hasNext()) {
			Usuario user = it.next();
			if(mayor[0] < user.getNick().length()) {
				mayor[0] = user.getNick().length();
			}
			if(mayor[1] < user.getPass().length()) {
				mayor[1] = user.getPass().length();
			}
			
		}
		return mayor;
		
	}
	
	protected int mayorEnLista(ArrayList<Consulta> lista) {
		Iterator<Consulta> it = lista.iterator();
		int mayor = 0;
		while(it.hasNext()) {
			Consulta c = it.next();
			if(mayor < c.getTitulo().length()) {
				mayor = c.getTitulo().length();
			}
			
		}
		return mayor;
		
	}
	
	protected int mayorEnListaM(ArrayList<Mensaje> lista) {
		Iterator<Mensaje> it = lista.iterator();
		int mayor = 0;
		while(it.hasNext()) {
			Mensaje m = it.next();
			if(mayor < +m.getUsuario().getNick().length()) {
				mayor = +m.getUsuario().getNick().length();
			}
			
		}
		return mayor;
		
	}
}
