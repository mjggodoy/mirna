package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mature", discriminatorType=DiscriminatorType.INTEGER)
public class MiRna extends ModelClass {

	@Column(name = "id", nullable = false, length = 20)
	protected String id;

	@Column(name = "accession_number", nullable = true, length = 20)
	private String accessionNumber;

	@Column(name = "previous_id", nullable = true, length = 90)
	private String previousId;
	
	@Column(name = "mature", nullable = true, length = 1, insertable=false, updatable=false)
	private boolean mature;
	
	@Column(name = "dead", nullable = true, length = 1)
	private boolean dead;
	
	@Column(name = "mirbase_pk", nullable = true)
	private Integer mirBasePk;
	
	@ManyToMany
	@JoinTable(
			name="mirna_has_pubmed_document2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="mirna_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="pubmed_document_pk")
			})
	private Set<PubmedDocument> pubmedDocuments;

	@ManyToMany
	@JoinTable(
			name="mirna_has_expression_data2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="mirna_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="expression_data_pk")
			})
	private Set<ExpressionData> expressionDatas;
	
//	@ManyToMany
//	@JoinTable(
//			name="mirna_pk_translation",
//			schema="mirna",
//			joinColumns={
//					@JoinColumn(name="mirna_pk")
//			},
//			inverseJoinColumns={
//					@JoinColumn(name="expression_data_pk")
//			})
//
//	private Set<InteractionData> expressionDatas;
	
	public MiRna() {}

	public String getId() {
		return id;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public String getPreviousId() {
		return previousId;
	}

	public boolean isMature() {
		return mature;
	}

	public boolean isDead() {
		return dead;
	}
	
	public Integer getMirBasePk() {
		return mirBasePk;
	}
	
	public Set<PubmedDocument> getPubmedDocuments() {
		return pubmedDocuments;
	}

	public Set<ExpressionData> getExpressionDatas() {
		return expressionDatas;
	}
	
}