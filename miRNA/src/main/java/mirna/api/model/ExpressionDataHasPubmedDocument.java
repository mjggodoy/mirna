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
@Table(name = "expression_data_has_pubmed_document", schema="mirna")
public class ExpressionDataHasPubmedDocument implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "expression_data_pk", nullable = false)
	private int expressionDataPk;
	
	@Id
	@Column(name = "pubmed_document_pk", nullable = false)
	private int pubmedDocumentPk;

	public ExpressionDataHasPubmedDocument() {
		super();	
	}

	public int getExpresssionDataPk() {
		return expressionDataPk;
	}

	public int getPubmedDocumentPk() {
		return pubmedDocumentPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expressionDataPk;
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
		ExpressionDataHasPubmedDocument other = (ExpressionDataHasPubmedDocument) obj;
		if (expressionDataPk != other.expressionDataPk)
			return false;
		if (pubmedDocumentPk != other.pubmedDocumentPk)
			return false;
		return true;
	}
	
	
	
	
	

}