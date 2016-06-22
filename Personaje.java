package DEV;
 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
* Declaracion de la clase Personaje
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public abstract class Personaje {
       
        enum dir {N, S, E, O};
       
        private String nombrePersonaje;
        private int idEstacion;
        private char marcaId;
        private int turno;
        private int turnoActual;
        private boolean haMovido; //Indica si el personaje ha actuado este turno o no
        private Queue<dir> camino;
        private LinkedList<Midi> pilaMidi;
        
       /**
        * Constructor default de la clase Personaje
       	* 
       	*/
        public Personaje(){
        	
                this.nombrePersonaje = "";
                this.marcaId = ' ';
                this.idEstacion = -1;
                this.turno = -1;
                this.turnoActual = 0;
                haMovido = false;
                camino = new LinkedList<dir>();
                pilaMidi = new LinkedList<Midi>();
        }
        
       /**
        * Constructor default de la clase Personaje
        * 
        * @param nombre Nombre del personaje
        * @param marcaId Marca del personaje
        * @param turno Turno en el que empieza a moverse el personaje
        * @param idEstacion Estacion en la que se encuentra el personaje
        * @param gal Nuestra Galaxia
        * 
        */
        public Personaje(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal){
        	
                this.nombrePersonaje = nombre;
                this.marcaId = marcaId;
                this.idEstacion = idEstacion;
                this.turno = turno;
                this.turnoActual = 0;
                haMovido = false;
                camino = new LinkedList<dir>();
                pilaMidi = new LinkedList<Midi>();
                hallarCamino(gal);
        }
        
        /**
       	 * Metodo para aniadir un tipo dir a la cola del Personaje
       	 * 
       	 * @param Dir Objeto de tipo dir
       	 * 
       	 */
        public void aniadirCamino(dir Dir){
        	
        	camino.add(Dir);	
        }
        
        /**
		 * Indica si el Personaje tiene camino
		 * 
		 * @return True : si el Personaje tiene algun camino <br> False : si el Personaje no tiene ningun camino
		 * 
		 */
		public boolean tieneCamino(){
			
			return !camino.isEmpty();
		}

		/**
		 * Metodo para devolver el camino de un Personaje en forma de String
		 * 
		 * @return Cadena con el camino del personaje
		 * 
		 */
		public String mostrarCamino(){
			
			Iterator<dir> it = camino.iterator();
			String aux ="";
			
			while (it.hasNext()){
				
				aux += it.next() + " ";
			}
			
		  return aux;
		}
		
		/**
       	 * Metodo para aniadir un Midi a la pila del Personaje
       	 * 
       	 * @param midi Objeto de la clase Midi
       	 * 
       	 */
		public void aniadirMidi(Midi midi){
			
			pilaMidi.addFirst(midi);
		}
		
		/**
	   	 * Metodo para obtener y quitar el primer Midi de la pila del Personaje
	   	 * 
	   	 * @return Objeto de la clase Midi
	   	 * 
	   	 */
		public Midi sacarMidi(){
			
			return pilaMidi.poll();
		}
		
		/**
	   	 * Indica si el Personaje tiene Midi
	   	 * 
	   	 * @return True : si el Personaje tiene algun Midi <br> False : si el Personaje no tiene ningun Midi
	   	 * 
	   	 */
		public boolean tieneMidis(){
			return !pilaMidi.isEmpty();
		}
		
		/**
	   	 * Obtiene el numero de Midi que se encuentran en la pila del Personaje
	   	 * 
	   	 * @return Entero con el numero de Midi que tiene el Personaje
	   	 * 
	   	 */
		public int cuantosMidis(){
			return pilaMidi.size();
		}

		// Getters & Setters
        
        /**
         * Indica si el Personaje ha realizado algun movimiento durante un turno de la simulacion
         * 
         * @return True si el Personaje ha movido durante el turno actual <br> False si el Personaje no se ha movido todavia durante el turno actual
         * 
         */
		public boolean isHaMovido() {
			return haMovido;
		}
		
		/**
		 * Cambia el valor del booleano haMovido de la clase Personaje
		 * 
		 * @param haMovido Nuevo valor (True || False)
		 * 
		 */
		public void setHaMovido(boolean haMovido) {
			this.haMovido = haMovido;
		}
		
		/**
		 * Obtiene el atributo nombrePersonaje de la clase Personaje
		 * 
		 * @return String con el nombre del Personaje
		 * 
		 */
		public String getNombrePersonaje() {
			return nombrePersonaje;
		}
		
		/**
		 * Cambia el valor del atributo nombrePersonaje de la clase Personaje
		 * 
		 * @param nombrePersonaje Nuevo valor
		 * 
		 */
		public void setNombrePersonaje(String nombrePersonaje) {
			this.nombrePersonaje = nombrePersonaje;
		}
		
		/**
		 * Obtiene el atributo idEstacion de la clase Personaje
		 * 
		 * @return Id de la estacion en la que se encuentra el Personaje
		 * 
		 */
		public int getIdEstacion() {
			return idEstacion;
		}
		
		/**
		 * Cambia el valor del atributo idEstacion de la clase Personaje
		 * 
		 * @param idEstacion Nuevo valor
		 * 
		 */
		public void setIdEstacion(int idEstacion) {
			this.idEstacion = idEstacion;
		}
		
		/**
		 * Obtiene el atributo marcaId de la clase Personaje
		 * 
		 * @return Caracter que identifica al Personaje
		 * 
		 */
		public char getMarcaId() {
			return marcaId;
		}
		
		/**
		 * Cambia el valor del atributo marcaId de la clase Personaje
		 * 
		 * @param marcaId Nuevo valor
		 * 
		 */
		public void setMarcaId(char marcaId) {
			this.marcaId = marcaId;
		}
		
		/**
		 * Obtiene el atributo turno de la clase Personaje
		 * 
		 * @return Turno en el que el Personaje comienza a moverse
		 * 
		 */
		public int getTurno() {
			return turno;
		}
		
		/**
		 * Cambia el valor del atributo turno de la clase Personaje
		 * 
		 * @param turno Nuevo valor
		 * 
		 */
		public void setTurno(int turno) {
			this.turno = turno;
		}
		
		/**
		 * Obtiene el atributo turnoActual de la clase Personaje
		 * 
		 * @return Turno de la simulacion en el que se encuentra el Personaje
		 * 
		 */
		public int getTurnoActual() {
			return turnoActual;
		}
		
		/**
		 * Cambia el valor del atributo turnoActual de la clase Personaje
		 * 
		 * @param turnoActual Nuevo valor
		 * 
		 */
		public void setTurnoActual(int turnoActual) {
			this.turnoActual = turnoActual;
		}
		
		/**
	   	 * Obtiene la cola de caminos de la clase Personaje
	   	 * 
	   	 * @return Cola de tipo dir
	   	 * 
	   	 */
		public Queue<dir> getCamino() {
			return camino;
		}
		
		/**
	   	 * Cambia la cola de caminos de la clase Personaje
	   	 * 
	   	 * @param camino Nueva cola de tipo dir
	   	 * 
	   	 */
		public void setCamino(Queue<dir> camino) {
			this.camino = camino;
		}
		
		/**
	   	 * Obtiene la lista de Midi de la clase Personaje
	   	 * 
	   	 * @return Lista de tipo Midi
	   	 * 
	   	 */
		public LinkedList<Midi> getPilaMidi() {
			return pilaMidi;
		}
		
		/**
	   	 * Cambia la lista de Midi de la clase Personaje
	   	 * 
	   	 * @param pilaMidi Nueva lista de tipo Midi
	   	 * 
	   	 */
		public void setPilaMidi(LinkedList<Midi> pilaMidi) {
			this.pilaMidi = pilaMidi;
		}
		
		// Metodos PJ
		
		//Halla el camino que tiene que seguir el personaje. Lo implementa cada uno individualmente
		public abstract void hallarCamino(Galaxia gal);
		
		/**
		 * Metodo para convertir un movimiento de una casilla a otra en un tipo dir
		 * 
		 * @param origen Casilla de origen
		 * @param destino Casilla destino
		 * 
		 * @return Tipo dir
		 * 
		 */
		public dir interpretarCamino(int origen, int destino){
			if (origen < destino && origen != destino - 1)
				return dir.S;
			if (origen == destino - 1)
				return dir.E;
			if (origen == destino + 1)
				return dir.O;
			if (origen > destino && origen !=  destino + 1)
				return dir.N;
			return null;
		}
		
		/**
		 * Metodo para convertir un tipo dir a un entero que se adapte a las dimensiones de la Galaxia y seniale la Estacion destino
		 * 
		 * @param direccion Tipo dir
		 * @param ancho Anchura de la Galaxia
		 * 
		 * @return Entero que señala la id de la Estacion destino
		 * 
		 */
		private int dirACamino(dir direccion, int ancho){
			
			int sig = 0;
			
			switch (direccion){
			case S:
				sig = idEstacion + ancho;
				break;
			case E:
				sig = idEstacion + 1;
				break;
			case N:
				sig = idEstacion - ancho;
				break;
			case O:
				sig = idEstacion - 1;  
				break;
			}
			return sig;
		}
		
		/**
	   	 * Indica si el Personaje puede actuar en el turno actual
	   	 * 
	   	 * @return True : si el Personaje puede actuar <br> False : si el Personaje ha de esperar a su turno
	   	 * 
	   	 */
		public boolean esSuTurno(Galaxia gal){
			
			boolean turnoPJ = false;
					
			if (turnoActual >= turno-1)	// -1 Para que actue en el turno exacto que indica el archivo de config
			{
			    turnoPJ = true;
			}
			
			return turnoPJ;
		}
		
		
		/**
		 * Metodo para llevar a cabo el movimiento del personaje
		 * 
		 * @param gal Nuestra Galaxia
		 * 
		 */
		public void turnoPj(Galaxia gal){
			
			setHaMovido(true);
			turnoActual++;
			
			if (gal.getId_salida() == idEstacion)	//Si la estacion en la que se encuentra el pj es la de salida, interactuar con la puerta, y si queda camino, moverse al que indica
			{
				tocarPuerta(gal.getPuertaGal());
			 	if (camino.peek() != null)
			 	{
			 		gal.buscarEstacion(dirACamino(camino.peek(), gal.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
			 		setIdEstacion(dirACamino(camino.remove(), gal.getAncho()));
			 	}
			 	else gal.buscarEstacion(idEstacion).aniadirPj(this);
			}
			else
			{
				gal.buscarEstacion(dirACamino(camino.peek(), gal.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
				setIdEstacion(dirACamino(camino.remove(), gal.getAncho()));
			 
				tocarMidi(gal.buscarEstacion(idEstacion));
			}
		}
		
		//Realiza la accion apropiada para cada pj, es llamado por mover
		public abstract void tocarPuerta(Puerta puertaGal);
		
		public abstract void tocarMidi(Estacion estacion);

		// To
		
		/**
		 * Muestra la informacion de la clase Personaje
		 * 
		 */
		@Override
		public String toString() {
		
			String Midis = "";
			Iterator<Midi> it = pilaMidi.iterator();
			
			while(it.hasNext() == true){
				 
				Midis = Midis + " " + it.next().toString();
			}
		
		  return "(" + getClass().getSimpleName() + ":" + marcaId + ":" + idEstacion + ":" + turnoActual + ":" + Midis + ")";
		}
 
}