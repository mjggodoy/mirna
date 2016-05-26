package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_mirbase_database_links", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MirnaDatabaseLink extends ModelClass{
	
	@Column(name = "db_id", nullable = true)
	private String dbId;
	
	@Column(name = "db_link", nullable = true)
	private String dbLink;
	
	@Column(name = "db_secondary", nullable = true)
	private String dbSecondary;
	
	
	public MirnaDatabaseLink() {	}

	public String getDbId() {
		return dbId;
	}

	public String getDbLink() {
		return dbLink;
	}

	public String getDbSecondary() {
		return dbSecondary;
	}
	
	
}
