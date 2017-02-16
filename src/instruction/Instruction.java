package instruction;

/**
 * Clase padre abstracta de todos los tipos de Instrucción
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * Instruction.java
 */
public abstract class Instruction {
	
	public abstract void addLabel(String newLabel);
	public abstract String getName();
	public abstract String getLabel();
	public abstract String toString();
	
}
