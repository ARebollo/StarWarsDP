package DEV;

import java.util.LinkedList;
import java.util.List;

/**
* Declaracion de la clase FamiliaReal
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class FamiliaReal extends Personaje{
        
	   /**
 		* Constructor parametrizado de la clase FamiliaReal
	 	* 
	 	* @param nombre Nombre del personaje
	 	* @param marcaId Marca del personaje
	 	* @param turno Turno en el que comienza a mover el personaje
	 	* @param idEstacion Estacion en la que se encuentra el personaje
	 	* 
	 	*/
        public FamiliaReal(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal){
        	
        	super(nombre, marcaId, turno, idEstacion, gal); 
        	setTipoPj("FamiliaReal");
            System.out.println("Personaje " + getNombrePersonaje() +" creado.");
        }

	@Override
	public void hallarCamino(Galaxia gal) {
		
		List<Integer> listaAux = new LinkedList<Integer>();
		Grafo grafoAux = gal.getGrafoGal();
		
		listaAux= grafoAux.encontrarCaminoList(0, -1, gal.getId_salida(), listaAux);
		int dirAnt = 0;
		int dirSig;
		
		while (listaAux.isEmpty() == false) {
			
			dirSig = listaAux.remove(0);
			aniadirCamino(interpretarCamino(dirAnt,dirSig));
			dirAnt = dirSig;
		}
	}


	@Override
	protected void actuar(Galaxia gal) {
		// TODO Auto-generated method stub
		
	}
 
}