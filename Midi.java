package DEV;

/**
* Declaracion de la clase Midi
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Midi implements Comparable<Midi> {
	
	int id;
	
	/**
   	 * Constructor default de la clase Midi
   	 * 
   	 */
	Midi (){
		
		id = -1;
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Midi
   	 * 
   	 * @param id Id del midicloriano
   	 * 
   	 */
	Midi (int id){
		
		this.id = id;
		
	}

	/**
   	 * Constructor por copia de la clase Midi
   	 * 
   	 * @param midi Objeto de la clase Midi
   	 * 
   	 */
	Midi (Midi midi) {
		
		this.id = midi.getId();
		
	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el atributo Id de la clase Midi
   	 * 
   	 * @return Id del midicloriano
   	 * 
   	 */
	public int getId (){
		
		return id;
		
	}
	
	/**
   	 * Cambia el valor del atributo Id de la clase Midi
   	 * 
   	 * @param id Nuevo valor
   	 * 
   	 */
	public void setId (int id){
		
		this.id = id;
		
	}
	
	/**
   	 * Compara el valor de id entre dos Midis
   	 * 
   	 * @param mComp Objeto de clase Midi
   	 * 
   	 * @return -1 si la id de la clase es menor <br> 0 si la id de la clase es la misma <br> 1 si la id de la clase es mayor
   	 * 
   	 */
	@Override
	public int compareTo(Midi mComp){
		if (this.id < mComp.getId())
			return -1;
		if (this.id == mComp.getId())
			return 0;
		else
			return 1;
	}
	
	
	
	/**
   	 * Muestra la informacion de la clase Midi
   	 * 
   	 */
	@Override
	public String toString(){
		
		String aux = "" + id;
		return aux;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	/**
   	 * Comprueba si dos Midi son iguales
   	 * 
   	 * @return True : si ambos Midi son iguales <br> False : si los Midi son distintos
   	 * 
   	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Midi other = (Midi) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
