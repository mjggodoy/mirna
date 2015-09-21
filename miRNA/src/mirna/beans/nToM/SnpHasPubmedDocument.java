package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "snp_has_pubmed_document")
public class SnpHasPubmedDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "snp_pk", nullable = false, length = 11, unique = false)
	private Integer snpPk;

	@Id
	@Column(name = "pubmed_document_pk", nullable = false, length = 11, unique = false)
	private Integer pubmedDocumentPk;
	
	public SnpHasPubmedDocument() {
		super();
	}

	public SnpHasPubmedDocument(Integer snpPk,Integer pubmedDocumentPk) {
		super();
		this.snpPk = snpPk;
		this.pubmedDocumentPk = pubmedDocumentPk;
	}

	@Override
	public String toString() {
		return "SnpHasPubmedDocument [snpPk=" + snpPk + ", pubmedDocumentPk="
				+ pubmedDocumentPk + "]";
	}
	
	

}
