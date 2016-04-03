package DEV;

import java.util.*;

/**
* Declaracion de la clase Puerta
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class Puerta {

	private abb<Midi> Probados;
	private abb<Midi> Combinacion;
	private boolean estado; // True abierta, False cerrada
	private int profundidad;
	private Midi[] vectorCfg;
	
	/**
   	 * Constructor default de la clase Puerta
   	 * 
   	 */
	Puerta(){
		
		estado = false;
		Probados = new abb<Midi>();
		Combinacion = new abb<Midi>();
		profundidad = 0;
		vectorCfg = new Midi[200];	
		
	}
	
	/**
   	 * Constructor parametrizado de la clase Puerta
   	 * 
   	 * @param vector Vector de midiclorianos
   	 * @param constante Constante de profundidad para la cerradura de la puerta
   	 * 
   	 */
	Puerta(Midi [] vector, int constante){ // Constructor parametrizado, para configurar la combinacion pertinente
		
		estado = false;
		Probados = new abb<Midi>();
		Combinacion = new abb<Midi>();
		configurarCombinacion(vector, 0, vector.length-1);
		profundidad = constante;
		vectorCfg = vector;	
		
	}
	
	/**
   	 * Crea la combinacion de la puerta a traves de una List de Midi
   	 * 
   	 */
	public void setCombinacionList(List<Midi> list){
		
		vectorCfg = new Midi[list.size()];
		
		for (int i = 0; i < list.size(); i++){
			
			vectorCfg[i] = list.get(i);
		}
		
		configurarCombinacion(vectorCfg, 0, vectorCfg.length-1);
	}
	
	/**
   	 * Transforma una linkedlist de Midi a un vector de Midi
   	 * 
   	 */
	public void setVectorCfgLinkedList(LinkedList<Midi> list){
		
		vectorCfg = new Midi[list.size()];
		
		for (int i = 0; i < list.size(); i++){
			
			vectorCfg[i] = list.get(i);
		}
		
	}
	
	/**
   	 * Muestra el vector de configuracion por pantalla
   	 * 
   	 */
	public void mostrarVectorCfg(){
		
		for (int i = 0;i<vectorCfg.length;i++){
			
			System.out.print(" "+ vectorCfg[i]);
		}
			
	}
	
	public String mostrarVectorCfgString(){
		
		String a ="";
		
		for (int i = 0;i<vectorCfg.length;i++){

			a += vectorCfg[i] + " ";
		}
		
	  return a;
	}

	
	/**
   	 * Cierra la puerta en caso de que este abierta, si no, la reinicia a su estado original
   	 * 
   	 */
	public void cerrarPuerta (){
		
		if (this.estado == false)  // Si ya estaba cerrada reiniciarla
		{		 
		 this.Combinacion = new abb<Midi>();
		 configurarCombinacion(vectorCfg, 0, vectorCfg.length-1);
		 System.out.println();
		 System.out.println("La puerta se esta reiniciando");
		}
		else  // Si estaba abierta, cerrarla
		{
		 this.estado = false;
		 System.out.println();
		 System.out.println("La puerta se esta cerrando");
		}
		
	}

	/**
   	 * Crea la combinacion de la puerta a partir de un vector de Midiclorianos, siguiendo el algoritmo especificado en la entrega
   	 * Para ello inserta el valor medio y se llama recursivamente primero para la mitad derecha del vector y posteriormente para la izquierda, usando un vector auxiliar como parametro de entrada
   	 * 
   	 * @param vector Vector de Midi
   	 * 
   	 */
	private void configurarCombinacion (Midi [] vector, int izq, int der){
	       
        int mit = izq + (der - izq)/2;
         //Insercion valor intermedio
        
        if (izq<der && mit != 0)
        {
        	Combinacion.insertar(vector[mit]);
         //Copia subvector derecho y llamada recursiva
         configurarCombinacion(vector, mit+1, der);
         
         //Copia subvector izquierdo y llamada recursiva
         configurarCombinacion(vector, izq, mit-1);
        }
       
    }
	
    /**
   	 * Coge los midiclorianos de un personaje y los va probando en la puerta
   	 * Los que ya han sido probados son introducidos en el arbol de probados
   	 * Si se intentan probar dos midiclorianos iguales, salta una alarma
   	 * Si coincide un midicloriano probado con uno de la cerradura de la puerta, este es eliminado de la misma
   	 * Se comprueba la condicion de apertura de la puerta, y si se cumple se abre y para el bucle
   	 * 
   	 * @param listaMidi Lista de midiclorianos
   	 * 
   	 */
	public void probarMidiclorianos (LinkedList<Midi> listaMidi){  
		
		Midi aux;
			
		for (int i = 0; i < listaMidi.size()&& estado == false; i++){
			
			aux = listaMidi.remove(i);
			if (Probados.existe(aux) == true)
			{
			 System.out.println("ALARMA, TRAIDORES DEL IMPERIO DETECTADOS");
			}
			else
			{
			 Probados.insertar(aux);
			}
			
			if (Combinacion.existe(aux) == true)
			{
			 Combinacion.eliminar(aux);
			}
		
			if (Combinacion.altura() <= profundidad && Combinacion.numHojas() <= (Combinacion.numNodos()-Combinacion.numHojas()))
			{
			 estado = true;
			 System.out.println("THE GATES ARE OPEN");
			}	
		 }
			
	}
	
	/**
   	 * Metodo para probar un unico midicloriano en la cerradura
   	 * 
   	 * @param midi Objeto de la clase Midi
   	 * 
   	 */
	public void probarMidicloriano (Midi midi){  
			
			if (Probados.existe(midi) == true)
			{
			 System.out.println("ALARMA, TRAIDORES DEL IMPERIO DETECTADOS");
			}
			else
			{
			 Probados.insertar(midi);
			}
			
			if (Combinacion.existe(midi) == true)
			{
			 Combinacion.eliminar(midi);
			}
		
			if (Combinacion.altura() <= profundidad && Combinacion.numHojas() <= (Combinacion.numNodos()-Combinacion.numHojas()))
			{
			 estado = true;
			 System.out.println("ABRIENDO PUERTA");
			}	 

	}
	
	// Getters & Setters
	
	/**
   	 * Obtiene el arbol Probados de la clase Puerta
   	 * 
   	 * @return Arbol de los Midi probados
   	 * 
   	 */
	public abb<Midi> getProbados() {
		return Probados;
	}
	
	/**
   	 * Cambia el valor del arbol Probados de la clase Puerta
   	 * 
   	 * @param probados Nuevo arbol de Midi
   	 * 
   	 */
	public void setProbados(abb<Midi> probados) {
		Probados = probados;
	}
	
	/**
   	 * Obtiene el arbol Combinacion de la clase Puerta
   	 * 
   	 * @return Arbol de la combinacion de la Puerta
   	 * 
   	 */
	public abb<Midi> getCombinacion() {
		return Combinacion;
	}
	
	/**
   	 * Cambia el valor del arbol Combinacion de la clase Puerta
   	 * 
   	 * @param combinacion Nuevo arbol de Midi
   	 * 
   	 */
	public void setCombinacion(abb<Midi> combinacion) {
		Combinacion = combinacion;
	}
	
	/**
   	 * Indica si la Puerta esta abierta o no
   	 * 
   	 * @return True si la Puerta esta abierta <br> False si la Puerta esta cerrada
   	 * 
   	 */
	public boolean isEstado() {
		return estado;
	}
	
	/**
   	 * Cambia el valor del booleano Estado de la clase Estacion
   	 * 
   	 * @param estado Nuevo valor (True || False)
   	 * 
   	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	/**
   	 * Obtiene el atributo Profundidad de la clase Puerta
   	 * 
   	 * @return Profundidad de la Puerta
   	 * 
   	 */
	public int getProfundidad() {
		return profundidad;
	}
	
	/**
   	 * Cambia el valor del atributo Profundidad de la clase Puerta
   	 * 
   	 * @param profundidad Nuevo valor
   	 * 
   	 */
	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	/**
   	 * Obtiene el valor del vector VectorCfg de la clase Puerta
   	 * 
   	 * @return Vector con los midiclorianos que se van a usar en la configuracion de la combinacion
   	 * 
   	 */
	public Midi[] getVectorCfg() {
		return vectorCfg;
	}
	
	/**
   	 * Cambia el valor del vector VectorCfg de la clase Puerta
   	 * 
   	 * @param vectorCfg Nuevo vector de Midi
   	 * 
   	 */
	public void setVectorCfg(Midi[] vectorCfg) {
		this.vectorCfg = vectorCfg;
	}
	
}

