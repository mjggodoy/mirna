package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.SNP;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.SnpHasDisease;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3 extends MiRdSNP {

	private static final String TABLE_NAME = "miRdSNP3";

	public MiRdSNP3() throws MiRnaException { super(TABLE_NAME); }

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

	public void processRow(Session session, ResultSet rs) throws Exception{

			String gene_name = rs.getString("gene").toLowerCase().trim();
			String ref_seq = rs.getString("refseq").toLowerCase().trim();
			String mirna_name = rs.getString("miR").toLowerCase().trim();
			String snp_id = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("diseases").toLowerCase().trim();
			String distance = rs.getString("distance").toLowerCase().trim();// I'm not going to use this field! 

			Disease disease = new Disease();
			disease.setName(disease_name);
			
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

			}

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
			
			transcript.setGeneId(gene.getPk());
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

			snp.setGene_pk(gene.getPk());		
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

			target.setTranscript_pk(transcript.getPk());
			session.save(target);
			session.flush(); // to get the PK
			
			// Relaciona SNP y Disease
			// Relaciona SNP y Gene_id

			SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
			Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
					.add( Restrictions.eq("snpPk", snp.getPk()) )
					.add( Restrictions.eq("diseasePk", disease.getPk()) )
					.uniqueResult();
			if (oldSnphasDisease==null) {
				session.save(snpHasDisease);
			}

			ed.setMirnaPk(mirna.getPk());
			ed.setDiseasePk(disease.getPk());
			session.save(ed);
			id.setMirna_pk(mirna.getPk());
			id.setGene_pk(gene.getPk());
			id.setTarget_pk(target.getPk());
			id.setExpression_data_pk(ed.getPk());
			session.save(id);
	}

}