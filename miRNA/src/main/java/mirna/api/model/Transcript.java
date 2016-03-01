package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
	private Set<Protein> protein;
	
	
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

	public Set<Protein> getProtein() {
		return protein;
	}	
}
