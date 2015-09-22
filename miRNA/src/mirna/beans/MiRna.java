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
	
	@Column(name = "accession_number", nullable = true, length = 100)
	private String accessionNumber;
	
	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	@Column(name = "organism_pk", nullable = true)
	private Integer organismPk;
	
	
	/**
	 * XUXA
	 */
//	private Integer hairpinPk; // He inclu�do estos dos atributos dado
//	private Integer maturePk;
	
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

//	public Integer getHairpinPk() {
//		return hairpinPk;
//	}
//
//	public void setHairpinPk(Integer hairpinPk) {
//		this.hairpinPk = hairpinPk;
//	}
//	
//	public Integer getMaturePk() {
//		return hairpinPk;
//	}
//
//	public void setMaturePk(Integer hairpinPk) {
//		this.hairpinPk = hairpinPk;
//	}

	public int checkConflict(MiRna mirna) {
		int res = 0;
		if (this.pk!=null) {
			if (mirna.getPk()==null) res++;
			else if (!this.pk.equals(mirna.getPk())) return -1;
		}
		if (this.name!=null) {
			if (mirna.getName()==null) res++;
			else if (!this.name.equals(mirna.getName())) {
				return -1;
			}
		}
		if (this.accessionNumber!=null) {
			if (mirna.getAccessionNumber()==null) res++;
			else if (!this.accessionNumber.equals(mirna.getAccessionNumber())) return -1;
		}
		if (this.resource!=null) {
			if (mirna.getResource()==null) res++;
			else if (!this.resource.equals(mirna.getResource())) return -1;
		}
		if (this.organismPk!=null) {
			if (mirna.getOrganismPk()==null) res++;
			else if (!this.organismPk.equals(mirna.getOrganismPk())) return -1;
		}
		
//		if (this.hairpinPk!=null) {
//			if (mirna.getHairpinPk()==null) res++;
//			else if (!this.hairpinPk.equals(mirna.getHairpinPk())) return -1;
//		}
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
		if (mirna.getResource()!=null) this.resource = mirna.getResource();
		if (mirna.getOrganismPk()!=null) this.organismPk = mirna.getOrganismPk();
//		if (mirna.getHairpinPk()!=null) this.hairpinPk = mirna.getHairpinPk();
//		if (mirna.getMaturePk()!=null) this.maturePk = mirna.getMaturePk();
		
	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", accessionNumber=" + accessionNumber
				+ ", resource=" + resource
				+ ", organismPk=" + organismPk
//				+ ", hairpinPk=" + hairpinPk
//				+ ", maturePk=" + maturePk
				+ ", pk=" + pk + "]";
	}

}