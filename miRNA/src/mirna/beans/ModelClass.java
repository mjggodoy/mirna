package mirna.beans;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ModelClass {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pk", nullable=false, unique=true, length=11)
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
