package DEV;

import java.util.*;

/**
* Declaracion de la clase abb
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class abb <T extends Comparable<T>> {
	 
	private class nodoArbol<T extends Comparable<T>> {

        private abb<T> hd;

        private abb<T> hi;

        private T dato;


        private nodoArbol(){

            hd = null;

            hi = null;

        }

    }

    public nodoArbol<T> raiz;
    
    public abb(){

        @SuppressWarnings("unused")
		nodoArbol<T> raiz = new nodoArbol<T>();

    }

    /**
	 * Comprueba si el arbol esta vacio
	 *
	 * @return True : si el arbol esta vacio <br> False : si el arbol no esta vacio
	 * 
	 */
    public boolean esVacio(){

        return (raiz == null);

    }
 
    /**
	 * Inserta un nuevo dato en el arbol, ordenandolo adecuadamente.
	 *
	 * @param dato Dato a introducir
	 * 
	 */
    public void insertar(T dato){

        if (esVacio())
        {
         nodoArbol<T> nuevo = new nodoArbol<T>();
         nuevo.dato = dato;
         nuevo.hd = new abb<T>();
         nuevo.hi = new abb<T>();
         raiz = nuevo;
        }
        else 
        {

            if (raiz.dato.compareTo(dato) == -1) 
            {
             (raiz.hd).insertar(dato);
            }

            if (raiz.dato.compareTo(dato) == 1)
            {
             (raiz.hi).insertar(dato);
            }
        }

    }

    /**
	 * Muestra el arbol siguiendo un recorrido preOrder
	 *
	 */
	public void preOrder(){

        if (!esVacio()) 
        {
         System.out.print( raiz.dato.toString() + ", "  );

         raiz.hi.preOrder();

         raiz.hd.preOrder();
        }

    }

	/**
	 * Muestra el arbol siguiendo un recorrido inOrder
	 *
	 */
    public void inOrder(){

        if (!esVacio()) 
        {
         raiz.hi.inOrder();
         
         System.out.print( raiz.dato.toString() + ", "  );

         raiz.hd.inOrder();
        }

    }

    /**
   	 * Muestra el arbol siguiendo un recorrido posOrder
   	 *
   	 */
    public void posOrder(){

        if (!esVacio()) 
        {
         raiz.hd.posOrder();
         
         raiz.hi.posOrder();

         System.out.print( raiz.dato.toString() + ", "  );
        }
        
    }

    /**
   	 * Muestra el arbol siguiendo un recorrido en anchura
   	 *
   	 */
    public void anchura(){
        
        Queue<nodoArbol<T>> cola, colaAux;
        nodoArbol<T> aux;
        cola = new LinkedList<nodoArbol<T>>();
        colaAux = new LinkedList<nodoArbol<T>>();
        
        if (esVacio() == false)
        {  
        	cola.add(this.raiz); 
        	while (cola.isEmpty() == false) 
        	{
        	 aux = cola.remove();
        	 if (aux != null)
        	 {
        	  colaAux.add(aux); 
        	 	if (aux.hi != null) 
        	 	{
        	 	 cola.add(aux.hi.raiz); 
        	 	}
        	
        	 	if (aux.hd != null) 
        	 	{
        	 	 cola.add(aux.hd.raiz); 
        	 	}
        	 }
        }
        	
        while (colaAux.isEmpty() == false){
        	
        System.out.print(colaAux.remove().dato + ", ");	
        
        }
        
        System.out.println("");
        
        }
    }
    
    /**
   	 * Busca un dato en el arbol y si lo encuentra, devuelve el subarbol de ese dato
   	 *
   	 * @param dato Dato a buscar
   	 * 
   	 * @return Arbol del dato buscado
   	 * 
   	 */
    public abb<T> buscar(T dato){

        abb<T> arbolito = null;
        if (!esVacio()) 
        {

            if (raiz.dato.compareTo(dato) == 0) 
            {
             return this; 
            }
            else
            {

                if (raiz.dato.compareTo(dato) == 1) 
                {
                 arbolito = raiz.hi.buscar(dato);
                }
                else 
                {
                 arbolito = raiz.hd.buscar(dato);
                }

            }

        }

      return arbolito;
    }

    /**
   	 * Busca un dato en el arbol
   	 *
   	 * @param dato Dato a buscar
   	 * 
   	 * @return True : si el dato se encuentra en el arbol <br> False : si el dato no esta en el arbol
   	 * 
   	 */
    public boolean existe(T dato){

    if (raiz != null && raiz.dato != null) {
            if (dato.compareTo(raiz.dato) == 0) 
            {
             return true;
            }
            else 
            {
            	
            	boolean resultado;
            	
                if (raiz.dato.compareTo(dato) == 1) 
                {
                 resultado = raiz.hi.existe(dato);
                }
                else
                {
                 resultado = raiz.hd.existe(dato);
                }
                
                return resultado;
            }
        }
    
      else return false;
    }
 
    /**
   	 * Calcula la profundidad del arbol
   	 *
   	 * @return Profundidad del arbol
   	 * 
   	 */
    public int altura() {

        if (esVacio() == true)
        {
         return 0;
        }

        else 
        {
         return (1 + Math.max(((raiz.hi).altura()), ((raiz.hd).altura())));
        }
    }

    /**
   	 * Busca el dato mas pequeño del arbol
   	 *
   	 * @return Dato mas pequeño
   	 * 
   	 */
    public T buscarMin() {
    	
    	T aux;
    	
        abb<T> arbolActual = this;

        while (arbolActual.raiz.hi.esVacio() == false){

            arbolActual = arbolActual.raiz.hi;

        }

        aux = arbolActual.raiz.dato;

      return aux;
    }
 
    /**
   	 * Identifica si un nodo es hoja
   	 *
   	 * @return True : si el nodo es hoja <br> False : si el nodo no es hoja
   	 * 
   	 */
    private boolean esHoja() {

        boolean hoja = false;

        if (raiz.hi.esVacio() == true && raiz.hd.esVacio() == true ) 
        {
         hoja = true;
        }

      return hoja;
    }

    /**
     * Devuelve los contenidos del arbol en un string
     * 
     * @return a String que contiene el arbol, con espacios entre cada valor.
     * 
     */
    public String arbolAString() {
        
        String a =  "";
     
        	if (!esVacio()) 
            {
             a = a + raiz.hi.arbolAString();
             a = a + " " + raiz.dato.toString();
             a = a + raiz.hd.arbolAString();          
            }
        
      return a;
    } 
    /**
   	 * Elimina un dato del arbol, y lo reestructura de manera correcta
   	 *
   	 * @param dato Dato a eliminar
   	 * 
   	 */      
    public abb<T> eliminar(T dato){
        T datoaux;
        abb<T> retorno=this;
        abb<T> aborrar, candidato, antecesor;
 
        if (esVacio() == false) {
            if (raiz.dato.compareTo(dato)>0){       // dato<datoRaiz
                        raiz.hi = raiz.hi.eliminar(dato);
            }
            else
                if (raiz.dato.compareTo(dato)<0) {  // dato>datoRaiz
                       raiz.hd = raiz.hd.eliminar(dato);
                }
                else {
                    aborrar=this;
                    if (esHoja() == true) { /*si es hoja*/
                        abb<T> nulo = new abb<T>();
                        retorno= nulo;
                    }
                    else {
                        if (raiz.hd.esVacio() == true) { /*Solo hijo izquierdo*/
                            aborrar=raiz.hi;
                            datoaux=raiz.dato;
                            raiz.dato=raiz.hi.raiz.dato;
                            raiz.hi.raiz.dato = datoaux;
                            raiz.hi=raiz.hi.raiz.hi;
                            raiz.hd=aborrar.raiz.hd;
 
                            retorno=this;
                        }
                        else
                            if (raiz.hi.esVacio() == true) { /*Solo hijo derecho*/
                                aborrar=raiz.hd;
                                datoaux=raiz.dato;
                                raiz.dato=raiz.hd.raiz.dato;
                                raiz.hd.raiz.dato = datoaux;
                                raiz.hd=raiz.hd.raiz.hd;
                                raiz.hi=aborrar.raiz.hi;
 
                                retorno=this;
                            }
                            else { /* Tiene dos hijos */
                                candidato = raiz.hi;
                                antecesor = this;
                                while (candidato.raiz.hd.esVacio() == false) {
                                    antecesor = candidato;
                                    candidato = candidato.raiz.hd;
                                }
 
                                /*Intercambio de datos de candidato*/
                                datoaux = raiz.dato;
                                raiz.dato = candidato.raiz.dato;
                                candidato.raiz.dato=datoaux;
                                aborrar = candidato;
                                if (antecesor.equals(this))
                                    raiz.hi=candidato.raiz.hi;
                                else
                                    antecesor.raiz.hd=candidato.raiz.hi;
                            } //Eliminar solo ese nodo, no todo el subarbol
                        aborrar.raiz.hi=null;
                        aborrar.raiz.hd=null;
                    }
                }
        }
        return retorno;
    }
        
    /**
   	 * Calcula el numero de hojas totales del arbol
   	 * 
   	 * @return Numero de hojas del arbol
   	 * 
   	 */
    public int numHojas(){
    	
    	int cont;
    	
    	if (esVacio() == true)
    	{
    	 cont = 0;
    	}
    	else
    	{
    	
    		if (esHoja() == true)
    		{
    		 cont = 1;
    		}
    		else
    		{
    		 cont = raiz.hi.numHojas() + raiz.hd.numHojas();
    		}
    	}
    	
      return cont;	
    }  
    
    /**
   	 * Calcula el numero de nodos que tiene el arbol
   	 * 
   	 * @return Numero de nodos del arbol
   	 * 
   	 */
    public int numNodos(){
    	
    	int cont;
    	
    	if (esVacio())
    	{
    	 cont = 0;
    	}
    	else
    	{
         cont = 1 + raiz.hi.numNodos() + raiz.hd.numNodos();
    	}
    	
      return cont;	
    }      
    
}
