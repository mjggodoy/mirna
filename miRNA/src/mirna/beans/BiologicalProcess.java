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

}
