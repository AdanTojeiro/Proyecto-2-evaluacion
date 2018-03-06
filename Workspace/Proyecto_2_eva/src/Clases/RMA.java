package Clases;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @version 1.0
 */

public class RMA {

	public static void main(String[] args) {

		Menu menu = new Menu();
		Scanner sc = new Scanner(System.in);

		boolean seguir;
		Consulta consultaActual;
		Usuario usuarioActual;
		String opcion, nombre, apellidos, apellido1, apellido2, dni, nick, pass, titulo, mensaje;;

		final String[] menuLogin = { "Inciar sesion", "Registrarse", "Borrar cuenta", "Salir" };
		final String[] menuCliente = { "Abrir Consulta", "Ver mis consultas", "Cambiar contraseña", "Cerrar sesion" };
		final String[] menuTecnico = { "Todas las consultas", "Consultas nuevas", "Mensajes nuevos","Consultas cerradas",
				"Cambiar contraseña", "Cerrar sesion" };
		final String[] menuConsulta= { "Añadir mensaje", "Borrar Consulta", "Volver Atras" };
		final String[] menuHistorial= { "Volver Atras" };
		final String[] normasNick = { "Debe estar disponible", "Entre 4 y 12 caracteres",
				"Solo numeros, letras y guiones" };
		final String[] normasPass = { "Minimo 8 caracteres", "Mninimo 1 mayuscula", "Minimo 1 minuscula",
				"Minimo un caracter especial", "Sin espacios" };
		final String[] normasNombre = { "Entre 2 y 20 caracteres", "Solo letras" };
		final String[] normasApellidos = { "Entre 2 y 12 caracteres", "Solo letras" };
		final String[] normasDNI = { "Debe empezar por 8 digitos", "Debe terminar con una letra",
				"NIE puede empezar por X, Y o Z" };

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		ArrayList<Consulta> consultas = new ArrayList<Consulta>();
		ArrayList<Consulta> historial = new ArrayList<Consulta>();
		Cliente c = new Cliente("Mario", "jojo", "1", "1", "1");
		Cliente c2 = new Cliente("pepe", "jojo", "1", "1", "1");
		Tecnico t = new Tecnico("Sidorf", "jojo", "1", "1", "1");
		usuarios.add(c);
		usuarios.add(t);
		consultas.add(new Consulta(c, "consulta1"));
		consultas.add(new Consulta(c2, "consulta2"));
		consultas.add(new Consulta(c, "consulta3"));
		consultas.add(new Consulta(c2, "consulta4"));
		consultas.add(new Consulta(c, "consulta5"));
		consultas.get(0).addMensaje(new Mensaje(c,
				"Hola tengo un problema con mi ordenador. Me gustaria hablar con un tecnico. Grasias de antebraso."));
		consultas.get(0).addMensaje(
				new Mensaje(t, "Hola yo sou un tecnico y ademas soy 100tifico, que te sucede loco que te ayudo."));
		consultas.get(0)
				.addMensaje(new Mensaje(c, "Como hago pa meter internet en un usb y llevarmelo donde me agüela."));
		consultas.get(0)
				.addMensaje(new Mensaje(t, "Tienes que arrastrar el icono dentro del usb, mira a ver si funsiona"));
		consultas.get(0).addMensaje(new Mensaje(c, "Vale ya esta ahora como pruebo si funsiona?"));
		consultas.get(0).addMensaje(new Mensaje(t,
				"Mira a ver si tienes una carpeta que se llama system32, es un virus loco, BORRALA YA! TE JACKIARON WEY!"));
		consultas.get(0).addMensaje(new Mensaje(c, "Una despedida sin cabras es un cumpleaños."));

		// Bucle de login
		do {
			// Pintar menu y pedir al usuario un valor para la opcion
			menu.ultimaFila();
			menu.generarMenu("LOGIN", menuLogin);
			menu.pedir("OPCION");
			opcion = sc.nextLine();
			seguir = true;
			// Switch del login
			switch (opcion) {

			case "1":
				menu.pedir("NICK");
				nick = sc.nextLine();
				if (buscarNickUsuario(nick, usuarios)) { // Comprueba que el nick existe dentro de la lista
					menu.pedir("CONTRASEÑA");
					pass = sc.nextLine();
					if (comprobarPass(nick, pass, usuarios)) { // Comprueba pass en relacion al nick
						menu.ultimaFila();
						menu.filaCentrada("Bienvenid@ " + nick);
						menu.ultimaFila();
						usuarioActual = iniciarSesion(nick, usuarios); // Devuelve el usuario en relacion al nick
						/*-------------------------------------------
						 * 				SESION INICIADA
						 * ------------------------------------------
						 */
						do {// Bucle menus principales
							switch (usuarioActual.acceso()) { // Switch para determinar tipo de sesion Tecnico/Cliente
							case 1:
								/*
								 * !!! SESION TECNICO !!!!
								 */
								seguir = true;
								do {
									menu.generarMenu("MENU TECNICO", menuTecnico);
									menu.pedir("OPCION");
									opcion = sc.nextLine();
									switch(opcion) {
									case "1"://Todas las consultas
										seguir = true;
										do {
											menu.mostrarTodasLasConsultas(consultas);
											menu.filaCentrada("Seleciona una consulta");
											do {
												menu.pedir("OPCION");
												opcion = sc.nextLine();
												switch (opcion.toLowerCase()) {
												case "x":
													menu.ultimaFila();
													seguir = false;
													break;
												default:
													if (comprobarOpcionConsulta(usuarioActual, consultas, opcion)) {
														consultaActual = consultas.get(Integer.parseInt(opcion));
														if (consultaActual.isNuevoMensajeCliente()) {
															consultaActual.setNuevoMensajeCliente(false);
														}
														// Menu consulta
														menu.ultimaFila();
														menu.generarMenu(("CONSULTA: " + consultaActual.getTitulo()),
																menuConsulta, consultaActual);
														do {
															menu.pedir("OPCION");
															opcion = sc.nextLine();
															switch (opcion) {
															case "1":// Añadir Mensaje
																do {
																	menu.pedir("MENSAJE");
																	mensaje = sc.nextLine();
																} while (!comprobarMensaje(mensaje));
																consultaActual.addMensaje(
																		new Mensaje(usuarioActual, mensaje));
																menu.ultimaFila();
																menu.filaCentrada("Mensaje enviado");
																menu.ultimaFila();
																opcion = "3";
																break;
															case "2":// Borrar Consulta
																menu.generarConfirmacion(
																		"¿Borrar " + consultaActual.getTitulo() + "?");
																do {
																	seguir = true;
																	menu.pedir("OPCION");
																	opcion = sc.nextLine();
																	switch (opcion) {
																	case "1":// Borrar consulta
																		seguir = false;
																		historial.add(consultaActual);
																		consultas.remove(consultaActual);
																		menu.ultimaFila();
																		menu.filaCentrada("Consulta Borrada");
																		menu.ultimaFila();
																		break;
																	case "2":// No borrar
																		seguir = false;
																		menu.ultimaFila();
																		menu.filaCentrada(
																				"La consulta no ha sido borrada");
																		menu.ultimaFila();
																		break;
																	default:// Error Opcion
																		menu.filaCentrada(
																				"#ERROR: Opcion no reconocida");
																	}
																} while (seguir);
																opcion = "3";
																seguir = true;
																break;
															default:// Salir / Error Opcion
																if (!opcion.equals("3")) {
																	menu.ultimaFila();
																	menu.filaCentrada("#ERROR: Opcion no reconocida");
																}
															}

														} while (!opcion.equals("3"));

													}
												}

												opcion = "x";

											} while (!opcion.toLowerCase().equals("x"));
										} while (seguir);
										seguir = true;
										break;
									case "2"://Consultas nuevas
										seguir = true;
										do {
											menu.mostrarConsultasNuevas(consultas);
											menu.filaCentrada("Seleciona una consulta");
											do {
												menu.pedir("OPCION");
												opcion = sc.nextLine();
												switch (opcion.toLowerCase()) {
												case "x":
													menu.ultimaFila();
													seguir = false;
													break;
												default:
													if (comprobarOpcionConsulta(usuarioActual, consultas, opcion)) {
														consultaActual = consultas.get(Integer.parseInt(opcion));
														if (consultaActual.isNuevoMensajeCliente()) {
															consultaActual.setNuevoMensajeCliente(false);
														}
														// Menu consulta
														menu.ultimaFila();
														menu.generarMenu(("CONSULTA: " + consultaActual.getTitulo()),
																menuConsulta, consultaActual);
														do {
															menu.pedir("OPCION");
															opcion = sc.nextLine();
															switch (opcion) {
															case "1":// Añadir Mensaje
																do {
																	menu.pedir("MENSAJE");
																	mensaje = sc.nextLine();
																} while (!comprobarMensaje(mensaje));
																consultaActual.addMensaje(
																		new Mensaje(usuarioActual, mensaje));
																menu.ultimaFila();
																menu.filaCentrada("Mensaje enviado");
																menu.ultimaFila();
																opcion = "3";
																break;
															case "2":// Borrar Consulta
																menu.generarConfirmacion(
																		"¿Borrar " + consultaActual.getTitulo() + "?");
																do {
																	seguir = true;
																	menu.pedir("OPCION");
																	opcion = sc.nextLine();
																	switch (opcion) {
																	case "1":// Borrar consulta
																		seguir = false;
																		historial.add(consultaActual);
																		consultas.remove(consultaActual);
																		menu.ultimaFila();
																		menu.filaCentrada("Consulta Borrada");
																		menu.ultimaFila();
																		break;
																	case "2":// No borrar
																		seguir = false;
																		menu.ultimaFila();
																		menu.filaCentrada(
																				"La consulta no ha sido borrada");
																		menu.ultimaFila();
																		break;
																	default:// Error Opcion
																		menu.filaCentrada(
																				"#ERROR: Opcion no reconocida");
																	}
																} while (seguir);
																opcion = "3";
																seguir = true;
																break;
															default:// Salir / Error Opcion
																if (!opcion.equals("3")) {
																	menu.ultimaFila();
																	menu.filaCentrada("#ERROR: Opcion no reconocida");
																}
															}

														} while (!opcion.equals("3"));

													}
												}

												opcion = "x";

											} while (!opcion.toLowerCase().equals("x"));
										} while (seguir);
										seguir = true;
										break;
									case "3"://Mensajes nuevos
										seguir = true;
										do {
											menu.mostrarConsultasConMensajesNuevos(consultas);
											menu.filaCentrada("Seleciona una consulta");
											do {
												menu.pedir("OPCION");
												opcion = sc.nextLine();
												switch (opcion.toLowerCase()) {
												case "x":
													menu.ultimaFila();
													seguir = false;
													break;
												default:
													if (comprobarOpcionConsulta(usuarioActual, consultas, opcion)) {
														consultaActual = consultas.get(Integer.parseInt(opcion));
														if (consultaActual.isNuevoMensajeCliente()) {
															consultaActual.setNuevoMensajeCliente(false);
														}
														// Menu consulta
														menu.ultimaFila();
														menu.generarMenu(("CONSULTA: " + consultaActual.getTitulo()),
																menuConsulta, consultaActual);
														do {
															menu.pedir("OPCION");
															opcion = sc.nextLine();
															switch (opcion) {
															case "1":// Añadir Mensaje
																do {
																	menu.pedir("MENSAJE");
																	mensaje = sc.nextLine();
																} while (!comprobarMensaje(mensaje));
																consultaActual.addMensaje(
																		new Mensaje(usuarioActual, mensaje));
																menu.ultimaFila();
																menu.filaCentrada("Mensaje enviado");
																menu.ultimaFila();
																opcion = "3";
																break;
															case "2":// Borrar Consulta
																menu.generarConfirmacion(
																		"¿Borrar " + consultaActual.getTitulo() + "?");
																do {
																	seguir = true;
																	menu.pedir("OPCION");
																	opcion = sc.nextLine();
																	switch (opcion) {
																	case "1":// Borrar consulta
																		seguir = false;
																		historial.add(consultaActual);
																		consultas.remove(consultaActual);
																		menu.ultimaFila();
																		menu.filaCentrada("Consulta Borrada");
																		menu.ultimaFila();
																		break;
																	case "2":// No borrar
																		seguir = false;
																		menu.ultimaFila();
																		menu.filaCentrada(
																				"La consulta no ha sido borrada");
																		menu.ultimaFila();
																		break;
																	default:// Error Opcion
																		menu.filaCentrada(
																				"#ERROR: Opcion no reconocida");
																	}
																} while (seguir);
																opcion = "3";
																seguir = true;
																break;
															default:// Salir / Error Opcion
																if (!opcion.equals("3")) {
																	menu.ultimaFila();
																	menu.filaCentrada("#ERROR: Opcion no reconocida");
																	menu.ultimaFila();
																}
															}

														} while (!opcion.equals("3"));

													}
												}

												opcion = "x";

											} while (!opcion.toLowerCase().equals("x"));
										} while (seguir);
										seguir = true;
										break;
									case "4"://Consultas cerradas
										seguir = true;
										do {
											menu.mostrarHistorial(historial);
											menu.filaCentrada("Seleciona una consulta");
											do {
												menu.pedir("OPCION");
												opcion = sc.nextLine();
												switch (opcion.toLowerCase()) {
												case "x":
													menu.ultimaFila();
													seguir = false;
													break;
												default:
													if (comprobarOpcionConsulta(usuarioActual, historial, opcion)) {
														consultaActual = historial.get(Integer.parseInt(opcion));
														if (consultaActual.isNuevoMensajeCliente()) {
															consultaActual.setNuevoMensajeCliente(false);
														}
														// Menu consulta
														menu.ultimaFila();
														menu.generarMenu(("CONSULTA: " + consultaActual.getTitulo()),
																menuHistorial, consultaActual);
														do {
															menu.pedir("OPCION");
															opcion = sc.nextLine();
															switch (opcion) {
															default:// Salir / Error Opcion
																if (!opcion.equals("1")) {
																	menu.ultimaFila();
																	menu.filaCentrada("#ERROR: Opcion no reconocida");
																	menu.ultimaFila();
																}
															}

														} while (!opcion.equals("1"));

													}
												}

												opcion = "x";

											} while (!opcion.toLowerCase().equals("x"));
										} while (seguir);
										seguir = true;
										break;
									case "5"://Cambiar contraseña
										menu.ultimaFila();
										menu.cabezera("CAMBIO DE CONTRASEÑA");
										menu.generarNormas("CONTRASEÑA", normasPass);
										do {
											seguir = true;
											menu.pedir("CONTRASEÑA ACTUAL");
											pass = sc.nextLine();
											if (comprobarPass(usuarioActual.getNick(), pass, usuarios)) {
												menu.pedir("NUEVA CONTRASEÑA");
												pass = sc.nextLine();
												if (comprobarPass(pass)) {
													seguir = false;
													usuarioActual.setPass(pass);
													menu.ultimaFila();
													menu.filaCentrada("Contraseña cambiada");
													menu.ultimaFila();
												}
											}
										} while (seguir);
										seguir = true;
										break;
									case "6"://Cerrar sesion
										menu.ultimaFila();
										menu.filaCentrada("Fin de sesion");
										seguir = false;
										break;
									default://Error opcion
										menu.ultimaFila();
										menu.filaCentrada("#ERROR: Opcion no reconocida");
										menu.ultimaFila();
									}
								}while(!opcion.equals("6"));
								break;
							case -1:
								/*
								 * !!! SESION CLIENTE !!!!
								 */
								seguir = true;
								do {
									menu.generarMenu("MENU CLIENTE", menuCliente);
									menu.pedir("OPCION");
									opcion = sc.nextLine();
									switch (opcion) {
									case "1":// Abrir consulta
										menu.ultimaFila();
										menu.cabezera("ABRIR CONSULTA");
										// Peticion titulo
										do {
											menu.pedir("TITULO");
											titulo = sc.nextLine();
										} while (!comprobarTitulo(titulo));
										menu.ultimaFila();
										// Peticion Mensaje
										do {
											menu.pedir("MENSAJE");
											mensaje = sc.nextLine();
										} while (!comprobarMensaje(mensaje));
										menu.ultimaFila();
										menu.generarConfirmacion("¿Crear la consulta?");
										seguir = true;
										do {
											menu.pedir("OPCION");
											opcion = sc.nextLine();
											switch (opcion) {
											case "1":
												consultaActual = new Consulta((Cliente) usuarioActual, titulo);
												consultaActual.addMensaje(new Mensaje(usuarioActual, mensaje));
												consultas.add(consultaActual);
												menu.ultimaFila();
												menu.filaCentrada("Consulta creada con exito");
												seguir = false;
												break;
											case "2":
												menu.ultimaFila();
												menu.filaCentrada("La consulta no ha sido creada");
												menu.ultimaFila();
												seguir = false;
												break;
											default:
												if (opcion.equals("4")) {
													opcion = " ";
												}
												menu.ultimaFila();
												menu.filaCentrada("#ERROR: Opcion no reconocida");
												menu.ultimaFila();
											}
										} while (seguir);
										seguir = true;
										break;
									case "2":// Ver consultas
										seguir = true;
										do {
											menu.mostrarConsultas(usuarioActual, (Cliente) usuarioActual, consultas);
											menu.filaCentrada("Seleciona una consulta");
											do {
												menu.pedir("OPCION");
												opcion = sc.nextLine();
												switch (opcion.toLowerCase()) {
												case "x":
													menu.ultimaFila();
													seguir = false;
													break;
												default:
													if (comprobarOpcionConsulta(usuarioActual, consultas, opcion)) {
														consultaActual = consultas.get(Integer.parseInt(opcion));
														if (consultaActual.isNuevoMensajeTecnico()) {
															consultaActual.setNuevoMensajeTecnico(false);
														}
														// Menu consulta
														menu.ultimaFila();
														menu.generarMenu(("CONSULTA: " + consultaActual.getTitulo()),
																menuConsulta, consultaActual);
														do {
															menu.pedir("OPCION");
															opcion = sc.nextLine();
															switch (opcion) {
															case "1":// Añadir Mensaje
																do {
																	menu.pedir("MENSAJE");
																	mensaje = sc.nextLine();
																} while (!comprobarMensaje(mensaje));
																consultaActual.addMensaje(
																		new Mensaje(usuarioActual, mensaje));
																menu.ultimaFila();
																menu.filaCentrada("Mensaje enviado");
																menu.ultimaFila();
																opcion = "3";
																break;
															case "2":// Borrar Consulta
																menu.generarConfirmacion(
																		"¿Borrar " + consultaActual.getTitulo() + "?");
																do {
																	seguir = true;
																	menu.pedir("OPCION");
																	opcion = sc.nextLine();
																	switch (opcion) {
																	case "1":// Borrar consulta
																		seguir = false;
																		historial.add(consultaActual);
																		consultas.remove(consultaActual);
																		menu.ultimaFila();
																		menu.filaCentrada("Consulta Borrada");
																		menu.ultimaFila();
																		break;
																	case "2":// No borrar
																		seguir = false;
																		menu.ultimaFila();
																		menu.filaCentrada(
																				"La consulta no ha sido borrada");
																		menu.ultimaFila();
																		break;
																	default:// Error Opcion
																		menu.filaCentrada(
																				"#ERROR: Opcion no reconocida");
																	}
																} while (seguir);
																opcion = "3";
																seguir = true;
																break;
															default:// Salir / Error Opcion
																if (!opcion.equals("3")) {
																	menu.ultimaFila();
																	menu.filaCentrada("#ERROR: Opcion no reconocida");
																}
															}

														} while (!opcion.equals("3"));

													}
												}

												opcion = "x";

											} while (!opcion.toLowerCase().equals("x"));
										} while (seguir);
										seguir = true;
										break;
									case "3":// Cambiar contraseña
										menu.ultimaFila();
										menu.cabezera("CAMBIO DE CONTRASEÑA");
										menu.generarNormas("CONTRASEÑA", normasPass);
										do {
											seguir = true;
											menu.pedir("CONTRASEÑA ACTUAL");
											pass = sc.nextLine();
											if (comprobarPass(usuarioActual.getNick(), pass, usuarios)) {
												menu.pedir("NUEVA CONTRASEÑA");
												pass = sc.nextLine();
												if (comprobarPass(pass)) {
													seguir = false;
													usuarioActual.setPass(pass);
													menu.ultimaFila();
													menu.filaCentrada("Contraseña cambiada");
													menu.ultimaFila();
												}
											}
										} while (seguir);
										seguir = true;
										break;
									case "4": // Salir
										menu.ultimaFila();
										menu.filaCentrada("Fin de sesion");
										seguir = false;
										break;
									default: // Error opcion
										menu.ultimaFila();
										menu.filaCentrada("#ERROR: Opcion no reconocida");
										menu.ultimaFila();
									}
								} while (!opcion.equals("4"));
								break;
							}
						} while (seguir);
						seguir = true;
						/*---------------------------------------------
						 *              SESION FINALIZADA
						 * --------------------------------------------
						 */
					}
				} else {
					// El usuario no existe
					menu.ultimaFila();
					menu.filaCentrada("#ERROR: Usuario no encontrado");

				}
				break;
			case "2": // Registrarse
				menu.ultimaFila();
				menu.cabezera("REGISTRO");
				// Peticion de Nombre
				menu.generarNormas("NOMBRE", normasNombre);
				do {
					menu.pedir("NOMBRE");
					nombre = sc.nextLine();
				} while (!comprobarNombre(nombre));
				menu.ultimaFila();
				// Peticion de Apellidos
				menu.generarNormas("APELLIDOS", normasApellidos);
				do {
					menu.pedir("PRIMER APELLIDO");
					apellido1 = sc.nextLine();
					menu.pedir("SEGUNDO APELLIDO");
					apellido2 = sc.nextLine();
					apellidos = apellido1 + " " + apellido2;
				} while (!comprobarApellidos(apellido1, apellido2));
				menu.ultimaFila();
				// Peticion de DNI
				menu.generarNormas("DNI", normasDNI);
				do {
					menu.pedir("DNI");
					dni = sc.nextLine();
				} while (!comprobarDNI(dni, usuarios));
				menu.ultimaFila();
				// Peticion de Nick
				menu.generarNormas("NICK", normasNick);
				do {
					menu.pedir("NOMBRE DE USUARIO");
					nick = sc.nextLine();
				} while (!comprobarNick(nick, usuarios));
				menu.ultimaFila();
				// Peticion de Contraseña
				menu.generarNormas("CONTRASEÑA", normasPass);
				do {
					menu.pedir("CONTRASEÑA");
					pass = sc.nextLine();
				} while (!comprobarPass(pass));
				// Confirmacion
				menu.ultimaFila();
				menu.generarConfirmacion("¿Crear la cuenta?", dni, nombre, apellidos, nick, pass);
				seguir = true;
				do {
					menu.pedir("OPCION");
					opcion = sc.nextLine();
					switch (opcion) {
					case "1":
						usuarios.add(new Cliente(nick, pass, nombre, apellidos, dni));
						menu.ultimaFila();
						menu.filaCentrada("Cuenta creada con exito");
						seguir = false;
						break;
					case "2":
						menu.ultimaFila();
						menu.filaCentrada("La Cuenta no ha sido creada");
						seguir = false;
						break;
					default:
						menu.ultimaFila();
						menu.filaCentrada("#ERROR: Opcion no reconocida");
					}
				} while (seguir);
				seguir = true;
				break;
			case "3": // Borrar cuenta
				menu.pedir("NOMBRE DE USUARIO");
				nick = sc.nextLine();
				if (buscarNickUsuario(nick, usuarios)) { // Comprueba que el nick existe dentro de la lista
					menu.pedir("CONTRASEÑA");
					pass = sc.nextLine();
					if (comprobarPass(nick, pass, usuarios)) { // Comprueba pass en relacion al nick
						// Confirmacion
						menu.ultimaFila();
						menu.generarConfirmacion("Borrar la cuenta");
						menu.pedir("OPCION");
						opcion = sc.nextLine();
						switch (opcion) {
						case "1":
							usuarios.remove(iniciarSesion(nick, usuarios)); // Borra el usuario que coincida con el nick
							menu.ultimaFila();
							menu.filaCentrada("Cuenta borrada con exito");
							break;
						case "2":
							menu.ultimaFila();
							menu.filaCentrada("La Cuenta no ha sido borrada");
							break;
						default:
							menu.ultimaFila();
							menu.filaCentrada("#ERROR: Opcion no reconocida");
						}
					}
				}
				break;
			case "4": // Salir
				menu.ultimaFila();
				menu.filaCentrada("Fin del programa");
				menu.ultimaFila();
				seguir = false;
				break;
			default: // Error opcion
				menu.ultimaFila();
				menu.filaCentrada("#ERROR: Opcion no reconocida");

			}

		} while (seguir);
		sc.close();
	}

