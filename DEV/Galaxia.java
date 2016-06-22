package DEV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Estructuras.Grafo;
import Estructuras.Midi;
import Excepciones.ConfigNoValida;
import Excepciones.PersNoValido;
import Personajes.Contrabandista;
import Personajes.FamiliaReal;
import Personajes.Imperial;
import Personajes.Jedi;
import Personajes.Personaje;

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

	/**
	 * Constructor default de la clase Galaxia <br>
	 * Carga los datos desde el fichero "test"
	 * 
	 * @param _nombreFichero
	 *            Nombre del fichero de configuracion
	 * 
	 * @throws FileNotFoundException,
	 *             IOException, ConfigNoValida
	 * 
	 */
	Galaxia(String _nombreFichero) throws IOException, FileNotFoundException, ConfigNoValida {

		try {
			configurarDeFichero(_nombreFichero);
			simular();

		} catch (FileNotFoundException e) {
			System.out.println("ARCHIVO NO ENCONTRADO");
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ConfigNoValida e) {
			if (e.getMessage() == "KILL") {
				throw e;
			} else
				System.out.println(e.getMessage());
		} catch (PersNoValido e) {
			System.out.println(e.getMessage());
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

				listaEstaciones[i][j] = new Estacion(contador);
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
	public Estacion buscarEstacion(int id) {

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
	 * Metodo para repartir los midiclorianos (siguiendo la ruta de los familia
	 * real) que se encuentran en la lista de la galaxia
	 * 
	 */
	private void repartirMidi() {

		Estacion estAux;

		List<Integer> ruta = new LinkedList<Integer>();
		ruta = grafoGal.encontrarCaminoList(0, -1, id_salida, ruta);

		Iterator<Integer> it = ruta.iterator();

		estAux = buscarEstacion(0); // El origen no esta en la ruta, asique lo
									// aniadimos a mano

		for (int x = 0; x < 5 && listaMidiGal.isEmpty() == false; x++) {

			estAux.aniadirMidi(listaMidiGal.poll());

		}

		// Repartir entre estaciones, siguiendo la ruta de los FamiliaReal

		while (it.hasNext()) {

			estAux = buscarEstacion(it.next());

			for (int j = 0; j < 5 && listaMidiGal.isEmpty() == false; j++) {

				estAux.aniadirMidi(listaMidiGal.poll());

			}
		}
	}

	/**
	 * Metodo para procesar el fichero, cambiar los atributos de la galaxia y
	 * generar los personajes correspondientes
	 * 
	 * @param _nombreFichero
	 *            Nombre del fichero de configuracion
	 * 
	 * @throws FileNotFoundException,
	 *             IOException, ConfigNoValida
	 * 
	 */
	private void configurarDeFichero(String _nombreFichero)
			throws FileNotFoundException, IOException, ConfigNoValida, PersNoValido {

		System.out.println(" ");
		System.out.println("Intentando leer fichero :");
		try {
			BufferedReader flujo = new BufferedReader(new FileReader(_nombreFichero));

			String linea = flujo.readLine();

			List<String> vCampos = new ArrayList<String>();
			@SuppressWarnings("unused")
			int numCampos = 0; // Utilizado para la funcion trocearLineaInicial
			@SuppressWarnings("unused")
			char x; // Utilizado para extraer un char
			boolean galCreada = false; // Utilizado para evitar crear 2 galaxias
										// en
										// la misma ejecucion
			Personaje persAux; // Utilizado para introducir el camino a los
								// personajes
			if (flujo.ready() == false) {
				System.out.println("Problemas con el buffer (flujo)...");
			} else {

				while (linea != null) {

					if (linea.contains("--") == false) // Solo tomar en cuenta
														// lineas sin
														// comentarios
					{
						// Metemos la linea en vCampos
						vCampos.clear();
						numCampos = trocearLineaInicial(linea, vCampos);
						if (vCampos.isEmpty() == true) {
							try {
								throw new ConfigNoValida("LÏNEA NO VÄLIDA");
							} catch (ConfigNoValida e) {
								System.out.println(e.getMessage());
							}
						}
					}
					linea = flujo.readLine(); // Avanzar en el fichero

					if (vCampos.isEmpty() == false) {
						switch (vCampos.get(0)) { // Miramos de que tipo es el
													// campo
						case ("GALAXIA"):
							try {
								if (galCreada == false) {

									// Comprobamos que los parámetros sean
									// válidos
									// (ancho,alto mayores que 0, salida dentro
									// de
									// la galaxia)
									if (Integer.parseInt(vCampos.get(1)) <= 0 || Integer.parseInt(vCampos.get(2)) <= 0
											|| Integer.parseInt(vCampos.get(3)) < 0
											|| Integer.parseInt(vCampos.get(3)) > (Integer.parseInt(vCampos.get(1))
													* Integer.parseInt(vCampos.get(2)))
											|| Integer.parseInt(vCampos.get(4)) <= 0) {
										throw new ConfigNoValida("DATOS DE LA GALAXIA INVÁLIDOS");
									}

									// Para que no se cree más de una galaxia al
									// cargar de fichero

									mostrarDatosInicial(vCampos);
									// Obtenemos alto, ancho y salida
									this.alto = Integer.parseInt(vCampos.get(1));
									this.ancho = Integer.parseInt(vCampos.get(2));
									this.id_salida = Integer.parseInt(vCampos.get(3));

									// Midis para la combinacion de la puerta
									List<Midi> listCfg = new LinkedList<Midi>();
									for (int i = 1; i < 30; i = i + 2)
										listCfg.add(new Midi(i));

									// Configuramos la puerta
									puertaGal = new Puerta();
									puertaGal.setProfundidad(Integer.parseInt(vCampos.get(4)));
									puertaGal.setCombinacionList(listCfg);

									// Creamos las estaciones
									listaEstaciones = new Estacion[alto][ancho];
									listaMidiGal = new LinkedList<Midi>();

									for (int i = 0; i < alto; i++) {
										for (int j = 0; j < ancho; j++) {
											listaEstaciones[i][j] = new Estacion(j + ancho * i);
										}
									}

									buscarEstacion(id_salida).setPuertaSalida(true);

									// Creamos el grafo
									grafoGal = new Grafo(alto, ancho, id_salida);
									grafoGal.procesarParedes(ancho, 12345); // TODO
																			// Semilla

									// Impedimos que se cree más de una galaxia
									galCreada = true;

									// Dibujamos el mapa de la galaxia al inicio
									// de
									// la simulacion (en fichero de log)
									datosAFichero(0, true);

								} else {
									throw new ConfigNoValida("ERROR, YA EXISTE UNA GALAXIA");
								}

							} catch (ConfigNoValida e) {
								System.out.println(e.getMessage());
							}
							break;

						case ("FAMILIAREAL"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (galCreada == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new FamiliaReal(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, this);

										listaEstaciones[0][0].aniadirPj(persAux);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {

							} catch (PersNoValido e) {
							}
							break;

						case ("JEDI"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (galCreada == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Jedi(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), 0, this);

										listaEstaciones[0][0].aniadirPj(persAux);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
							break;

						case ("IMPERIAL"):

							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (galCreada == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Imperial(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (alto * ancho) - 1, this);

										listaEstaciones[alto - 1][ancho - 1].aniadirPj(persAux);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
							break;

						case ("CONTRABANDISTA"):
							try {
								// Si lleja a un pj y no hay galaxia, error
								// fatal
								if (galCreada == true) {
									if (Integer.parseInt(vCampos.get(3)) >= 1) {
										mostrarDatosInicial(vCampos);
										persAux = new Contrabandista(vCampos.get(1), x = vCampos.get(2).charAt(0),
												Integer.parseInt(vCampos.get(3)), (alto * ancho) - ancho, this);

										listaEstaciones[alto - 1][0].aniadirPj(persAux);
									} else
										throw new PersNoValido("Personaje No Válido");
								} else
									throw new ConfigNoValida("KILL");
							} catch (ConfigNoValida e) {
								throw e;
							} catch (PersNoValido e) {
							}
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
		} catch (FileNotFoundException e) {
			throw e;
		}
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
					System.out.print(" ");
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
	 * Metodo para mostrar el mapa de la Galaxia junto con los personajes en
	 * cada Estacion
	 * 
	 */
	private void mostrarMapa() {

		int y = 0;
		int x = 0;
		Boolean arcoBajo = true; // Para comprobar si hay un arco debajo del
									// nodo actual
		Boolean arcoDer = false; // Para comprobar si hay un arco a la derecha
									// del nodo actual

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
																				// muestra
																				// cuántos
																				// hay
					} else {
						System.out.print(listaEstaciones[i][j].mirarPJ().getMarcaId());
					}
				} else {
					y = 0;
					arcoBajo = false;

					while (y < grafoGal.devolverArcos().size() && arcoBajo == false) { // Comprobamos
																						// si
																						// hay
																						// un
																						// arco
																						// con
																						// el
																						// nodo
																						// de
																						// debajo

						if (grafoGal.devolverArcos().get(y).getFirst() == i * ancho + j) // Si
																							// el
																							// nodo
																							// de
																							// la
																							// lista
																							// coincide
																							// con
																							// el
																							// que
																							// estamos
																							// continuamos
						{
							if (grafoGal.devolverArcos().get(y).getFirst() + ancho == grafoGal.devolverArcos().get(y)
									.getSecond()) {
								System.out.print(' ');
								arcoBajo = true;
							}
						}
						y = y + 1;
					} // Fin while arcoBajo
				}

				if (arcoBajo == false)
					System.out.print('_'); // Imprimir antes de comprobar arcos
											// a la der, por errores de espacio

				x = 0;
				arcoDer = false;

				while (x < grafoGal.devolverArcos().size() && arcoDer == false) { // Comprobamos
																					// si
																					// hay
																					// un
																					// arco
																					// con
																					// el
																					// nodo
																					// de
																					// la
																					// derecha

					if (grafoGal.devolverArcos().get(x).getFirst() == i * ancho + j) // Si
																						// el
																						// nodo
																						// de
																						// la
																						// lista
																						// coincide
																						// con
																						// el
																						// que
																						// estamos
																						// continuamos
					{
						if (grafoGal.devolverArcos().get(x).getFirst() + 1 == grafoGal.devolverArcos().get(x)
								.getSecond()) {
							System.out.print(' ');
							arcoDer = true;
						}
					}
					x = x + 1;
				} // Fin while arcoDer

				if (arcoDer == false)
					System.out.print('|');

			} // Fin for j

			System.out.println();

		} // Fin for i

		System.out.print(" ");

		System.out.println();
	}

	/**
	 * Metodo para guardar el mapa de la Galaxia junto con los personajes en
	 * cada Estacion en el archivo de registro
	 * 
	 * @param out
	 *            Acceso al fichero en modo escritura
	 * 
	 */
	private void mapaAFichero(PrintWriter out) throws IOException {

		int y = 0;
		int x = 0;
		Boolean arcoBajo = true; // Para comprobar si hay un arco debajo del
									// nodo actual
		Boolean arcoDer = false; // Para comprobar si hay un arco a la derecha
									// del nodo actual

		out.println();
		out.print(" ");

		for (int i = 0; i < (ancho * 2) - 1; i++) {

			out.print('_');
		}

		out.println("");

		for (int i = 0; i < alto; i++) {

			out.print('|');

			for (int j = 0; j < ancho; j++) {

				arcoBajo = true;

				if (listaEstaciones[i][j].hayPersonajes() == true) {
					if (listaEstaciones[i][j].cuantosPJ() > 1) {
						out.print(listaEstaciones[i][j].cuantosPJ()); // Si hay
																		// mas
																		// de 2
																		// personajes
																		// en la
																		// misma
																		// estacion,
																		// muestra
																		// cuántos
																		// hay
					} else {
						out.print(listaEstaciones[i][j].mirarPJ().getMarcaId());
					}
				} else {
					y = 0;
					arcoBajo = false;

					while (y < grafoGal.devolverArcos().size() && arcoBajo == false) { // Comprobamos
																						// si
																						// hay
																						// un
																						// arco
																						// con
																						// el
																						// nodo
																						// de
																						// debajo

						if (grafoGal.devolverArcos().get(y).getFirst() == i * ancho + j) // Si
																							// el
																							// nodo
																							// de
																							// la
																							// lista
																							// coincide
																							// con
																							// el
																							// que
																							// estamos
																							// continuamos
						{
							if (grafoGal.devolverArcos().get(y).getFirst() + ancho == grafoGal.devolverArcos().get(y)
									.getSecond()) {
								out.print(' ');
								arcoBajo = true;
							}
						}
						y = y + 1;
					} // Fin while arcoBajo
				}

				if (arcoBajo == false)
					out.print('_'); // Imprimir antes de comprobar arcos a la
									// der, por errores de espacio

				x = 0;
				arcoDer = false;

				while (x < grafoGal.devolverArcos().size() && arcoDer == false) { // Comprobamos
																					// si
																					// hay
																					// un
																					// arco
																					// con
																					// el
																					// nodo
																					// de
																					// la
																					// derecha

					if (grafoGal.devolverArcos().get(x).getFirst() == i * ancho + j) // Si
																						// el
																						// nodo
																						// de
																						// la
																						// lista
																						// coincide
																						// con
																						// el
																						// que
																						// estamos
																						// continuamos
					{
						if (grafoGal.devolverArcos().get(x).getFirst() + 1 == grafoGal.devolverArcos().get(x)
								.getSecond()) {
							out.print(' ');
							arcoDer = true;
						}
					}
					x = x + 1;
				} // Fin while arcoDer

				if (arcoDer == false)
					out.print('|');

			} // Fin for j

			out.println();

		} // Fin for i

		out.print(" ");

		out.println();

	}

	/**
	 * Metodo para mostrar los resultados al finalizar la simulación
	 * 
	 */
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

		mostrarMapa();

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

		System.out.println("");
		System.out.println("-------------------------------------------");

		// ----------------------
		// Mostrando ganadores
		// ----------------------
		System.out.println();
		System.out.println("Ganadores:");
		System.out.println("-------------------------------------------");

		if (puertaGal.isEstado() == true) {
			System.out.println("Ganador/es: ");
			buscarEstacion(id_salida).mostrarPersonajes();
		} else {
			for (int i = 0; i < alto; i++) {

				for (int j = 0; j < ancho; j++) {

					listaEstaciones[i][j].mostrarImperial();
				}
			}
		}

		System.out.println("");
		System.out.println("-------------------------------------------");
	}

	/**
	 * Metodo para guardar los resultados al finalizar la simulacion en el
	 * archivo de registro
	 * 
	 * @param out
	 *            Acceso al fichero en modo escritura
	 * 
	 */
	private void finAFichero(PrintWriter out) {

		Personaje persAux;

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
		out.print(puertaGal.mostrarVectorCfgString());
		out.println(" y su profundidad es " + puertaGal.getProfundidad());
		out.print("Se han probado los midiclorianos: " + puertaGal.getProbados().arbolAString());
		out.println();

		// ----------------------
		// Mostrando tablero final
		// ----------------------

		out.println("El tablero al finalizar la simulación es el siguiente:");

		try {
			mapaAFichero(out);
		} catch (IOException e) {
		}

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

						if (persAux.getClass().getSimpleName().contains("Imperial")) {
							out.println(persAux.toString());
						}
					}
				}
			}
		}

		out.println("-------------------------------------------");

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

		mostrarMapa();

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
	 * Metodo para almacenar los datos de un turno en el archivo de registro
	 * 
	 * @param turno
	 *            Numero del turno en el que se encuentra la simulacion
	 * @param mapaInicial
	 *            Booleano para indicar si solo hay que guardar el mapa de la
	 *            Galaxia o la informacion completa del turno
	 * 
	 * @throws IOException
	 * 
	 */
	private void datosAFichero(int turno, boolean mapaInicial) throws IOException {

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

		if (mapaInicial == false) {

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
																					// Contrabandistas

				while (it.hasNext() == true) {

					persAux = it.next();
					out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
				}

				it = buscarEstacion(alto * ancho - 1).getColaPers().iterator(); // Para
																				// los
																				// Imperiales

				while (it.hasNext() == true) {

					persAux = it.next();
					out.println("(ruta:" + persAux.getMarcaId() + ":" + persAux.mostrarCamino() + ")");
				}

			}

			if (finJuego() == false) {

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

				mapaAFichero(out);

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

				out.close(); // Cerrar flujo
			} else // Si ha terminado la simulacion, imprimir lo siguiente
			{
				finAFichero(out);
				out.close(); // Cerrar flujo
			}

		} else {
			mapaAFichero(out);
			out.close();
		}

	}

	/**
	 * Metodo para indicar a todos los Personajes de la Galaxia que han de
	 * realizar su turno
	 * 
	 * @return True : si todos los Personajes han realizado su turno <br>
	 *         False : si algun Personaje no ha realizado su turno
	 * 
	 */
	private boolean activarEstaciones() {

		boolean fin = false;

		for (int i = 0; i < alto && fin == false; i++) {

			for (int j = 0; j < ancho && fin == false; j++) {

				fin = listaEstaciones[i][j].activarPJ(this);
			}
		}
		return fin;
	}

	/**
	 * Metodo para indicar a todos los Personajes de la Galaxia que ha comenzado
	 * un nuevo turno y pueden moverse de nuevo
	 * 
	 */
	private void reiniciarTurno() {

		for (int i = 0; i < alto; i++) {

			for (int j = 0; j < ancho; j++) {

				listaEstaciones[i][j].reiniciarTurnoPj();
			}
		}
	}

	/**
	 * Metodo para iniciar la simulacion
	 * 
	 */
	private void simular() {

		boolean fin = false;

		for (int turno = 0; turno < 51 && fin == false; turno++) {

			try {
				datosAFichero(turno, false);
			} catch (IOException e) {
			}

			infoTurno(turno);

			fin = activarEstaciones();

			if (fin == false)
				reiniciarTurno();
			else
				try {
					datosAFichero(turno, false);
				} catch (IOException e) {
				}

		}
		mostrarFin();
	}

	/**
	 * Obtiene el atributo grafoGal de la clase Galaxia
	 * 
	 * @return Objeto de la clase Grafo
	 * 
	 */
	public Grafo getGrafoGal() {
		return grafoGal;
	}

	/**
	 * Cambia el grafoGal de la clase Galaxia
	 * 
	 * @param grafoGal
	 *            Nuevo objeto de clase Grafo
	 * 
	 */
	public void setGrafoGal(Grafo grafoGal) {
		this.grafoGal = grafoGal;
	}

	/**
	 * Obtiene el atributo alto de la clase Galaxia
	 * 
	 * @return Entero con la altura de la Galaxia
	 * 
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Cambia el valor del atributo alto de la clase Galaxia
	 * 
	 * @param alto
	 *            Nuevo valor entero
	 * 
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}

	/**
	 * Obtiene el atributo ancho de la clase Galaxia
	 * 
	 * @return Entero con la anchura de la Galaxia
	 * 
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Cambia el valor del atributo ancho de la clase Galaxia
	 * 
	 * @param ancho
	 *            Nuevo valor entero
	 * 
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Obtiene el atributo id_salida de la clase Galaxia
	 * 
	 * @return Entero con la id de la Estacion que contiene la Puerta de salida
	 *         de la Galaxia
	 * 
	 */
	public int getId_salida() {
		return id_salida;
	}

	/**
	 * Cambia el valor del atributo id_salida de la clase Galaxia
	 * 
	 * @param id_salida
	 *            Nuevo valor entero
	 * 
	 */
	public void setId_salida(int id_salida) {
		this.id_salida = id_salida;
	}

	/**
	 * Obtiene el atributo puertaGal de la clase Galaxia
	 * 
	 * @return Objeto de la clase Puerta
	 * 
	 */
	public Puerta getPuertaGal() {
		return puertaGal;
	}

	/**
	 * Cambia la puertaGal de la clase Galaxia
	 * 
	 * @param puertaGal
	 *            Nuevo objeto de la clase Puerta
	 * 
	 */
	public void setPuertaGal(Puerta puertaGal) {
		this.puertaGal = puertaGal;
	}

	/**
	 * Obtiene la matriz de las Estaciones que componen la Galaxia
	 * 
	 * @return Matriz de tipo Estacion que forma el tablero de la Galaxia
	 * 
	 */
	public Estacion[][] getListaEstaciones() {
		return listaEstaciones;
	}

	/**
	 * Cambia la matriz de las Estaciones que componen la Galaxia
	 * 
	 * @param listaEstaciones
	 *            Nueva matriz de tipo Estacion
	 * 
	 */
	public void setListaEstaciones(Estacion[][] listaEstaciones) {
		this.listaEstaciones = listaEstaciones;
	}

	/**
	 * Obtiene la cola de Midi de la clase Galaxia
	 * 
	 * @return Cola de tipo Midi que contiene los midiclorianos no repartidos de
	 *         la Galaxia
	 * 
	 */
	public Queue<Midi> getListaMidiGal() {
		return listaMidiGal;
	}

	/**
	 * Cambia la cola de Midi de la clase Galaxia
	 * 
	 * @param listaMidiGal
	 *            Nueva cola de tipo Midi
	 * 
	 */
	public void setListaMidiGal(Queue<Midi> listaMidiGal) {
		this.listaMidiGal = listaMidiGal;
	}

	/**
	 * Indica si la Puerta de la Galaxia esta abierta
	 * 
	 * @return True : si la Puerta esta abierta <br>
	 *         False : si la Puerta esta cerrada
	 * 
	 */
	public boolean finJuego() {
		return puertaGal.isEstado();
	}

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		Galaxia pepe;
		try {
			if (args.length == 0)
				pepe = new Galaxia("inicio.txt");
			else
				pepe = new Galaxia(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigNoValida e) {
			e.printStackTrace();
		}

	}
}
