package mirna.beans;

public class Protein extends ModelClass {
	
	private String Uniprot_id;
	private String Swiss_prot_id;
	private String Type;
	
	
	public Protein() {
		
		super();
		
	}
	

	public Protein(int pk,String uniprot_id, String swiss_prot_id, String type) {
		super(pk);
		Uniprot_id = uniprot_id;
		Swiss_prot_id = swiss_prot_id;
		Type = type;
		
	}

	public String getUniprot_id() {
		return Uniprot_id;
	}

	public void setUniprot_id(String uniprot_id) {
		Uniprot_id = uniprot_id;
	}


	public String getSwiss_prot_id() {
		return Swiss_prot_id;
	}


	public void setSwiss_prot_id(String swiss_prot_id) {
		Swiss_prot_id = swiss_prot_id;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		this.Type = type;
	}


	@Override
	public String toString() {
		return "Protein [Uniprot_id=" + Uniprot_id + ", Swiss_prot_id="
				+ Swiss_prot_id + ", Type=" + Type + ", pk=" + pk + "]";
	}








}
