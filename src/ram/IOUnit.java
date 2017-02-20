package ram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instruction.*;

/**
 * Clase que define las cintas de entrada y salida de la máquina, además del análisis sintáctico del programa.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * IOUnit.java
 */
public class IOUnit {
	
	/**
	 * Mapa que relaciona las etiquetas del programa y su índice dentro de la cola de ejecución.
	 */
	private Map<String, Integer> labelMap;
	
	/**
	 * Cintas de entrada y salida.
	 */
	private List<Integer> inputTape, outputTape;
	
	/**
	 * Ruta del archivo donde se escribirá el contenido de la cinta de salida.
	 */
	private String outputPath;
	
	/**
	 * Número de línea actual dentro del parseo del programa.
	 */
	private int lineNumber;
	
	public IOUnit(String inputPath_p, String outputPath_p){
		
		outputPath = outputPath_p;
		inputTape = new ArrayList<Integer>();
		outputTape = new ArrayList<Integer>();
		labelMap = new HashMap<String, Integer>();
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
	
	/**
	 * Lee el primer elemento de la cinta y lo borra.
	 * @return
	 */
	public int readTape(){
		int currentNumber = inputTape.get(0);
		inputTape.remove(0);
		return currentNumber;
	}
	
	/**
	 * Añade un nuevo elemento al final de la cinta.
	 * @param newNumber
	 */
	public void writeTape(int newNumber){
		outputTape.add(newNumber);
	}
	
	/**
	 * Escribe el contenido de la cinta en el archivo '.out' cuya ruta se introdujo al inicializar la clase.
	 */
	public void dumpToFile(){
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

			for(int i : outputTape)
				bw.write(i + " ");
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Parsea línea a línea todas las instrucciones del archivo fuente y comprueba si las etiquetas de los saltos son correctas.
	 * @param filePath	Ruta del archivo '.ram' que contiene la secuencia de instrucciones.
	 * @return
	 */
	public List<Instruction> parseInstructions(String filePath){
		
		List<Instruction> instArray = new ArrayList<Instruction>();
		lineNumber = 1;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String sCurrentLine;
			Instruction newInst = new InstDefault("HALT");
			
			while ((sCurrentLine = br.readLine()) != null) {
				
				lineNumber++;
				// Si la línea está comentada o vacía, la saltamos.
				if(sCurrentLine.startsWith("#") || sCurrentLine.trim().isEmpty())
					continue;
				
				// Dividimos la línea con '#' como separador para eliminar el comentario.
				String[] trimComment = sCurrentLine.split("#");
				
				// El primer token contiene la instrucción, quitamos los espacios sobrantes, reemplazamos tabuladores
				// y cadenas de espacios por un solo espacio y lo divimos usando ':' como separador.
				String[] fullInst = trimComment[0].trim().replace("\t", "").replaceAll("\\s{2,}", " ").split(":");
				
				String label = "", noTagInst = "";
				
				// Búsqueda de etiqueta
				// Si hay más de 2 tokens hay un error de sintaxis, solo puede haber un ':'.
				if(fullInst.length > 2)
					throw new SyntaxException(newInst + " : MORE THAN ONE ':' ON INSTRUCTION");
				
				// Si hay dos tokens, la instrucción tiene una etiqueta.
				else if (fullInst.length == 2){
					label = fullInst[0];
					noTagInst = fullInst[1];
				}
				
				// Si hay un token, la instrucción no tiene una etiqueta.
				else
					noTagInst = fullInst[0];
				
				// Ahora queda la instrucción sola, sin más texto, reemplazamos tabuladores y dividimos con el espacio como separador.
				String[] instTokens = noTagInst.replace("\t", " ").trim().split("\\s");
				
				// Si hay dos tokens, la instrucción tiene un argumento.
				if (instTokens.length == 2){
					
					if (instTokens[1].matches("[0-9]+"))
						newInst = new InstOperation(instTokens[0].toUpperCase(), Addressing.DIRECT, Integer.parseInt(instTokens[1]));
					else if (instTokens[1].startsWith("="))
						newInst = new InstOperation(instTokens[0].toUpperCase(), Addressing.CONSTANT, Integer.parseInt(instTokens[1].substring(1)));
					else if (instTokens[1].startsWith("*"))
						newInst = new InstOperation(instTokens[0].toUpperCase(), Addressing.INDIRECT, Integer.parseInt(instTokens[1].substring(1)));
					else if (instTokens[1].matches("^[a-zA-Z0-9]*$"))
						newInst = new InstJump(instTokens[0].toUpperCase(), instTokens[1]);
					else 
						throw new SyntaxException(newInst + " : WRONG ARGUMENT SYNTAX [=NUMBER, *NUMBER, NUMBER, [0-9a-zA-Z]+]");

				}
				
				// Si hay un token, la instrucción no tiene argumentos.
				else if (instTokens.length == 1)
					newInst = new InstDefault(instTokens[0].toUpperCase());
				
				else
					throw new SyntaxException(newInst + " : WRONG NUMBER OF ARGUMENTS");
				
				// Si se encontró una etiqueta se añade a la instrucción.
				if (label != "")
					newInst.addLabel(label);
								
				instArray.add(newInst);
				
			}
			
			// Añadimos las etiquetas y sus índices al mapa.
			for(int i = 0; i < instArray.size(); i++)
				 if(!instArray.get(i).getLabel().equals(""))
					 labelMap.put(instArray.get(i).getLabel(), i);
			
			// Recorremos todas las instrucciones de salto y comprobamos que la etiqueta a la que salta esté en el mapa.
			InstJump jumpInst = new InstJump("JUMP", "");
			for(int i = 0; i < instArray.size(); i++)
				 if(instArray.get(i) instanceof InstJump){
					 jumpInst = (InstJump) instArray.get(i);
					 if(!labelMap.containsKey(jumpInst.getArgument()))
						 throw new SyntaxException(newInst + " : " + jumpInst.getArgument() + " LABEL DOES NOT EXIST");
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
	
	/**
	 * Maneja las excepciones que puedan ocurrir durante el parseo del programa y la inicialización de las cintas.
	 * @param e
	 */
	public void syntaxErrorHandler(Exception e){
		System.out.println("SYNTAX ERROR - LINE " + (lineNumber-1) + " - " + e.getMessage() + " !!");
		System.exit(0);
	}
	
	/**
	 * Devuelve el contenido de la cinta de entrada en el momento que se llama a la función.
	 * @return
	 */
	public String inputTapeToString(){
		return inputTape.toString();
	}
	
	/**
	 * Devuelve el contenido de la cinta de salida en el momento que se llama a la función.
	 * @return
	 */
	public String outputTapeToString(){
		return outputTape.toString();
	}
	
	/**
	 * Busca en el mapa de etiquetas según el nombre de esta y devuelve su índice en la cola de ejecución.
	 * @param label	Etiqueta a la que se quiere saltar.
	 * @return		Índice de la instrucción con dicha etiqueta.
	 */
	public int getIndexLabel(String label){
		return labelMap.get(label);
	}

}
