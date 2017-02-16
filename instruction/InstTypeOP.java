package instruction;

/**
 * Clase que define instrucciones que requieren un operando entero y un modo de direccionamiento.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeOP.java
 */
public class InstTypeOP extends Instruction {

	private String name;
	private Addressing addressing;
	private int argument;
	private String label;
	
	public InstTypeOP(String name_p, Addressing addressing_p, int argument_p) {
		
		if (instExists(name_p) && operCompatible(name_p, addressing_p, argument_p)){
			name = name_p;
			addressing = addressing_p;
			argument = argument_p;
			label = "";
		}
		else
			throw new IllegalArgumentException("ERROR - INSTRUCTION " + name_p + " DOESN'T EXIST WITH " + addressing_p + " ADDRESSING !!");
		
	}
	
	public void addLabel(String newLabel){
		label = newLabel;
	}
	
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
	
	private boolean operCompatible(String newInst, Addressing newOperType, int newOperValue){

		if((newInst.equals("READ") || newInst.equals("WRITE")) && newOperType == Addressing.DIRECT && newOperValue == 0) 
			return false;
		if((newInst.equals("READ") || newInst.equals("STORE")) && newOperType == Addressing.CONSTANT)
			return false;
		
		return true;
	}
	
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
	
	public String getName(){
		return name;
	}
	
	public int getArgument(){
		return argument;
	}
	
	public Addressing getAddressing(){
		return addressing;
	}
	
	public String getLabel(){
		return label;
	}
	
}
