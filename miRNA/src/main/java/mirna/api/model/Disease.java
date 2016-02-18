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
@Table(name = "disease", schema = "mirna" )
public class Disease extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	private String name;

	@Column(name = "disease_class", nullable = true, length = 20)
	private String diseaseClass;
	
	@ManyToMany
	@JoinTable(
			name="snp_has_disease",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="disease_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="snp_pk")
			})
	private Set<SNP> snps;
	
	public Disease() {}
	
	public String getName() {
		return name;
	}

	public String getDiseaseClass() {
		return diseaseClass;
	}

}
