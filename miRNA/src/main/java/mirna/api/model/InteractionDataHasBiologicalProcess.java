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
@Table(name = "interaction_data_has_biological_process", schema="mirna")
public class InteractionDataHasBiologicalProcess implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "interaction_data_pk", nullable = false)
	private int interactionDataPk;
	
	@Id
	@Column(name = "biological_process_pk", nullable = false)
	private int biologicalProcessPk;

	public InteractionDataHasBiologicalProcess() {
		super();
		
	}

	public int getInteractionDataPk() {
		return interactionDataPk;
	}


	public int getBiologicalProcessPk() {
		return biologicalProcessPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + biologicalProcessPk;
		result = prime * result + interactionDataPk;
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
		InteractionDataHasBiologicalProcess other = (InteractionDataHasBiologicalProcess) obj;
		if (biologicalProcessPk != other.biologicalProcessPk)
			return false;
		if (interactionDataPk != other.interactionDataPk)
			return false;
		return true;
	}
	
}
