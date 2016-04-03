package DEV;

import java.util.Iterator;
import java.util.*;

/**
* Declaracion de la clase PruebasGrafo
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class PruebasGrafo {

		public static void ejecutarPruebas(){
		
		System.out.println("-----------------------------------");
		
		System.out.println("INICIANDO PRUEBAS Grafo.java:");
		System.out.println();
		
		Grafo g = new Grafo (5,5, 24);
		
	    g.procesarParedes(5, 1);
	    System.out.println("Mostrando arcos despues de tirar las paredes:");
	    g.mostrarArcos();
	    Queue<Personaje.dir> colaPrueb = new LinkedList<Personaje.dir>();
	    try 
	    {
	    colaPrueb = g.asignarCamino(5, "Jedi", 24);
	    }
	    catch (PersNoValido e)
	    {
	    	
	    }
	    System.out.println("En este grafo, el camino para un Jedi seria:");
	    Iterator<Personaje.dir> it = colaPrueb.iterator();
	    while (it.hasNext())
	    	System.out.print(it.next()+ " ");

	    System.out.println();		
		try 
	    {
		System.out.print("Comprobando excepcion personajes no validos...   ");
	    colaPrueb = g.asignarCamino(5, "Jeedi", 24);
		System.out.println("Error.");
	    }
	    catch (PersNoValido e)
	    {
	    	System.out.println("Correcto.");
	    }
		
		System.out.println("En este grafo, la ruta para un FamliaReal (usando asignarMidis) seria:");
		int[] vectPrueb = g.asignarMidis(24);
		
		for (int i = 0; i<vectPrueb.length; i++)
			System.out.print(vectPrueb[i] + " ");
		
		System.out.println("");
		//vectPrueb = g.devolverArcos();
		//TODO 4.0 A New Pair
		boolean correcto = true;
		System.out.print("Comprobando vector de arcos...   ");
		
		for (int i =0; i<vectPrueb.length;i=i+2)
		{
			if(g.getArco(vectPrueb[i], vectPrueb[i+1])==Grafo.INFINITO)
				correcto = false;
		}
		
		if (correcto == true)
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		System.out.println("-----------------------------------");
	}
	
	public static void main(String [ ] args){
		
		ejecutarPruebas();
	}

}
