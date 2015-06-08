package mirna.beans;

import mirna.exception.ConflictException;

public class Protein extends ModelClass {
	
	private String Uniprot_id;
	private String Swiss_prot_id;
	private String Type;
	private Integer Transcript_id;
	
	
	public Protein() {
		
		super();
		
	}
	

	public Protein(int pk,String uniprot_id, String swiss_prot_id, String type, Integer transcript_id) {
		super(pk);
		Uniprot_id = uniprot_id;
		Swiss_prot_id = swiss_prot_id;
		Type = type;
		Transcript_id = transcript_id;
		
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
	

	public Integer getTranscript_id() {
		return Transcript_id;
	}


	public void setTranscript_id(Integer transcript_id) {
		Transcript_id = transcript_id;
	}


	public int checkConflict(Protein protein) {
		
		int res = 0;
		
		if (this.pk!=null) {
			if (protein.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(protein.getPk())) return -1;
		}
		
		if (this.Swiss_prot_id!=null) {
			if (protein.Swiss_prot_id ==null) res++; 
			else if (!this.Swiss_prot_id.equals(protein.Swiss_prot_id  )) return -1;
		}
		
		if (this.Type!=null) {
			if (protein.Type ==null) res++; 
			else if (!this.Type.equals(protein.Type)) return -1;
		}
		
		if (this.Uniprot_id!=null) {
			if (protein.Uniprot_id ==null) res++; 
			else if (!this.Uniprot_id.equals(protein.Uniprot_id)) return -1;
		}
		
		if (this.Transcript_id !=null) {
			if (protein.Transcript_id ==null) res++; 
			else if (!this.Transcript_id.equals(protein.Transcript_id)) return -1;
		}
		
		
		return res;
	}
	
	

	public void update(Protein protein) throws ConflictException {
		this.update(protein, true);
	}
	
	
	public void update(Protein protein, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(protein)==-1) throw new ConflictException(this, protein);
		}
		if (protein.getPk() !=null) this.pk = protein.getPk();
		if (protein.getSwiss_prot_id() !=null) this.Swiss_prot_id = protein.getSwiss_prot_id();
		if (protein.getType() !=null) this.Type = protein.getType();
		if (protein.getUniprot_id()!=null) this.Uniprot_id = protein.getUniprot_id();	
		if(protein.getTranscript_id() !=null) this.Transcript_id =protein.getTranscript_id();
	}


	@Override
	public String toString() {
		return "Protein [Uniprot_id=" + Uniprot_id + ", Swiss_prot_id="
				+ Swiss_prot_id + ", Type=" + Type + ", Transcript_id="
				+ Transcript_id + ", pk=" + pk + "]";
	}

}
