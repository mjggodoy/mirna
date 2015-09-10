package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class microT_CDS_data extends MirnaDatabase {

	private final String tableName = "microt_cds";

	public microT_CDS_data() throws MiRnaException {
		super();
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
	public void insertIntoSQLModel() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Connection con = null;
		Transaction tx = session.beginTransaction();

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			stmt.setFetchSize(Integer.MIN_VALUE);

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of
			// using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the java resultset
			int count = 0;

			rs.next();
			rs.next();
			rs.next();
			// CAMBIAR ESTO:

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
			
			transcript.setGeneId(gene.getPk());
			
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
			
			count++;
			if (count%100==0) {
				System.out.println(count);
				session.flush();
		        session.clear();
			}

			stmt.close();
			tx.commit();
			
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}

	}

	public static void main(String[] args) throws Exception {

		microT_CDS_data microT_CDS_data = new microT_CDS_data();

		// String inputFile =
		// "/Users/esteban/Softw/miRNA/microalgo/microT_CDS_data.csv";
		// microT_CDS_data.insertInTable(inputFile);

		microT_CDS_data.insertIntoSQLModel();

	}

}
