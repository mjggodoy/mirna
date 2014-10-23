package mirna.beans;

public class Mature extends MiRna {
	
	private String name; //ok
	private String sequence; //ok
	private String length; //ok
	private String GC_proportion; //ok
	
	public Mature() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getGC_proportion() {
		return GC_proportion;
	}

	public void setGC_proportion(String gC_proportion) {
		GC_proportion = gC_proportion;
	}

	

}
