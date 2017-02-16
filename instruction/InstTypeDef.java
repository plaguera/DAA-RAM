package instruction;

/**
 * Clase que define instrucciones sin argumentos.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * InstTypeDef.java
 */
public class InstTypeDef extends Instruction {
	
	private String name;
	private String label;
	
	public InstTypeDef(String name_p) {
		
		if (instExists(name_p)){
			name = name_p;
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
			case "HALT": break;
			default: exists = false;
		}
		return exists;
	}
	
	public String toString(){
		String output = "";
		if (label != "")
			output = label + ": ";
		
		return output + name;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLabel(){
		return label;
	}

}
