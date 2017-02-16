package ram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import instruction.*;

public class IOUnit {
	
	private List<Integer> inputTape, outputTape;
	private String outputPath;
	private int lineNumber;
	
	public IOUnit(String inputPath_p, String outputPath_p){
		
		outputPath = outputPath_p;
		inputTape = new ArrayList<Integer>();
		outputTape = new ArrayList<Integer>();
		lineNumber = 1;
		
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
		lineNumber = 1;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String sCurrentLine;
			Instruction newInst = new InstTypeDef("HALT");
			
			while ((sCurrentLine = br.readLine()) != null) {
				
				lineNumber++;
				if(sCurrentLine.startsWith("#") || sCurrentLine.trim().isEmpty())
					continue;
				
				String[] trimComment = sCurrentLine.split("#");
				
				String[] fullInst = trimComment[0].trim().replace("\t", "").replaceAll("\\s{2,}", " ").split(":");
				
				String label = "", noTagInst = "";
				
				if(fullInst.length > 2)
					throw new SyntaxException(newInst + " : MORE THAN ONE ':' ON INSTRUCTION");
				
				else if (fullInst.length == 2){
					label = fullInst[0];
					noTagInst = fullInst[1];
				}
				else
					noTagInst = fullInst[0];
				
				String[] instTokens = noTagInst.replace("\t", " ").trim().split("\\s");
				
				if (instTokens.length == 2){
					
					if (instTokens[1].matches("[0-9]+"))
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Addressing.DIRECT, Integer.parseInt(instTokens[1]));
					else if (instTokens[1].startsWith("="))
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Addressing.CONSTANT, Integer.parseInt(instTokens[1].substring(1)));
					else if (instTokens[1].startsWith("*"))
						newInst = new InstTypeOP(instTokens[0].toUpperCase(), Addressing.INDIRECT, Integer.parseInt(instTokens[1].substring(1)));
					else if (instTokens[1].matches("^[a-zA-Z0-9]*$"))
						newInst = new InstTypeJump(instTokens[0].toUpperCase(), instTokens[1]);
					else 
						throw new SyntaxException(newInst + " : WRONG ARGUMENT SYNTAX [=NUMBER, *NUMBER, NUMBER, [0-9a-zA-Z]+]");

				}
				
				else if (instTokens.length == 1)
					newInst = new InstTypeDef(instTokens[0].toUpperCase());
				
				else
					throw new SyntaxException(newInst + " : WRONG NUMBER OF ARGUMENTS");
				
				if (label != "")
					newInst.addLabel(label);
				
				//System.out.println(newInst);
				
				instArray.add(newInst);
				
			}
							
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e){
			syntaxErrorHandler(e);
		}
		catch (SyntaxException e){
			syntaxErrorHandler(e);
		}
		
		return instArray;
		
	}
	
	public void syntaxErrorHandler(Exception e){
		System.out.println("SYNTAX ERROR - LINE " + (lineNumber-1) + " - " + e.getMessage() + " !!");
		System.exit(0);
	}
	
	public String inputTapeToString(){
		return inputTape.toString();
	}
	
	public String outputTapeToString(){
		return outputTape.toString();
	}

}
