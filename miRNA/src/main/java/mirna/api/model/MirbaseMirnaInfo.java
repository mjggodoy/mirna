package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_mirbase_info", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MirbaseMirnaInfo extends ModelClass {
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "comment", nullable = true)
	private String comment;
	
	public MirbaseMirnaInfo() { }
	
	public String getDescription() {
		return description;
	}
	
	public String getComment() {
		return comment;
	}

}
