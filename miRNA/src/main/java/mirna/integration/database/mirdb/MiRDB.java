package mirna.integration.database.mirdb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRDB extends NewMirnaDatabase {

	private final static String TABLE_NAME = "miRDB";

	public MiRDB() throws MiRnaException {
		super(TABLE_NAME);
		this.fetchSizeMin = true;
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

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String accesionNumber = tokens[0];
				String target = tokens[1];
				String score = tokens[2];

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ accesionNumber + "','"
						+ target + "','"
						+ score + "')";

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
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String miRNA = rs.getString("mir");
		String target_name = rs.getString("target");
		String score = rs.getString("score");

		InteractionData id = new InteractionData();
		id.setScore(score);
		id.setProvenance("miRDB");
		
		Target target = new Target();
		
		Transcript transcript = new Transcript();
		transcript.setName(target_name);
		
		MiRna miRna = new MiRna();
		miRna.setName(miRNA);
		
		// Inserta el gene (o recupera su id. si ya existe)
		
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

		
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK
		
		// Inserta nueva InteractinData
		// (y la relaciona con el MiRna y Target correspondientes)

		id.setMirna_pk(miRna.getPk());
		id.setTarget_pk(target.getPk());
		session.save(id);
		session.flush(); // to get the PK
		
	}
	
	public static void main(String[] args) throws Exception {

		MiRDB mirdb = new MiRDB();

		// /* 1. meter datos en mirna_raw */
		//String inputFile = "/Users/esteban/Softw/miRNA/MirTarget2_v4.0_prediction_result.txt";
		//mirdb.insertInTable(inputFile);

		/* 2. meter datos en mirna */
		mirdb.insertIntoSQLModel();

	}

}