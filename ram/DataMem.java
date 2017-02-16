package ram;

import java.util.List;
import java.util.ArrayList;

public class DataMem {
	
	private List<Integer> registry;
	
	public DataMem(){
		registry = new ArrayList<Integer>();
		registry.add(0);
	}
	
	public void set(int index, int element){
		resize(index);
		registry.set(index, element);	
	}
	
	public int get(int index){
		resize(index);
		return registry.get(index);
	}
	
	public int getDirect(int index){
		resize(index);
		return registry.get(index);
	}
	
	public int getIndirect(int index){
		resize(index);
		resize(registry.get(index));
		return registry.get(registry.get(index));
	}
	
	private void resize(int index){
		while(index >= registry.size())
			registry.add(0);
	}
	
	public int size(){
		return registry.size();
	}
	
	public String toString(){
		return registry.toString();
	}

}
