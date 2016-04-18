package mirna.api.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna_has_expression_data2", schema="mirna")
public class MirnaHasExpressionData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "mirna_pk", nullable = false)
	private int mirnaPk;
	
	@Id
	@Column(name = "expression_data_pk", nullable = false)
	private int expressionDataPk;

	public MirnaHasExpressionData() {
		super();
		
	}

	public int getMirnaPk() {
		return mirnaPk;
	}

	public int getExpressionDataPk() {
		return expressionDataPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expressionDataPk;
		result = prime * result + mirnaPk;
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
		MirnaHasExpressionData other = (MirnaHasExpressionData) obj;
		if (expressionDataPk != other.expressionDataPk)
			return false;
		if (mirnaPk != other.mirnaPk)
			return false;
		return true;
	}

	

	

	

	
}
