package ram;

public class InstTypeOP extends Instruction {

	public String instName;
	public Operand operType;
	public int operValue;
	public String label;
	
	public InstTypeOP(String instName_p, Operand operType_p, int operValue_p) {
		
		if (instExists(instName_p) && operCompatible(instName_p, operType_p, operValue_p)){
			instName = instName_p;
			operType = operType_p;
			operValue = operValue_p;
			label = "";
		}
		else {
			System.out.println("ERROR - INSTRUCTION " + instName_p + " DOESN'T EXIST WITH " + operType_p + " !!");
			//System.exit(0);
		}
		
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
	
	private boolean operCompatible(String newInst, Operand newOperType, int newOperValue){
		
		if(newInst.equals("STORE") && newOperType == Operand.CONSTANT)
			return false;
		if((newInst.equals("READ") || newInst.equals("WRITE")) && newOperType == Operand.INDIRADDRESS && newOperValue == 0) 
			return false;
		if((newInst.equals("READ") || newInst.equals("WRITE")) && newOperType == Operand.CONSTANT)
			return false;
		
		return true;
	}
	
	public String toString(){
		String output = "";
		if (label != "")
			output = label + ": ";
		output += instName;
		if (operType == Operand.CONSTANT)
			output += " =" + operValue;
		else if (operType == Operand.INDIRADDRESS)
			output += " " + operValue;
		else if (operType == Operand.DIRECTADDRESS)
			output += " *" + operValue;
		
		return output;
	}
	
	public String getLabel(){
		return label;
	}
	
}
