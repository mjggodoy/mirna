package mirna.integration.database.mirdSNP;

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
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.Gene;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.SNP;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.beans.nToM.SnpHasDisease;
import mirna.integration.beans.nToM.SnpHasGene;
import mirna.integration.beans.nToM.TranscriptHasGene;
import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3 extends NewMirnaDatabase {

	private final static String TABLE_NAME = "miRdSNP3";

	public MiRdSNP3() throws MiRnaException {
		super(TABLE_NAME); 
		this.fetchSizeMin = true;

	}

	protected String quitarComillas(String token) {
		if (token.startsWith("\"") && token.endsWith("\"")) {
			token = token.substring(1, token.length()-1);
		}
		return token;
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

				String geneName = tokens[0];
				String refseqId = tokens[1];
				String miR = tokens[2];
				String SNP = tokens[3];
				String diseases = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];

				String expConf = "";//tokens[6];

				if (tokens.length==7) {
					expConf = tokens[6];

					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}

				if (tokens.length>7) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ geneName + "','"
						+ refseqId + "','"
						+ miR + "','"
						+ SNP + "','"
						+ diseases + "','"
						+ distance + "','"
						+ expConf + "')";

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

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String gene_name = rs.getString("gene").toLowerCase().trim();
		String ref_seq = rs.getString("refseq").toLowerCase().trim();
		String mirna_name = rs.getString("miR").toLowerCase().trim();
		String snp_id = rs.getString("snp").toLowerCase().trim();
		String disease_name = rs.getString("diseases").toLowerCase().trim();
		@SuppressWarnings("unused") // I'm not going to use this field!
		String distance = rs.getString("distance").toLowerCase().trim();

		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");
		List<Disease> diseaseList = new ArrayList<Disease>();

		for (String token : diseaseTokens) {

			Disease disease = new Disease();
			disease.setName(token);
			diseaseList.add(disease);
		}

		Transcript transcript = new Transcript();
		transcript.setTranscriptID(ref_seq);

		Gene gene = new Gene();
		gene.setName(gene_name);
		//gene.setAccessionumber(ref_seq);

		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);

		Target target = new Target();

		SNP snp = new SNP();
		snp.setSnp_id(snp_id);

		InteractionData id = new InteractionData();
		id.setProvenance("miRdSNP");

		ExpressionData ed = new ExpressionData();
		ed.setProvenance("miRdSNP");

		Object oldGene = session.createCriteria(Gene.class)
				.add( Restrictions.eq("name", gene.getName()) )
				.uniqueResult();
		if (oldGene==null) {
			session.save(gene);
			session.flush();  // to get the PK
		} else {
			Gene geneToUpdate = (Gene) oldGene;
			geneToUpdate.update(gene);
			session.update(geneToUpdate);
			gene = geneToUpdate;
		}

		Object oldTranscript = session.createCriteria(Transcript.class)
				.add(Restrictions.eq("transcriptID", transcript.getTranscriptID()))
				.uniqueResult();
		if (oldTranscript == null) {
			session.save(transcript);
			session.flush(); // to get the PK
		} else {
			Transcript transcriptToUpdate = (Transcript) oldTranscript;
			transcriptToUpdate.update(transcript);
			session.update(transcriptToUpdate);
			transcript = transcriptToUpdate;
		}
		
		TranscriptHasGene transcripthasGene =
				new TranscriptHasGene(transcript.getPk(), gene.getPk());
		Object oldTranscripthasGene = session.createCriteria(TranscriptHasGene.class)
				.add(Restrictions.eq("transcriptPk", transcript.getPk()))
				.add(Restrictions.eq("genePk", gene.getPk()))
				.uniqueResult();
		if (oldTranscripthasGene == null) {
	        session.save(transcripthasGene);
		}

		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", mirna.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(mirna);
			session.flush();  // to get the PK
		} else {

			MiRna mirnaToUpdate = (MiRna) oldMiRna;
			mirnaToUpdate.update(mirna);
			session.update(mirnaToUpdate);
			mirna = mirnaToUpdate;
		}

		Object oldSnp = session.createCriteria(SNP.class)
				.add( Restrictions.eq("snp_id", snp.getSnp_id()))
				.uniqueResult();
		if (oldSnp==null) {
			session.save(snp);
			session.flush();  // to get the PK
		} else {
			SNP snpToUpdate = (SNP) oldSnp;
			snpToUpdate.update(snp);
			session.update(snpToUpdate);
			snp = snpToUpdate;
		}
		
		SnpHasGene snpHasGene = new SnpHasGene(snp.getPk(), gene.getPk());
		Object oldSnphasGene = session.createCriteria(SnpHasDisease.class)
				.add( Restrictions.eq("snpPk", snp.getPk()) )
				.add( Restrictions.eq("genePk", gene.getPk()) )
				.uniqueResult();
		if (oldSnphasGene==null) {
			session.save(snpHasGene);
		}

		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK

		// Relaciona SNP y Disease
		// Relaciona SNP y Gene_id

		for (Disease disease : diseaseList) {

			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush();  // to get the PK
				System.out.println("Salvo ESTE disease:");
				System.out.println(snp);
			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
				System.out.println("Recupero ESTE disease:");
				System.out.println(snp);
				
				SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
				Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
						.add( Restrictions.eq("snpPk", snp.getPk()) )
						.add( Restrictions.eq("diseasePk", disease.getPk()) )
						.uniqueResult();
				if (oldSnphasDisease==null) {
					session.save(snpHasDisease);
				}

				id.setMirna_pk(mirna.getPk());
				id.setGene_pk(gene.getPk());
				id.setTarget_pk(target.getPk());
				session.save(id);
				session.flush(); // to get the ed pk
				
				ed.setMirnaPk(mirna.getPk());
				ed.setDiseasePk(disease.getPk());
				ed.setInteraction_data_pk(id.getPk());
				session.save(ed);

			}
		}
	}

}