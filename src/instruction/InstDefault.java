package instruction;

/**
 * Clase que define instrucciones sin argumentos. (LABEL): NAME
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeDef.java
 */
public class InstDefault extends Instruction {
	
	private String name;
	private String label;
	
	public InstDefault(String name_p) {
		
		if (instExists(name_p)){
			name = name_p;
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
			case "HALT": break;
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
		
		return output + name;
	}
	
	/**
	 * Getter del nombre de la instrucción.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Getter de la etiqueta de la instrucción.
	 */
	public String getLabel(){
		return label;
	}

}
