package mirna.beans;

public class Mature extends MiRna {
	
	//private String name; //HEREDADO
	//private String sequence; //HEREDADO
	private String length; //ok
	private String gcProportion; //ok
	
	public Mature() { }
	
	public Mature(int pk, String name, String sequence, String length,
			String gcProportion) {
		this.pk = pk;
		this.name = name;
		this.sequence = sequence;
		this.length = length;
		this.gcProportion = gcProportion;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getGcProportion() {
		return gcProportion;
	}

	public void setGcProportion(String gcProportion) {
		this.gcProportion = gcProportion;
	}

	@Override
	public String toString() {
		return "Mature [length=" + length + ", gcProportion=" + gcProportion
				+ ", name=" + name + ", sequence=" + sequence + ", pk=" + pk
				+ "]";
	}

	
	
	
	
	
}
