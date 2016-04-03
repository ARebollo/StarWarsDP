package DEV;

/**
* Declaracion de la clase pruebasEstacion
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class PruebasEstacion {
	
	public static void ejecutarPruebas(){
		
		System.out.println("-----------------------------------");
		
		System.out.println("INICIANDO PRUEBAS Estacion.java:");
		System.out.println();
		
		Estacion estPrueba = new Estacion();
		
		System.out.println("Comprobando aniadirMidi y mostrarLista (se añaden 3,7,11,5 y deberia devolverlos ordenados de menor a mayor):   ");
		estPrueba.aniadirMidi(new Midi(3));
		estPrueba.aniadirMidi(new Midi(7));
		estPrueba.aniadirMidi(new Midi(11));
		estPrueba.aniadirMidi(new Midi(5));
		estPrueba.mostrarLista();
		
		System.out.println();		
		System.out.println("-----------------------------------");
		
	}
	
	public static void main(String [ ] args){
		
		ejecutarPruebas();
	}

}
