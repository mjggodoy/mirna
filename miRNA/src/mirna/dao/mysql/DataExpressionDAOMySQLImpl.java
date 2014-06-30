package mirna.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirna.dao.DataExpressionDAO;
import mirna.db.DBConnection;
import mirna.db.mysql.DBConnectionMySQLImpl;
import mirna.exception.MiRnaException;
import beans.DataExpression;

public class DataExpressionDAOMySQLImpl implements DataExpressionDAO {
	
	@Override
	public void create(DataExpression newDataExpression) throws MiRnaException {
		System.out.println("MIRNA: create began-----------.");
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into data_expression ("
					+ "expression, phenomic_id, foldchange_min, foldchange_max,"
					+ "id, study_design, method, treatment,"
					+ "support, profile, pubmed_id, year, description,"
					+ "cellular_line, condition_) values "
					+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',"
					+ "'%s', '%s', '%s', '%s', '%s', '%s', '%s')";
			String queryString = String.format(queryTemplate, 
					newDataExpression.getExpression(),
					newDataExpression.getPhenomicId(),
					newDataExpression.getFoldchangeMin(),
					newDataExpression.getFoldchangeMax(),
					newDataExpression.getId(),
					newDataExpression.getStudyDesign(),
					newDataExpression.getMethod(),
					newDataExpression.getTreatment(),
					newDataExpression.getSupport(),
					newDataExpression.getProfile(),
					newDataExpression.getPubmedId(),
					newDataExpression.getYear(),
					newDataExpression.getDescription(),
					newDataExpression.getCellularLine(),
					newDataExpression.getCondition());
			System.out.println(queryString);
			queryString = queryString.replaceAll("'null'", "null");
			System.out.println(queryString);
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public DataExpression read(int id) throws MiRnaException {
		System.out.println("MIRNA: readRec began-----------.");
		DataExpression dataExpression = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from data_expression where pk=%d";
			String queryString = String.format(queryTemplate, id);
			System.out.println(queryString);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				dataExpression = new DataExpression(
						(Integer) row.get("pk"),
						(String) row.get("expression"),
						(String) row.get("phenomic_id"),
						(String) row.get("foldchange_min"),
						(String) row.get("foldchange_max"),
						(String) row.get("id"),
						(String) row.get("study_design"),
						(String) row.get("method"),
						(String) row.get("treatment"),
						(String) row.get("suuport"),
						(String) row.get("profile"),
						(String) row.get("pubmed_id"),
						(String) row.get("year"),
						(String) row.get("description"),
						(String) row.get("cellular_line"),
						(String) row.get("condition_"));
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return dataExpression;
	}
	
	@Override
	public List<DataExpression> readAll() throws MiRnaException {
		System.out.println("MIRNA: readRec began-----------.");
		List<DataExpression> dataExpressionList = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryString = "select * from data_expression";
			list = con.query(queryString);
			dataExpressionList = new ArrayList<DataExpression>();
			
			for (Map<String, Object> row : list) {
				DataExpression dataExpression = new DataExpression(
						(Integer) row.get("pk"),
						(String) row.get("expression"),
						(String) row.get("phenomic_id"),
						(String) row.get("foldchange_min"),
						(String) row.get("foldchange_max"),
						(String) row.get("id"),
						(String) row.get("study_design"),
						(String) row.get("method"),
						(String) row.get("treatment"),
						(String) row.get("suuport"),
						(String) row.get("profile"),
						(String) row.get("pubmed_id"),
						(String) row.get("year"),
						(String) row.get("description"),
						(String) row.get("cellular_line"),
						(String) row.get("condition_"));

				dataExpressionList.add(dataExpression);
			}
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return dataExpressionList;
	}

	@Override
	public void update(DataExpression dataExpressionToUpdate) throws MiRnaException {
		System.out.println("MIRNA: updateRec began-----------.");
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			
			String queryTemplate = "update data_expression set expression=%s,"
					+ "phenomic_id=%s, foldchange_min=%s, foldchange_max=%s,"
					+ "id=%s, study_design=%s, method=%s, treatment=%s,"
					+ "support=%s, profile=%s, pubmed_id=%s, year=%s, description=%s,"
					+ "cellular_line=%s, condition_=%s where pk=%d";
			String queryString = String.format(queryTemplate, 
					dataExpressionToUpdate.getExpression(),
					dataExpressionToUpdate.getPhenomicId(),
					dataExpressionToUpdate.getFoldchangeMin(),
					dataExpressionToUpdate.getFoldchangeMax(),
					dataExpressionToUpdate.getId(),
					dataExpressionToUpdate.getStudyDesign(),
					dataExpressionToUpdate.getMethod(),
					dataExpressionToUpdate.getTreatment(),
					dataExpressionToUpdate.getSupport(),
					dataExpressionToUpdate.getProfile(),
					dataExpressionToUpdate.getPubmedId(),
					dataExpressionToUpdate.getYear(),
					dataExpressionToUpdate.getDescription(),
					dataExpressionToUpdate.getCellularLine(),
					dataExpressionToUpdate.getCondition(),
					dataExpressionToUpdate.getId());
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public void delete(int id) throws MiRnaException {
		System.out.println("MIRNA: deleteRec began-----------.");
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "delete from data_expression where pk=%d";
			String queryString = String.format(queryTemplate, id);
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public boolean findByPrimaryKey(int id) throws MiRnaException {
		System.out.println("MIRNA: findByPrimaryKey began-----------.");
		boolean result = false;
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from data_expression where pk=%d";
			String queryString = String.format(queryTemplate, id);
			list = con.query(queryString);
			result = (list.size()>0);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return result;
	}

	@Override
	public int findTotalNumber() throws MiRnaException {
		System.out.println("MIRNA: findTotalNumber began-----------.");
		int total = -1;
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			list = con.query("select count(pk) from data_expression");
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