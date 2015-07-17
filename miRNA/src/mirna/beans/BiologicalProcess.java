package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "biological_process")
public class BiologicalProcess extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 45, unique = true)
	private String name;
	
	public BiologicalProcess() { }



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int checkConflict(BiologicalProcess biologicalprocess) {
		int res = 0;

		if (this.pk != null) {
			if (biologicalprocess.getPk() == null)
				res++; // res = 1
			else if (!this.pk.equals(biologicalprocess.getPk()))
				return -1;
		}

		if (this.name != null) {
			if (biologicalprocess.getName() == null)
				res++;
			else if (!this.name.equals(biologicalprocess.getName()))
				return -1;
		}

		return res;
	}

	public void update(BiologicalProcess biologicalprocess) throws ConflictException {
		this.update(biologicalprocess, true);
	}

	public void update(BiologicalProcess biologicalprocess, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(biologicalprocess) == -1)
				throw new ConflictException(this, biologicalprocess);
		}
		if (biologicalprocess.getPk() != null)
			this.pk = biologicalprocess.getPk();
		if (biologicalprocess.getName() != null)
			this.name = biologicalprocess.getName();

	}

	@Override
	public String toString() {
		return "BiologicalProcess [name=" + name + ", pk=" + pk + "]";
	}


}
