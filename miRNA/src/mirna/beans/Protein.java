package mirna.beans;

public class Protein extends ModelClass {
	
	private String Uniprot_id;
	
	
	public Protein() {
		
		super();
		
	}
	

	public Protein(int pk,String uniprot_id) {
		super(pk);
		Uniprot_id = uniprot_id;
	}

	public String getUniprot_id() {
		return Uniprot_id;
	}

	public void setUniprot_id(String uniprot_id) {
		Uniprot_id = uniprot_id;
	}


	@Override
	public String toString() {
		return "Protein [Uniprot_id=" + Uniprot_id + ", pk=" + pk
				+ ", getUniprot_id()=" + getUniprot_id() + ", getPk()="
				+ getPk() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}	

}
