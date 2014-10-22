package mirna.dao;

import java.util.List;

import mirna.beans.ExpressionData;
import mirna.exception.MiRnaException;

public interface ExpressionDataDAO {
	
	public int create(ExpressionData newDataExpression) throws MiRnaException;

	public List<ExpressionData> readAll() throws MiRnaException;

	public void update(ExpressionData dataExpressionToUpdate) throws MiRnaException;

	public void delete(int id) throws MiRnaException;

	public ExpressionData findByPrimaryKey(int id) throws MiRnaException;
	
	//public Collection<DataExpression> findByName(String name) throws MiRnaException;

	public int findTotalNumber() throws MiRnaException;
	
	public void newRelatedDisease(int dataExpressionId, int diseaseId) throws MiRnaException;
	
	public void newMiRnaInvolved(int dataExpressionId, int miRnaId) throws MiRnaException;

}
