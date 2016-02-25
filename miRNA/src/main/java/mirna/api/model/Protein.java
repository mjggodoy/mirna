package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "protein", schema = "mirna")
public class Protein extends ModelClass {

	@Column(name = "uniprot_id", nullable = true, length = 45, unique = true)
	private String id;

	@Column(name = "type", nullable = true, length = 300, unique = true)
	private String type;

	public Protein() {

	}

	public Protein(int pk, String uniprot_id, String type) {
		super(pk);
		this.id = uniprot_id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}
}
