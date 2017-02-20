package ram;

import java.util.List;
import instruction.InstDefault;
import instruction.InstJump;
import instruction.InstOperation;
import instruction.Instruction;

/**
 * Clase que contiene todos los componentes de la máquina RAM y se encarga de la ejecución del programa.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * ControlUnit.java
 */
public class ControlUnit {
	
	/**
	 * Atributo que en caso de activarse se muestra información extra en tiempo de ejecución.
	 */
	public final boolean DEBUG;
	
	/**
	 * La cola de ejecución es una lista de instrucciones.
	 */
	private List<Instruction> progMem;
	
	/**
	 * Memoria de datos o banco de registros.
	 */
	private DataMem dataMem;
	
	/**
	 * Instruction Pointer apunta a la instrucción que se está ejecutando actualmente. 'instructionsExec' es el
	 * número total de instrucciones ejecutadas hasta el momento.
	 */
	private int IP, instructionsExec;
	
	/**
	 * Unidad de entrada y salida y análisis sintáctico.
	 */
	private IOUnit ioUnit;

	public ControlUnit(String filePath, String inputTape, String outputTape, String debug){
		
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
			
			// Instrucción de tipo Default.
			if(newInst instanceof InstDefault){
				
				InstDefault currentInst = (InstDefault) progMem.get(IP);
				switch (currentInst.getName()){
					case "HALT": halt = true; break;
					default: break;
				}
				
			}
			
			// Instrucción de tipo Jump.
			else if(newInst instanceof InstJump){
				
				InstJump currentInst = (InstJump) progMem.get(IP);
				int newIP = ioUnit.getIndexLabel(currentInst.getArgument());
						
				switch (currentInst.getName()){
					case "JUMP": IP = newIP; break;
					case "JZERO": if(dataMem.get(0) == 0) IP = newIP; else IP++; break;
					case "JGTZ": if(dataMem.get(0) > 0) IP = newIP; else IP++; break;
					default: break;
				}
				
			}
			
			// Instrucción de tipo Operation.
			else if(newInst instanceof InstOperation){
				
				InstOperation currentInst = (InstOperation) progMem.get(IP);
				int finalValue = 0;
				
				if(currentInst.getName().equals("STORE") || currentInst.getName().equals("READ")){
					switch (currentInst.getAddressing()){
						case CONSTANT: 	break;
						case DIRECT: 	finalValue = currentInst.getArgument(); break;
						case INDIRECT:	finalValue = dataMem.getDirect(currentInst.getArgument()); break;
					}
		
					switch (currentInst.getName()){
						case "STORE": 	dataMem.set(finalValue, dataMem.get(0)); break;
						case "READ": 	dataMem.set(finalValue, ioUnit.readTape()); break;
						default: break;
					}
				}
				else{
					switch (currentInst.getAddressing()){
						case CONSTANT: 	finalValue = currentInst.getArgument(); break;
						case DIRECT: 	finalValue = dataMem.getDirect(currentInst.getArgument()); break;
						case INDIRECT:	finalValue = dataMem.getIndirect(currentInst.getArgument()); break;
					}
	
					switch (currentInst.getName()){
						case "LOAD": 	dataMem.set(0, finalValue); break;
						case "ADD": 	dataMem.set(0, dataMem.get(0) + finalValue); break;
						case "SUB":		dataMem.set(0, dataMem.get(0) - finalValue); break;
						case "MUL":		dataMem.set(0, dataMem.get(0) * finalValue); break;
						case "DIV":		dataMem.set(0, dataMem.get(0) / finalValue); break;
						case "WRITE": 	ioUnit.writeTape(finalValue); break;
						default: break;
					}
				}
				IP++;	
				
			}
			instructionsExec++;
			if(DEBUG){
				System.out.println("IP = " + IP);
				System.out.println("INST -> " + newInst);
				System.out.println("DataMem : " + dataMem);
				System.out.println("Input Tape : " + ioUnit.inputTapeToString());
				System.out.println("Output Tape : " + ioUnit.outputTapeToString());
				System.out.println("Executed Instruction Number : " + instructionsExec + "\n");
			}		
		}
		System.out.println("Execution Successful");
		ioUnit.dumpToFile();
	}
	
}
