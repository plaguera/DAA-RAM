package instruction;

/**
 * Clase que define instrucciones que requieren un operando entero y un modo de direccionamiento. (LABEL): NAME (ADDRESSING)ARGUMENT
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeOP.java
 */
public class InstOperation extends Instruction {

	private String name;
	private Addressing addressing;
	private int argument;
	private String label;
	
	public InstOperation(String name_p, Addressing addressing_p, int argument_p) {
		
		if (instExists(name_p) && operCompatible(name_p, addressing_p, argument_p)){
			name = name_p;
			addressing = addressing_p;
			argument = argument_p;
			label = "";
		}
		else
			throw new IllegalArgumentException("ERROR - INSTRUCTION " + name_p + " DOESN'T EXIST WITH " + addressing_p + " ADDRESSING !!");
		
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
			case "LOAD": break;
			case "STORE": break;
			case "ADD": break;
			case "SUB": break;
			case "MUL": break;
			case "DIV": break;
			case "READ": break;
			case "WRITE": break;
			default: exists = false;
		}
		return exists;
	}
	
	/**
	 * Comprueba si la combinación de instrucción, direccionamiento y operando está permitida.
	 * @param newInst
	 * @param newOperType
	 * @param newOperValue
	 * @return True si está permitida, False si no está permitida.
	 */
	private boolean operCompatible(String newInst, Addressing newOperType, int newOperValue){

		if((newInst.equals("READ") || newInst.equals("WRITE")) && newOperType == Addressing.DIRECT && newOperValue == 0) 
			return false;
		if((newInst.equals("READ") || newInst.equals("STORE")) && newOperType == Addressing.CONSTANT)
			return false;
		
		return true;
	}
	
	/**
	 * Permite imprimir objetos de esta clase de manera formateada por pantalla.
	 * @return
	 */
	public String toString(){
		String output = "";
		if (label != "")
			output = label + ": ";
		output += name;
		if (addressing == Addressing.CONSTANT)
			output += " =" + argument;
		else if (addressing == Addressing.DIRECT)
			output += " " + argument;
		else if (addressing == Addressing.INDIRECT)
			output += " *" + argument;
		
		return output;
	}
	
	/**
	 * Getter del nombre de la instrucción.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Getter del argumento de la instrucción.
	 * @return
	 */
	public int getArgument(){
		return argument;
	}
	
	/**
	 * Getter del direccionamiento de la instrucción.
	 * @return
	 */
	public Addressing getAddressing(){
		return addressing;
	}
	
	/**
	 * Getter de la etiqueta de la instrucción.
	 * @return
	 */
	public String getLabel(){
		return label;
	}
	
}
