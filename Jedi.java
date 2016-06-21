package DEV;

import java.util.LinkedList;
import java.util.List;

/**
* Declaracion de la clase Jedi
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Jedi extends Personaje{
        
	   /**
 		* Constructor parametrizado de la clase Jedi
	 	* 
	 	* @param nombre Nombre del personaje
	 	* @param marcaId Marca del personaje
	 	* @param turno Turno en el que comienza a mover el personaje
	 	* @param idEstacion Estacion en la que se encuentra el personaje
	 	* 
	 	*/
        public Jedi(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal){
        	
            super(nombre, marcaId, turno, idEstacion, gal);
            
            System.out.println("Personaje " + getNombrePersonaje() +" creado.");
        }

	@Override
	public void hallarCamino(Galaxia gal) {
		
		List<Integer> listaAux = new LinkedList<Integer>();
		Grafo grafoAux = gal.getGrafoGal();
		
		listaAux= grafoAux.manoDerecha(0, gal.getId_salida(), gal.getAncho());
		int dirAnt = listaAux.remove(0);
		int dirSig;
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
		
	}

	@Override
	public void tocarPuerta(Puerta puertaGal) {
		if (tieneMidis())
		{
			puertaGal.probarMidicloriano(sacarMidi());	
		}
	}
	
	@Override
	public void tocarMidi(Estacion estacion) {
		
		if (estacion.hayMidis())
		{
		 aniadirMidi(estacion.sacarMidi());
		}
		
	}
 
}
