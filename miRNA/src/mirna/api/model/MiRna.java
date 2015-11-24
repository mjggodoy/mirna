package mirna.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna", schema = "mirna")
public class MiRna extends ModelClass {

	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;

	@Column(name = "arm", nullable = true, length = 5)
	private String arm;

	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	@OneToMany
	@JoinColumn(name = "mirna_pk", referencedColumnName = "pk")
	private List<ExpressionData> expressionDatas;

	public MiRna() {}

	public String getName() {
		return name;
	}

	public String getResource() {
		return resource;
	}

	public String getArm() {
		return arm;
	}

}