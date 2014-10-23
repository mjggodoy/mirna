package mirna.beans;

public class EnvironmentalFactor extends ModelClass  {
	
	private String name;

	public EnvironmentalFactor() {
		
		super();
	}

	
	
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
	
	
	
	
}
