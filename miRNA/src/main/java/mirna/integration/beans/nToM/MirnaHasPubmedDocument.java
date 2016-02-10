package mirna.integration.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_pubmed_document")
public class MirnaHasPubmedDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirnaPk;

	@Id
	@Column(name = "pubmed_document_pk", nullable = false, length = 11, unique = false)
	private Integer pubmedDocumentPk;
	
	public MirnaHasPubmedDocument() {
		super();
	}

	public MirnaHasPubmedDocument(Integer mirnaPk, Integer pubmedDocumentPk) {
		super();
		this.mirnaPk = mirnaPk;
		this.pubmedDocumentPk = pubmedDocumentPk;
	}
	
	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	public Integer getPubmedDocumentPk() {
		return pubmedDocumentPk;
	}

	public void setPubmedDocumentPk(Integer pubmedDocumentPk) {
		this.pubmedDocumentPk = pubmedDocumentPk;
	}

	@Override
	public String toString() {
		return "MirnaHasPubmedDocument [mirnaPk="
				+ mirnaPk + ", pubmedDocumentPk=" + pubmedDocumentPk
				+ "]";
	}

}
