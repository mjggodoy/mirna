package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.integration.beans.Disease;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasPubmedDocument;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de HMDD
 * 
 * @author Esteban López Camacho
 *
 */
public class HMDD extends NewMirnaDatabase {
	
	private static final String TABLE_NAME = "hmdd";
	
	public HMDD() throws MiRnaException { super(TABLE_NAME); }
	
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
	protected void processRow(Session session, ResultSet rs) throws Exception {
		String id = rs.getString("id");
		String mir = rs.getString("mir").trim();
		String diseaseField = rs.getString("disease").toLowerCase().trim();
		String pubmedid = rs.getString("pubmedid").trim();
		String description = rs.getString("description").trim();
		
		MiRna miRna = new MiRna();
		miRna.setName(mir);
		
		Disease disease = new Disease();
		disease.setName(diseaseField);
		
		ExpressionData expressionData = new ExpressionData();
		expressionData.setDescription(description);
		expressionData.setProvenanceId(id);
		expressionData.setProvenance("HMDD");
		
		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pubmedid);
		
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
		
		// Inserta PubmedDocument (o recupera su id. si ya existe)
		Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
				.add( Restrictions.eq("id", pubmedDoc.getId()) )
				.uniqueResult();
		if (oldPubmedDoc==null) {
			session.save(pubmedDoc);
			session.flush(); // to get the PK
		} else {
			PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
			pubmedDocToUpdate.update(pubmedDoc);
			session.update(pubmedDocToUpdate);
			pubmedDoc = pubmedDocToUpdate;
		}
		
		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Disease correspondiente)
		expressionData.setMirnaPk(miRna.getPk());
		expressionData.setDiseasePk(disease.getPk());
		session.save(expressionData);
		session.flush(); // to get the PK
		// ExpressionData igual (?)
		
		MirnaHasPubmedDocument mirnaHasPubmedDocument =
				new MirnaHasPubmedDocument(miRna.getPk(), pubmedDoc.getPk());
		ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
				new ExpressionDataHasPubmedDocument(expressionData.getPk(), pubmedDoc.getPk());
		
		// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
		Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
				.add( Restrictions.eq("mirnaPk", miRna.getPk()) )
				.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
				.uniqueResult();
		if (oldMirnaHasPubmedDocument==null) {
			session.save(mirnaHasPubmedDocument);
		}
		
		// Relaciona PubmedDocument con ExpressionData
		session.save(expresDataHasPubmedDocument);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		HMDD hmdd = new HMDD();
		hmdd.insertIntoSQLModel();
		
	}

}