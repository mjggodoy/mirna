package mirna.integration.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sequence")
public class Sequence extends ModelClass {
	
	@Column(name = "sequence", nullable = false, length = 400, unique = false)
	private String sequence;
	
	@Column(name = "length", nullable = true, length = 10)
	private String length; //lo he puesto nuevo
	
	@Column(name = "gc_proportion", nullable = true, length = 10)
	private String GC_proportion;//lo he puesto nuevo
	
	public Sequence() { }
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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
		if (this.length!=null) {
			if (sequence.getLength()==null) res++;
			else if (!this.length.equals(sequence.getLength())) return -1;
		}
		if (this.GC_proportion!=null) {
			if (sequence.getGC_proportion()==null) res++;
			else if (!this.GC_proportion.equals(sequence.getGC_proportion())) return -1;
		}
		return res;
	}
	
	public void update(Sequence sequence, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(sequence)==-1) throw new ConflictException(this, sequence);
		}
		if (sequence.getPk()!=null) this.pk = sequence.getPk();
		if (sequence.getSequence()!=null) this.sequence = sequence.getSequence();
		if (sequence.getLength()!=null) this.length = sequence.getLength();
		if (sequence.getGC_proportion()!=null) this.GC_proportion = sequence.getGC_proportion();
	}
	
	public void update(Sequence sequence) throws ConflictException {
		this.update(sequence, true);
	}
	
	@Override
	public String toString() {
		return "Sequence [sequence=" + sequence + ", length=" + length + ", GC_proportion=" + GC_proportion + ", pk=" + pk + "]";
	}
	
}
