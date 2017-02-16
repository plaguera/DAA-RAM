package instruction;

public class InstTypeJump extends Instruction {

	public String instName;
	public String operValue;
	public String label;
	
	public InstTypeJump(String instName_p, String operValue_p) {
		
		if (instExists(instName_p)){
			instName = instName_p;
			operValue = operValue_p;
			label = "";
		}
		else {
			throw new IllegalArgumentException("ERROR - INSTRUCTION " + instName_p + " DOESN'T EXIST !!");
		}
		
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
		
		return output + instName + " " + operValue;
	}
	
	public String getLabel(){
		return label;
	}
	
}
