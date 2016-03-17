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
	
	@ManyToOne
	@JoinColumn(name = "organism_pk")
	private Organism organism;
	
	public Target() { }
	

	public Organism getOrganism() {
		return organism;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((binding_site_end == null) ? 0 : binding_site_end.hashCode());
		result = prime
				* result
				+ ((binding_site_start == null) ? 0 : binding_site_start
						.hashCode());
		result = prime * result + ((cds_end == null) ? 0 : cds_end.hashCode());
		result = prime * result
				+ ((cds_start == null) ? 0 : cds_start.hashCode());
		result = prime * result
				+ ((chromosome == null) ? 0 : chromosome.hashCode());
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result
				+ ((gc_proportion == null) ? 0 : gc_proportion.hashCode());
		result = prime * result
				+ ((gu_proportion == null) ? 0 : gu_proportion.hashCode());
		result = prime * result
				+ ((organism == null) ? 0 : organism.hashCode());
		result = prime * result
				+ ((polarity == null) ? 0 : polarity.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result
				+ ((repeated_motifs == null) ? 0 : repeated_motifs.hashCode());
		result = prime * result
				+ ((seed_match == null) ? 0 : seed_match.hashCode());
		result = prime * result
				+ ((sequence_pk == null) ? 0 : sequence_pk.hashCode());
		result = prime
				* result
				+ ((site_conservation_score == null) ? 0
						: site_conservation_score.hashCode());
		result = prime * result
				+ ((strand_end == null) ? 0 : strand_end.hashCode());
		result = prime * result
				+ ((strand_start == null) ? 0 : strand_start.hashCode());
		result = prime * result
				+ ((target_ref == null) ? 0 : target_ref.hashCode());
		result = prime * result
				+ ((transcript == null) ? 0 : transcript.hashCode());
		result = prime
				* result
				+ ((utr3_conservation_score == null) ? 0
						: utr3_conservation_score.hashCode());
		result = prime * result
				+ ((utr3_end == null) ? 0 : utr3_end.hashCode());
		result = prime * result
				+ ((utr3_start == null) ? 0 : utr3_start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Target other = (Target) obj;
		if (binding_site_end == null) {
			if (other.binding_site_end != null)
				return false;
		} else if (!binding_site_end.equals(other.binding_site_end))
			return false;
		if (binding_site_start == null) {
			if (other.binding_site_start != null)
				return false;
		} else if (!binding_site_start.equals(other.binding_site_start))
			return false;
		if (cds_end == null) {
			if (other.cds_end != null)
				return false;
		} else if (!cds_end.equals(other.cds_end))
			return false;
		if (cds_start == null) {
			if (other.cds_start != null)
				return false;
		} else if (!cds_start.equals(other.cds_start))
			return false;
		if (chromosome == null) {
			if (other.chromosome != null)
				return false;
		} else if (!chromosome.equals(other.chromosome))
			return false;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (gc_proportion == null) {
			if (other.gc_proportion != null)
				return false;
		} else if (!gc_proportion.equals(other.gc_proportion))
			return false;
		if (gu_proportion == null) {
			if (other.gu_proportion != null)
				return false;
		} else if (!gu_proportion.equals(other.gu_proportion))
			return false;
		if (organism == null) {
			if (other.organism != null)
				return false;
		} else if (!organism.equals(other.organism))
			return false;
		if (polarity == null) {
			if (other.polarity != null)
				return false;
		} else if (!polarity.equals(other.polarity))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (repeated_motifs == null) {
			if (other.repeated_motifs != null)
				return false;
		} else if (!repeated_motifs.equals(other.repeated_motifs))
			return false;
		if (seed_match == null) {
			if (other.seed_match != null)
				return false;
		} else if (!seed_match.equals(other.seed_match))
			return false;
		if (sequence_pk == null) {
			if (other.sequence_pk != null)
				return false;
		} else if (!sequence_pk.equals(other.sequence_pk))
			return false;
		if (site_conservation_score == null) {
			if (other.site_conservation_score != null)
				return false;
		} else if (!site_conservation_score
				.equals(other.site_conservation_score))
			return false;
		if (strand_end == null) {
			if (other.strand_end != null)
				return false;
		} else if (!strand_end.equals(other.strand_end))
			return false;
		if (strand_start == null) {
			if (other.strand_start != null)
				return false;
		} else if (!strand_start.equals(other.strand_start))
			return false;
		if (target_ref == null) {
			if (other.target_ref != null)
				return false;
		} else if (!target_ref.equals(other.target_ref))
			return false;
		if (transcript == null) {
			if (other.transcript != null)
				return false;
		} else if (!transcript.equals(other.transcript))
			return false;
		if (utr3_conservation_score == null) {
			if (other.utr3_conservation_score != null)
				return false;
		} else if (!utr3_conservation_score
				.equals(other.utr3_conservation_score))
			return false;
		if (utr3_end == null) {
			if (other.utr3_end != null)
				return false;
		} else if (!utr3_end.equals(other.utr3_end))
			return false;
		if (utr3_start == null) {
			if (other.utr3_start != null)
				return false;
		} else if (!utr3_start.equals(other.utr3_start))
			return false;
		return true;
	}

	
	
}
