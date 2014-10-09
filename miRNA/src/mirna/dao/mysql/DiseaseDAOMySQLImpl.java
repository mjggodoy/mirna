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

public class DiseaseDAOMySQLImpl implements DiseaseDAO {
	
	@Override
	public int create(Disease newDisease) throws MiRnaException {
		DBConnection con = null;
		int res = -1;

		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into disease ("
					+ "name, disease_sub, disease_class, phenomic_id, description,"
					+ "pubmed_id, tissue) values "
					+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s')";
			String queryString = String.format(queryTemplate, newDisease.getName(), 
					newDisease.getDiseaseSub(), newDisease.getDiseaseClass(),
					newDisease.getPhenomicId(), newDisease.getDescription(),
					newDisease.getPubmedId(), newDisease.getTissue());
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
			String queryString = "select * from disease";
			list = con.query(queryString);
			diseaseList = new ArrayList<Disease>();
			
			for (Map<String, Object> row : list) {
				Disease disease = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_sub"),
						(String) row.get("disease_class"),
						(String) row.get("phenomic_id"),
						(String) row.get("description"),
						(String) row.get("pubmed_id"),
						(String) row.get("tissue"));

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
			String queryTemplate = "update disease set name=%s, disease_sub=%s, "
					+ "disease_class=%s, phenomic_id=%s, description=%s, "
					+ "pubmed_id=%s, tissue=%s, where pk=%d";
			String queryString = String.format(queryTemplate,
					diseaseToUpdate.getName(), diseaseToUpdate.getDiseaseSub(),
					diseaseToUpdate.getDiseaseClass(), diseaseToUpdate.getPhenomicId(),
					diseaseToUpdate.getDescription(), diseaseToUpdate.getPubmedId(),
					diseaseToUpdate.getTissue(), diseaseToUpdate.getPk());
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
			String queryTemplate = "delete from disease where pk=%d";
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
			String queryTemplate = "select * from disease where pk=%d";
			String queryString = String.format(queryTemplate, id);
			System.out.println(queryString);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				disease = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_sub"),
						(String) row.get("disease_class"),
						(String) row.get("phenomic_id"),
						(String) row.get("description"),
						(String) row.get("pubmed_id"),
						(String) row.get("tissue"));
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
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from disease where name='%s'";
			String queryString = String.format(queryTemplate, name);
			System.out.println(queryString);
			list = con.query(queryString);
			for (Map<String, Object> row : list) {
				res = new Disease(
						(Integer) row.get("pk"),
						(String) row.get("name"),
						(String) row.get("disease_sub"),
						(String) row.get("disease_class"),
						(String) row.get("phenomic_id"),
						(String) row.get("description"),
						(String) row.get("pubmed_id"),
						(String) row.get("tissue"));
				diseaseList.add(res);
			}
			if (diseaseList.size()>1) {
				throw new MiRnaException("Found two Disease with the same name (" + name + ")");
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
			list = con.query("select count(pk) from disease");
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