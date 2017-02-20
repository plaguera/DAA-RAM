package ram;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase que define la memoria de datos de la máquina.
 * @author Pedro Miguel Lagüera Cabrera
 * Feb 16, 2017
 * DataMem.java
 */
public class DataMem {
	
	private List<Integer> registry;
	
	public DataMem(){
		registry = new ArrayList<Integer>();
		registry.add(0);
	}
	
	/**
	 * Cambia el valor del registro 'index'.
	 * @param index		Posición del elemento a cambiar.
	 * @param element	Nuevo valor
	 */
	public void set(int index, int element){
		resize(index);
		registry.set(index, element);	
	}
	
	/**
	 * Devuelve el valor del registro 'index'.
	 * @param index
	 * @return
	 */
	public int get(int index){
		return getDirect(index);
	}
	
	/**
	 * Devuelve el valor deseado según el direccionamiento directo a partir de un valor. R[index]
	 * @param index
	 * @return
	 */
	public int getDirect(int index){
		resize(index);
		return registry.get(index);
	}
	
	/**
	 * Devuelve el valor deseado según el direccionamiento indirecto a partir de un valor. R[R[index]]
	 * @param index
	 * @return
	 */
	public int getIndirect(int index){
		resize(index);
		resize(registry.get(index));
		return registry.get(registry.get(index));
	}
	
	/**
	 * Aumenta el tamaño del banco de registros hasta que coincida con el parámetro. 
	 * @param index
	 */
	private void resize(int index){
		while(index >= registry.size())
			registry.add(0);
	}
	
	/**
	 * Devuelve el tamaño del banco de registros.
	 * @return
	 */
	public int size(){
		return registry.size();
	}
	
	/**
	 * Permite imprimir objetos de esta clase de manera formateada por pantalla.
	 * @return
	 */
	public String toString(){
		return registry.toString();
	}

}
