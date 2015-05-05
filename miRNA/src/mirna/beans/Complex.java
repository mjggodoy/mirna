package mirna.beans;

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



	@Override
	public String toString() {
		return "Complex [minimal_free_energy=" + minimal_free_energy
				+ ", normalized_minimal_free_energy="
				+ normalized_minimal_free_energy + ", binding_site_pattern="
				+ binding_site_pattern + ", pk=" + pk + "]";
	}



	
	
	
	
	
	
}
