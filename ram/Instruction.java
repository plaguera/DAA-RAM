package ram;

abstract class Instruction {
	
	public abstract void addLabel(String newLabel);
	public abstract String getLabel();
	public abstract String toString();
	
}
