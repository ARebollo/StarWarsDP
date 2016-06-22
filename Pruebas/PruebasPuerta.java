package Pruebas;

import java.util.LinkedList;

import DEV.Puerta;
import Estructuras.Midi;

/**
* Declaracion de la clase PruebasPuerta
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class PruebasPuerta {

	public static void ejecutarPruebas(){
		
		System.out.println("-----------------------------------");
		
		System.out.println("INICIANDO PRUEBAS Puerta.java:");
		System.out.println();
		
		Puerta puerPrueba = new Puerta();
        LinkedList<Midi> list = new LinkedList<Midi>();
        for (int i = 1;i<30;i++)
            list.add(new Midi(i));
       
        puerPrueba.setVectorCfgLinkedList(list);
        System.out.println("La combinacion es:" );
        puerPrueba.mostrarVectorCfg();
        puerPrueba.cerrarPuerta();
        
        System.out.print("Comprobando cerrarPuerta...   ");
        if (puerPrueba.isEstado() == false)
        	System.out.println("Correcto.");
        else 
        	System.out.println("Error.");
        
        System.out.println("Mostrando combinación puerta (por profundidad):");
        puerPrueba.getCombinacion().anchura();
        
        Midi aborrar = new Midi(11);
        puerPrueba.probarMidicloriano(aborrar);
        System.out.println("Comprobando si se ha probado el Midi 11:");
        puerPrueba.getProbados().inOrder();
        
        System.out.println();
        System.out.println("Mostrando combinacion restante:");
        puerPrueba.getCombinacion().anchura();
        
        puerPrueba.setProfundidad(10);
        Midi aborrar2 = new Midi(12);
        puerPrueba.probarMidicloriano(aborrar2);
        
        System.out.print("Comprobando apertura de la puerta...   ");
        if (puerPrueba.isEstado() == true)
        	System.out.println("Correcto.");
        else 
        	System.out.println("Error.");

		
		System.out.println();		
		System.out.println("-----------------------------------");
		
	}
	
	public static void main(String [ ] args){
		
		ejecutarPruebas();
	}

}
