package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "small_molecule", schema = "mirna")
public class SmallMolecule extends ModelClass  {

	@Column(name = "fda", nullable = true, length = 45)
	private String fda;
	
	@Column(name = "db", nullable = true, length = 45)
	private String db;
	
	@Column(name = "cid", nullable = true, length = 45)
	private String cid;

	public SmallMolecule() {
		super();
	}

	public String getFda() {
		return fda.substring(0, 1).toUpperCase() + fda.substring(1);
	}

	public String getDb() {
		return db;
	}

	public String getCid() {
		return cid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + ((fda == null) ? 0 : fda.hashCode());
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
		SmallMolecule other = (SmallMolecule) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (db == null) {
			if (other.db != null)
				return false;
		} else if (!db.equals(other.db))
			return false;
		if (fda == null) {
			if (other.fda != null)
				return false;
		} else if (!fda.equals(other.fda))
			return false;
		return true;
	}
	
}
