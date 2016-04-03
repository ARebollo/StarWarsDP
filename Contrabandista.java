package DEV;

/**
* Declaracion de la clase Contrabandista
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Contrabandista extends Personaje {
        
	   /**
	 	* Constructor parametrizado de la clase Contrabandista
   	 	* 
   	 	* @param nombre Nombre del personaje
   	 	* @param marcaId Marca del personaje
   	 	* @param turno Turno en el que comienza a mover el personaje
   	 	* @param idEstacion Estacion en la que se encuentra el personaje
   	 	* 
   	 	*/
        public Contrabandista(String nombre, char marcaId, int turno, int idEstacion){
        	
        	super(nombre, marcaId, turno, idEstacion);
            setTipoPj("Contrabandista");
            System.out.println("Personaje " + getNombrePersonaje() +" creado.");
        }

	@Override
	public void HallarCamino(Galaxia gal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actuar(Galaxia gal) {
		// TODO Auto-generated method stub
		
	}
     
}
