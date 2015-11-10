package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.Hairpin;
import mirna.beans.MiRna;
import mirna.beans.PubmedDocument;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasHairpin;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class Phenomir extends NewMirnaDatabase {

	private static final String TABLE_NAME = "phenomir";

	public Phenomir() throws MiRnaException { super(TABLE_NAME); }

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

					if (tokens.length<13) {
						count++;
						System.out.println(count);
						String line2 = br.readLine();
						if (line2.startsWith(",,")) line2 = line2.substring(2);
						line = line + "\t" + line2;
						tokens = StringUtils.splitPreserveAllTokens(line, "\t");
					}

					String phenomicid = tokens[0];
					String pmid = tokens[1];
					String disease = tokens[2];
					String diseasesubId = tokens[3];
					String class_ = tokens[4];
					String miRNA = tokens[5];
					String accession = tokens[6];
					String expression = tokens[7];
					String foldchangemin = tokens[8];
					String foldchangemax = tokens[9];
					String id = tokens[10];
					String name = tokens[11];
					String method = tokens[12];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ phenomicid + "','"
							+ pmid + "','"
							+ disease + "','"
							+ diseasesubId + "','"
							+ class_ + "','"
							+ miRNA + "','"
							+ accession + "','"
							+ expression + "','"
							+ foldchangemin + "','"
							+ foldchangemax + "','"
							+ id + "','"
							+ name + "','"
							+ method + "')";

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

	public String specificFileFix(String csvInputFile) throws IOException {

		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);

		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;

		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {

			if (line1.startsWith(",,")) {
				line0 = line0.trim() + "\t\t" + line1.substring(2);
				line1 = br.readLine();
			}

			pw.println(line0);
			line0 = line1;

		}

		if (line0!=null) pw.println(line0);

		br.close();
		pw.close();

		return newFile;
	}

	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String phenomicid = nullifyField(rs.getString("phenomicid"));
		String pmid = nullifyField(rs.getString("pmid"));
		String diseaseField = nullifyField(rs.getString("disease").toLowerCase().trim());
		String diseaseClass = nullifyField(rs.getString("class").toLowerCase().trim());
		String mirna = nullifyField(rs.getString("miRNA").trim());
		String accession = nullifyField(rs.getString("accession").toLowerCase().trim());
		String evidence = nullifyField(rs.getString("expression"));
		String foldchangemin = nullifyField(rs.getString("foldchangemin"));
		String foldchangemax = nullifyField(rs.getString("foldchangemax"));
		String studyDesign = nullifyField(rs.getString("name"));
		String method = nullifyField(rs.getString("method"));

		MiRna miRna = new MiRna();
		miRna.setName(mirna);
		//miRna.setAccessionNumber(accession);

		if (!createdObject(mirna)) { 
			miRna = null;
		}
		
		// TODOS LOS ACESSIONS SON DE HAIRPINS
		Hairpin hairpin = new Hairpin();
		hairpin.setAccession_number(accession);
		if (!createdObject(accession)) { 
			hairpin = null;
		}

		Disease disease = new Disease();
		disease.setName(diseaseField);
		disease.setDiseaseClass(diseaseClass);

		if (!createdObject(diseaseField, diseaseClass)) { 
			disease = null;
		}

		ExpressionData ed = new ExpressionData();
		ed.setProvenanceId(phenomicid);
		ed.setEvidence(evidence);
		ed.setFoldchangeMin(foldchangemin);
		ed.setFoldchangeMax(foldchangemax);
		ed.setStudyDesign(studyDesign);
		ed.setMethod(method);
		ed.setProvenance("PhenomiR");

		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pmid);

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

		if(miRna != null){
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

			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(miRna.getPk(), pubmedDoc.getPk());

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", miRna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}
			
			if (hairpin!=null) {
				session.save(hairpin);
				session.flush();
				
				MirnaHasHairpin mhh = new MirnaHasHairpin(miRna.getPk(), hairpin.getPk());
				session.save(mhh);
			}
			
		}

		if(disease != null){
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
		}

		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Disease correspondiente)
		ed.setMirnaPk(miRna.getPk());
		ed.setDiseasePk(disease.getPk());
		session.save(ed);
		session.flush(); // to get the PK
		// ExpressionData igual (?)

		ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
				new ExpressionDataHasPubmedDocument(ed.getPk(), pubmedDoc.getPk());

		// Relaciona PubmedDocument con ExpressionData
		session.save(expresDataHasPubmedDocument);

	}

	private String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "NULL".equals(field.trim()) ? null : field.trim();
	}


	public static void main(String[] args) throws Exception {

		Phenomir phenomir = new Phenomir();
		phenomir.insertIntoSQLModel();

	}

}