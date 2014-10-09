package mirna.beans;

public class MiRna extends ModelClass {
	
	private String name;
	private String accessionNumber;
	private String subName;
	private String provenance;
	private String chromosome;
	private String version;
	private String sequence;
	private String newName;
	
	public MiRna() {}
	
	public MiRna(int id, String name, String accessionNumber, String subName,
			String provenance, String chromosome, String version,
			String sequence, String newName) {
		super(id);
		this.name = name;
		this.accessionNumber = accessionNumber;
		this.subName = subName;
		this.provenance = provenance;
		this.chromosome = chromosome;
		this.version = version;
		this.sequence = sequence;
		this.newName = newName;
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
