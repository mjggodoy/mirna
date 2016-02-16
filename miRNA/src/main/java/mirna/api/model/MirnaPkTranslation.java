package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_pk_translation", schema = "mirna")
public class MirnaPkTranslation implements Serializable {
	
	@Id
	@Column(name = "old_pk", nullable = false)
	private int oldPk;
	
	@Id
	@Column(name = "new_pk", nullable = false)
	private int newPk;

	private MirnaPkTranslation() {
		super();
	}

	public int getOldPk() {
		return oldPk;
	}

	public int getNewPk() {
		return newPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + newPk;
		result = prime * result + oldPk;
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
		MirnaPkTranslation other = (MirnaPkTranslation) obj;
		if (newPk != other.newPk)
			return false;
		if (oldPk != other.oldPk)
			return false;
		return true;
	}
	
}
