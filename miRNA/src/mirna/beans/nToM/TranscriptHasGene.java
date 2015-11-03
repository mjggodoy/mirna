package mirna.beans.nToM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transcript_has_gene")
public class TranscriptHasGene  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "transcript_pk", nullable = false, length = 11, unique = false)
	private Integer transcriptPk;

	@Id
	@Column(name = "gene_pk", nullable = false, length = 11, unique = false)
	private Integer genePk;
	
	public TranscriptHasGene(){		
		super();
		
	}

	public TranscriptHasGene(Integer transcriptPk, Integer genePk) {
		super();
		this.transcriptPk = transcriptPk;
		this.genePk = genePk;
	}

	public Integer getTranscriptPk() {
		return transcriptPk;
	}

	public void setTranscriptPk(Integer transcriptPk) {
		this.transcriptPk = transcriptPk;
	}

	public Integer getGenePk() {
		return genePk;
	}

	public void setGenePk(Integer genePk) {
		this.genePk = genePk;
	}
	
}
