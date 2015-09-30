package mirna.beans.nToM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transcript_produces_protein")
public class TranscriptProducesProtein implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "transcript_pk", nullable = false, length = 11, unique = false)
	private Integer transcript_pk;

	@Id
	@Column(name = "protein_pk", nullable = false, length = 11, unique = false) // Y LO TRADUCE POR ESTO
	private Integer protein_pk; // BUSCA ESTO (EN ESTA CASO SON IGUALES :P)
	
	
	public TranscriptProducesProtein(){		
		super();
		
		
	}
	
	
	public TranscriptProducesProtein(Integer transcript_pk, Integer protein_pk){		
		super();
		this.protein_pk = protein_pk;
		this.transcript_pk = transcript_pk;
		
	}


	public Integer getTranscript_pk() {
		return transcript_pk;
	} 


	public void setTranscript_pk(Integer transcript_pk) {
		this.transcript_pk = transcript_pk;
	}


	public Integer getProtein_pk() {
		return protein_pk;
	}


	public void setProtein_pk(Integer protein_pk) {
		this.protein_pk = protein_pk;
	}


	@Override
	public String toString() {
		return "TranscriptProducesProtein [transcript_pk=" + transcript_pk
				+ ", protein_pk=" + protein_pk + ", getTranscript_pk()="
				+ getTranscript_pk() + ", getProtein_pk()=" + getProtein_pk()
				+ "]";
	}	

}
