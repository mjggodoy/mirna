package mirna.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirna.beans.MiRna;
import mirna.dao.MiRnaDAO;
import mirna.db.DBConnection;
import mirna.db.mysql.DBConnectionMySQLImpl;
import mirna.exception.MiRnaException;

public class MiRnaDAOMySQLImpl extends ModelDAOMySQLImpl implements MiRnaDAO {
	
	@Override
	public int create(MiRna newMiRna) throws MiRnaException {
		DBConnection con = null;
		int res = -1;

		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna.mirna ("
					+ "name, accession_number, sequence, resource, organism_pk) values "
					+ "('%s', '%s', '%s', '%s', %d)";
			String queryString = String.format(queryTemplate,
					safe(newMiRna.getName()), 
					newMiRna.getAccessionNumber(),
					newMiRna.getSequence(),
					newMiRna.getResource(),
					newMiRna.getOrganismPk());
			queryString = queryString.replaceAll("'null'", "null");
			res = con.insert(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return res;
	}

	@Override
	public List<MiRna> readAll() throws MiRnaException {
		List<MiRna> miRnaList = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryString = "select * from mirna.mirna";
			list = con.query(queryString);
			miRnaList = new ArrayList<MiRna>();
			
			for (Map<String, Object> row : list) {
				MiRna miRna = new MiRna();
				miRna.setPk((Integer) row.get("pk"));
				miRna.setName((String) row.get("name"));
				miRna.setAccessionNumber((String) row.get("accession_number"));
				miRna.setSequence((String) row.get("sequence"));
				miRna.setResource((String) row.get("resource"));
				miRna.setOrganismPk((Integer) row.get("organism_pk"));
				miRnaList.add(miRna);
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return miRnaList;
	}

	@Override
	public void update(MiRna miRnaToUpdate) throws MiRnaException {
		DBConnection con = null;
		String queryString = "";
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "update mirna.mirna set name='%s', accession_number='%s', "
					+ "sequence='%s', resource='%s', organism_pk=%d where pk=%d";
			queryString = String.format(queryTemplate,
					safe(miRnaToUpdate.getName()),
					miRnaToUpdate.getAccessionNumber(),
					miRnaToUpdate.getSequence(),
					miRnaToUpdate.getResource(),
					miRnaToUpdate.getOrganismPk(),
					miRnaToUpdate.getPk());
			con.update(queryString);
		} catch (SQLException ex) {
			System.err.println(queryString);
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public void delete(int id) throws MiRnaException {
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "delete from mirna.mirna where pk=%d";
			String queryString = String.format(queryTemplate, id);
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public MiRna findByPrimaryKey(int id) throws MiRnaException {
		MiRna miRna = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from mirna.mirna where pk=%d";
			String queryString = String.format(queryTemplate, id);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				miRna = new MiRna();
				miRna.setPk((Integer) row.get("pk"));
				miRna.setName((String) row.get("name"));
				miRna.setAccessionNumber((String) row.get("accession_number"));
				miRna.setSequence((String) row.get("sequence"));
				miRna.setResource((String) row.get("resource"));
				miRna.setOrganismPk((Integer) row.get("organism_pk"));
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return miRna;
	}

	@Override
	public MiRna findByName(String name) throws MiRnaException {
		List<MiRna> miRnaList = new ArrayList<MiRna>();
		MiRna res = null;
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from mirna.mirna where name='%s'";
			String queryString = String.format(queryTemplate, name);
			list = con.query(queryString);
			for (Map<String, Object> row : list) {
				res = new MiRna();
				res.setPk((Integer) row.get("pk"));
				res.setName((String) row.get("name"));
				res.setAccessionNumber((String) row.get("accession_number"));
				res.setSequence((String) row.get("sequence"));
				res.setResource((String) row.get("resource"));
				res.setOrganismPk((Integer) row.get("organism_pk"));
				miRnaList.add(res);
			}
			if (miRnaList.size()>1) {
				throw new MiRnaException("Found two MiRna with the same name (" + name + ")");
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return res;
	}

	@Override
	public int findTotalNumber() throws MiRnaException {
		int total = -1;
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			list = con.query("select count(pk) from mirna.mirna");
			Object o = list.get(0).get("C1");
			total = ((Long)o).intValue();
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return total;
	}

}