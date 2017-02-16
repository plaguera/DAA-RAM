package ram;

/**
 * Clase que define una excepción de error de sintaxis.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * SyntaxException.java
 */
public class SyntaxException extends Exception {
	
	public SyntaxException() { super(); }
	public SyntaxException(String message) { super(message); }
	public SyntaxException(String message, Throwable cause) { super(message, cause); }
	public SyntaxException(Throwable cause) { super(cause); }
	
}
