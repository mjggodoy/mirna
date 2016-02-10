package mirna.integration.beans;

public class Pathway {

	private String id;



	public Pathway() {}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Pathway [id=" + id + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	

}
