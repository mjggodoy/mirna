package mirna.integration.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mature")

public class Mature extends ModelClass {
	
	@Column(name = "proportion", nullable = true, length = 80, unique = true)
	protected String proportion;
	
	@Column(name = "accession_number", nullable = true)
	private String accession_number;
	
	public Mature() { }

	
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}


	public String getAccession_number() {
		return accession_number;
	}


	public void setAccession_number(String accession_number) {
		this.accession_number = accession_number;
	}


	public int checkConflict(Mature mature) {
		int res = 0;
		if (this.pk!=null) {
			if (mature.getPk()==null) res++;
			else if (!this.pk.equals(mature.getPk())) return -1;
		}
		if (this.proportion!=null) {
			if (mature.getProportion()==null) res++;
			else if (!this.proportion.equals(mature.getProportion())) {
				return -1;
			}
		}
		
		if (this.accession_number!=null) {
			if (mature.getAccession_number()==null) res++;
			else if (!this.accession_number.equals(mature.getAccession_number())) {
				return -1;
			}
		}
		
		
		
		return res;
	}
	
	public void update(Mature mature) throws ConflictException {
		this.update(mature, true);
	}
	
	public void update(Mature mature, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(mature)==-1) throw new ConflictException(this, mature);
		}
		if (mature.getPk()!=null) this.pk = mature.getPk();
		if (mature.getProportion()!=null) this.proportion = mature.getProportion();
		if (mature.getAccession_number()!=null) this.accession_number = mature.getAccession_number();

	}


	@Override
	public String toString() {
		return "Mature [proportion=" + proportion + ", accession_number="
				+ accession_number + ", pk=" + pk + "]";
	}


	
		
}
