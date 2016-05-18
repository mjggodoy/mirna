package mirna.integration.database.microtcds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.integration.beans.Gene;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.beans.nToM.TranscriptHasGene;
import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MicroTCdsData extends NewMirnaDatabase {

	private final static String TABLE_NAME = "microt_cds";

	public MicroTCdsData() throws MiRnaException {
		super(TABLE_NAME);
		this.fetchSizeMin = true;
	}
	
	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null;
		String[] tokens2 = null;

		String query = "";

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();

			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);

			int count = 0;

			br.readLine();

			String transcript_id = null;
			String gene_id = null;
			String miRNA = null;
			String miTG_score = null;
			String region = null;
			// String location = null;
			String chromosome = null;
			String coordinates = null;

			while ((line = br.readLine()) != null) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, ",");

				if (line != null && !line.startsWith("UTR3")
						&& !line.startsWith("CDS")) {

					transcript_id = tokens[0].replaceAll("'", "\\\\'");
					gene_id = tokens[1].replaceAll("'", "\\\\'");
					miRNA = tokens[2].replaceAll("'", "\\\\'");
					miTG_score = tokens[3].replaceAll("'", "\\\\'");

				} else {

					region = tokens[0].replaceAll("'", "\\\\'");
					// location = tokens[1];

					tokens2 = StringUtils
							.splitPreserveAllTokens(tokens[1], ":");
					chromosome = tokens2[0].replaceAll("'", "\\\\'");
					coordinates = tokens2[1].replaceAll("'", "\\\\'");

					query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ transcript_id + "','" + gene_id + "','" + miRNA
							+ "','" + miTG_score + "','" + region + "','"
							+ chromosome + "','" + coordinates + "')";

					stmt.executeUpdate(query);

					chromosome = null;
					coordinates = null;

				}

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(query);
			if (line != null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con != null)
				con.close();
		}

	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String transcriptId = rs.getString("transcript_id");
		String geneId = rs.getString("gene_id");
		String miRNA = rs.getString("miRNA");
		String miTG_score = rs.getString("miTG_score");
		String region = rs.getString("region");
		String chromosome = rs.getString("chromosome");
		String coordinates = rs.getString("coordinates");

		MiRna miRna = new MiRna();
		miRna.setName(miRNA);

		Gene gene = new Gene();
		gene.setName(geneId);

		InteractionData id = new InteractionData();
		id.setMiTG_score(miTG_score);
		id.setProvenance("microT-CDS");

		Target target = new Target();
		target.setRegion(region);
		target.setChromosome(chromosome);
		target.setCoordinates(coordinates);

		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcriptId);

		/*
		 * System.out.println(miRna); System.out.println(gene);
		 * System.out.println(id); System.out.println(target);
		 * System.out.println(transcript);
		 */

		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name", miRna.getName()))
				.uniqueResult();
		if (oldMiRna == null) {
			session.save(miRna);
			session.flush(); // to get the PK
		} else {
			MiRna miRnaToUpdate = (MiRna) oldMiRna;
			miRnaToUpdate.update(miRna);
			session.update(miRnaToUpdate);
			miRna = miRnaToUpdate;
		}

		// Inserta gene (o recupera su id. si ya existe)
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", gene.getName()))
				.uniqueResult();
		if (oldGene == null) {
			session.save(gene);
			session.flush(); // to get the PK
		} else {
			Gene geneToUpdate = (Gene) oldGene;
			geneToUpdate.update(gene);
			session.update(geneToUpdate);
			gene = geneToUpdate;
		}
		
		// Inserta Transcript (o recupera su id. si ya existe)
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

		// Inserta Target
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK

		//Inserta nueva interactionData
		// (y la relaciona con el MiRna y Target correspondiente)
	
		id.setTarget_pk(target.getPk());
		id.setMirna_pk(miRna.getPk());
		id.setGene_pk(gene.getPk());
		session.save(id);
		session.flush();
		
	}
	
	public static void main(String[] args) throws Exception {

		MicroTCdsData microT_CDS_data = new MicroTCdsData();
		
		// /* 1. meter datos en mirna_raw */
		// String inputFile = "/Users/esteban/Softw/miRNA/microalgo/microT_CDS_data.csv";
		// microT_CDS_data.insertInTable(inputFile);
				
		/* 2. meter datos en mirna */
		microT_CDS_data.insertIntoSQLModel();

	}

}
