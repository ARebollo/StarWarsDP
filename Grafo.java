package DEV;

import DEV.Personaje.dir;
import java.util.*;

/**
* Declaracion de la clase grafo
* @author
*   <b> Profesores DP </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 11/12 </b>
*/
public class Grafo {
	public static final int MAXVERT=115;
	public static final int INFINITO = 9999;
	public static final int NOVALOR = -1;
	
	/** Numero de nodos del grafo */
    private int numNodos;        

    /** Vector que almacena los nodos del grafo */
    private int[] nodos = new int[MAXVERT];           

    /** Matriz de adyacencia, para almacenar los arcos del grafo */
    private int[][] arcos = new int[MAXVERT][MAXVERT];    

	/** Matriz de Camino (Warshall - Path) */
    private boolean [][] warshallC = new boolean[MAXVERT][MAXVERT];    

    /** Matriz de Costes (Floyd - Cost) */
    private int[][] floydC = new int[MAXVERT][MAXVERT];    
	
    /** Matriz de Camino (Floyd - Path) */
    private int[][] floydP = new int[MAXVERT][MAXVERT];	

   /**
   	* Constructor default de la clase Grafo
   	* 
   	*/
    Grafo() {
    	
        int x,y;
        setNumNodos(0);

        for (x=0;x<MAXVERT;x++)
            nodos[x]= NOVALOR;

        for (x=0;x<MAXVERT;x++)
            for (y=0;y<MAXVERT;y++){
                arcos[x][y]=INFINITO;
                warshallC[x][y]=false;
                floydC[x][y]=INFINITO;
                floydP[x][y]=NOVALOR;
            }
      
        // Diagonales
        for (x=0;x<MAXVERT;x++){
            arcos[x][x]=0;
            warshallC[x][x]=false;
            floydC[x][x]=0;
            //floydP[x][x]=NO_VALOR;
        }
    }
    
   /**
   	* Constructor parametrizado de la clase Grafo
   	* 
   	* @param alto Altura de la galaxia
   	* @param ancho Anchura de la galaxia
   	* 
   	*/
    Grafo(int alto, int ancho, int puertaGal){
    	
    	int x,y;
        setNumNodos(alto*ancho);

        for (x=0;x<MAXVERT;x++)
            nodos[x]= x;

        for (x=0;x<MAXVERT;x++)
            for (y=0;y<MAXVERT;y++){
                arcos[x][y]=INFINITO;
                warshallC[x][y]=false;
                floydC[x][y]=INFINITO;
                floydP[x][y]=NOVALOR;
            }
      
        // Diagonales
        for (x=0;x<MAXVERT;x++){
            arcos[x][x]=0;
            warshallC[x][x]=false;
            floydC[x][x]=0;
            //floydP[x][x]=NO_VALOR;
        }
        
        almacenarParedesPair(ancho); 
    	
    }
    
    
   /**
    * Metodo que comprueba si el grafo esta vacio
    * 
    * @return Retorna un valor booleano que indica si el grafo esta o no vacio
    * 
    */
    public boolean esVacio () {
        return (getNumNodos()==0);
    }

