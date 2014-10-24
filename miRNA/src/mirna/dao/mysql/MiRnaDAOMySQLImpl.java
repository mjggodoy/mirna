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
					+ "name, accession_number, sequence, resource) values "
					+ "('%s', '%s', '%s', '%s')";
			String queryString = String.format(queryTemplate,
					safe(newMiRna.getName()), 
					newMiRna.getAccessionNumber(),
					newMiRna.getSequence(),
					newMiRna.getResource());
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
				MiRna miRna = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sequence"),
						(String) row.get("resource"));

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
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "update mirna.mirna set name=%s, accession_number=%s, "
					+ "sequence=%s, resource=%s where pk=%d";
			String queryString = String.format(queryTemplate,
					safe(miRnaToUpdate.getName()),
					miRnaToUpdate.getAccessionNumber(),
					miRnaToUpdate.getSequence(),
					miRnaToUpdate.getResource(),
					miRnaToUpdate.getPk());
			con.update(queryString);
		} catch (SQLException ex) {
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
				miRna = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sequence"),
						(String) row.get("resource"));
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
				res = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sequence"),
						(String) row.get("resource"));
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