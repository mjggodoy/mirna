package beans;

public class Hairpin extends MiRna {
	
	private String newSequence;
	private String label;
	private String start_strand;
	
	
	public Hairpin(){}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNewSequence() {
		return newSequence;
	}

	public void setNewSequence(String newSequence) {
		this.newSequence = newSequence;
	}
	
	
	

}
