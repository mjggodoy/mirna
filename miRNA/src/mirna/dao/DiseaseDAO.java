package mirna.dao;

import java.util.List;

import mirna.exception.MiRnaException;
import beans.Disease;

public interface DiseaseDAO {
	
	public int create(Disease newDisease) throws MiRnaException;

	public Disease read(int id) throws MiRnaException;
	
	public List<Disease> readAll() throws MiRnaException;

	public void update(Disease diseaseToUpdate) throws MiRnaException;

	public void delete(int id) throws MiRnaException;

	public boolean findByPrimaryKey(int id) throws MiRnaException;
	
	public Disease findByName(String name) throws MiRnaException;

	public int findTotalNumber() throws MiRnaException;

}
