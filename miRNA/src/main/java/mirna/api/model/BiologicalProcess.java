package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "biological_process", schema="mirna")
public class BiologicalProcess extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 45, unique = true)
	private String name;
	
	public BiologicalProcess() { }
	
	@ManyToMany
	@JoinTable(
			name="mirna_involves_biological_process2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="biological_process_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="mirna_pk")
	})
	
	private Set<MiRna> mirnas;

	public String getName() {
		return name;
	}

	public Set<MiRna> getMirnas() {
		return mirnas;
	}
	
}
