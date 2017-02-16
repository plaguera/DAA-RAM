import ram.ControlUnit;

/**
 * Programa que ejecuta una serie de instrucciones '.ram', y genera datos en cinta de salida a partir de la cinta de entrada.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * MainRam.java
 */
public class MainRam {

	public static void main(String[] args) {
		
		if(args.length == 4){
			ControlUnit ramMachine = new ControlUnit (args[0], args[1], args[2], args[3]);
			ramMachine.exec();
		}
		if(args.length == 3){
			ControlUnit ramMachine = new ControlUnit (args[0], args[1], args[2], "");
			ramMachine.exec();
		}
		else{
			System.out.println("java MainRam file.ram file.in file.out [debug]");
		}
		
	}

}
