package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transcript_has_gene", schema = "mirna")
public class TranscriptHasGene implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "transcript_pk", nullable = false)
	private int transcriptPk;
	
	@Id
	@Column(name = "gene_pk", nullable = false)
	private int genePk;

	private TranscriptHasGene() {
		super();
	}

	public int getTranscriptPk() {
		return transcriptPk;
	}

	public int getGenePk() {
		return genePk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + genePk;
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
		TranscriptHasGene other = (TranscriptHasGene) obj;
		if (genePk != other.genePk)
			return false;
		if (transcriptPk != other.transcriptPk)
			return false;
		return true;
	}

}
