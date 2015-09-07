package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sequence")
public class Sequence extends ModelClass {
	
	@Column(name = "sequence", nullable = false, length = 200, unique = true)
	private String sequence;
	
	public Sequence() { }
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int checkConflict(Sequence sequence) {
		int res = 0;
		
		if (this.pk!=null) {
			if (sequence.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(sequence.getPk())) return -1;
		}
		
		if (this.sequence!=null) {
			if (sequence.getSequence()==null) res++; // res = 1
			else if (!this.sequence.equals(sequence.getSequence())) return -1;
		}
		return res;
	}
	
	public void update(Sequence sequence, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(sequence)==-1) throw new ConflictException(this, sequence);
		}
		if (sequence.getPk()!=null) this.pk = sequence.getPk();
		if (sequence.getSequence()!=null) this.sequence = sequence.getSequence();
				
	}
	
	public void update(Sequence sequence) throws ConflictException {
		this.update(sequence, true);
	}
	
	
	
	
	@Override
	public String toString() {
		return "Sequence [sequence=" + sequence + ", pk=" + pk + "]";
	}
	
}
