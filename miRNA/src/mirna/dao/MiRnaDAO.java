package mirna.dao;

import java.util.List;

import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

public interface MiRnaDAO {
	
	public int create(MiRna newMiRna) throws MiRnaException;

	public List<MiRna> readAll() throws MiRnaException;

	public void update(MiRna miRnaToUpdate) throws MiRnaException;

	public void delete(int id) throws MiRnaException;

	public MiRna findByPrimaryKey(int id) throws MiRnaException;
	
	public MiRna findByName(String name) throws MiRnaException;

	public int findTotalNumber() throws MiRnaException;

}
