package mirna.integration.beans;

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
	protected Integer pk;
	
	public ModelClass() {}
	
	public ModelClass(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return pk;
	}
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "ModelClass [pk=" + pk + ", getPk()=" + getPk()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
