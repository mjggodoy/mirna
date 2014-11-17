package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna")
public class MiRna extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 20)
	protected String name;
	
	@Column(name = "accession_number", nullable = true, length = 45, unique = true)
	private String accessionNumber;
	
	@Column(name = "sequence", nullable = true, length = 45)
	protected String sequence;
	
	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	@Column(name = "organism_pk", nullable = true, length = 45)
	private Integer organismPk;
	
//	private String journal;//ok X
	
//	private String subName; //ok X
//	private String provenance; //ok X
//	private String length;//ok X
//	private String binding_site_pattern;//ok X
//	private String GC_proportion; //ok
//	private String chromosome; //ok
//	private String version; //ok 
//	private String newName; //ok
//	private String minimal_free_energy;//ok
//	private String normalized_minimal_free_energy;//ok
//	private String polarity;//ok
//	private String pubmedId ;//ok
//	private String start_strand;//ok
//	private String title_reference;//ok
//	private String year;
	
	public MiRna() {}
	
//	public MiRna(int pk, String name, String accessionNumber, String sequence, String resource) {
//		super(pk);
//		this.name = name;
//		this.accessionNumber = accessionNumber;
//		this.sequence = sequence;
//		this.resource = resource;
//	}
	
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
	
	public Integer getOrganismPk() {
		return organismPk;
	}

	public void setOrganismPk(Integer organismPk) {
		this.organismPk = organismPk;
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
		if (this.organismPk!=null) {
			if (mirna.getOrganismPk()==null) res++;
			else if (!this.organismPk.equals(mirna.getOrganismPk())) return -1;
		}
		return res;
	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", accessionNumber=" + accessionNumber
				+ ", sequence=" + sequence + ", resource=" + resource
				+ ", organismPk=" + organismPk + ", pk=" + pk + "]";
	}
	
}