package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de HMDD
 * 
 * @author Esteban López Camacho
 *
 */
public class HMDD implements IMirnaDatabase {
	
	private final String tableName = "hmdd";
	
	public HMDD() { }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					String id = tokens[0];
					String mir = tokens[1];
					String disease = tokens[2].replaceAll("'", "\\\\'");
					String pubmedid = tokens[3];
					String description = tokens[4].replaceAll("'", "\\\\'");

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ id + "','"
							+ mir + "','"
							+ disease + "','" 
							+ pubmedid + "','"
							+ description + "')";
					
					stmt.executeUpdate(query);
	
				}
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	@Override
	public void insertIntoSQLModel() throws Exception {
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";

		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		//start transaction
        Transaction tx = session.beginTransaction();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			while (rs.next()) {
				
				String id = rs.getString("id");
				String mir = rs.getString("mir").toLowerCase().trim();
				String diseaseField = rs.getString("disease").toLowerCase().trim();
				String pubmedid = rs.getString("pubmedid").trim();
				String description = rs.getString("description").trim();
				
				MiRna miRna = new MiRna();
				miRna.setName(mir);
				
				Disease disease = new Disease();
				disease.setName(diseaseField);
				
				ExpressionData expressionData = new ExpressionData();
				expressionData.setDescription(description);
				expressionData.setPubmedId(pubmedid);
				expressionData.setProvenanceId(id);
				expressionData.setProvenance("hmdd");
				
				// Inserta MiRna (o recupera su id. si ya existe)
				Object oldMiRna = session.createCriteria(MiRna.class)
						.add( Restrictions.eq("name", miRna.getName()) )
						.uniqueResult();
				if (oldMiRna==null) {
					session.save(miRna);
					session.flush();  // to get the PK
				} else {
					MiRna miRnaToUpdate = (MiRna) oldMiRna;
					miRnaToUpdate.update(miRna);
					session.update(miRnaToUpdate);
					miRna = miRnaToUpdate;
				}
				
				// Inserta Disease (o recupera su id. si ya existe)
				Object oldDisease = session.createCriteria(Disease.class)
						.add( Restrictions.eq("name", disease.getName()) )
						.uniqueResult();
				if (oldDisease==null) {
					session.save(disease);
					session.flush(); // to get the PK
				} else {
					Disease diseaseToUpdate = (Disease) oldDisease;
					diseaseToUpdate.update(disease);
					session.update(diseaseToUpdate);
					disease = diseaseToUpdate;
				}
				
				// Inserta nueva DataExpression
				// (y la relaciona con el MiRna y Disease correspondiente)
				expressionData.setMirnaPk(miRna.getPk());
				expressionData.setDiseasePk(disease.getPk());
				session.save(expressionData);
				// ExpressionData igual (?)
				
				count++;
				if (count%100==0) {
					System.out.println(count);
					session.flush();
			        session.clear();
				}
				
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
		tx.commit();
		sessionFactory.close();
		
	}

	
	public static void main(String[] args) throws Exception {
		
		HMDD hmdd = new HMDD();
		
		// String inputFile = "/Users/esteban/Softw/miRNA/hmdd/alldata.txt";
		// hmdd.insertInTable(inputFile);
		
		hmdd.insertIntoSQLModel();
		
	}

}