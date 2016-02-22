package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "mature_mirbase_info", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MirbaseMatureInfo extends ModelClass {
	
	@Column(name = "evidence", nullable = true)
	private String evidence;
	
	@Column(name = "experiment", nullable = true)
	private String experiment;
	
	@Column(name = "similarity", nullable = true)
	private String similarity;
	
	public MirbaseMatureInfo() { }

	public String getEvidence() {
		return evidence;
	}

	public String getExperiment() {
		return experiment;
	}

	public String getSimilarity() {
		return similarity;
	}

}
