package DEV;

import java.util.*;

/**
 * Declaracion de la clase Imperial
 * 
 * @author <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
 *         <b> Asignatura Desarrollo de Programas</b><br>
 *         <b> Curso 15/16 </b>
 */
public class Imperial extends Personaje {

	/**
	 * Constructor parametrizado de la clase Imperial
	 * 
	 * @param nombre
	 *            Nombre del personaje
	 * @param marcaId
	 *            Marca del personaje
	 * @param turno
	 *            Turno en el que comienza a mover el personaje
	 * @param idEstacion
	 *            Estacion en la que se encuentra el personaje
	 * 
	 */
	public Imperial(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal) {

		super(nombre, marcaId, turno, idEstacion);
		setTipoPj("Imperial");
		System.out.println("Personaje " + getNombrePersonaje() + " creado.");

		for (int i = 1; i <= 29; i = i + 2) {

			pilaMidi.add(new Midi(i)); // Los imperiales tienen una pila de
										// midiclorianos del 1 al 29 saltandose
										// los pares
		}
		HallarCamino(gal);
	}

	@Override
	public void HallarCamino(Galaxia gal) {
		Grafo grafoAux = gal.getGrafoGal();
		List<Integer> listaAux = new LinkedList<Integer>();
		listaAux = grafoAux.encontrarCaminoList(gal.getAncho() - 1, -1, 0, listaAux);
		int dirAnt, dirSig;
		dirAnt = gal.getId_salida();
		while (listaAux.isEmpty() == false) {
			dirSig = listaAux.remove(0);
			camino.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		listaAux = grafoAux.encontrarCaminoList(0, -1, grafoAux.getNumNodos() - gal.getAncho(), listaAux);
		while (listaAux.isEmpty() == false) {
			dirSig = listaAux.remove(0);
			camino.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		listaAux = grafoAux.encontrarCaminoList(grafoAux.getNumNodos() - gal.getAncho(), -1, grafoAux.getNumNodos() - 1, listaAux);
		while (listaAux.isEmpty() == false) {
			dirSig = listaAux.remove(0);
			camino.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
	}

	@Override
	protected void actuar(Galaxia gal) {
		// TODO Auto-generated method stub

	}

}