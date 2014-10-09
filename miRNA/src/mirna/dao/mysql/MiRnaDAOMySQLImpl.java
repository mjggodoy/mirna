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

public class MiRnaDAOMySQLImpl implements MiRnaDAO {
	
	@Override
	public int create(MiRna newMiRna) throws MiRnaException {
		DBConnection con = null;
		int res = -1;

		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna ("
					+ "name, accession_number, sub_name, provenance, chromosome,"
					+ "version, sequence, new_name) values "
					+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
			String queryString = String.format(queryTemplate, newMiRna.getName(), 
					newMiRna.getAccessionNumber(), newMiRna.getSubName(),
					newMiRna.getProvenance(), newMiRna.getChromosome(),
					newMiRna.getVersion(), newMiRna.getSequence(),
					newMiRna.getNewName());
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
			String queryString = "select * from mirna";
			list = con.query(queryString);
			miRnaList = new ArrayList<MiRna>();
			
			for (Map<String, Object> row : list) {
				MiRna miRna = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sub_name"),
						(String) row.get("provenance"),
						(String) row.get("chromosome"),
						(String) row.get("version"),
						(String) row.get("sequence"),
						(String) row.get("new_name"));

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
			String queryTemplate = "update mirna set name=%s, accession_number=%s, "
					+ "sub_name=%s, provenance=%s, chromosome=%s, "
					+ "version=%s, sequence=%s, new_name=%s where pk=%d";
			String queryString = String.format(queryTemplate,
					miRnaToUpdate.getName(), miRnaToUpdate.getAccessionNumber(),
					miRnaToUpdate.getSubName(), miRnaToUpdate.getProvenance(),
					miRnaToUpdate.getChromosome(), miRnaToUpdate.getVersion(),
					miRnaToUpdate.getSequence(), miRnaToUpdate.getNewName(),
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
			String queryTemplate = "delete from mirna where pk=%d";
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
			String queryTemplate = "select * from mirna where pk=%d";
			String queryString = String.format(queryTemplate, id);
			System.out.println(queryString);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				miRna = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sub_name"),
						(String) row.get("provenance"),
						(String) row.get("chromosome"),
						(String) row.get("version"),
						(String) row.get("sequence"),
						(String) row.get("new_name"));
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
			String queryTemplate = "select * from mirna where name='%s'";
			String queryString = String.format(queryTemplate, name);
			System.out.println(queryString);
			list = con.query(queryString);
			for (Map<String, Object> row : list) {
				res = new MiRna(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("accession_number"),
						(String) row.get("sub_name"),
						(String) row.get("provenance"),
						(String) row.get("chromosome"),
						(String) row.get("version"),
						(String) row.get("sequence"),
						(String) row.get("new_name"));
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
			list = con.query("select count(pk) from mirna");
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