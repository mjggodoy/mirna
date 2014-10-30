package mirna.beans;

public class MiRna extends ModelClass {
	
	private String name; //ok
//	private String journal;//ok X
	private String accessionNumber; //ok
//	private String subName; //ok X
//	private String provenance; //ok X
//	private String length;//ok X
//	private String binding_site_pattern;//ok X
//	private String GC_proportion; //ok
//	private String chromosome; //ok
//	private String version; //ok 
	private String sequence; //ok
//	private String newName; //ok
//	private String minimal_free_energy;//ok
//	private String normalized_minimal_free_energy;//ok
//	private String polarity;//ok
//	private String pubmedId ;//ok
	private String resource;//ok
//	private String start_strand;//ok
//	private String title_reference;//ok
//	private String year;
	
	public MiRna() {}
	
	public MiRna(int pk, String name, String accessionNumber, String sequence, String resource) {
		super(pk);
		this.name = name;
		this.accessionNumber = accessionNumber;
		this.sequence = sequence;
		this.resource = resource;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int checkConflict(MiRna mirna) {
		int res = 0;
		if (this.name!=null) {
			if (mirna.getName()==null) res++;
			else if (!this.name.equals(mirna.getName())) return -1;
		}
		if (this.accessionNumber!=null) {
			if (mirna.getAccessionNumber()==null) res++;
			else if (!this.accessionNumber.equals(mirna.getAccessionNumber())) return -1;
		}
		if (this.sequence!=null) {
			if (mirna.getSequence()==null) res++;
			else if (!this.sequence.equals(mirna.getSequence())) return -1;
		}
		if (this.resource!=null) {
			if (mirna.getResource()==null) res++;
			else if (!this.resource.equals(mirna.getResource())) return -1;
		}
		return res;
	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", accessionNumber=" + accessionNumber
				+ ", sequence=" + sequence + ", resource=" + resource + "]";
	}
}
