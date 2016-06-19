package DEV;

/**
* Declaracion de la clase PersNoValido
* @author
*   <b> Antonio Rebollo Guerra, Carlos Salguero Sanchez </b><br>
*   <b> Asignatura Desarrollo de Programas</b><br>
*   <b> Curso 15/16 </b>
*/

@SuppressWarnings("serial")
public class PersNoValido extends Exception {

	public PersNoValido() {
		
	}

	public PersNoValido(String arg0) {
		super(arg0);
		
	}

	public PersNoValido(Throwable arg0) {
		super(arg0);
		
	}

	public PersNoValido(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public PersNoValido(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

}
