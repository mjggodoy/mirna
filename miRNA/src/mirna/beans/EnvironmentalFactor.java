package mirna.beans;

public class EnvironmentalFactor extends ModelClass  {
	
	protected String name;

	public EnvironmentalFactor() { }
	
	public EnvironmentalFactor(int pk, String name) {
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
		return "EnvironmentalFactor [name=" + name + ", pk=" + pk + "]";
	}

	
	
}
