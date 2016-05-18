package mirna.api.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "interaction_data_has_pubmed_document", schema="mirna")
public class InteractionDataHasPubmedDocument implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "interaction_data_pk", nullable = false)
	private int interactionDataPk;
	
	@Id
	@Column(name = "pubmed_document_pk", nullable = false)
	private int pubmedDocumentPk;

	public InteractionDataHasPubmedDocument() {
		super();	
	}

	public int getInteractionDataPk() {
		return interactionDataPk;
	}

	public int getPubmedDocumentPk() {
		return pubmedDocumentPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + interactionDataPk;
		result = prime * result + pubmedDocumentPk;
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
		InteractionDataHasPubmedDocument other = (InteractionDataHasPubmedDocument) obj;
		if (interactionDataPk != other.interactionDataPk)
			return false;
		if (pubmedDocumentPk != other.pubmedDocumentPk)
			return false;
		return true;
	}

}