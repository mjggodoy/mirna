package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transcript", schema = "mirna")
public class Transcript extends ModelClass {

	@Column(name = "id", nullable = true, length = 20, unique = true)
	private String id;
	
	@Column(name = "name", nullable = true, length = 20, unique = false)
	private String name;
	
	@Column(name = "isoform", nullable = true, length = 45, unique = false)
	private String isoform;
	
	@Column(name = "external_name", nullable = true, length = 20, unique = true)
	private String externalName;

	public Transcript() {
	}

	public Transcript(int pk, String id, String name, String isoform
			, String externalName) {
		super(pk);
		this.id = id;
		this.name = name;
		this.isoform = isoform;
		this.externalName = externalName;
	}

	@ManyToMany(mappedBy="transcripts")
	private Set<Protein> proteins;
	
	
	@ManyToMany
	@JoinTable(
			name="transcript_has_gene",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="gene_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="transcript_pk")
	})
	
	private Set<Gene> genes;
	
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getIsoform() {
		return isoform;
	}
	
	public String getExternalName() {
		return externalName;
	}

	public Set<Protein> getProteins() {
		return proteins;
	}

	public Set<Gene> getGenes() {
		return genes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((externalName == null) ? 0 : externalName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isoform == null) ? 0 : isoform.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Transcript other = (Transcript) obj;
		if (externalName == null) {
			if (other.externalName != null)
				return false;
		} else if (!externalName.equals(other.externalName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isoform == null) {
			if (other.isoform != null)
				return false;
		} else if (!isoform.equals(other.isoform))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
	
		return true;
	}

	

	
	
	
}
