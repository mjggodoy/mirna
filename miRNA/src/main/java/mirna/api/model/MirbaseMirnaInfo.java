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
	
	@Column(name = "wp_title", nullable = true)
	private String wp_title;
	
	@Column(name = "wp_description", nullable = true)
	private String wp_description;
	
	public MirbaseMirnaInfo() { }
	
	public String getDescription() {
		return description;
	}
	
	public String getComment() {
		return comment;
	}

	public String getWp_title() {
		return wp_title;
	}

	public String getWp_description() {
		return wp_description;
	}

}
