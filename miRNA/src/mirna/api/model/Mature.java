package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mature", schema="mirna")

public class Mature extends ModelClass {
	
	@Column(name = "proportion", nullable = true, length = 80, unique = true)
	protected String proportion;
	
	@Column(name = "accession_number", nullable = true)
	private String accession_number;
	
	public Mature() { }
	
	public String getProportion() {
		return proportion;
	}

	public String getAccession_number() {
		return accession_number;
	}

}
