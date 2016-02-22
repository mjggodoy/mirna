package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "dead_mirbase_info", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MirbaseDeadInfo extends ModelClass {
	
	@Column(name = "forward_to", nullable = true)
	private String forwardTo;
	
	@Column(name = "comment", nullable = true)
	private String comment;
	
	public MirbaseDeadInfo() { }
	
	public String getForwardTo() {
		return forwardTo;
	}
	
	public String getComment() {
		return comment;
	}

}
