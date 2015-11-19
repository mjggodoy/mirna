package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna")
public class MiRna extends ModelClass {

	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;

	@Column(name = "arm", nullable = true, length = 5)
	private String arm;

	@Column(name = "resource", nullable = true, length = 45)
	private String resource;


	public MiRna() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

	public int checkConflict(MiRna mirna) {
		int res = 0;
		if (this.pk!=null) {
			if (mirna.getPk()==null) res++;
			else if (!this.pk.equals(mirna.getPk())) return -1;
		}
		if (this.name!=null) {
			if (mirna.getName()==null) res++;
			else if (!this.name.equals(mirna.getName())) {
				return -1;
			}
		}

		if (this.resource!=null) {
			if (mirna.getResource()==null) res++;
			else if (!this.resource.equals(mirna.getResource())) return -1;
		}

		if (this.arm!=null) {
			if (mirna.getArm()==null) res++;
			else if (!this.arm.equals(mirna.getArm())) return -1;
		}

		return res;
	}

	public void update(MiRna mirna) throws ConflictException {
		this.update(mirna, true);
	}

	public void update(MiRna mirna, boolean checkConflict) throws ConflictException {

		if (checkConflict) {
			if (this.checkConflict(mirna)==-1) throw new ConflictException(this, mirna);
		}

		if (mirna.getPk()!=null) this.pk = mirna.getPk();
		if (mirna.getName()!=null) this.name = mirna.getName();
		if (mirna.getResource()!=null) this.resource = mirna.getResource();
		if(mirna.getArm() != null) this.arm = mirna.getArm();

	}

	@Override
	public String toString() {
		return "MiRna [name=" + name + ", arm=" + arm + ", resource="
				+ resource + "]";
	}
	
}