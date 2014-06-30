package mirna.dao;

import java.util.List;

import mirna.exception.MiRnaException;
import beans.DataExpression;

public interface DataExpressionDAO {
	
	public void create(DataExpression newDataExpression) throws MiRnaException;

	public DataExpression read(int id) throws MiRnaException;
	
	public List<DataExpression> readAll() throws MiRnaException;

	public void update(DataExpression dataExpressionToUpdate) throws MiRnaException;

	public void delete(int id) throws MiRnaException;

	public boolean findByPrimaryKey(int id) throws MiRnaException;
	
	//public Collection<DataExpression> findByName(String name) throws MiRnaException;

	public int findTotalNumber() throws MiRnaException;

}
