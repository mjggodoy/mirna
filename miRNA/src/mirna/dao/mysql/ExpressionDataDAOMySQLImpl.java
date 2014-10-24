package mirna.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirna.beans.ExpressionData;
import mirna.dao.ExpressionDataDAO;
import mirna.db.DBConnection;
import mirna.db.mysql.DBConnectionMySQLImpl;
import mirna.exception.MiRnaException;

public class ExpressionDataDAOMySQLImpl extends ModelDAOMySQLImpl implements ExpressionDataDAO {
	
	@Override
	public int create(ExpressionData newDataExpression) throws MiRnaException {
		DBConnection con = null;
		int res = -1;
		String queryString = "";
		try {
			
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna.expression_data ("
					+ "title_reference, foldchange_min, foldchange_max,"
					+ "provenance_id, provenance, study_design, method,"
					+ "treatment, evidence, pubmed_id, year, description,"
					+ "cellular_line, condition_) values "
					+ "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',"
					+ "'%s', '%s', '%s', '%s', '%s', '%s')";
			queryString = String.format(queryTemplate, 
					newDataExpression.getTitleReference(),
					newDataExpression.getFoldchangeMin(),
					newDataExpression.getFoldchangeMax(),
					newDataExpression.getProvenanceId(),
					newDataExpression.getProvenance(),
					newDataExpression.getStudyDesign(),
					newDataExpression.getMethod(),
					newDataExpression.getTreatment(),
					newDataExpression.getEvidence(),
					newDataExpression.getPubmedId(),
					newDataExpression.getYear(),
					safe(newDataExpression.getDescription()),
					newDataExpression.getCellularLine(),
					newDataExpression.getCondition());
			queryString = queryString.replaceAll("'null'", "null");
			res = con.insert(queryString);
		} catch (SQLException ex) {
			System.err.println(queryString);
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		
		return res;
	}

	@Override
	public List<ExpressionData> readAll() throws MiRnaException {
		List<ExpressionData> dataExpressionList = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryString = "select * from mirna.expression_data";
			list = con.query(queryString);
			dataExpressionList = new ArrayList<ExpressionData>();
			
			for (Map<String, Object> row : list) {
				ExpressionData dataExpression = new ExpressionData(
						(Integer) row.get("pk"),
						(String) row.get("title_reference"),
						(String) row.get("foldchange_min"),
						(String) row.get("foldchange_max"),
						(String) row.get("provenance_id"),
						(String) row.get("provenance"),
						(String) row.get("study_design"),
						(String) row.get("method"),
						(String) row.get("treatment"),
						(String) row.get("evidence"),
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
	public void update(ExpressionData dataExpressionToUpdate) throws MiRnaException {
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			
			String queryTemplate = "update mirna.expression_data set title_reference=%s, "
					+ "foldchange_min=%s, foldchange_max=%s, provenance_id=%s, "
					+ "provenance=%s, study_design=%s, method=%s, treatment=%s,"
					+ "evidence=%s, pubmed_id=%s, year=%s, description=%s,"
					+ "cellular_line=%s, condition_=%s where pk=%d";
			String queryString = String.format(queryTemplate, 
					dataExpressionToUpdate.getTitleReference(),
					dataExpressionToUpdate.getFoldchangeMin(),
					dataExpressionToUpdate.getFoldchangeMax(),
					dataExpressionToUpdate.getProvenanceId(),
					dataExpressionToUpdate.getProvenance(),
					dataExpressionToUpdate.getStudyDesign(),
					dataExpressionToUpdate.getMethod(),
					dataExpressionToUpdate.getTreatment(),
					dataExpressionToUpdate.getEvidence(),
					dataExpressionToUpdate.getPubmedId(),
					dataExpressionToUpdate.getYear(),
					safe(dataExpressionToUpdate.getDescription()),
					dataExpressionToUpdate.getCellularLine(),
					dataExpressionToUpdate.getCondition(),
					dataExpressionToUpdate.getPk());
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
			String queryTemplate = "delete from mirna.expression_data where pk=%d";
			String queryString = String.format(queryTemplate, id);
			con.update(queryString);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

	@Override
	public ExpressionData findByPrimaryKey(int id) throws MiRnaException {
		ExpressionData dataExpression = null;
		DBConnection con = null;

		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			String queryTemplate = "select * from mirna.expression_data where pk=%d";
			String queryString = String.format(queryTemplate, id);
			list = con.query(queryString);
			
			if (list.size()==1) {
				Map<String, Object> row = list.get(0);
				dataExpression = new ExpressionData(
						(Integer) row.get("pk"),
						(String) row.get("title_reference"),
						(String) row.get("foldchange_min"),
						(String) row.get("foldchange_max"),
						(String) row.get("provenance_id"),
						(String) row.get("provenance"),
						(String) row.get("study_design"),
						(String) row.get("method"),
						(String) row.get("treatment"),
						(String) row.get("evidence"),
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
	public int findTotalNumber() throws MiRnaException {
		int total = -1;
		DBConnection con = null;
		try {
			con = new DBConnectionMySQLImpl();
			List<Map<String, Object>> list = null;
			list = con.query("select count(pk) from mirna.expression_data");
			Object o = list.get(0).get("C1");
			total = ((Long)o).intValue();
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
		return total;
	}
	
	public void newRelatedDisease(int dataExpressionId, int diseaseId) throws MiRnaException {
		DBConnection con = null;
		String queryString = "";
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna.expression_data_related_to_disease "
					+ "values (%d, %d)";
			queryString = String.format(queryTemplate, 
					dataExpressionId, diseaseId);
			con.update(queryString);
		} catch (SQLException ex) {
			System.err.println(queryString);
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}
	
	public void newMiRnaInvolved(int dataExpressionId, int miRnaId) throws MiRnaException {
		DBConnection con = null;
		String queryString = "";
		try {
			con = new DBConnectionMySQLImpl();
			String queryTemplate = "insert into mirna.expression_data_involves_mirna "
					+ "values (%d, %d)";
			queryString = String.format(queryTemplate, 
					dataExpressionId, miRnaId);
			con.update(queryString);
		} catch (SQLException ex) {
			System.err.println(queryString);
			throw new MiRnaException("SQLException:" + ex.getMessage());
		} finally {
			if (con!=null) con.closeDBConnection();
		}
	}

}