package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.PubmedDocument;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class miRDB extends MirnaDatabase {

	private final String tableName = "miRDB";

	public miRDB() throws MiRnaException { super(); }

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
	public void insertIntoSQLModel() throws Exception {

		Statement stmt = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Connection con = null;
		Transaction tx = session.beginTransaction();

		try {
			System.out.println(dbUrl);
			System.out.println(dbUser);
			System.out.println(dbPassword);
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			stmt = (Statement) con.createStatement();
			stmt.setFetchSize(Integer.MIN_VALUE);

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of
			// using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);

			int count = 0;
			rs.next();

			String miRNA = rs.getString("mir");
			String target_name = rs.getString("target");
			String score = rs.getString("score");

			InteractionData id = new InteractionData();
			id.setScore(score);
			id.setProvenance("Microcosm");

			Target target = new Target();

			Transcript transcript = new Transcript();
			transcript.setTranscriptID(target_name);

			MiRna miRna = new MiRna();
			miRna.setName(miRNA);

		
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

			target.setTranscript_pk(transcript.getPk());
			session.save(target);
			session.flush(); // to get the PK

			// Inserta nueva InteractinData
			// (y la relaciona con el MiRna y Target correspondientes)

			id.setMirna_pk(miRna.getPk());
			id.setTarget_pk(target.getPk());
			session.save(id);
			session.flush(); // to get the PK

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

		miRDB mirdb = new miRDB();

		//String inputFile = "/Users/esteban/Softw/miRNA/MirTarget2_v4.0_prediction_result.txt";
		//mirdb.insertInTable(inputFile);

		mirdb.insertIntoSQLModel();

	}

}