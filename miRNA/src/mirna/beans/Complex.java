package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "complex")
public class Complex extends ModelClass{


	@Column(name = "minimal_free_energy", nullable = true, length = 80, unique = true)
	protected String minimal_free_energy;
	@Column(name = "normalized_minimal_free_energy", nullable = true, length = 80, unique = true)
	protected String normalized_minimal_free_energy;
	@Column(name = "binding_site_pattern", nullable = true, length = 80, unique = true)
	protected String binding_site_pattern;
	@Column(name = "target_pk", nullable = false, length = 80, unique = true)
	protected Integer target_pk;
	@Column(name = "mirna_pk", nullable = false, length = 80, unique = true)
	protected Integer mirna_pk;
	@Column(name = "interaction_data_pk", nullable = true, length = 80, unique = true)
	protected Integer interaction_data_pk;


	public Complex() { }

	public String getMinimal_free_energy() {
		return minimal_free_energy;
	}

	public void setMinimal_free_energy(String minimal_free_energy) {
		this.minimal_free_energy = minimal_free_energy;
	}

	public String getNormalized_minimal_free_energy() {
		return normalized_minimal_free_energy;
	}

	public void setNormalized_minimal_free_energy(
			String normalized_minimal_free_energy) {
		this.normalized_minimal_free_energy = normalized_minimal_free_energy;
	}

	public String getBinding_site_pattern() {
		return binding_site_pattern;
	}

	public void setBinding_site_pattern(String binding_site_pattern) {
		this.binding_site_pattern = binding_site_pattern;
	}
	

	public Integer getTarget_pk() {
		return target_pk;
	}

	public void setTarget_pk(Integer target_pk) {
		this.target_pk = target_pk;
	}

	public Integer getMirna_pk() {
		return mirna_pk;
	}

	public void setMirna_pk(Integer mirna_pk) {
		this.mirna_pk = mirna_pk;
	}

	public Integer getInteraction_data_pk() {
		return interaction_data_pk;
	}

	public void setInteraction_data_pk(Integer interaction_data_pk) {
		this.interaction_data_pk = interaction_data_pk;
	}

	public void update(Complex complex) throws ConflictException {
		this.update(complex, true);
	}

	public void update(Complex complex, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(complex) == -1)
				throw new ConflictException(this, complex);
		}
		if (complex.getPk() != null)
			this.pk = complex.getPk();

		if(complex.getMinimal_free_energy() != null)
			this.minimal_free_energy = complex.getMinimal_free_energy();

		if(complex.getNormalized_minimal_free_energy() != null)
			this.normalized_minimal_free_energy = complex.getNormalized_minimal_free_energy();	

		if(complex.getBinding_site_pattern() != null)
			this.binding_site_pattern = complex.getBinding_site_pattern();	
		
		if(complex.getMirna_pk() != null)
			this.mirna_pk = complex.getMirna_pk();	
		
		if(complex.getTarget_pk() != null)
			this.target_pk = complex.getTarget_pk();	
		
		if(complex.getInteraction_data_pk() != null)
			this.interaction_data_pk = complex.getInteraction_data_pk();

	}

	public int checkConflict(Complex complex) {
		int res = 0;

		if (this.pk != null) {
			if (complex.getPk() == null)
				res++; // res = 1
			else if (!this.pk.equals(complex.getPk()))
				return -1;
		}

		if (this.minimal_free_energy != null) {
			if (complex.getMinimal_free_energy() == null)
				res++; // res = 1
			else if (!this.minimal_free_energy.equals(complex.getMinimal_free_energy()))
				return -1;
		}

		if (this. normalized_minimal_free_energy != null) {
			if (complex.getNormalized_minimal_free_energy() == null)
				res++; // res = 1
			else if (!this.normalized_minimal_free_energy.equals(complex.getNormalized_minimal_free_energy()))
				return -1;
		}

		if (this.binding_site_pattern != null) {
			if (complex.getBinding_site_pattern() == null)
				res++; // res = 1
			else if (!this.binding_site_pattern.equals(complex.getBinding_site_pattern()))
				return -1;
		}
		
		if (this.target_pk != null) {
			if (complex.getTarget_pk() == null)
				res++; // res = 1
			else if (!this.target_pk.equals(complex.getTarget_pk()))
				return -1;
		}
		
		if (this.interaction_data_pk != null) {
			if (complex.getInteraction_data_pk() == null)
				res++; // res = 1
			else if (!this.interaction_data_pk.equals(complex.getInteraction_data_pk()))
				return -1;
		}

		if (this.mirna_pk != null) {
			if (complex.getMirna_pk() == null)
				res++; // res = 1
			else if (!this.mirna_pk.equals(complex.getMirna_pk()))
				return -1;
		}
		
		
		return res;
	}

	@Override
	public String toString() {
		return "Complex [minimal_free_energy=" + minimal_free_energy
				+ ", normalized_minimal_free_energy="
				+ normalized_minimal_free_energy + ", binding_site_pattern="
				+ binding_site_pattern + ", target_pk=" + target_pk
				+ ", mirna_pk=" + mirna_pk + ", interaction_data_pk="
				+ interaction_data_pk + ", pk=" + pk + "]";
	}

	


	

}
