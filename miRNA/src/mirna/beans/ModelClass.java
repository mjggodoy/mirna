package mirna.beans;

public class ModelClass {
	
	protected int pk;
	
	public ModelClass() {}
	
	public ModelClass(int pk) {
		this.pk = pk;
	}
	
	public int getPk() {
		return pk;
	}
	
	public void setPk(int pk) {
		this.pk = pk;
	}
	
}
