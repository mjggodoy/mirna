package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pubmed_document", schema = "mirna")
public class PubmedDocument extends ModelClass {
	
	@Column(name = "id", nullable = true, length = 10, unique = true)
	private String id;

	@Column(name = "title", nullable = true)
	private String title;

	@Column(name = "authors", nullable = true)
	private String authors;
	
	@ManyToMany
	@JoinTable(
			name="snp_has_pubmed_document",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="pubmed_document_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="snp_pk")
			})
	private Set<SNP> snps;

	@ManyToMany(mappedBy = "pubmedDocuments")
	private Set<MiRna> mirnas;
	
	@ManyToMany(mappedBy = "pubmedDocuments")
	private Set<ExpressionData> expressionDatas;
	
	public PubmedDocument() {}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthors() {
		return authors;
	}
	
	public Set<MiRna> getMirnas() {
		return mirnas;
	}
	
}
