package mirna.integration.beans;

import mirna.integration.exception.ConflictException;

import javax.persistence.*;

/**
 * Created by Esteban on 20/05/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna2", schema = "mirna")
public class MiRNA2 extends ModelClass {

	@Column(name = "id", nullable = false, length = 20, unique = true)
	private String id;

	@Column(name = "accession_number", nullable = true, length = 20)
	private String accessionNumber;

	@Column(name = "previous_id", nullable = true, length = 90)
	private String previousId;

	@Column(name = "type", nullable = false)
	private String type;

	public String getId() {
		return id;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public String getPreviousId() {
		return previousId;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "MiRNA2{" +
				"id='" + id + '\'' +
				", accessionNumber='" + accessionNumber + '\'' +
				", previousId='" + previousId + '\'' +
				", type='" + type + '\'' +
				"} ";
	}
}
