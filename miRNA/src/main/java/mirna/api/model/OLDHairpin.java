package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "hairpin", schema="mirna")
public class OLDHairpin extends ModelClass {
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "accession_number", nullable = true)
	private String accession_number;
	
	public String getName() {
		return name;
	}

	public String getAccession_number() {
		return accession_number;
	}

	public OLDHairpin() { }
	
}
