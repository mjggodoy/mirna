package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.integration.beans.Disease;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.SNP;
import mirna.integration.beans.nToM.SnpHasDisease;
import mirna.integration.beans.nToM.SnpHasPubmedDocument;
import mirna.integration.exception.MiRnaException;

/**
 * Código para procesar los datos de miRdSNP
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP1 extends MiRdSNP {

	private static final String TABLE_NAME = "miRdSNP1";

	public MiRdSNP1() throws MiRnaException { 
		super(TABLE_NAME); 
	}

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

			while (((line = br.readLine()) != null)) {

				count++;
				System.out.println(count);

				//tokens = StringUtils.splitPreserveAllTokens(line, ",");
				tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				for (int i=0; i<tokens.length; i++) {
					tokens[i] = quitarComillas(tokens[i]);
				}

				String pubmedid = tokens[0];
				String year = tokens[1];
				String month = tokens[2];
				String article_date = tokens[3];
				String journal = tokens[4].replaceAll("'", "\\\\'");
				String title = tokens[5].replaceAll("'", "\\\\'");
				String snpId = tokens[6];
				String disease = tokens[7].replaceAll("'", "\\\\'");
				String link = tokens[8];

				if (tokens.length>9) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ pubmedid + "','"
						+ year + "','"
						+ month + "','"
						+ article_date + "','"
						+ journal + "','"
						+ title + "','"
						+ snpId + "','"
						+ disease + "','"
						+ link + "')";
				stmt.executeUpdate(query);
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

	public void  processRow(Session session, ResultSet rs) throws Exception {

		String pubmedId = nullifyField(rs.getString("pubmed_id").toLowerCase().trim());
		@SuppressWarnings("unused") // I'm not going to use this field.
		String year = nullifyField(rs.getString("year").toLowerCase().trim());
		@SuppressWarnings("unused") // I'm not going to use this field.
		String journal = nullifyField(rs.getString("journal").toLowerCase().trim());
		String description = nullifyField(rs.getString("title").toLowerCase().trim());
		String snp_id = nullifyField(rs.getString("snp_id").toLowerCase().trim());
		String disease_name = nullifyField(rs.getString("disease").toLowerCase().trim());
		String resource = nullifyField(rs.getString("link").toLowerCase().trim());

		Disease disease = new Disease();
		disease.setName(disease_name);
		if (!createdObject(disease_name)) {
			disease = null;
		}

		String[] snpTokens = StringUtils.splitPreserveAllTokens(snp_id, ",");

		List<SNP> snpList = new ArrayList<SNP>();

		for (String token : snpTokens) {
			SNP snp = new SNP();
			snp.setSnp_id(token);
			snpList.add(snp);
			if (!createdObject(token)) {
				snp = null;

			}
		}

		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pubmedId);
		pubmedDoc.setDescription(description);
		pubmedDoc.setResource(resource);
		if (!createdObject(pubmedId, description, resource )) {
			pubmedDoc = null;

		}


		// Inserta Disease (o recupera su id. si ya existe)

		if(disease !=null){
			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush();  // to get the PK
			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
			}
		}

		if(pubmedDoc !=null){
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
		}

		// Inserta SNP (o recupera su id. si ya existe)

		for(SNP snp : snpList){

			if(snp != null){

				Object oldSnp = session.createCriteria(SNP.class)
						.add( Restrictions.eq("snp_id", snp.getSnp_id()) )
						.uniqueResult();
				if (oldSnp==null) {

					session.save(snp); // mutation_pk puede ser nulo
					session.flush();  // to get the PK
					//System.out.println("SALVO ESTE SNP:");
					//System.out.println(snp);

				} else {
					SNP snptoUpdate = (SNP) oldSnp;
					snptoUpdate.update(snp); //incluir el update en la clase SNP
					session.update(snptoUpdate);
					snp = snptoUpdate;
				}

				// Relaciona SNP y Disease
				if(disease!= null){
					SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
					Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
							.add( Restrictions.eq("snpPk", snp.getPk()) )
							.add( Restrictions.eq("diseasePk", disease.getPk()) )
							.uniqueResult();
					if (oldSnphasDisease==null) {
						session.save(snpHasDisease);
					}
				}

				// Relaciona SNP y Pubmed document
				if(pubmedDoc !=null){
					SnpHasPubmedDocument snpHasPubmedDocument = new SnpHasPubmedDocument(snp.getPk(), pubmedDoc.getPk());
					Object oldSnpHasPubmedDocument = session.createCriteria(SnpHasPubmedDocument.class)
							.add( Restrictions.eq("snpPk", snp.getPk()) )
							.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
							.uniqueResult();
					if (oldSnpHasPubmedDocument==null) {
						session.save(snpHasPubmedDocument);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		MiRdSNP1 mirdSNP1 = new MiRdSNP1();
		mirdSNP1.insertIntoSQLModel();

	}

}