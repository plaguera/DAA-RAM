package instruction;

/**
 * Clase que define instrucciones de salto.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeJump.java
 */
public class InstTypeJump extends Instruction {

	private String name;
	private String argument;
	private String label;
	
	public InstTypeJump(String name_p, String argument_p) {
		
		if (instExists(name_p)){
			name = name_p;
			argument = argument_p;
			label = "";
		}
		else
			throw new IllegalArgumentException("ERROR - INSTRUCTION " + name_p + " DOESN'T EXIST !!");
		
	}
	
	public void addLabel(String newLabel){
		label = newLabel;
	}
	
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
	
	public String toString(){
		String output = "";
		if (label != "")
			output = label + ": ";
		
		return output + name + " " + argument;
	}
	
	public String getName(){
		return name;
	}
	
	public String getArgument(){
		return argument;
	}
	
	public String getLabel(){
		return label;
	}
	
}
