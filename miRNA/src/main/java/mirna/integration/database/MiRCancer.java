package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.integration.beans.Disease;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.MiRna;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de miRNA cancer
 * http://mircancer.ecu.edu
 * 
 * @author María Jesús García Godoy
 *
 */
public class MiRCancer extends NewMirnaDatabase {
	
	private final static String TABLE_NAME = "miRCancer";
	
	public MiRCancer() throws MiRnaException { super(TABLE_NAME); }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					String mirId = tokens[0];
					String cancer = tokens[1];
					String profile = tokens[2];
					String pubMedArticle = tokens[3].replaceAll("'", "\\\\'");
					
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ mirId + "','"
							+ cancer + "','"
							+ profile + "','"
							+ pubMedArticle + "')";
					
					stmt.executeUpdate(query);
	
				}
	
			}
			fr.close();
			br.close();
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
		
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String cancer = rs.getString("cancer").trim();
		String mirId = rs.getString("mirId").trim();
		String evidence = rs.getString("profile").trim();
		String pubmedArticle = rs.getString("pubmed_article").trim();
		
		MiRna miRna = new MiRna();
		miRna.setName(mirId);
		
		Disease disease = new Disease();
		disease.setName(cancer);
		
		ExpressionData expressionData = new ExpressionData();
		expressionData.setDescription(pubmedArticle);
		expressionData.setEvidence(evidence);
		expressionData.setProvenance("miRCancer");
		
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
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		MiRCancer miRCancer = new MiRCancer();
		miRCancer.insertIntoSQLModel();
		
	}

}