package instruction;

/**
 * Clase que define instrucciones de salto. (LABEL): NAME ARGUMENT
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeJump.java
 */
public class InstJump extends Instruction {

	private String name;
	private String argument;
	private String label;
	
	public InstJump(String name_p, String argument_p) {
		
		if (instExists(name_p)){
			name = name_p;
			argument = argument_p;
			label = "";
		}
		else
			throw new IllegalArgumentException("ERROR - INSTRUCTION " + name_p + " DOESN'T EXIST !!");
		
	}
	
	/**
	 * Añade una etiqueta a la instrucción.
	 * @param newLabel
	 */
	public void addLabel(String newLabel){
		label = newLabel;
	}
	
	/**
	 * Comprueba si la instrucción existe dentro del repertorio de este tipo.
	 * @param newInst
	 * @return	True si existe, False si no existe.
	 */
	private boolean instExists(String newInst){
		boolean exists = true;
		switch (newInst){
			case "JUMP": break;
			case "JZERO": break;
			case "JGTZ": break;
			default: exists = false;
		}
		return exists;
	}
	
	/**
	 * Permite imprimir objetos de esta clase de manera formateada por pantalla.
	 * @return
	 */
	public String toString(){
		String output = "";
		if (label != "")
			output = label + ": ";
		
		return output + name + " " + argument;
	}
	
	/**
	 * Getter del nombre de la instrucción.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Getter del argumento (etiqueta a la que salta) de la instrucción.
	 */
	public String getArgument(){
		return argument;
	}
	
	/**
	 * Getter de la etiqueta de la instrucción.
	 */
	public String getLabel(){
		return label;
	}
	
}
