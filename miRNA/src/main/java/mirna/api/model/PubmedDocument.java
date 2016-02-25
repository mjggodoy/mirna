package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pubmed_document", schema = "mirna")
public class PubmedDocument extends ModelClass {
	
	@Column(name = "id", nullable = true, length = 10, unique = true)
	private String id;
	
	@Column(name = "description", nullable = true, length = 1000, unique = true)
	private String description;
	
	@Column(name = "resource", nullable = true, length = 300, unique = true)
	private String resource;
	
	@ManyToMany(mappedBy = "pubmedDocuments")
	private Set<MiRna> mirnas;
	
	@ManyToMany(mappedBy = "pubmedDocuments")
	private Set<ExpressionData> expressionDatas;
	
	public PubmedDocument() {}
	
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getResource() {
		return resource;
	}
	
	public Set<MiRna> getMirnas() {
		return mirnas;
	}
	
}
