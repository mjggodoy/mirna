package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_organism")

public class MirnaHasOrganism implements Serializable{
	
	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirna_pk;

	@Id
	@Column(name = "organism_pk", nullable = false, length = 11, unique = false) // Y LO TRADUCE POR ESTO
	private Integer organism_pk; // BUSCA ESTO (EN ESTA CASO SON IGUALES :P)
	

	
	

}
