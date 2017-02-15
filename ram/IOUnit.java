package ram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUnit {
	
	private ArrayList<Integer> inputTape, outputTape;
	private String outputPath;
	
	public IOUnit(String inputPath_p, String outputPath_p){
		
		outputPath = outputPath_p;
		inputTape = new ArrayList<Integer>();
		outputTape = new ArrayList<Integer>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputPath_p))) {

			String sCurrentLine = br.readLine();
			String[] items = sCurrentLine.split("\\s");
			for(int i = 0; i < items.length; i++)
				inputTape.add(Integer.parseInt(items[i]));
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int readTape(){
		int currentNumber = inputTape.get(0);
		inputTape.remove(0);
		return currentNumber;
	}
	
	public void writeTape(int newNumber){
		outputTape.add(newNumber);
	}
	
	public void dumpToFile(){
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

			for(int i : outputTape)
				bw.write(i + " ");
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Instruction> parseInstructions(String filePath){
		
		List<Instruction> instArray = new ArrayList<Instruction>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String sCurrentLine;
			Instruction newInst = new InstTypeDef("HALT");
			
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.startsWith("#") || sCurrentLine.trim().isEmpty())
					continue;
				String[] fullInst = sCurrentLine.trim().replace("\t", "").split(":");
				String etiq = "", noTagInst;
				
				if(fullInst.length > 2){
					System.out.println("ERROR - Too many ':'");
					break;
				}
				else if (fullInst.length == 2){
					etiq = fullInst[0];
					noTagInst = fullInst[1];
				}
				else
					noTagInst = fullInst[0];
				
				String[] instTokens = noTagInst.replace("\t", " ").trim().split("\\s");
				
				if (instTokens.length == 2){
					if (instTokens[1].matches("\\d+")){
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Operand.INDIRADDRESS, Integer.parseInt(instTokens[1]));
					}
					else if (instTokens[1].startsWith("=")){
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Operand.CONSTANT, Integer.parseInt(instTokens[1].substring(1)));
					}
					else if (instTokens[1].startsWith("*")){
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Operand.DIRECTADDRESS, Integer.parseInt(instTokens[1].substring(1)));
					}
					else if (instTokens[1].matches("[0-9a-zA-Z]+")){
						newInst = new InstTypeJump(instTokens[0].toUpperCase(), instTokens[1]);
					}
				}
				else if (instTokens.length == 1){
					newInst = new InstTypeDef(instTokens[0].toUpperCase());
				}
				if (etiq != "")
					newInst.addLabel(etiq);
				
				System.out.println(newInst);
				
				instArray.add(newInst);
				
			}
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instArray;
		
	}

}
