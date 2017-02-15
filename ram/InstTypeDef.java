package ram;

public class InstTypeDef extends Instruction {
	
	public String instName;
	public String label;
	
	public InstTypeDef(String instName_p) {
		
		if (instExists(instName_p)){
			instName = instName_p;
			label = "";
		}
		else {
			System.out.println("ERROR - INSTRUCTION" + instName_p + " DOESN'T EXIST !!");
			//System.exit(0);
		}
		
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
		
		return output + instName;
	}
	
	public String getLabel(){
		return label;
	}

}
