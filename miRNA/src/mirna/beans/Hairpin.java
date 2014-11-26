package mirna.beans;

public class Hairpin extends MiRna {
	
	//private String newSequence;
	//private String label;
	//private String start_strand;
	//private String sequence; // HEREDADO
	//private String name; // HEREDADO
	
	public Hairpin() { }

	public Hairpin(int pk, String name, String sequence) {
		this.pk = pk;
		this.name = name;
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Hairpin [name=" + name + ", sequence=" + sequence + ", pk="
				+ pk + ", getName()=" + getName() + ", getAccessionNumber()="
				+ getAccessionNumber() + ", getSequence()=" + getSequence()
				+ ", getResource()=" + getResource() + ", getOrganismPk()="
				+ getOrganismPk() + ", toString()=" + super.toString()
				+ ", getPk()=" + getPk() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
	

}
