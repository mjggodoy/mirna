package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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

	@Override
	public String toString() {
		return "Sequence [sequence=" + sequence + ", pk=" + pk + "]";
	}
	
}
