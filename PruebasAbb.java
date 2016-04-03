package DEV;

/**
* Declaracion de la clase PruebasAbb
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/
public class PruebasAbb {
	
	public static void ejecutarPruebas(){
		
		System.out.println("-----------------------------------");
		System.out.println("INICIANDO PRUEBAS abb.java:");
		
		abb<Integer> arbolPrueb = new abb<Integer>();
		
		System.out.println();
		System.out.print("Comprobando esVacio...   ");
		
		if (arbolPrueb.esVacio())
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		arbolPrueb.insertar(7);
		arbolPrueb.insertar(1);
		arbolPrueb.insertar(5);
		arbolPrueb.insertar(6);
		arbolPrueb.insertar(11);
		arbolPrueb.insertar(8);
		arbolPrueb.insertar(2);
		arbolPrueb.insertar(3);
		arbolPrueb.insertar(4);
		arbolPrueb.insertar(10);
		arbolPrueb.insertar(9);
		
		System.out.print("Comprobando existe...   ");
		if (arbolPrueb.existe(3))
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		System.out.println();
		System.out.print("Mostrando arbol inOrden: ");
		arbolPrueb.inOrder();
		
		System.out.println();
		System.out.print("Mostrando arbol preOrden: ");
		arbolPrueb.preOrder();
		
		System.out.println();
		System.out.print("Mostrando arbol posOrden: ");
		arbolPrueb.posOrder();
		
		System.out.println();
		System.out.print("Mostrando arbol por profundidad: ");
		arbolPrueb.anchura();
		
		System.out.println("La profundidad del arbol es " + arbolPrueb.altura());
		
		System.out.println();
		System.out.print("Comprobando arbolAString...   ");
		if (arbolPrueb.arbolAString().equals(" 1 2 3 4 5 6 7 8 9 10 11"))
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		System.out.print("Comprobando eliminar...   ");
		arbolPrueb.eliminar(4);
		if (!arbolPrueb.existe(4))
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		arbolPrueb.insertar(4);
		
		System.out.print("Comprobando numHojas...   ");
		if (arbolPrueb.numHojas()==3)
			System.out.println("Correcto.");
		else 
			System.out.println("Error.");
		
		System.out.print("Comprobando numNodos...   ");
		if (arbolPrueb.numNodos()==11)
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
