package ram;

import java.util.List;

public class Ram {
	
	private List<Instruction> progMem;
	private DataMem dataMem;
	private int IP;
	private IOUnit ioUnit;

	public Ram(String filePath, String inputTape, String outputTape){
		IP = 0;
		ioUnit = new IOUnit(inputTape, outputTape);
		progMem = ioUnit.parseInstructions(filePath);
		dataMem = new DataMem();
		exec();
	}
	
	private void exec(){
		boolean halt = false;
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
				boolean outOfBounds = false;
				switch (currentInst.operType){
					case CONSTANT: 		finalValue = currentInst.operValue; break;
					case DIRECTADDRESS: finalValue = dataMem.getDirect(currentInst.operValue); break;
					case INDIRADDRESS:	finalValue = dataMem.getIndirect(currentInst.operValue); break;
				}
				
				switch (currentInst.instName){
					case "LOAD": 	dataMem.set(0, finalValue);
					case "STORE": 	dataMem.set(finalValue, dataMem.get(0)); break;
					case "ADD": 	dataMem.set(0, dataMem.get(0) + finalValue); break;
					case "SUB":		dataMem.set(0, dataMem.get(0) - finalValue); break;
					case "MUL":		dataMem.set(0, dataMem.get(0) * finalValue); break;
					case "DIV":		dataMem.set(0, dataMem.get(0) / finalValue); break;
					case "READ": 	dataMem.set(finalValue, ioUnit.readTape()); break;
					case "WRITE": 	ioUnit.writeTape(finalValue); break;
					default: break;
				}
				IP++;
			}
		}
		for(int i = 0; i < dataMem.size(); i++)
			System.out.println("R" + i + " : " + dataMem.get(i));
		ioUnit.dumpToFile();
	}
	
}
