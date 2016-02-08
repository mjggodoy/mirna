package mirna.integration.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pubmed_document")
public class PubmedDocument extends ModelClass {
	
	@Column(name = "id", nullable = true, length = 10, unique = true)
	private String id;
	
	@Column(name = "description", nullable = true, length = 1000, unique = true)
	private String description;
	
	@Column(name = "resource", nullable = true, length = 300, unique = true)
	private String resource;
	
	public PubmedDocument() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
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
		if (this.description!=null) {
			if (pubmedDoc.getDescription()  ==null) res++;
			else if (!this.description.equals(pubmedDoc.getDescription())) return -1;
		}
		if (this.resource!=null) {
			if (pubmedDoc.getResource()  ==null) res++;
			else if (!this.resource.equals(pubmedDoc.getResource())) return -1;
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
		if (pubmedDoc.getDescription()!=null) this.description = pubmedDoc.getDescription();
		if (pubmedDoc.getResource()!=null) this.resource = pubmedDoc.getResource();
		
	}

	@Override
	public String toString() {
		return "PubmedDocument [id=" + id + ", description=" + description
				+ ", resource=" + resource + ", pk=" + pk + "]";
	}
	
}
