package DEV;
 
import java.util.*;

/**
* Declaracion de la clase Personaje
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public abstract class Personaje {
       
        enum dir {N, S, E, O};
       
        private String tipoPj;
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
        	
                this.tipoPj = "";
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
        * 
        */
        public Personaje(String nombre, char marcaId, int turno, int idEstacion, Galaxia gal){
        	
                this.tipoPj = "";
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
        
        public void aniadirCamino(dir Dir){
        	
        	camino.add(Dir);	
        }
        
        /**
         * Metodo para devolver el camino de un Personaje
         * 
         * @return String con el camino del personaje
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
		 * Obtiene el atributo tipoPj de la clase Personaje
		 * 
		 * @return String con el tipo de Personaje
		 * 
		 */	
		public String getTipoPj() {
			return tipoPj;
		}
		
		/**
		 * Cambia el valor del atributo tipoPj de la clase Personaje
		 * 
		 * @param tipoPj Nuevo valor
		 * 
		 */
		public void setTipoPj(String tipoPj) {
			this.tipoPj = tipoPj;
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

		  return "(" + tipoPj + ":" + marcaId + ":" + idEstacion + ":" + turnoActual + ":" + Midis + ")";
		}

		public Queue<dir> getCamino() {
			return camino;
		}

		public void setCamino(Queue<dir> camino) {
			this.camino = camino;
		}

		public LinkedList<Midi> getPilaMidi() {
			return pilaMidi;
		}

		public void setPilaMidi(LinkedList<Midi> pilaMidi) {
			this.pilaMidi = pilaMidi;
		}
		
		public void aniadirMidi(Midi midi){
			
			pilaMidi.addFirst(midi);
		}
		
		public Midi sacarMidi(){
			
			return pilaMidi.poll();
		}

		//Halla el camino que tiene que seguir el personaje. Lo implementa cada uno individualmente
		public abstract void hallarCamino(Galaxia gal);
		
		//Convierte un movimiento de una casilla a otra en un Dir
		protected dir interpretarCamino(int origen, int destino){
			if (origen<destino && origen != destino-1)
				return dir.S;
			if (origen == destino -1)
				return dir.E;
			if (origen == destino +1)
				return dir.O;
			if (origen>destino && origen !=  destino+1)
				return dir.N;
			return null;
		}
		
		private int dirACamino(dir direccion, int ancho){
			int sig = 0;
			switch (direccion){
			case S:
				sig = idEstacion + ancho;
				break;
			case E:
				sig = idEstacion +1;
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
		
		//Mueve el personaje
		public void turnoPj(Galaxia gal){
			
			if (gal.getId_salida() == idEstacion)
			{
			 tocarPuerta(gal.getPuertaGal());	
			}
			else
			{
			 gal.buscarEstacion(dirACamino(camino.peek(), gal.getAncho())).aniadirPj(this);  // Obtiene el camino del pj, busca la estacion con esa id y luego añade el personaje
			 setIdEstacion(dirACamino(camino.remove(), gal.getAncho()));
			 
			 tocarMidi(gal.buscarEstacion(idEstacion));
			}
		}
		
		//Realiza la accion apropiada para cada pj, es llamado por mover
		protected abstract void tocarPuerta(Puerta puertaGal);
		
		protected abstract void tocarMidi(Estacion estacion);
 
}