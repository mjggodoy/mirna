package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snp_has_pubmed_document", schema = "mirna")
public class SnpHasPubmedDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "snp_pk", nullable = false)
	private int snpPk;
	
	@Id
	@Column(name = "pubmed_document_pk", nullable = false)
	private int pubmedDocumentPk;

	private SnpHasPubmedDocument() {
		super();
	}

	public int getSnpPk() {
		return snpPk;
	}

	public int getPubmedDocumentPk() {
		return pubmedDocumentPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pubmedDocumentPk;
		result = prime * result + snpPk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnpHasPubmedDocument other = (SnpHasPubmedDocument) obj;
		if (pubmedDocumentPk != other.pubmedDocumentPk)
			return false;
		if (snpPk != other.snpPk)
			return false;
		return true;
	}
	
}