	public static boolean buscarNickUsuario(String nick, ArrayList<Usuario> usuarios) { // Busca el nick en la lista de
																						// usuarios
		Iterator<Usuario> it = usuarios.iterator();
		boolean control = false;
		while (it.hasNext()) {
			Usuario user = it.next();
			if (user.getNick().equalsIgnoreCase(nick)) {
				control = true;
			}
		}
		return control;
	}

	public static boolean buscarDNIUsuario(String dni, ArrayList<Usuario> usuarios) { // Busca el nick en la lista de
																						// usuarios
		Iterator<Usuario> it = usuarios.iterator();
		boolean control = false;
		while (it.hasNext()) {
			Usuario user = it.next();
			if (user.getDni().equalsIgnoreCase(dni)) {
				control = true;
			}
		}
		return control;
	}

	public static boolean comprobarPass(String nick, String pass, ArrayList<Usuario> usuarios) { // Comprueba la pass en
																									// relacion al nick
		Iterator<Usuario> it = usuarios.iterator();
		Menu menu = new Menu();
		boolean control = false;
		while (it.hasNext()) {
			Usuario user = it.next();
			if (user.getNick().equalsIgnoreCase(nick)) {
				if (user.getPass().equals(pass)) {
					control = true;
				} else {
					menu.ultimaFila();
					menu.filaCentrada("#ERROR: Contraseña incorrecta");
				}
			}

		}
		return control;
	}

