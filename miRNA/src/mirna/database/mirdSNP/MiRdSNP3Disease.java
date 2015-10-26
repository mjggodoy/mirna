package mirna.database.mirdSNP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mirna.beans.Disease;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3Disease extends MiRdSNP3 {

	public MiRdSNP3Disease() throws MiRnaException {
		super();
		super.selectQuery = "select distinct TRIM(diseases) from mirna_raw.miRdSNP3";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String disease_name = rs.getString("TRIM(diseases)");
		disease_name.trim();
		
		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");
		List<String> diseaseList1 = new ArrayList<String>();
		Set<String> diseaseList2 = new HashSet<String>();

		
		for (String token : diseaseTokens) {

			//System.out.println(token);
			diseaseList1.add(token.trim().toLowerCase());
			
		}
	
		diseaseList2.addAll(diseaseList1);
		diseaseList1.clear();
		diseaseList1.addAll(diseaseList2);

	
		for(String disease  : diseaseList1){
			
			System.out.println(disease);

	
		}
		
	}
	
	protected List<String> processRow(ResultSet rs) throws Exception {
		
		String disease_name = rs.getString("TRIM(diseases)");
		disease_name.trim();
		
		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");
		List<String> diseaseList1 = new ArrayList<String>();
		
		for (String token : diseaseTokens) {
			diseaseList1.add(token.trim().toLowerCase());
		}
	
		return diseaseList1;
		
	}
	
	@Override
	public void insertIntoSQLModel() throws Exception {
		
		Connection con = null;
		
		// Get session
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//start transaction
		Transaction tx = session.beginTransaction();
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			if (fetchSizeMin) stmt.setFetchSize(Integer.MIN_VALUE);
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = String.format(selectQuery, tableName);
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			//System.out.println("EMPIEZO EN LA PRIMERA LINEA");
			
			Set<String> diseases = new HashSet<String>();
			
			while (count<ROWS_TO_READ && rs.next()) {
				
				diseases.addAll(processRow(rs));
				
				count++;
				if (count%1000==0) {
					System.out.println(tableName + ": " + count);
				}
				
			}
			
			stmt.close();
			
			count = 0;
			for (String diseaseName : diseases) {
				Disease disease = new Disease();
				disease.setName(diseaseName);
				session.save(disease);
				
				count++;
				if (count%1000==0) {
					System.out.println(tableName + ": " + count);
					session.flush();
			        session.clear();
				}
			}
			
			tx.commit();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			//if (con!=null) con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}
	}
	
	
	
	
	public static void main(String[] args) throws Exception {

		MiRdSNP3Disease mirdb = new MiRdSNP3Disease();
		mirdb.insertIntoSQLModel();

	}

}