package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna")
public class MiRna extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;
	
	@Column(name = "accession_number", nullable = true, length = 45)
	private String accessionNumber;
	
	@Column(name = "sequence", nullable = true, length = 45)
	protected String sequence;
	
	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	//TODO: Poner como bean aparte
	//@Column(name = "pubmed_id", nullable = true, length = 10)
	//private String pubmedId; //lo he puesto nuevo
	
	@Column(name = "length", nullable = true, length = 10)
	private String length; //lo he puesto nuevo
	
	@Column(name = "gc_proportion", nullable = true, length = 10)
	private String GC_proportion;//lo he puesto nuevo
	
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

	public Integer getOrganismPk() {
		return organismPk;
	}

	public void setOrganismPk(Integer organismPk) {
		this.organismPk = organismPk;
	}

	public int checkConflict(MiRna mirna) {
		int res = 0;
		if (this.pk!=null) {
			if (mirna.getPk()==null) res++;
			else if (!this.pk.equals(mirna.getPk())) return -1;
		}
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
		if (this.length!=null) {
			if (mirna.getLength()==null) res++;
			else if (!this.length.equals(mirna.getLength())) return -1;
		}
		if (this.GC_proportion!=null) {
			if (mirna.getGC_proportion()==null) res++;
			else if (!this.GC_proportion.equals(mirna.getGC_proportion())) return -1;
		}
		if (this.organismPk!=null) {
			if (mirna.getOrganismPk()==null) res++;
			else if (!this.organismPk.equals(mirna.getOrganismPk())) return -1;
		}
		return res;
	}
	
	public void update(MiRna mirna) throws ConflictException {
		this.update(mirna, true);
	}
	
	public void update(MiRna mirna, boolean checkConflict) throws ConflictException {
		
		if (checkConflict) {
			if (this.checkConflict(mirna)==-1) throw new ConflictException(this, mirna);
		}
		
		if (mirna.getPk()!=null) this.pk = mirna.getPk();
		if (mirna.getName()!=null) this.name = mirna.getName();
		if (mirna.getAccessionNumber()!=null) this.accessionNumber = mirna.getAccessionNumber();
		if (mirna.getSequence()!=null) this.sequence = mirna.getSequence();
		if (mirna.getResource()!=null) this.resource = mirna.getResource();
		if (mirna.getLength()!=null) this.length = mirna.getLength();
		if (mirna.getGC_proportion()!=null) this.GC_proportion = mirna.getGC_proportion();
		if (mirna.getOrganismPk()!=null) this.organismPk = mirna.getOrganismPk();
		
	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", accessionNumber=" + accessionNumber
				+ ", sequence=" + sequence + ", resource=" + resource
				+ ", organismPk=" + organismPk
				+ ", length=" + length + ", GC_proportion=" + GC_proportion
				+ ", pk=" + pk + "]";
	}

}