	public static Usuario iniciarSesion(String nick, ArrayList<Usuario> usuarios) { // Devuelve el usuario en relacion
																					// al nick
		Iterator<Usuario> it = usuarios.iterator();
		Usuario user = new Usuario();
		while (it.hasNext()) {
			Usuario user2 = it.next();
			if (user2.getNick().equalsIgnoreCase(nick)) {
				user = user2;
			}
		}
		return user;
	}

	public static boolean comprobarNick(String nick, ArrayList<Usuario> usuarios) {
		Menu menu = new Menu();
		/*
		 * "^[a-zA-Z0-9_-]{4,12}$"
		 * 
		 * [a-zA-Z0-9_-] -> Solo permite letras (aA - zZ), digitos (0-9), '-' y '_'.
		 * {4,12} -> Minimo una minuscula.
		 */
		boolean control = false;
		if (!buscarNickUsuario(nick, usuarios)) { // Busca para saber si el usuario existe

			Pattern pat = Pattern.compile("^[a-zA-Z0-9_-]{4,12}$");
			Matcher mat = pat.matcher(nick);
			if (mat.matches()) {
				control = true;
			} else {
				menu.filaCentrada("#Error: El nick no es valido");
			}
		} else {
			menu.filaCentrada("#Error: El nick ya esta en uso");
		}
		return control;
	}

