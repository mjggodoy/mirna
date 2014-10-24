package mirna.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirna.beans.Disease;
import mirna.dao.DiseaseDAO;
import mirna.db.DBConnection;
import mirna.db.mysql.DBConnectionMySQLImpl;
import mirna.exception.MiRnaException;

public class DiseaseDAOMySQLImpl extends ModelDAOMySQLImpl implements DiseaseDAO {
	
	@Override
	public int create(Disease newDisease) throws MiRnaException {
		DBConnection con = null;
		int res = -1;

		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna.disease ("
					+ "name, disease_class) values ('%s', '%s')";
			String queryString = String.format(queryTemplate,
					safe(newDisease.getName()), 
					safe(newDisease.getDiseaseClass()));
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
	public List<Disease> readAll() throws MiRnaException {
		List<Disease> diseaseList = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryString = "select * from mirna.disease";
			list = con.query(queryString);
			diseaseList = new ArrayList<Disease>();
			
			for (Map<String, Object> row : list) {
				Disease disease = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_class"));

				diseaseList.add(disease);
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return diseaseList;
	}

	@Override
	public void update(Disease diseaseToUpdate) throws MiRnaException {
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "update mirna.disease set name=%s, "
					+ "disease_class=%s where pk=%d";
			String queryString = String.format(queryTemplate,
					safe(diseaseToUpdate.getName()),
					safe(diseaseToUpdate.getDiseaseClass()),
					diseaseToUpdate.getPk());
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
			String queryTemplate = "delete from mirna.disease where pk=%d";
			String queryString = String.format(queryTemplate, id);
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public Disease findByPrimaryKey(int id) throws MiRnaException {
		Disease disease = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from mirna.disease where pk=%d";
			String queryString = String.format(queryTemplate, id);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				disease = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_class"));
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return disease;
	}

	@Override
	public Disease findByName(String name) throws MiRnaException {
		List<Disease> diseaseList = new ArrayList<Disease>();
		Disease res = null;
		DBConnection con = null;
		String queryString = "";
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from mirna.disease where name='%s'";
			queryString = String.format(queryTemplate, safe(name));
			list = con.query(queryString);
			for (Map<String, Object> row : list) {
				res = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_class"));
				diseaseList.add(res);
			}
			if (diseaseList.size()>1) {
				throw new MiRnaException("Found two Disease with the same name (" + name + ")");
			}
		} catch (SQLException ex) {
			System.err.println(queryString);
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
			list = con.query("select count(pk) from mirna.disease");
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