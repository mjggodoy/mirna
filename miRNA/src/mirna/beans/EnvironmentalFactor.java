package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "environmental_factor")
public class EnvironmentalFactor extends ModelClass  {

	@Column(name = "name", nullable = false, length = 300)
	private String name;



	public EnvironmentalFactor() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int checkConflict(EnvironmentalFactor ef) {
		int res = 0;

		if (this.pk!=null) {
			if (ef.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(ef.getPk())) { 
				return -1;
			};
		}

		if (this.name!=null) {
			if (ef.getName()==null) res++; 
			else if (!this.name.equals(ef.getName())){ 
				return -1;
			}
		}

		return res;
	}


	public void update(EnvironmentalFactor ef) throws ConflictException {
		this.update(ef, true);
	}


	public void update(EnvironmentalFactor ef, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(ef)==-1) throw new ConflictException(this, ef);
		}
		if (ef.getPk()!=null) this.pk = ef.getPk();
		if (ef.getName()!=null) this.name = ef.getName();

	}

	@Override
	public String toString() {
		return "EnvironmentalFactor [name=" + name + ", pk=" + pk + "]";
	}





}
