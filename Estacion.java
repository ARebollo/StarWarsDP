package DEV;

import java.util.*;

/**
* Declaracion de la clase Estacion
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Estacion implements Comparable<Estacion> {
	
	private int id;
	private int frecuencia;
	private boolean puertaSalida;
    private List<Midi> listaMidiEst;
	private Queue<Personaje> colaPers;
	
	/**
   	 * Constructor default de la clase Estacion
   	 * 
   	 */
	Estacion(){
		
		id = -1;
		frecuencia = 0;
		puertaSalida = false;
		listaMidiEst = new LinkedList<Midi>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Estacion
   	 * 
   	 * @param id Id de la puerta
   	 * @param frec Frecuencia de la puerta
   	 * 
   	 */
	Estacion(int id, int frec){
		
		this.id = id;
		frecuencia = frec;	
		puertaSalida = false;
		listaMidiEst = new LinkedList<Midi>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Estacion
   	 * 
   	 * @param id Id de la puerta
   	 * @param frec Frecuencia de la puerta
   	 * @param puerta Indica si la estacion contiene una puerta (True : la contiene | False : no la contiene)
   	 * 
   	 */
	Estacion(int id, int frec, boolean puerta){
		
		this.id = id;
		frecuencia = frec;	
		puertaSalida = puerta;	
		listaMidiEst = new LinkedList<Midi>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor por copia de la clase Estacion
   	 * 
   	 * @param est Objeto de la clase Estacion
   	 * 
   	 */
	Estacion (Estacion est) {
		
		this.id = est.getId();
		this.frecuencia = est.getFrecuencia();
		this.puertaSalida = est.isPuertaSalida();
		this.listaMidiEst = est.listaMidiEst;
		this.colaPers = est.colaPers;
		
	}
	
	// List
	
	/**
   	 * Metodo para añadir un Midi a la Estacion, ordenandolos de menor a mayor
   	 * 
   	 * @param midi Objeto de la clase Midi
   	 * 
   	 */
	public void aniadirMidi(Midi midi){
		
		int i = 0;
		boolean enc = false;
		
		if (listaMidiEst.isEmpty() == true)
		{
		 listaMidiEst.add(midi);	 // Si la lista esta vacia, insertamos el valor
		 enc = true;
		}
		
		while (i < listaMidiEst.size() && enc == false){
			
			if (midi.getId() < listaMidiEst.get(i).getId())
			{
			 listaMidiEst.add(i, midi);
			 enc = true;
			}
			
			i++;
		}
		
		if (enc == false)
		{
		 listaMidiEst.add(midi);  // Si no ha encontrado, es que el elemento es el mayor de la lista y lo insertamos al final
		}
		
	}
	
	/**
   	 * Metodo para mostrar los Midi que tiene la Estacion
   	 * 
   	 */
	public void mostrarLista(){
		
		Iterator<Midi> it = listaMidiEst.iterator();
		
		while (it.hasNext() == true){
			
			System.out.print(it.next().toString() + " ");

		}
		
	}
	
	/**
   	 * Metodo para devolver los Midi que tiene la Estacion en forma de String
   	 * 
   	 * @return String con la lista de Midi de la Estacion
   	 * 
   	 */
	public String mostrarListaStr(){
		String a = "";
		Iterator<Midi> it = listaMidiEst.iterator();
		
		while (it.hasNext() == true){
			
			a += it.next().toString() + " ";
		}
		
	  return a;
	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el atributo Id de la clase Estacion
   	 * 
   	 * @return Id de la Estacion
   	 * 
   	 */
	public int getId() {
		return id;
	}
	
	/**
   	 * Cambia el valor del atributo Id de la clase Estacion
   	 * 
   	 * @param id Nuevo valor
   	 * 
   	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
   	 * Obtiene el atributo Frecuencia de la clase Estacion
   	 * 
   	 * @return Frecuencia de la Estacion
   	 * 
   	 */
	public int getFrecuencia() {
		return frecuencia;
	}
	
	/**
   	 * Cambia el valor del atributo Frecuencia de la clase Estacion
   	 * 
   	 * @param frecuencia Nuevo valor
   	 * 
   	 */
	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	/**
   	 * Indica si la Estacion contiene una puerta de salida
   	 * 
   	 * @return True si la Estacion contiene una puerta de salida <br> False si la Estacion no contiene una puerta de salida
   	 * 
   	 */
	public boolean isPuertaSalida() {
		return puertaSalida;
	}
	
	/**
   	 * Cambia el valor del booleano PuertaSalida de la clase Estacion
   	 * 
   	 * @param puertaSalida Nuevo valor (True || False)
   	 * 
   	 */
	public void setPuertaSalida(boolean puertaSalida) {
		this.puertaSalida = puertaSalida;
	}
	
	// To
	
	/**
   	 * Muestra la informacion de la clase Estacion
   	 * 
   	 */
	@Override
	public String toString() {
		return "Estacion [id=" + id + ", frecuencia=" + frecuencia + "]";
	}
	
	/**
   	 * Compara el valor de frecuencia entre dos Estaciones		// TODO borrar? no se usa pa na
   	 * 
   	 * @param eComp Objeto de clase Estacion
   	 * 
   	 * @return -1 si la id de la clase es menor <br> 0 si la id de la clase es la misma <br> 1 si la id de la clase es mayor
   	 * 
   	 */
	@Override
	public int compareTo(Estacion eComp){
		if (this.id < eComp.getId())
			return -1;
		if (this.id == eComp.getId())
			return 0;
		else
			return 1;
	}

}

