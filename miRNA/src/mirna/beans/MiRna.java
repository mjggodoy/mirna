package mirna.beans;

public class MiRna extends ModelClass {
	
	private String name; //ok
	private String id; //ok
	private String journal;//ok
	private String accessionNumber; //ok
	private String subName; //ok
	private String provenance; //ok
	private String length;//ok
	private String binding_site_pattern;//ok
	private String GC_proportion; //ok
	private String chromosome; //ok
	private String version; //ok 
	private String sequence; //ok
	private String newName; //ok
	private String minimal_free_energy;//ok
	private String normalized_minimal_free_energy;//ok
	private String polarity;//ok
	private String pubmedId ;//ok
	private String resource;//ok
	private String start_strand;//ok
	private String title_reference;//ok
	private String year;
	
	
	public MiRna() {}
	
	public MiRna(int id, String name, String accessionNumber, String subName,
			String provenance, String chromosome, String version,
			String sequence, String newName, String binding_site_pattern, String GC_proportion, String journal, String length,
			String minimal_free_energy, String normalized_minimal_free_energy, String polarity, String pubmedId, String resource, 
			String start_strand, String title_reference, String year) {
		super(id);
		this.name = name;
		this.accessionNumber = accessionNumber;
		this.subName = subName;
		this.provenance = provenance;
		this.chromosome = chromosome;
		this.version = version;
		this.sequence = sequence;
		this.newName = newName;
		this.binding_site_pattern = binding_site_pattern;
		this.GC_proportion = GC_proportion;
		this.journal = journal;
		this.length = length;
		this.minimal_free_energy = minimal_free_energy;
		this.normalized_minimal_free_energy = normalized_minimal_free_energy;
		this.polarity = polarity;
		this.pubmedId = pubmedId;
		this.resource = resource;
		this.start_strand = start_strand;
		this.title_reference = title_reference;
		this.year = year;
	}
	
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle_reference() {
		return title_reference;
	}

	public void setTitle_reference(String title_reference) {
		this.title_reference = title_reference;
	}

	public String getStart_strand() {
		return start_strand;
	}

	public void setStart_strand(String start_strand) {
		this.start_strand = start_strand;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public String getPolarity() {
		return polarity;
	}

	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}

	public String getNormalized_minimal_free_energy() {
		return normalized_minimal_free_energy;
	}

	public void setNormalized_minimal_free_energy(
			String normalized_minimal_free_energy) {
		this.normalized_minimal_free_energy = normalized_minimal_free_energy;
	}

	public String getMinimal_free_energy() {
		return minimal_free_energy;
	}

	public void setMinimal_free_energy(String minimal_free_energy) {
		this.minimal_free_energy = minimal_free_energy;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getGC_proportion() {
		return GC_proportion;
	}

	public void setGC_proportion(String GC_proportion) {
		this.GC_proportion = GC_proportion;
	}

	public String getBinding_site_pattern() {
		return binding_site_pattern;
	}

	public void setBinding_site_pattern(String binding_site_pattern) {
		this.binding_site_pattern = binding_site_pattern;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}
	
	public boolean checkConflict(MiRna mirna) {
		if ((mirna.getName() != null)
				&& (this.name != null)
				&& (!this.name.equals(mirna.getName()))) {
			return false;
		}
		if ((mirna.getAccessionNumber() != null)
				&& (this.accessionNumber != null)
				&& (!this.accessionNumber.equals(mirna.getAccessionNumber()))) {
			return false;
		}
		if ((mirna.getSubName() != null)
				&& (this.subName != null)
				&& (!this.subName.equals(mirna.getSubName()))) {
			return false;
		}
		if ((mirna.getProvenance() != null)
				&& (this.provenance != null)
				&& (!this.provenance.equals(mirna.getProvenance()))) {
			return false;
		}
		if ((mirna.getChromosome() != null)
				&& (this.chromosome != null)
				&& (!this.chromosome.equals(mirna.getChromosome()))) {
			return false;
		}
		if ((mirna.getVersion() != null)
				&& (this.version != null)
				&& (!this.version.equals(mirna.getVersion()))) {
			return false;
		}
		if ((mirna.getSequence() != null)
				&& (this.sequence != null)
				&& (!this.sequence.equals(mirna.getSequence()))) {
			return false;
		}
		if ((mirna.getNewName() != null)
				&& (this.newName != null)
				&& (!this.newName.equals(mirna.getNewName()))) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MiRna other = (MiRna) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", accessionNumber=" + accessionNumber
				+ ", subName=" + subName + ", provenance=" + provenance
				+ ", chromosome=" + chromosome + ", version=" + version
				+ ", sequence=" + sequence + ", newName=" + newName + "]";
	}
	
}