   /**
    * Metodo que inserta un nuevo arco en el grafo
    * 
    * @param origen Es el nodo de origen del arco nuevo
    * @param destino Es el nodo de destino del arco nuevo
    * @param valor Es el peso del arco nuevo 
    * 
    * @return True si se pudo insertar
    * 
    */
    private boolean nuevoArco(int origen, int destino, int valor) {
        boolean resultado= false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos()))	{
            arcos[origen][destino]=valor; 
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que borra un arco del grafo
    * 
    * @param origen Es el nodo de origen del arco nuevo
    * @param destino Es el nodo de destino del arco nuevo
    * 
    * @return True si se pudo borrar
    * 
    */
    @SuppressWarnings("unused")
	private boolean borraArco(int origen, int destino) {
        boolean resultado = false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) {
        	arcos[origen][destino]=INFINITO;	
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que comprueba si dos nodos son adyacentes
    * 
    * @param origen Es el primer nodo
    * @param destino Es el segundo nodo
    * 
    */
    @SuppressWarnings("unused")
	private boolean adyacente (int origen, int destino) {
        boolean resultado= false;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos()))      
    		resultado = (arcos[origen][destino]!=INFINITO); 
        return resultado;
    }

   /**
    * Metodo que retorna el peso de un arco
    * 
    * @param origen Es el primer nodo del arco
    * @param destino Es el segundo nodo del arco
    * 
    */
    public int getArco (int origen, int destino) {
        int arco=NOVALOR;
        if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) 	
    		arco=arcos[origen][destino];				     
        return arco;
    }

   /**
    * Metodo que inserta un nuevo nodo en el grafo
    * 
    * @param n Es el nodo que se desea insertar
    * 
    */
    @SuppressWarnings("unused")
	private boolean nuevoNodo(int n) {
        boolean resultado=false;

        if (getNumNodos()<MAXVERT){
            nodos[getNumNodos()]=n;
            setNumNodos(getNumNodos() + 1);
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que elimina un nodo del grafo
    * 
    * @param nodo Nodo que se desea eliminar
    * 
    */
    @SuppressWarnings("unused")
	private boolean borraNodo(int nodo) {
        boolean resultado=false;
    	int pos = nodo; 

        if ((getNumNodos()>0) && (pos <= getNumNodos())) {
            int x,y;
            // Borrar el nodo
            for (x=pos; x<getNumNodos()-1; x++){		
                nodos[x]=nodos[x+1];
    			System.out.println(nodos[x+1]);
    		}
            // Borrar en la Matriz de Adyacencia
            // Borra la fila
            for (x=pos; x<getNumNodos()-1; x++)		
                for (y=0;y<getNumNodos(); y++)
                    arcos[x][y]=arcos[x+1][y];
            // Borra la columna
            for (x=0; x<getNumNodos(); x++)
                for (y=pos;y<getNumNodos()-1; y++)	
                    arcos[x][y]=arcos[x][y+1];
            // Decrementa el numero de nodos
            setNumNodos(getNumNodos() - 1);
            resultado=true;
        }
        return resultado;
    }

   /**
    * Metodo que muestra el vector de nodos del grafo
    * 
    */
    public void mostrarNodos() {
        System.out.println("NODOS:");
        for (int x=0;x<getNumNodos();x++)
            System.out.print(nodos[x] + " ");
        System.out.println();
    }

   /**
    * Metodo que muestra los arcos del grafo (la matriz de adyacencia)
    * 
    */
    public void mostrarArcos()
    {
        int x,y;

        System.out.println("ARCOS:");
        for (x=0;x<getNumNodos();x++) {
            for (y=0;y<getNumNodos();y++) {
                //cout.width(3);
                if (arcos[x][y]!=INFINITO)
                    System.out.format("%4d",arcos[x][y]);
                else
                    System.out.format("%4s","#");
            }
            System.out.println();
        }
    }


   /**
    * Metodo que devuelve el conjunto de nodos adyacentes al nodo actual
    * 
    * @param origen Es el nodo actual
    * @param ady En este conjunto se almacenaran los nodos adyacentes al nodo origen
    * 
    */
    public void adyacentes(int origen, Set<Integer> ady){
       if ((origen >= 0) && (origen < getNumNodos())) {
    		for (int i=0;i<getNumNodos();i++) {
           	 	if ((arcos[origen][i]!=INFINITO) && (arcos[origen][i]!=0))	
              		ady.add(i);	
          	}
    	}
    }

    
    /**
     * Metodo que muestra la matriz de Warshall
     * 
     */
     public void mostrarPW()
     {
         int x,y;

         System.out.println("warshallC:");
         for (x=0;x<getNumNodos();x++) {
             for (y=0;y<getNumNodos();y++)
                 System.out.format("%6b",warshallC[x][y]);
             System.out.println();
         }
     }

    /**
     * Metodo que muestra las matrices de coste y camino de Floyd
     * 
     */
     public void mostrarFloydC()
     {
         int x,y;
         System.out.println("floydC:");
         for (y=0;y<getNumNodos();y++) {
             for (x=0;x<getNumNodos();x++) {
                 //cout.width(3);
                 System.out.format("%4d",floydC[x][y]);
             }
             System.out.println();
         }

         System.out.println("floydP:");
         for (x=0;x<getNumNodos();x++) {
             for (y=0;y<getNumNodos();y++) {
                 //cout.width(2);
                 System.out.format("%4d",floydP[x][y]);
             }
             System.out.println();
         }
     }

    /**
     * Metodo que realiza el algoritmo de Warshall sobre el grafo
     * 
     */
     @SuppressWarnings("unused")
	private void warshall() {
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<getNumNodos();i++)
             for (j=0;j<getNumNodos();j++)
                 warshallC[i][j]=(arcos[i][j]!=INFINITO);

         // Iterar
         for (k=0;k<getNumNodos();k++)
             for (i=0;i<getNumNodos();i++)
                 for (j=0;j<getNumNodos();j++)
                     warshallC[i][j]=(warshallC[i][j] || (warshallC[i][k] && warshallC[k][j]));
     }

    /**
     * Metodo que realiza el algoritmo de Floyd sobre el grafo
     * 
     */
     private void floyd (){
         int i,j,k;

         // Obtener la matriz de adyacencia en P
         for (i=0;i<getNumNodos();i++)
             for (j=0;j<getNumNodos();j++){
                 floydC[i][j]=arcos[i][j];
     			floydP[i][j]=NOVALOR; 	
     		}

         // Iterar
         for (k=0;k<getNumNodos();k++)
             for (i=0;i<getNumNodos();i++)
                 for (j=0;j<getNumNodos();j++)
                     if (i!=j)
                         if ((floydC[i][k]+floydC[k][j] < floydC[i][j])) {
                             floydC[i][j]=floydC[i][k]+floydC[k][j];		
                             floydP[i][j]=k;
                         }
     }

     /**
      * Metodo que devuelve el siguiente nodo en la ruta entre un origen y un destino
      * 
      * @param origen Es el primer nodo
      * @param destino Es el siguiente nodo
      * 
      */
      @SuppressWarnings("unused")
	private int siguiente(int origen, int destino){
      	int sig=-1; // Si no hay camino posible
          if ((origen >= 0) && (origen < getNumNodos()) && (destino >= 0) && (destino < getNumNodos())) {
      		if (warshallC[origen][destino]){ // Para comprobar que haya camino
      	    	if (floydP[origen][destino]!=NOVALOR)
      				sig = siguiente(origen, floydP[origen][destino]);	
          		else
          			sig=destino;
      		}
      	}
          return sig;
      }
      
	     /**
	      * Metodo que almacena las paredes adyacentes de cada nodo, siguiendo el orden N-E-S-O
	      * 
	      * @param ancho Anchura de la galaxia
	      * 
	      * @return LinkedList con parejas de enteros
	      * 
	      */  
	  private LinkedList<Pair<Integer,Integer>> almacenarParedesPair(int ancho){
			
		  LinkedList<Pair<Integer,Integer>> listaParedes = new LinkedList<Pair<Integer,Integer>>();	
		
		  for (int i = 0; i < getNumNodos(); i++){
			
			if (i - ancho >= 0)  // N 
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i-ancho));
			}
			
			if (i/ancho == (i+1)/ancho && i+1 <= getNumNodos())   // E
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i+1));
			}
			
			if (i + ancho < getNumNodos())   // S
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i+ancho));
			}
			
			if (i/ancho == (i-1)/ancho && i-1 >= 0)   // O
			{
			 listaParedes.add(new Pair<Integer,Integer>(i,i-1));
			}
			
		  }
	    return listaParedes;		
	  }
	  

 	 /**
 	  * Metodo para tirar las paredes de la matriz, siguiendo el algoritmo de Kruskal y mediante la genereacion de numeros aleatorios (con semilla)
 	  * 
 	  * @param lista LinkedList que contiene las paredes de todos los nodos con el formato (x-y)
 	  * @param semilla Semilla para el generador de numeros aleatorios
 	  * 
 	  */ 
       private void tirarParedesPair(LinkedList<Pair<Integer,Integer>> lista, long semilla){	
     	  
     	 Pair<Integer,Integer> aux;
     	 int x = 0;
     	 int y = 0;
     	 int randomInt = 0;
     	 Random randomGenerator = new Random(semilla);  
     	 
     	 while (lista.isEmpty() == false){
     		
         	 randomInt = randomGenerator.nextInt(lista.size());
         	 
     		 aux = lista.remove(randomInt);
         	 
         	 x = aux.getFirst();
    	 
         	 y = aux.getSecond();
         	 
         	 if (floydC[x][y] == Grafo.INFINITO) // Si no existe un camino hasta el nodo, tirar la pared, asi evitamos ciclos
         	 {
         	  nuevoArco (x,y,1);
         	  nuevoArco (y,x,1);
         	 }
         	 
         	 x = 0;
         	 y = 0;
         	 floyd();
     	 }	 
     	 	 
       }
      
      public void procesarParedes(int ancho, long semilla){
    	  
    	  tirarParedesPair(almacenarParedesPair(ancho), semilla);
    	  
      }
      
     /**
      * Metodo que dado un inicio y final devuelve el camino entre los dos puntos de una matriz. NO DEVUELVE EL NODO ORIGEN
      * 
      * @param i Es el nodo actual, en la primera llamada es el inicial
      * @param x Es el nodo del que viene, para no volver hacia atr�s
      * @param k Es el nodo al que se desea llegar
      * 
      * @return Devuelve el camino en forma de cadena
      * 
      */
      private String encontrarCamino(int i, int x, int k){
      
     	 String aux = "";
     	 String ultimo = "" + k;
     	 String aux2 = "";
     	
 
     	 if (arcos[i][k] != Grafo.INFINITO )
 		 {
 		  aux += k +" ";
 		 }
     	 else for (int j = 0; j < getNumNodos() && aux.endsWith(" " + ultimo) == false; j++){
     	 
     		 if (arcos[i][j] != Grafo.INFINITO && i != j && j != x)
     		 {
     		  aux2 = aux;
     		  aux += j;
     	      aux = aux + " " + encontrarCamino(j, i, k);
     	      
     	      	if (aux.endsWith(" " + ultimo + " ") == false)   // Quitar del camino final los que hemos ido probando y no han resultado
     	      	{
     	      	 aux = aux2;
     	      	}
     		 }   		 
     	 }

        return aux;		 
      }
      
      /**
       * Metodo que dado un inicio y final devuelve el camino entre los dos puntos de una matriz. NO DEVUELVE EL NODO ORIGEN
       * 
       * @param i Es el nodo actual, en la primera llamada es el inicial
       * @param x Es el nodo del que viene, para no volver hacia atr�s
       * @param k Es el nodo al que se desea llegar
       * 
       * @return Devuelve el camino en forma de lista de enteros
       * 
       */
       protected List<Integer> encontrarCaminoList(int i, int x, int k, List<Integer> Camino){

      	 if (arcos[i][k] != Grafo.INFINITO )
  		 {
  		  Camino.add(k);
  		  //TODO Podriamos a�adir el origen?
  		 }
      	 else for (int j = 0; j < getNumNodos() && Camino.contains(k) == false; j++){
      	 
      		 if (arcos[i][j] != Grafo.INFINITO && i != j && j != x)
      		 {
      		  Camino.add(j);
      		  encontrarCaminoList(j,i,k,Camino);

      	      	if (Camino.contains(k) == false)   // Quitar del camino final los que hemos ido probando y no han resultado
      	      	{
      	      	 Camino.remove(j);
      	      	}
      		 }   		 
      	 }

         return Camino;
       }
      
     /**
      * Metodo que aplica el algoritmo de la mano derecha a la matriz de arcos, devolviendo el camino en forma de List
      *
      * @param i El nodo de inicio
      * @param j El nodo fin
      * @param ancho Anchura de la galaxia
      * 
      * @return El camino seguido en forma de Lista de enteros
      * 
      */
      private List<Integer> manoDerecha (int i, int j, int ancho){
          int anterior = i;
          boolean fin = false;
          List<Integer> camino = new LinkedList<Integer>();
          //MOVIMIENTO INICIAL
          if (arcos[i][i+ancho] != Grafo.INFINITO)
          {
           camino.add(i+ancho);
           i+=ancho;
          }         
          else if (arcos[i][i+1] != Grafo.INFINITO)
          {
           camino.add(i+1);
           i++;
          }
          else 
          {
           camino.add(i-ancho);
           i -= ancho;
          }
          boolean haMovido;
          while (!fin)
          {
        	  haMovido = false;
              if (i-anterior == 1) //Si venimos de la izquierda comprobamos abajo, derecha, arriba, izquierda
              {
                  if (i+ancho < getNumNodos())
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
                  if (i-ancho >= 0 && haMovido == false)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                    	  camino.add(i-ancho);
                    	  anterior = i;
                    	  i-=ancho;
                    	  haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false)
                  {
                      camino.add(i-1);
                      anterior = i;
                      i--;
                      haMovido = true;
                  }
              }
              else if(i-anterior == ancho*(-1)) //Si venimos desde abajo comprobamos derecha, arriba, izquierda, abajo
              {
                  if ((i+1)/ancho == i/ancho)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
                  if (i-ancho >= 0 && haMovido == false)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i-=ancho;
                          haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false)
                  {
                  	  if (arcos[i][i-1] != Grafo.INFINITO)
                      {
                          camino.add(i-1);
                          anterior = i;
                          i--;
                          haMovido = true;
                      }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
              }
              else if(i-anterior == ancho) //Si venimos desde arriba comprobamos izquierda, abajo, derecha, arriba
              {
                  if ((i-1)/ancho == i/ancho)
                  {
                	  if (arcos[i][i-1] != Grafo.INFINITO)
                	  {
                		  camino.add(i-1);
                		  anterior = i;
                		  i--;
                		  haMovido = true;
                	  }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                  		if (arcos[i][i+1] != Grafo.INFINITO)
                  		{
                  			camino.add(i+1);
                  			anterior = i;
                  			i++;
                  			haMovido = true;
                  		}
                  }
                  if (i-ancho >= 0 && haMovido == false)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i-=ancho;
                          haMovido = true;
                      }
                  }
              }
              else //Si venimos desde la derecha comprobamos ariba, izquierda, abajo, derecha
              {
                  if (i-ancho >= 0)
                  {
                      if(arcos[i][i-ancho] != Grafo.INFINITO)
                      {
                          camino.add(i-ancho);
                          anterior = i;
                          i-=ancho;
                          haMovido = true;
                      }
                  }
                  if ((i-1)/ancho == i/ancho && haMovido == false)
                  {
                	  if (arcos[i][i-1] != Grafo.INFINITO)
                	  {
                		  camino.add(i-1);
                		  anterior = i;
                		  i--;
                		  haMovido = true;
                	  }
                  }
                  if (i+ancho < getNumNodos() && haMovido == false)
                  {
                      if (arcos[i][i+ancho] != Grafo.INFINITO)
                      {
                          camino.add(i+ancho);
                          anterior = i;
                          i += ancho;
                          haMovido = true;
                      }
                  }
                  if ((i+1)/ancho == i/ancho && haMovido == false)
                  {
                	  if (arcos[i][i+1] != Grafo.INFINITO)
                	  {
                		  camino.add(i+1);
                		  anterior = i;
                		  i++;
                		  haMovido = true;
                	  }
                  }
              }
                  
              if (i == j)
                  fin = true;
          }
          
        return camino;
      }
      
     /**
      * Metodo que interpretar el camino devuelto por los metodos encontrarCamino y manoDerecha, transformando los resultados en una cola de "dir" para los personajes 
      *
      * @param ancho Anchura de la galaxia
      * 
      * @return Cola de tipo "dir"
      * 
      * @throws PersNoValido 
      * 
      */
      public Queue<dir> asignarCamino(int ancho, String pj, int puertaGal) throws PersNoValido{
      //TODO Esto pasarlo a cada personaje, que solamente llamen a encontrarCamino o a manoDerecha, aunque antes se arregla  
    	  List<Integer> aux; 
    	  int actual = 0; // Marcador del nodo actual
    	  int sig = 0; // Marcador del nodo al que vamos
    	  Queue<dir> camino = new LinkedList<dir>();  // Para almacenar la ruta 
    	  //TODO este switch es un metodo Virtual en Personaje
    	  switch (pj)
    	  {
    	  case ("FamiliaReal"):
    	   aux = new LinkedList<Integer>();	  
    	   aux = encontrarCaminoList (0, -1, puertaGal, aux);  // Obtener el camino de esquina arriba izquierda a la puerta de salida
    	  break;
    	  
    	  case ("Jedi"):
    	   aux = manoDerecha (0, puertaGal, ancho);  // Obtener el camino de esquina arriba izquierda a la puerta de salida utilizando el metodo de la mano derecha
    	  break;
    	  
    	  case ("Contrabandista"):
       	   aux = manoDerecha (getNumNodos()-ancho, puertaGal, ancho);  // Obtener el camino de esquina inferior izquierda a la puerta de salida utilizando el metodo de la mano derecha
    	   actual = getNumNodos()-ancho;  // Nodo de salida del pj
       	  break;
       	  
    	  case ("Imperial"):
    	   aux = new LinkedList<Integer>();
    	   aux = encontrarCaminoList (getNumNodos()-1, -1, ancho-1,aux);
    	   List<Integer> auxImp = new LinkedList<Integer>();
    	   auxImp = encontrarCaminoList(ancho-1, -1, 0, auxImp);
    	   while (auxImp.isEmpty() == false)
    	   {
    		   aux.add(auxImp.remove(0));
    	   }
    	   auxImp = encontrarCaminoList(0, -1, getNumNodos()-ancho,auxImp);
    	   while (auxImp.isEmpty() == false)
    	   {
    		   aux.add(auxImp.remove(0));
    	   }
    	   auxImp = encontrarCaminoList(getNumNodos()-ancho,-1,getNumNodos()-1, auxImp);
    	   while (auxImp.isEmpty() == false)
    	   {
    		   aux.add(auxImp.remove(0));
    	   }
    	   actual = getNumNodos()-1;  // Nodo de salida del pj 
    	   break;
    	  
    	  default:
    		  throw new PersNoValido("Error, tipo de personaje no reconocido, imposible asignar ruta");
    	  }     	
    	     	   
    	  //TODO esto tiene que hacerlo cada PJ, aunque el metodo no deberia ser virtual, ya que el procedimiento es el mismo
    	  // Interpretar movimientos
    	  for (int i = 0; i < aux.size(); i++){
    		  
    		  sig = aux.get(i);  // El nodo al que vamos
    		  
    		  if ((sig - actual) == ancho)
    		  {
    		   camino.add(dir.S);
    		   actual = sig;
    		  }
    		  
    		  if ((sig - actual) == -ancho)
    		  {
    		   camino.add(dir.N);
    		   actual = sig;
    		  }
    		  
    		  if ((sig - actual) == 1 && ancho != 1)
    		  {
    		   camino.add(dir.E);
    		   actual = sig;
    		  }
    		  
    		  if ((sig - actual) == -1 && ancho != 1)
    		  {
    		   camino.add(dir.O);
    		   actual = sig;
    		  } 			  
    		  
    	  }
    	  
      return camino; 
    }
    
      
   /**
    * Metodo que devuelve la ruta de los FamiliaReal en un vector de enteros
    *
    * @param puertaGal Puerta de salida de la Galaxia
    * 
    * @return Vector de enteros con la id de los nodos de la ruta
    * 
    */
    public int[] asignarMidis(int puertaGal){
    	
    	List<Integer> aux = new LinkedList<Integer>();
    	aux = encontrarCaminoList(0, -1, puertaGal,aux);

  	    int[] vectorInt = new int[aux.size()];	// Crear un vector de Int para almacenar los valores
  	  
  	    for (int i = 0; i < aux.size(); i++){ 		  
									
  		    vectorInt[i] = aux.get(i);	
  	    } 
  	    
  	  return vectorInt;
    }
    
    /**
     * Metodo que devuelve los arcos que existen en el tablero de la galaxia en forma de un vector de enteros
     *
     * @return Vector de enteros con la id de los nodos que forman arcos (a pares)
     * 
     */
    public List<Pair<Integer,Integer>> devolverArcos(){
    	List<Pair<Integer,Integer>> listaArcos = new LinkedList<Pair<Integer,Integer>>();
    	for (int i = 0; i < getNumNodos(); i++){
    		
    		for (int j = i; j < getNumNodos(); j++){  // Solo diagonal superior
    			
    			if (arcos[i][j] == 1)
    			{
    			 listaArcos.add(new Pair<Integer,Integer>(i,j));	
    			}		
    		}
    	}
      return listaArcos; // Como los arcos siempre van en parejas, es facil distinguir sus uniones en su posterior uso 	
    }

	public int getNumNodos() {
		return numNodos;
	}

	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}
	
}

