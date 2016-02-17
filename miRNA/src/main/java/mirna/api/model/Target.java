package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "target", schema = "mirna")
public class Target extends ModelClass {
	
	@Column(name = "cds_start", nullable = true, length = 80, unique = true)
	private String cds_start;
	@Column(name = "cds_end", nullable = true, length = 80, unique = true)
	private String cds_end;
	@Column(name = "utr3_start", nullable = true, length = 80, unique = true)
	private String utr3_start;
	@Column(name = "utr3_end", nullable = true, length = 80, unique = true)
	private String utr3_end;
	@Column(name = "strand_start", nullable = true, length = 80, unique = true)
	private String strand_start;
	@Column(name = "strand_end", nullable = true, length = 80, unique = true)
	private String strand_end;
	@Column(name = "chromosome", nullable = true, length = 80, unique = true)
	private String chromosome;
	@Column(name = "polarity", nullable = true, length = 80, unique = true)
	private String polarity;
	@Column(name = "binding_site_start", nullable = true, length = 80, unique = true)
	private String binding_site_start;
	@Column(name = "binding_site_end", nullable = true, length = 80, unique = true)
	private String binding_site_end;
	@Column(name = "repeated_motifs", nullable = true, length = 80, unique = true)
	private String repeated_motifs;
	@Column(name = "utr3_conservation_score", nullable = true, length = 80, unique = true)
	private String utr3_conservation_score;
	@Column(name = "region", nullable = true, length = 80, unique = true)
	private String region;
	@Column(name = "seed_match", nullable = true, length = 80, unique = true)
	private String seed_match;
	@Column(name = "coordinates", nullable = true, length = 80, unique = true)
	private String coordinates;
	@Column(name = "gc_proportion", nullable = true, length = 80, unique = true)
	private String gc_proportion;
	@Column(name = "organism_pk", nullable = true, length = 80, unique = true)
	private Integer organism_pk;
	@Column(name = "sequence_pk", nullable = true, length = 80, unique = true) //TODO: Sequence_pk no deber�a ser nulo.
	private Integer sequence_pk;
	@Column(name = "target_ref", nullable = true, length = 80, unique = true)
	private String target_ref;
	@Column(name = "gu_proportion", nullable = true, length = 80, unique = true)
	private String gu_proportion;
	@Column(name = "site_conservation_score", nullable = true, length = 80, unique = true)
	private String site_conservation_score;
	
	@ManyToOne
	@JoinColumn(name = "transcript_pk")
	private Transcript transcript;
	
	public Target() { }

}
