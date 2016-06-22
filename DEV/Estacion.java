package DEV;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Estructuras.Midi;
import Personajes.Personaje;

/**
* Declaracion de la clase Estacion
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Estacion{
	
	private int id;
	private boolean puertaSalida;
    private List<Midi> listaMidiEst;
	private List<Personaje> colaPers;
	
	/**
   	 * Constructor default de la clase Estacion
   	 * 
   	 */
	public Estacion(){
		
		id = -1;
		puertaSalida = false;
		listaMidiEst = new LinkedList<Midi>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Estacion
   	 * 
   	 * @param id Id de la puerta
   	 * 
   	 */
	Estacion(int id){
		
		this.id = id;	
		puertaSalida = false;
		listaMidiEst = new LinkedList<Midi>();
		colaPers = new LinkedList<Personaje>();
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Estacion
   	 * 
   	 * @param id Id de la puerta
   	 * @param puerta Indica si la estacion contiene una puerta (True : la contiene | False : no la contiene)
   	 * 
   	 */
	Estacion(int id, boolean puerta){
		
		this.id = id;
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
		this.puertaSalida = est.isPuertaSalida();
		this.listaMidiEst = est.listaMidiEst;
		this.colaPers = est.colaPers;
		
	}
	
	// List
	
	/**
   	 * Metodo para aniadir un Midi a la Estacion, ordenandolos de menor a mayor
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
   	 * Metodo para obtener y quitar el primer Midi de la lista en la Estacion
   	 * 
   	 * @return Objeto de la clase Midi
   	 * 
   	 */
	public Midi sacarMidi(){
		
		return listaMidiEst.remove(0);
	}
	
	/**
   	 * Indica si la Estacion contiene Midi
   	 * 
   	 * @return True : si la Estacion contiene algun Midi <br> False : si la Estacion no contiene Midi
   	 * 
   	 */
	public boolean hayMidis(){
		return !listaMidiEst.isEmpty();
	}
	
	/**
   	 * Metodo para añadir un Personaje a la lista de la Estacion
   	 * 
   	 * @param pj Objeto de la clase Personaje
   	 * 
   	 */
	public void aniadirPj(Personaje pj){		
		colaPers.add(pj);
	}
	
	/**
   	 * Metodo para obtener al primer Personaje que se encuentra en la Estacion
   	 * 
   	 * @return Objeto de la clase Personaje
   	 * 
   	 */
	public Personaje mirarPJ(){
		return colaPers.get(0);
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
   	 * @return Cadena con la lista de Midi de la Estacion
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
	
	/**
   	 * Muestra la informacion de todos los Personajes que se encuentran en la Estacion
   	 * 
   	 */
	public void mostrarPersonajes(){
		
		Iterator<Personaje> it = colaPers.iterator();
		
		while (it.hasNext() == true){
			
			System.out.print(it.next().toString() + " ");

		}
	}
	
	/**
   	 * Muestra la informacion de los Personajes Imperiales que se encuentran en la Estacion
   	 * 
   	 */
	public void mostrarImperial(){
		
		Iterator<Personaje> it = colaPers.iterator();
		Personaje persAux;
		
		while (it.hasNext() == true){
			
			persAux = it.next();

			if (persAux.getClass().getSimpleName().contains("Imperial") ){
					
				System.out.println(persAux.toString() + " ");
			}
		}
	}
	
	/**
   	 * Indica si la Estacion contiene Personajes
   	 * 
   	 * @return True : si la Estacion contiene algun Personaje <br> False : si la Estacion no contiene Personajes
   	 * 
   	 */
	public boolean hayPersonajes(){
		return !colaPers.isEmpty();
	}
	
	/**
   	 * Obtiene el numero de Personajes que se encuentran en la lista de la clase Estacion
   	 * 
   	 * @return Entero con el numero de Personajes que se encuentran en la Estacion
   	 * 
   	 */
	public int cuantosPJ(){
		return colaPers.size();
	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el atributo Id de la clase Estacion
   	 * 
   	 * @return Entero con la id de la Estacion
   	 * 
   	 */
	public int getId() {
		return id;
	}
	
	/**
   	 * Cambia el valor del atributo Id de la clase Estacion
   	 * 
   	 * @param id Nuevo valor entero
   	 * 
   	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
   	 * Obtiene la lista de Midi de la clase Estacion
   	 * 
   	 * @return Lista de tipo Midi
   	 * 
   	 */
	public List<Midi> getListaMidiEst() {
		return listaMidiEst;
	}
	
	/**
   	 * Cambia la lista de Midi de la clase Estacion
   	 * 
   	 * @param listaMidiEst Nueva lista de tipo Midi
   	 * 
   	 */
	public void setListaMidiEst(List<Midi> listaMidiEst) {
		this.listaMidiEst = listaMidiEst;
	}
	
	/**
   	 * Obtiene la lista de Personajes de la clase Estacion
   	 * 
   	 * @return Lista de tipo Personaje
   	 * 
   	 */
	public List<Personaje> getColaPers() {
		return colaPers;
	}
	
	/**
   	 * Cambia la lista de Personajes de la clase Estacion
   	 * 
   	 * @param colaPers Nueva lista de tipo Personaje
   	 * 
   	 */
	public void setColaPers(List<Personaje> colaPers) {
		this.colaPers = colaPers;
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
	
	/**
   	 * Cambia el valor del booleano haMovido de cada Personaje que se encuentra en la estacion a False
   	 * 
   	 */
	public void reiniciarTurnoPj(){
		
		Personaje persAux;
		
		for (int i = 0; i < colaPers.size(); i++) {
			
			persAux = colaPers.get(i);
			
			persAux.setHaMovido(false);
		}	
	}
	
	/**
   	 * Llama al metodo turnoPj de cada Personaje que se encuentra en la estacion y no haya realizado su turno
   	 * 
   	 * @param gal Nuestra Galaxia
   	 * 
   	 */
	public boolean activarPJ (Galaxia gal){
		
		Personaje persAux;
		boolean fin = false;
		int cuantos = colaPers.size();
		
		for (int i = 0; i < cuantos && fin == false; i++) {
			
			persAux = colaPers.remove(0);
			
			if (persAux.isHaMovido() == false && persAux.esSuTurno(gal) == true)
				persAux.turnoPj(gal);
			else if (persAux.isHaMovido() == true)
				colaPers.add(persAux);
			else {
				colaPers.add(persAux);
				persAux.setTurnoActual(persAux.getTurnoActual() + 1);	// Para que no se mueva antes de tiempo, es decir turno < turnoActual
			}
			
			fin = gal.finJuego();
		}
		return fin;
	}
	
	// To
	
	/**
   	 * Muestra la informacion de la clase Estacion
   	 * 
   	 */
	@Override
	public String toString() {
		return "Estacion [id=" + id + "]";
	}

}