	public static boolean comprobarPass(String pass) {
		/*
		 * "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"
		 * 
		 * (?=.*[0-9]) -> Minimo un digito. (?=.*[a-z]) -> Minimo una minuscula.
		 * (?=.*[A-Z]) -> Minimo una mayuscula. (?=.*[@#$%^&+=]) -> Minimo un caracter
		 * especial. (?=\\S+$) -> No se permiten espacios en blanco. .{8,} -> Minimo 8
		 * caracteres.
		 */
		Menu menu = new Menu();
		boolean control = false;
		Pattern pat = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher mat = pat.matcher(pass);
		if (mat.matches()) {
			control = true;
		} else {
			menu.filaCentrada("#Error: No se cumple el formato");
		}
		return control;
	}

	public static boolean comprobarDNI(String dni, ArrayList<Usuario> usuarios) {
		/*
		 * "\\d{8}[a-hj-np-tv-z-A-HJ-NP-TV-Z]" \\d{8} -> La cadena empieza por 8
		 * digitos. [A-HJ-NP-TV-Z] -> El ultimo caracter debe ser una letra salvo(Ii |
		 * Oo | Uu).
		 */
		Menu menu = new Menu();
		boolean control = false;
		char pL = dni.charAt(0);// primer caracter de la cadena, necesario para comprobar NIE's de extranjeros
								// que empiezan por x, y, o z.
		char[] letras = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H',
				'L', 'C', 'K', 'E', 'T' };
		if (!buscarDNIUsuario(dni, usuarios)) {
			switch (pL) {

			case 'x' | 'X':
				dni = "0" + dni.substring(1);
				break;
			case 'y' | 'Y':
				dni = "1" + dni.substring(1);
				break;
			case 'z' | 'Z':
				dni = "2" + dni.substring(1);
				break;

			}
			Pattern pat = Pattern.compile("\\d{8}[a-hj-np-tv-z-A-HJ-NP-TV-Z]");
			Matcher mat = pat.matcher(dni);
			if (mat.matches()) {
				int nDNI = Integer.parseInt(dni.substring(0, 8));
				int resto = nDNI % 23;
				char letra = dni.charAt(8);
				if (Character.toUpperCase(letra) == letras[resto]) {
					control = true;
				} else {
					System.out.println(dni.substring(0, 8));
					System.out.println(letra);
					System.out.println(letras[resto]);
					System.out.println(nDNI);
					System.out.println(resto);
					menu.filaCentrada("#Error: La letra del DNI no coincide");
				}
			} else {
				menu.filaCentrada("#Error: El DNI no es valido");
			}
		} else {
			menu.filaCentrada("#Error: El DNI ya esta en uso");
		}

