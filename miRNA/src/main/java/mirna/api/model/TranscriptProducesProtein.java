package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transcript_produces_protein", schema = "mirna")
public class TranscriptProducesProtein implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "transcript_pk", nullable = false)
	private int transcriptPk;
	
	@Id
	@Column(name = "protein_pk", nullable = false)
	private int proteinPk;

	private TranscriptProducesProtein() {
		super();
	}

	public int getTranscriptPk() {
		return transcriptPk;
	}

	public int getProteinPk() {
		return proteinPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + proteinPk;
		result = prime * result + transcriptPk;
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
		TranscriptProducesProtein other = (TranscriptProducesProtein) obj;
		if (proteinPk != other.proteinPk)
			return false;
		if (transcriptPk != other.transcriptPk)
			return false;
		return true;
	}

}
