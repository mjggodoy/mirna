package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expression_data_has_pubmed_document")
public class ExpressionDataHasPubmedDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "expression_data_pk", nullable = false, length = 11, unique = false)
	private Integer expressionDataPk;

	@Id
	@Column(name = "pubmed_document_pk", nullable = false, length = 11, unique = false)
	private Integer pubmedDocumentPk;
	
	public ExpressionDataHasPubmedDocument() {
		super();
	}

	public ExpressionDataHasPubmedDocument(Integer expressionDataPk,
			Integer pubmedDocumentPk) {
		super();
		this.expressionDataPk = expressionDataPk;
		this.pubmedDocumentPk = pubmedDocumentPk;
	}

	@Override
	public String toString() {
		return "ExpressionDataHasPubmedDocument [expressionDataPk="
				+ expressionDataPk + ", pubmedDocumentPk=" + pubmedDocumentPk
				+ "]";
	}
	
}
