package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sequence", schema="mirna")
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

	public String getLength() {
		return length;
	}

	public String getGC_proportion() {
		return GC_proportion;
	}
	
}
