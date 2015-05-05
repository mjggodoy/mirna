package mirna.beans;

import mirna.exception.ConflictException;

public class EnvironmentalFactor extends ModelClass  {
	
	protected String name;

	public EnvironmentalFactor() { }
	
	public EnvironmentalFactor(int pk, String name) {
		super(pk);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int checkConflict(EnvironmentalFactor ef) {
		int res = 0;
		
		if (this.pk!=null) {
			if (ef.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(ef.getPk())) return -1;
		}
		
		if (this.name!=null) {
			if (ef.getName()==null) res++; 
			else if (!this.name.equals(ef.getName())) return -1;
		}
		
		return res;
	}
	
	
	public void update(EnvironmentalFactor ef) throws ConflictException {
		this.update(ef, true);
	}
	
	
	public void update(EnvironmentalFactor ef, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(ef)==-1) throw new ConflictException(this, ef);
		}
		if (ef.getPk()!=null) this.pk = ef.getPk();
		if (ef.getName()!=null) this.name = ef.getName();
		
	}

	@Override
	public String toString() {
		return "EnvironmentalFactor [name=" + name + ", pk=" + pk + "]";
	}
	
	

	
	
}
