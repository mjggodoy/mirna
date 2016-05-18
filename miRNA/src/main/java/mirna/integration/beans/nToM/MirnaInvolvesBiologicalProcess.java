package mirna.integration.beans.nToM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_involves_biological_process")
public class MirnaInvolvesBiologicalProcess implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirnaPk;

	@Id
	@Column(name = "biological_process_pk", nullable = false, length = 11, unique = false) // Y LO TRADUCE POR ESTO
	private Integer biological_process_pk; // BUSCA ESTO (EN ESTA CASO SON IGUALES :P)
	
	public MirnaInvolvesBiologicalProcess(){		
		super();
		
	}
	
	public MirnaInvolvesBiologicalProcess(Integer mirna_pk, Integer biological_process_pk ){		
		super();
		this.mirnaPk = mirna_pk;
		this.biological_process_pk = biological_process_pk;
	}


	public Integer getMirnaPk() {
		return mirnaPk;
	}


	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}


	public Integer getBiological_process_pk() {
		return biological_process_pk;
	}


	public void setBiological_process_pk(Integer biological_process_pk) {
		this.biological_process_pk = biological_process_pk;
	}


	@Override
	public String toString() {
		return "MirnaInvolvesBiologicalProcess [mirnaPk=" + mirnaPk
				+ ", biological_process_pk=" + biological_process_pk + "]";
	}
	
	

}