		return control;
	}

	public static boolean comprobarNombre(String nombre) {
		/*
		 * "^[a-zA-Z]{2,16}$"
		 * 
		 * [a-zA-] -> Solo permite letras (aA - zZ). {2,16} -> Entre 2 y 16 caracteres.
		 */
		Menu menu = new Menu();
		boolean control = false;
		Pattern pat = Pattern.compile("^[a-zA-Z\\s]{2,20}$");
		Matcher mat = pat.matcher(nombre);
		if (mat.matches()) {
			control = true;
		} else {
			menu.filaCentrada("#Error: No se cumple el formato");
		}
		return control;
	}

	public static boolean comprobarApellidos(String apellido1, String apellido2) {
		/*
		 * "^[a-zA-Z]{2,16}$"
		 * 
		 * [a-zA-] -> Solo permite letras (aA - zZ). {2,16} -> Entre 2 y 12 caracteres.
		 */
		Menu menu = new Menu();
		boolean control = false;
		Pattern pat = Pattern.compile("^[a-zA-Z]{2,12}$");
		Matcher mat1 = pat.matcher(apellido1);
		Matcher mat2 = pat.matcher(apellido2);
		if (mat1.matches() && mat2.matches()) {
			control = true;
		} else {
			menu.filaCentrada("#Error: No se cumple el formato");
		}
		return control;
	}

	public static boolean comprobarTitulo(String titulo) {
		Menu menu = new Menu();
		boolean control = false;
		Pattern pat = Pattern.compile("[a-zA-Z-_@#$%^&+=\\s]{2,30}$");
		Matcher mat = pat.matcher(titulo);
		if (mat.matches()) {
			control = true;
		} else {
			menu.filaCentrada("#Error: No se cumple el formato");
		}
		return control;
	}

	public static boolean comprobarMensaje(String mensaje) {
		Menu menu = new Menu();
		boolean control = false;
		if (!mensaje.equals("")) {
			control = true;
		} else {
			menu.filaCentrada("#Error: No se cumple el formato");
		}
		return control;
	}

	public static boolean comprobarOpcionConsulta(Usuario user, ArrayList<Consulta> consultas, String opcion) {
		Menu menu = new Menu();
		boolean control = false;
		int index;
		Pattern pat = Pattern.compile("[0-9]");
		Matcher mat = pat.matcher(opcion);
		if (mat.matches()) {
			index = Integer.parseInt(opcion);
			if (consultas.size() > index) {
				if (consultas.get(index).getCliente().equals(user) || user.acceso() == 1) {
					control = true;
				} else {
					menu.filaCentrada("#Error: Acceso denegado");
				}

			} else {
				menu.filaCentrada("#Error: La consulta no existe");
			}
		} else {
			menu.filaCentrada("#Error: Opcion no reconocida");
		}
		return control;
	}

}
