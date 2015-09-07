package mirna.beans;

import javax.persistence.Column;

import mirna.exception.ConflictException;

public class Mature extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;
	
	@Column(name = "mirna_pk", nullable = false)
	private Integer mirnaPk;
	
	@Column(name = "sequence_pk", nullable = false)
	private Integer sequence_pk;
	
	
	public Mature() { }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getMirnaPk() {
		return mirnaPk;
	}


	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}


	public Integer getSequence_pk() {
		return sequence_pk;
	}


	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}

	public int checkConflict(Mature mature) {
		int res = 0;
		if (this.pk!=null) {
			if (mature.getPk()==null) res++;
			else if (!this.pk.equals(mature.getPk())) return -1;
		}
		if (this.name!=null) {
			if (mature.getName()==null) res++;
			else if (!this.name.equals(mature.getName())) {
				return -1;
			}
		}
		if (this.sequence_pk!=null) {
			if (mature.getSequence_pk()==null) res++;
			else if (!this.sequence_pk.equals(mature.getSequence_pk())) return -1;
		}
		if (this.mirnaPk!=null) {
			if (mature.getMirnaPk()==null) res++;
			else if (!this.mirnaPk.equals(mature.getMirnaPk())) return -1;
		}
		return res;
	}
	
	public void update(Mature mature) throws ConflictException {
		this.update(mature, true);
	}
	
	public void update(Mature mature, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(mature)==-1) throw new ConflictException(this, mature);
		}
		if (mature.getPk()!=null) this.pk = mature.getPk();
		if (mature.getName()!=null) this.name = mature.getName();
		if (mature.getSequence_pk()!=null) this.sequence_pk = mature.getSequence_pk();
		if (mature.getMirnaPk()!=null) this.mirnaPk = mature.getMirnaPk();
	}


	@Override
	public String toString() {
		return "Mature [name=" + name + ", mirnaPk=" + mirnaPk
				+ ", sequence_pk=" + sequence_pk + ", pk=" + pk + "]";
	}
		
}
