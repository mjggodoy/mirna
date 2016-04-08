package mirna.api.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna_involves_biological_process2", schema="mirna")
public class MirnaInvolvesBiologicalProcess implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "mirna_pk", nullable = false)
	private int mirnaPk;
	
	@Id
	@Column(name = "biological_process_pk", nullable = false)
	private int biologicalProcessPk;

	public MirnaInvolvesBiologicalProcess() {
		super();
		
	}

	public int getMirnaPk() {
		return mirnaPk;
	}

	public int getBiologicalProcessPk() {
		return biologicalProcessPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + biologicalProcessPk;
		result = prime * result + mirnaPk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MirnaInvolvesBiologicalProcess other = (MirnaInvolvesBiologicalProcess) obj;
		if (biologicalProcessPk != other.biologicalProcessPk)
			return false;
		if (mirnaPk != other.mirnaPk)
			return false;
		return true;
	}

	
	
	
	
	
	
	
	
}
