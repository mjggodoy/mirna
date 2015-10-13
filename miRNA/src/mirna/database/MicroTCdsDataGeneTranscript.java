package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Gene;
import mirna.beans.MiRna;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;



public class MicroTCdsDataGeneTranscript extends NewMirnaDatabase {

	private final static String TABLE_NAME = "microt_cds";

	public MicroTCdsDataGeneTranscript() throws MiRnaException {
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
		String geneName = rs.getString("gene_id");

		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcriptId);
		//session.save(transcript);

		//  Recupera su id. si ya existe
		
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", geneName))
				.uniqueResult();
		
		Gene gene = (Gene) oldGene;
					
		transcript.setGeneId(gene.getPk());
		session.save(transcript);

		
				
	}

}
