package Personajes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DEV.Estacion;
import DEV.Galaxia;
import DEV.Puerta;
import Estructuras.Grafo;
import Estructuras.Midi;

/**
 * Declaracion de la clase Imperial
 * 
 * @author <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
 *         <b> Asignatura Desarrollo de Programas</b><br>
 *         <b> Curso 15/16 </b>
 */
public class Imperial extends Personaje {
	
	private Queue<dir> caminoI;
	
	/**
	 * Constructor parametrizado de la clase Imperial
	 * 
	 * @param nombre Nombre del personaje
	 * @param marcaId Marca del personaje
	 * @param turno Turno en el que comienza a mover el personaje
	 * @param idEstacion Estacion en la que se encuentra el personaje
	 * @param gal Nuestra Galaxia
	 * 
	 */
	public Imperial(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal) {

		super(nombre, marcaId, turno, idEstacion, gal);
		
		System.out.println("Personaje " + getNombrePersonaje() + " creado.");

		for (int i = 1; i <= 29; i = i + 2) {

			aniadirMidi(new Midi(i)); // Los imperiales tienen una pila de midiclorianos del 1 al 29 saltandose los pares
		}
	}

	@Override
	protected void hallarCamino(Galaxia gal) {
		
		Grafo grafoAux = gal.getGrafoGal();
		
		caminoI = new LinkedList<dir>();	// Para guardar una copia del camino del imperial (para usarlo en el reseteo)
		List<Integer> listaAux = new LinkedList<Integer>();
		
		
		int dirAnt, dirSig;

		listaAux = grafoAux.encontrarCaminoList(gal.getAncho() * gal.getAlto() - 1, -1, gal.getAncho() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			caminoI.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(gal.getAncho() - 1, -1, 0, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			caminoI.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(0, -1, grafoAux.getNumNodos() - gal.getAncho(), listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			caminoI.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
		listaAux = grafoAux.encontrarCaminoList(grafoAux.getNumNodos() - gal.getAncho(), -1, grafoAux.getNumNodos() - 1, listaAux);
		dirAnt = listaAux.remove(0);
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			caminoI.add(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
	}
	
	@Override
	protected void tocarPuerta(Puerta puertaGal) {
		
		puertaGal.cerrarPuerta();
		
		if (!tieneCamino()){
			setCamino(caminoI); 	// Resetear camino
		}
		
	}

	@Override
	protected void tocarMidi(Estacion estacion) {
		
		if (getIdEstacion() % 2 == 0 && tieneMidis())
		{
		 getPilaMidi().remove();
		}
		
		if (!tieneCamino()){
			setCamino(caminoI);		// Resetear camino
		}
	}
	
	

}