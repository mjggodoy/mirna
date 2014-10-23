package mirna.beans;

public class Hairpin extends MiRna {
	
	//private String newSequence;
	//private String label;
	//private String start_strand;
	private String sequence;
	private String name;
	
	
	public Hairpin() {
		super();
	}

	public Hairpin(int pk, String sequence, String name) {
		super(pk);
		this.sequence = sequence;
		this.name = name;
	}

	
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}



	//public String getStart_strand() {
		//return start_strand;
	//}



	//public void setStart_strand(String start_strand) {
		//this.start_strand = start_strand;
	//}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	//public String getLabel() {
		//return label;
	//}

	//public void setLabel(String label) {
		//this.label = label;
	//}

	
	//public String getNewSequence() {
		//return newSequence;
	//}

	//public void setNewSequence(String newSequence) {
		//this.newSequence = newSequence;
	//}
	
	
	

}
