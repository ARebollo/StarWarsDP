package Excepciones;

@SuppressWarnings("serial")
public class ConfigNoValida extends Exception {

	public ConfigNoValida() {
	
	}

	public ConfigNoValida(String arg0) {
		super(arg0);
	
	}

	public ConfigNoValida(Throwable arg0) {
		super(arg0);
	
	}

	public ConfigNoValida(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public ConfigNoValida(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	
	}

}
