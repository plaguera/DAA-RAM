package ram;

import java.util.List;

import instruction.InstTypeDef;
import instruction.InstTypeJump;
import instruction.InstTypeOP;
import instruction.Instruction;

public class Ram {
	
	public final boolean DEBUG;
	
	private List<Instruction> progMem;
	private DataMem dataMem;
	private int IP, instructionsExec;
	private IOUnit ioUnit;

	public Ram(String filePath, String inputTape, String outputTape, String debug){
		
		IP = 0;
		ioUnit = new IOUnit(inputTape, outputTape);
		progMem = ioUnit.parseInstructions(filePath);
		dataMem = new DataMem();
		instructionsExec = 1;
		
		if(debug.toLowerCase().equals("debug"))
			DEBUG = true;
		else
			DEBUG = false;
		
	}
	
	public void exec(){
		IP = 0;
		instructionsExec = 1;
		boolean halt = false;
		
		if(DEBUG)
			System.out.println("Input Tape Initially : " + ioUnit.inputTapeToString() + "\n");
		
		while(IP < progMem.size() && !halt){
			
			Instruction newInst = progMem.get(IP);
						
			if(newInst instanceof InstTypeDef){
				InstTypeDef currentInst = (InstTypeDef) progMem.get(IP);
				switch (currentInst.instName){
					case "HALT": halt = true; break;
					default: break;
				}
			}
			else if(newInst instanceof InstTypeJump){
				InstTypeJump currentInst = (InstTypeJump) progMem.get(IP);
				int newIP = -1;
				for(int i = 0; i < progMem.size(); i++)
					if(progMem.get(i).getLabel().equals(currentInst.operValue))
						newIP = i;
				
				if (newIP == -1){
					int line = IP+1;
					System.out.println("ERROR LINE " + line + ": NO SUCH LABEL !!");
					return;
				}
						
				switch (currentInst.instName){
					case "JUMP": IP = newIP; break;
					case "JZERO": if(dataMem.get(0) == 0) IP = newIP; else IP++; break;
					case "JGTZ": if(dataMem.get(0) > 0) IP = newIP; else IP++; break;
					default: break;
				}
			}
			else if(newInst instanceof InstTypeOP){
				InstTypeOP currentInst = (InstTypeOP) progMem.get(IP);
				int finalValue = 0;
				
				if(currentInst.instName.equals("STORE") || currentInst.instName.equals("READ")){
					switch (currentInst.operType){
						case DIRECT: 	finalValue = currentInst.operValue; break;
						case INDIRECT:	finalValue = dataMem.getDirect(currentInst.operValue); break;
					}
		
					switch (currentInst.instName){
						case "STORE": 	dataMem.set(finalValue, dataMem.get(0)); break;
						case "READ": 	dataMem.set(finalValue, ioUnit.readTape()); break;
						default: break;
					}
				}
				else{
					switch (currentInst.operType){
						case CONSTANT: 	finalValue = currentInst.operValue; break;
						case DIRECT: 	finalValue = dataMem.getDirect(currentInst.operValue); break;
						case INDIRECT:	finalValue = dataMem.getIndirect(currentInst.operValue); break;
					}
	
					switch (currentInst.instName){
						case "LOAD": 	dataMem.set(0, finalValue); break;
						case "ADD": 	dataMem.set(0, dataMem.get(0) + finalValue); break;
						case "SUB":		dataMem.set(0, dataMem.get(0) - finalValue); break;
						case "MUL":		dataMem.set(0, dataMem.get(0) * finalValue); break;
						case "DIV":		dataMem.set(0, dataMem.get(0) / finalValue); break;
						case "WRITE": 	ioUnit.writeTape(finalValue); break;
						default: break;
					}
				}
				if(DEBUG){
					System.out.println("IP = " + IP);
					System.out.println("INST : " + newInst);
					System.out.println("DataMem : " + dataMem);
					System.out.println("Input Tape : " + ioUnit.inputTapeToString());
					System.out.println("Output Tape : " + ioUnit.outputTapeToString());
					System.out.println("Executed Instruction Number : " + instructionsExec + "\n");
				}		
				IP++;
				instructionsExec++;
			}
		}
		ioUnit.dumpToFile();
	}
	
}
