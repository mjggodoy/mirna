package mirna.beans;

import mirna.exception.ConflictException;

public class Complex extends ModelClass{

	private String minimal_free_energy;
	private String normalized_minimal_free_energy;
	private String binding_site_pattern;

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
		
		return res;
	}
	
	
	@Override
	public String toString() {
		return "Complex [minimal_free_energy=" + minimal_free_energy
				+ ", normalized_minimal_free_energy="
				+ normalized_minimal_free_energy + ", binding_site_pattern="
				+ binding_site_pattern + ", pk=" + pk + "]";
	}

}
