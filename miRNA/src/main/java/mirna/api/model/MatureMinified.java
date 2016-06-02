package mirna.api.model;

import javax.persistence.*;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MatureMinified extends MiRnaMinified {

}