package instruction;

/**
 * Clase padre abstracta de todos los tipos de Instrucción
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * Instruction.java
 */
public abstract class Instruction {
	
	/**
	 * Añade una etiqueta a la instrucción.
	 * @param newLabel
	 */
	public abstract void addLabel(String newLabel);
	
	/**
	 * Getter del nombre de la instrucción.
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * Getter de la etiqueta de la instrucción.
	 * @return
	 */
	public abstract String getLabel();
	
	/**
	 * Permite imprimir objetos de esta clase de manera formateada por pantalla.
	 * @return
	 */
	public abstract String toString();
	
}
