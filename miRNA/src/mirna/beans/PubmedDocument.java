package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pubmed_document")
public class PubmedDocument extends ModelClass {
	
	@Column(name = "id", nullable = false, length = 10, unique = true)
	private String id;
	
	public PubmedDocument() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int checkConflict(PubmedDocument pubmedDoc) {
		int res = 0;
		if (this.pk!=null) {
			if (pubmedDoc.getPk()==null) res++;
			else if (!this.pk.equals(pubmedDoc.getPk())) return -1;
		}
		if (this.id!=null) {
			if (pubmedDoc.getId()==null) res++;
			else if (!this.id.equals(pubmedDoc.getId())) return -1;
		}
		return res;
	}
	
	public void update(PubmedDocument pubmedDoc) throws ConflictException {
		this.update(pubmedDoc, true);
	}
	
	public void update(PubmedDocument pubmedDoc, boolean checkConflict) throws ConflictException {
		
		if (checkConflict) {
			if (this.checkConflict(pubmedDoc)==-1) throw new ConflictException(this, pubmedDoc);
		}
		
		if (pubmedDoc.getPk()!=null) this.pk = pubmedDoc.getPk();
		if (pubmedDoc.getId()!=null) this.id = pubmedDoc.getId();
		
	}

	@Override
	public String toString() {
		return "PubmedDocument [id=" + id + ", pk=" + pk + "]";
	}

	
	
}