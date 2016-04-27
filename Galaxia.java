package DEV;

import java.util.*;
import java.io.*;

import DEV.Personaje.dir;

/**
 * Declaracion de la clase Galaxia
 * 
 * @author <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
 *         <b> Asignatura Desarrollo de Programas</b><br>
 *         <b> Curso 15/16 </b>
 */
public class Galaxia {

	private int alto;
	private int ancho;
	private int id_salida;
	private Puerta puertaGal;
	private Estacion[][] listaEstaciones;
	private Queue<Midi> listaMidiGal;
	private Grafo grafoGal;
	private String nombreFichero;

	/**
	 * Constructor default de la clase Galaxia <br>
	 * Carga los datos desde el fichero "test"
	 * 
	 * @throws FileNotFoundException,
	 *             IOException, ConfigNoValida
	 * 
	 */
	Galaxia(String _nombreFichero) throws IOException, FileNotFoundException, ConfigNoValida {

		try {
			configurarDeFichero(_nombreFichero);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (ConfigNoValida e) {
			System.out.println("CONFIGURACION DE LA GALAXIA INVALIDA, POR FAVOR REVISE EL FICHERO");
			throw e;
		}
	}

	/**
	 * Constructor parametrizado de la clase Galaxia
	 * 
	 * @param alto
	 *            Numero de columnas horizontales en la matriz galaxia
	 * @param ancho
	 *            Numero de columnas verticales en la matriz galaxia
	 * @param id_salida
	 *            Estacion en la que se encuentra la puerta de salida
	 * 
	 */
	Galaxia(int alto, int ancho, int id_salida) {

		this.alto = alto;
		this.ancho = ancho;
		puertaGal = new Puerta();
		listaEstaciones = new Estacion[alto][ancho];
		listaMidiGal = new LinkedList<Midi>();
		int contador = 0;

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				listaEstaciones[i][j] = new Estacion(contador, 0);
				contador++;

				if (contador == id_salida) {
					listaEstaciones[i][j].setPuertaSalida(true);
				}

			}

		}

	}

	/**
	 * Metodo para buscar una estacion en la galaxia
	 * 
	 * @param id
	 *            Id de la estacion a buscar
	 * 
	 * @return Objeto de la clase Estacion
	 * 
	 */
	protected Estacion buscarEstacion(int id) {

		return listaEstaciones[id / ancho][id % ancho];

	}

	/**
	 * Metodo para generar los midiclorianos que posteriormente se repartiran
	 * entre las estaciones de la galaxia
	 * 
	 */
	private void almacenarMidi() {

		// Generar y almacenar midiclorianos
		for (int i = 0; i < 30; i++) {

			listaMidiGal.add(new Midi(i));

			if (i % 2 == 1) {
				listaMidiGal.add(new Midi(i)); // Agregar uno extra si es impar
			}

		}

	}

	/**
	 * Metodo para repartir los midiclorianos que se encuentran en la lista de
	 * la galaxia
	 * 
	 */
	private void repartirMidi() {

		Estacion estAux;
		int[] vRuta = grafoGal.asignarMidis(id_salida);

		estAux = buscarEstacion(0); // El origen no esta en la ruta, asique lo
									// aniadimos a mano

		for (int x = 0; x < 5 && listaMidiGal.isEmpty() == false; x++) {

			estAux.aniadirMidi(listaMidiGal.poll());

		}

		// Repartir entre estaciones, siguiendo la ruta de los FamiliaReal

		for (int i = 0; i < vRuta.length; i++) {

			estAux = buscarEstacion(vRuta[i]);

			for (int j = 0; j < 5 && listaMidiGal.isEmpty() == false; j++) {

				estAux.aniadirMidi(listaMidiGal.poll());

			}
		}
	}

	/**
	 * Metodo para procesar el fichero, cambiar los atributos de la galaxia y
	 * generar los personajes correspondientes.
	 * 
	 * @throws FileNotFoundException,
	 *             IOException, ConfigNoValida
	 * 
	 */
	private void configurarDeFichero(String _nombreFichero) throws FileNotFoundException, IOException, ConfigNoValida {

		System.out.println(" ");
		System.out.println("Intentando leer fichero :");

		BufferedReader flujo = new BufferedReader(new FileReader(_nombreFichero));
		String linea = flujo.readLine();

		List<String> vCampos = new ArrayList<String>();
		@SuppressWarnings("unused")
		int numCampos = 0; // Utilizado para la funcion trocearLineaInicial
		@SuppressWarnings("unused")
		char x; // Utilizado para extraer un char
		boolean galCreada = false; // Utilizado para evitar crear 2 galaxias en
									// la misma ejecucion
		Personaje persAux; // Utilizado para introducir el camino a los
							// personajes

		if (flujo.ready() == false) {
			System.out.println("Problemas con el buffer (flujo)...");
		} else {
			while (linea != null) {
				if (linea.contains("--") == false) // Solo tomar en cuenta lineas sin comentarios
				{
					//Metemos la linea en vCampos
					vCampos.clear();
					numCampos = trocearLineaInicial(linea, vCampos);
				}

				linea = flujo.readLine(); // Avanzar en el fichero

				if (vCampos.isEmpty() == false) {
					switch (vCampos.get(0)) { //Miramos de que tipo es el campo
					case ("GALAXIA"):
						try {
							//Comprobamos que los parámetros sean válidos (ancho,alto mayores que 0, salida dentro de la galaxia)
							if (Integer.parseInt(vCampos.get(1)) <= 0 
									|| Integer.parseInt(vCampos.get(2)) <= 0
									|| Integer.parseInt(vCampos.get(3)) < 0
									|| Integer.parseInt(vCampos.get(3)) > (Integer.parseInt(vCampos.get(1))
											* Integer.parseInt(vCampos.get(2))))
								throw new ConfigNoValida();
							
							if (galCreada == false) {
								
								mostrarDatosInicial(vCampos);
								//Obtenemos alto, ancho y salida
								this.alto = Integer.parseInt(vCampos.get(1));
								this.ancho = Integer.parseInt(vCampos.get(2));
								this.id_salida = Integer.parseInt(vCampos.get(3));
								
								// Midis para la combinacion de la puerta
								List<Midi> listCfg = new LinkedList<Midi>(); 
								for (int i = 1; i < 30; i = i + 2)
									listCfg.add(new Midi(i));

								//Configuramos la puerta
								puertaGal = new Puerta();
								puertaGal.setProfundidad(Integer.parseInt(vCampos.get(4)));
								puertaGal.setCombinacionList(listCfg);

								//Creamos las estaciones
								listaEstaciones = new Estacion[alto][ancho];
								listaMidiGal = new LinkedList<Midi>();

								for (int i = 0; i < alto; i++){
									for (int j = 0; j < ancho; j++){
										listaEstaciones[i][j] = new Estacion(j + ancho * i, 0);
									}
								}

								buscarEstacion(id_salida).setPuertaSalida(true);

								//Creamos el grafo
								grafoGal = new Grafo(alto, ancho, id_salida);
								grafoGal.procesarParedes(ancho, 12345); //Semilla

								//Impedimos que se cree más de una galaxia
								galCreada = true;
							}

						} catch (ConfigNoValida e) {
							throw e;
						}

						break;
					
					case ("FAMILIAREAL"):
						mostrarDatosInicial(vCampos);
						persAux = new FamiliaReal(vCampos.get(1), x = vCampos.get(2).charAt(0),
								Integer.parseInt(vCampos.get(3)), 0, this);
						
						listaEstaciones[0][0].aniadirPj(persAux);
						break;

					case ("JEDI"):
						mostrarDatosInicial(vCampos);
						persAux = new Jedi(vCampos.get(1), x = vCampos.get(2).charAt(0),
								Integer.parseInt(vCampos.get(3)), 0,this);
						
						listaEstaciones[0][0].aniadirPj(persAux);
						break;

					case ("IMPERIAL"):
						mostrarDatosInicial(vCampos);
						persAux = new Imperial(vCampos.get(1), x = vCampos.get(2).charAt(0),
								Integer.parseInt(vCampos.get(3)), (alto * ancho) - 1,this);
						
						listaEstaciones[alto - 1][ancho - 1].aniadirPj(persAux);
						break;

					case ("CONTRABANDISTA"):
						mostrarDatosInicial(vCampos);
						persAux = new Contrabandista(vCampos.get(1), x = vCampos.get(2).charAt(0),
								Integer.parseInt(vCampos.get(3)), (alto * ancho) - ancho,this);
						listaEstaciones[alto - 1][0].aniadirPj(persAux);
						break;
					default:
						break;
					} // FIN SWITCH

				} // Fin IF

			} // Fin WHILE

			flujo.close();
			almacenarMidi();
			repartirMidi();
		} // Fin ELSE
	}

	/**
	 * Metodo para trocear cada linea y separarla por campos
	 * 
	 * @param S
	 *            cadena con la linea completa leida
	 * @param vCampos
	 *            Array de String que contiene en cada posicion un campo
	 *            distinto
	 * 
	 * @return numCampos Numero de campos encontrados
	 * 
	 */
	private int trocearLineaInicial(String S, List<String> vCampos) {

		boolean eol = false;
		int pos = 0, posini = 0, numCampos = 0;

		while (!eol) {

			pos = S.indexOf("#");
			if (pos != -1) {
				vCampos.add(new String(S.substring(posini, pos)));
				S = S.substring(pos + 1, S.length());
				numCampos++;
			} else
				eol = true;
		}
		return numCampos;
	}

	/**
	 * Metodo para mostrar los campos en los que se ha dividido una linea
	 * 
	 * @param vCampos
	 *            Array de String que contiene en cada posicion un campo
	 *            distinto
	 * 
	 */
	private void mostrarDatosInicial(List<String> vCampos) {
		System.out.println(vCampos);
	}

	/**
	 * Metodo para mostrar la matriz de la galaxia (formada por la id de las
	 * estaciones)
	 * 
	 */
	public void mostrarGalaxia() {

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				System.out.print(listaEstaciones[i][j].getId() + " ");

				if (listaEstaciones[i][j].getId() < 10) {
					System.out.print(" "); // +bonito
				}

				if (j == ancho - 1) {
					System.out.println(" ");
				}

			}
		}

	}

	/**
	 * Metodo para mostrar los Midi que no han sido asignados a ninguna estacion
	 * 
	 */
	public void mostrarMidiGal() {

		Iterator<Midi> it = listaMidiGal.iterator();

		if (it.hasNext() == true) {
			System.out.println("Mostrando midiclorianos de la galaxia...");
		} else {
			System.out.println("No hay midiclorianos en la galaxia");
		}

		while (it.hasNext() == true) {

			System.out.print(it.next().toString() + " ");

		}

		System.out.println();

	}

	/**
	 * Metodo para mostrar toda la informacion relacionada con un turno
	 * 
	 * @param turno
	 *            Numero del turno en el que se encuentra la simulacion
	 * 
	 */
	private void infoTurno(int turno) {

		System.out.println();
		System.out.println("(turno:" + turno + ")");
		System.out.println("(galaxia:" + (alto * ancho - 1) + ")");

		System.out.print("(puerta:");
		if (puertaGal.isEstado() == true) {
			System.out.print("abierta:" + puertaGal.getProfundidad() + ":");
			puertaGal.getProbados().inOrder();
			System.out.print(")");
		} else {
			System.out.print("cerrada:" + puertaGal.getProfundidad() + ":");
			puertaGal.getProbados().inOrder();
			System.out.print(")");
		}

		// ------------------------------------
		// Mostrar el mapa de la Galaxia
		// ------------------------------------

		int[] vArcos;
		int x = 0;
		int y = 0;

		// TODO TOCA CAMBIAR ESTO PARA QUE USE PAIRS, DEVOLVER ARCOS VA A
		// DEVOLVER PAIRS
		// vArcos = grafoGal.devolverArcos(); // Le pasamos los arcos que
		// existen en el grafo, para no dibujar '|' o '_' k no correspondan
		Boolean arcoDer = false; // Para comprobar si hay un arco a la derecha
									// del nodo actual
		Boolean arcoBajo = true; // Para comprobar si hay un arco debajo del
									// nodo actual

		System.out.println();
		System.out.print(" ");

		for (int i = 0; i < (ancho * 2) - 1; i++) {

			System.out.print('_');
		}

		System.out.println();

		for (int i = 0; i < alto; i++) {

			System.out.print('|');

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

				if (listaEstaciones[i][j].hayPersonajes() == true) {
					if (listaEstaciones[i][j].cuantosPJ() > 1) {
						System.out.print(listaEstaciones[i][j].cuantosPJ()); 
						// Si hay mas de 2 personajes en la misma estacion, muestra cuántos hay
					} else {
						System.out.print(listaEstaciones[i][j].sacarPJ().getMarcaId());
					}
				} else {
					y = 0;
					arcoBajo = false;

					while (y < vArcos.length && arcoBajo == false) {

						if (vArcos[y] == i * ancho + j) {
							if (vArcos[y + 1] == (vArcos[y] + ancho)) // Si
																		// [y+1]
																		// es ==
																		// [y]+ancho,
																		// entonces
																		// se
																		// trate
																		// de un
																		// arco
																		// con
																		// la
																		// estacion
																		// de
																		// debajo
							{
								System.out.print(' ');
								arcoBajo = true;
							}
						}

						y = y + 2; // Los arcos estan en parejas en el vector
					} // Fin while arcoBajo
				}

				if (arcoBajo == false)
					System.out.print('_'); // Imprimir antes de comprobar arcos
											// a la der, por errores y eso

				x = 0;
				arcoDer = false;

				while (x < vArcos.length && arcoDer == false) {

					if (vArcos[x] == i * ancho + j) {
						if (vArcos[x + 1] == (vArcos[x] + 1)) // Si [x+1] es ==
																// [x]+1,
																// entonces se
																// trate de un
																// arco con la
																// estacion a la
																// derecha
						{
							System.out.print(' ');
							arcoDer = true;
						}
					}

					x = x + 2; // Los arcos estan en parejas en el vector
				} // Fin while arcoDer

				if (arcoDer == false)
					System.out.print('|');

			} // Fin for j

			System.out.println();

		} // Fin for i

		System.out.print(" ");

		System.out.println();

		// ------------------------------------
		// Mostrar estaciones con midiclorianos
		// ------------------------------------

		System.out.println();

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				if (listaEstaciones[i][j].hayMidis() == true) {
					System.out.print("(estacion:" + listaEstaciones[i][j].getId() + ": ");
					listaEstaciones[i][j].mostrarLista();
					System.out.print(")");
					System.out.println();
				}
			}
		}

		// ------------------------------------
		// Mostrar personajes en la galaxia
		// ------------------------------------

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				listaEstaciones[i][j].mostrarPersonajes();
				}
			}
		}

	

	/**
	 * Metodo para almacenar los datos de un turno en un fichero externo llamado
	 * "logDatos.txt"
	 * 
	 * @param turno
	 *            Numero del turno en el que se encuentra la simulacion
	 * @param fin
	 *            Booleano para indicar si ha terminado o no la simulacion
	 * 
	 * @throws IOException
	 * 
	 */
	private void datosAFichero(int turno, boolean fin) throws IOException {

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("registro.log", true))); // Iniciar
																										// flujo
																										// (true
																										// para
																										// que
																										// el
																										// archivo
																										// añada
																										// datos
																										// al
																										// final)
		Personaje persAux; // Para extraer datos de los personajes

		if (turno == 0) // Imprimir todos los caminos de los personajes al
						// comienzo de la simulacion
		{
			Iterator<Personaje> it = buscarEstacion(0).getColaPers().iterator(); // Para
																			// los
																			// JEDI
																			// y
																			// FR

			while (it.hasNext() == true) {

				persAux = it.next();
				out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
			}

			it = buscarEstacion(alto * ancho - ancho).getColaPers().iterator(); // Para
																			// los
																			// JEDI
																			// y
																			// FR

			while (it.hasNext() == true) {

				persAux = it.next();
				out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
			}

			it = buscarEstacion(alto * ancho - 1).getColaPers().iterator(); // Para
																		// los
																		// JEDI
																		// y FR

			while (it.hasNext() == true) {

				persAux = it.next();
				out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
			}

		}

		if (fin == false) {

			out.println("===============================================");
			out.println("(turno:" + turno + ")");
			out.println("(galaxia:" + (alto * ancho - 1) + ")");

			out.print("(puerta:");
			if (puertaGal.isEstado() == true) {
				out.print("abierta:" + puertaGal.getProfundidad() + ":");
				out.print(puertaGal.getProbados().arbolAString());
				out.print(")");
			} else {
				out.print("cerrada:" + puertaGal.getProfundidad() + ":");
				out.print(puertaGal.getProbados().arbolAString());
				out.print(")");
			}

			// ------------------------------------
			// Mostrar el mapa de la Galaxia
			// ------------------------------------

			int[] vArcos;
			int x = 0;
			int y = 0;
			// TODO 2.0 Attack of the Pairs
			// vArcos = grafoGal.devolverArcos(); // Le pasamos los arcos que
			// existen en el grafo, para no dibujar '|' o '_' k no correspondan
			Boolean arcoDer = false;
			Boolean arcoBajo = true;

			out.println();
			out.print(" ");

			for (int i = 0; i < (ancho * 2) - 1; i++) {

				out.print('_');
			}

			out.println();

			for (int i = 0; i < alto; i++) {

				out.print('|');

				for (int j = 0; j < ancho; j++) {

					arcoBajo = true;

					if (listaEstaciones[i][j].hayPersonajes() == true) {
						if (listaEstaciones[i][j].cuantosPJ() > 1) {
							out.print(listaEstaciones[i][j].cuantosPJ()); // Si
																				// hay
																				// mas
																				// de
																				// 2
																				// personajes
																				// en
																				// la
																				// misma
																				// estacion,
																				// mostrar
																				// el
																				// numero
																				// de
																				// los
																				// mismos
						} else {
							out.print(listaEstaciones[i][j].sacarPJ().getMarcaId());
						}
					} else {
						y = 0;
						arcoBajo = false;

						while (y < vArcos.length && arcoBajo == false) {

							if (vArcos[y] == i * ancho + j) {
								if (vArcos[y + 1] == (vArcos[y] + ancho)) // Si
																			// [y+1]
																			// es
																			// ==
																			// [y]+ancho,
																			// entonces
																			// se
																			// trate
																			// de
																			// un
																			// arco
																			// con
																			// la
																			// estacion
																			// de
																			// debajo
								{
									out.print(' ');
									arcoBajo = true;
								}
							}

							y = y + 2; // Los arcos estan en parejas en el
										// vector
						} // Fin while arcoBajo
					}

					if (arcoBajo == false)
						out.print('_'); // Imprimir antes de comprobar arcos a
										// la der, por errores y eso

					x = 0;
					arcoDer = false;

					while (x < vArcos.length && arcoDer == false) {

						if (vArcos[x] == i * ancho + j) {
							if (vArcos[x + 1] == (vArcos[x] + 1)) // Si [x+1] es
																	// == [x]+1,
																	// entonces
																	// se trate
																	// de un
																	// arco con
																	// la
																	// estacion
																	// a la
																	// derecha
							{
								out.print(' ');
								arcoDer = true;
							}
						}

						x = x + 2; // Los arcos estan en parejas en el vector
					} // Fin while arcoDer

					if (arcoDer == false)
						out.print('|');

				} // Fin for j

				out.println();

			} // Fin for i

			out.print(" ");

			out.println();

			// ------------------------------------
			// Mostrar estaciones con midiclorianos
			// ------------------------------------

			out.println();

			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					Iterator<Midi> it = listaEstaciones[i][j].getListaMidiEst().iterator();

					if (it.hasNext() == true) {
						out.print("(estacion:" + listaEstaciones[i][j].getId() + ": ");

						while (it.hasNext() == true) {

							out.print(it.next().toString() + " ");
						}

						out.print(")");
						out.println();
					}
				}
			}

			// ------------------------------------
			// Mostrar personajes en la galaxia
			// ------------------------------------

			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					Iterator<Personaje> it = listaEstaciones[i][j].getColaPers().iterator();

					while (it.hasNext() == true) {

						out.println(it.next().toString());
					}
				}
			}

		} else // Si ha terminado la simulacion, imprimir lo siguiente
		{
			// La simulacion ha terminado
			out.println();
			out.println("---------------------------");
			out.println("La simulación ha terminado|");
			out.println("---------------------------");

			out.println("La puerta de salida se encontraba en la estación " + id_salida);

			out.print("Estado de la puerta: ");
			if (puertaGal.isEstado() == true) {
				out.println("ABIERTA");
			} else {
				out.println("CERRADA");
			}

			out.print("La combinación es:");
			out.println(puertaGal.mostrarVectorCfgString());
			out.println(" y su profundidad es " + puertaGal.getProfundidad());
			out.print("Se han probado los midiclorianos: " + puertaGal.getProbados().arbolAString());
			out.println();
			out.println();

			// ----------------------
			// Mostrando estaciones
			// ----------------------
			out.println("Id Estación | Midiclorianos restantes");

			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					if (listaEstaciones[i][j].hayMidis() == true) {
						out.print("     " + listaEstaciones[i][j].getId() + "      | ");
						out.print(listaEstaciones[i][j].mostrarListaStr());
						out.println();
					}
				}
			}

			// ----------------------
			// Mostrando personajes
			// ----------------------

			out.println();
			out.println("Personajes:");
			out.println("-------------------------------------------");

			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					Iterator<Personaje> it = listaEstaciones[i][j].getColaPers().iterator();

					while (it.hasNext()) {

						persAux = it.next();
						out.println(persAux.toString());
					}
				}
			}
			out.println("-------------------------------------------");

			// ----------------------
			// Mostrando ganadores
			// ----------------------
			out.println();
			out.println("Ganadores:");
			out.println("-------------------------------------------");

			if (puertaGal.isEstado() == true) // Si puerta abierta, ganan los
												// rebeldes
			{
				Iterator<Personaje> it = buscarEstacion(id_salida).getColaPers().iterator();

				while (it.hasNext() == true) {

					out.print("Ganador/es: ");
					persAux = it.next();
					out.println(persAux.toString());
				}
			} else // Si puerta cerrada, los imperiales, que pueden estar en
					// cualquier estacion de la galaxia
			{
				for (int i = 0; i < alto; i++) {

					for (int j = 0; j < ancho; j++) {

						Iterator<Personaje> it = listaEstaciones[i][j].getColaPers().iterator();

						while (it.hasNext()) {

							persAux = it.next();
							if (persAux.getTipoPj() == "Imperial" || persAux.getTipoPj() == "ImperialEste") // Solo
																											// los
																											// imperiales
																											// ganan
							{
								out.println(persAux.toString());
							}
						}
					}
				}
			}

			out.println("-------------------------------------------");
		}

		out.close(); // Cerrar flujo

	}

	private boolean activarEstaciones() {
		boolean fin = false;
		for (int i = 0; i<ancho && fin == false;i++){
			for (int j = 0; j<alto && fin == false;j++){
				fin = listaEstaciones[i][j].activarPJ(this);
			}
		}
		return fin;
	}
	
	private void reiniciarTurno(){
		for (int i = 0; i<ancho;i++){
			for (int j = 0; j<alto;j++){
				listaEstaciones[i][j].reiniciarTurnoPj();
			}
		}
	}
	
	private void simular() {
		boolean fin = false;
		for (int turno = 0;turno < 50 && fin == false; turno++){
			fin = activarEstaciones();
			if (fin == false)
				reiniciarTurno();
			infoTurno(turno);
			try {
				datosAFichero(turno,fin); //TODO Este booleano que se hace
			} catch (IOException e) {}
		}
		mostrarFin();
	}

	private void mostrarFin() {
		
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("La simulación ha terminado|");
		System.out.println("---------------------------");

		System.out.println("La puerta de salida se encontraba en la estación " + id_salida);

		System.out.print("Estado de la puerta: ");
		if (puertaGal.isEstado() == true) {
			System.out.println("ABIERTA");
		} else {
			System.out.println("CERRADA");
		}

		System.out.print("La combinación es:");
		puertaGal.mostrarVectorCfg();
		System.out.println(" y su profundidad es " + puertaGal.getProfundidad());
		System.out.print("Se han probado los midiclorianos: ");
		puertaGal.getProbados().inOrder();
		System.out.println();

		// ----------------------
		// Mostrando tablero final
		// ----------------------
		System.out.println("El tablero al finalizar la simulación es el siguiente:");
		int[] vArcos;
		int x = 0;
		int y = 0;
		// vArcos = grafoGal.devolverArcos(); // Le pasamos los arcos que
		// existen en el grafo, para no dibujar '|' o '_' k no correspondan
		// TODO 3.0 Revenge of the Pair
		Boolean arcoDer = false; // Para comprobar si hay un arco a la derecha
									// del nodo actual
		Boolean arcoBajo = true; // Para comprobar si hay un arco debajo del
									// nodo actual

		System.out.println();
		System.out.print(" ");

		for (int i = 0; i < (ancho * 2) - 1; i++) {

			System.out.print('_');
		}

		System.out.println();

		for (int i = 0; i < alto; i++) {

			System.out.print('|');

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

				if (listaEstaciones[i][j].hayPersonajes() == true) {
					if (listaEstaciones[i][j].cuantosPJ() > 1) {
						System.out.print(listaEstaciones[i][j].cuantosPJ()); // Si
																					// hay
																					// mas
																					// de
																					// 2
																					// personajes
																					// en
																					// la
																					// misma
																					// estacion,
																					// mostrar
																					// el
																					// numero
																					// de
																					// los
																					// mismos
					} else {
						System.out.print(listaEstaciones[i][j].sacarPJ().getMarcaId());
					}
				} else {
					y = 0;
					arcoBajo = false;

					while (y < vArcos.length && arcoBajo == false) {

						if (vArcos[y] == i * ancho + j) {
							if (vArcos[y + 1] == (vArcos[y] + ancho)) // Si
																		// [y+1]
																		// es ==
																		// [y]+ancho,
																		// entonces
																		// se
																		// trate
																		// de un
																		// arco
																		// con
																		// la
																		// estacion
																		// de
																		// debajo
							{
								System.out.print(' ');
								arcoBajo = true;
							}
						}

						y = y + 2; // Los arcos estan en parejas en el vector
					} // Fin while arcoBajo
				}

				if (arcoBajo == false)
					System.out.print('_'); // Imprimir antes de comprobar arcos
											// a la der, por errores y eso

				x = 0;
				arcoDer = false;

				while (x < vArcos.length && arcoDer == false) {

					if (vArcos[x] == i * ancho + j) {
						if (vArcos[x + 1] == (vArcos[x] + 1)) // Si [x+1] es ==
																// [x]+1,
																// entonces se
																// trate de un
																// arco con la
																// estacion a la
																// derecha
						{
							System.out.print(' ');
							arcoDer = true;
						}
					}

					x = x + 2; // Los arcos estan en parejas en el vector
				} // Fin while arcoDer

				if (arcoDer == false)
					System.out.print('|');

			} // Fin for j

			System.out.println();

		} // Fin for i

		System.out.print(" ");

		System.out.println();

		System.out.println();

		// ----------------------
		// Mostrando estaciones
		// ----------------------
		System.out.println("Id Estación | Midiclorianos restantes");

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				if (listaEstaciones[i][j].hayMidis() == true) {
					System.out.print("     " + listaEstaciones[i][j].getId() + "      | ");
					listaEstaciones[i][j].mostrarLista();
					System.out.println();
				}
			}
		}

		// ----------------------
		// Mostrando personajes
		// ----------------------
		System.out.println();
		System.out.println("Personajes:");
		System.out.println("-------------------------------------------");

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				listaEstaciones[i][j].mostrarPersonajes();
			}
		}
		System.out.println("-------------------------------------------");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		System.out.println();
		System.out.println("Ganadores:");
		System.out.println("-------------------------------------------");

		if (puertaGal.isEstado() == true) 
		{
			System.out.println("Ganador/es: ");
			buscarEstacion(id_salida).mostrarPersonajes();
		} 
		else 
		{
			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					listaEstaciones[i][j].mostrarImp();
					}
				}
			}
		

		System.out.println("-------------------------------------------");
	}
	
	public static void main(String[] args) {

		Galaxia pepe;

		try {
			pepe = new Galaxia(args[0]);
			pepe.simular();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ConfigNoValida e) {
			e.printStackTrace();
		}

	}

	public Grafo getGrafoGal() {
		return grafoGal;
	}

	public void setGrafoGal(Grafo grafoGal) {
		this.grafoGal = grafoGal;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getId_salida() {
		return id_salida;
	}

	public void setId_salida(int id_salida) {
		this.id_salida = id_salida;
	}

	public Puerta getPuertaGal() {
		return puertaGal;
	}

	public void setPuertaGal(Puerta puertaGal) {
		this.puertaGal = puertaGal;
	}

	public Estacion[][] getListaEstaciones() {
		return listaEstaciones;
	}

	public void setListaEstaciones(Estacion[][] listaEstaciones) {
		this.listaEstaciones = listaEstaciones;
	}

	public Queue<Midi> getListaMidiGal() {
		return listaMidiGal;
	}

	public void setListaMidiGal(Queue<Midi> listaMidiGal) {
		this.listaMidiGal = listaMidiGal;
	}
	
	//True si la puerta se ha abierto
	public boolean finJuego(){
		return puertaGal.isEstado();
	}
}
