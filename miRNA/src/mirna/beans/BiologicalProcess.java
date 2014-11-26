package mirna.beans;

public class BiologicalProcess extends ModelClass {
	
	private String name;
	
	public BiologicalProcess() { }

	public BiologicalProcess(int pk, String name) {
		super(pk);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BiologicalProcess [name=" + name + ", pk=" + pk
				+ ", getName()=" + getName() + ", getPk()=" + getPk()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
