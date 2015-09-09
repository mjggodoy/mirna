package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.ExpressionData;
import mirna.beans.Hairpin;
import mirna.beans.InteractionData;
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Sequence;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class PlantMirnaStemLoop extends MirnaDatabase {

	private final String tableName = "plant_mirna_stem_loop";

	public PlantMirnaStemLoop() throws MiRnaException {
		super();
	}

	@Override
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

			String specie = "", mirnaid = "";

			while (((line = br.readLine()) != null)) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\n");

				if (line.startsWith(">")) {

					String specie1 = tokens[0];
					int index1 = specie1.indexOf(">");
					int index2 = specie1.indexOf("-");
					specie = specie1.substring(index1 + 1, index2);
					mirnaid = specie1.substring(index2 + 1);

					// String query = "INSERT INTO " + tableName +
					// " VALUES (NULL, '"
					// + specie2 + "','"
					// + mirnaid + "')";
					//
					// stmt.executeUpdate(query);

				} else {

					String sequence = tokens[0];
					String query = "INSERT INTO " + tableName
							+ " VALUES (NULL, '" + specie + "','" + mirnaid
							+ "','" + sequence + "')";

					stmt.executeUpdate(query);

				}

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (line != null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": (" + tokens[j].length() + ") "
							+ tokens[j]);
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

		Connection con = null;

		// Get session
		Session session = HibernateUtil.getSessionFactory().openSession();

		// start transaction
		Transaction tx = session.beginTransaction();

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();

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
			// CAMBIAR ESTO:

			String specie = rs.getString("specie").toLowerCase().trim();
			String stemloop_id = rs.getString("mirna_id").toLowerCase().trim();
			String sequence_hairpin = rs.getString("sequence").toLowerCase().trim();

			MiRna miRNA = new MiRna();
			miRNA.setName(stemloop_id);

			Organism organism = new Organism();
			organism.setName(specie);

			Sequence sequence = new Sequence();
			sequence.setSequence(sequence_hairpin);

			Hairpin hairpin = new Hairpin();

			ExpressionData ed = new ExpressionData();
			ed.setProvenance("PlantMirna");

			// Inserta Organism (o recupera su id. si ya existe)
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism.getName()))
					.uniqueResult();
			if (oldOrganism == null) {
				session.save(organism);
				session.flush(); // to get the PK
			} else {
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism);
				session.update(organismToUpdate);
				organism = organismToUpdate;
			}

			// Inserta MiRna (o recupera su id. si ya existe)
			Object oldMiRna = session.createCriteria(MiRna.class)
					.add(Restrictions.eq("name", miRNA.getName()))
					.uniqueResult();
			if (oldMiRna == null) {
				session.save(miRNA);
				session.flush(); // to get the PK
			} else {
				MiRna miRnaToUpdate = (MiRna) oldMiRna;
				miRnaToUpdate.update(miRNA);
				session.update(miRnaToUpdate);
				miRNA = miRnaToUpdate;
			}
			
			hairpin.setMirnaPk(miRNA.getPk());
			hairpin.setSequence_pk(sequence.getPk());
			
			Object oldHairpin = session.createCriteria(Hairpin.class)
					.add( Restrictions.eq("sequence_pk", hairpin.getSequence_pk() ) )
					.uniqueResult();
			if (oldHairpin==null) {
				session.save(hairpin);
				session.flush();  // to get the PK

			} else {

				Hairpin hairpinToUpdate = (Hairpin) oldHairpin;
				hairpinToUpdate.update(hairpin);
				session.update(hairpinToUpdate);
				hairpin = hairpinToUpdate;
			}
			
			// Relaciona expressiondata data con mirna
			
			ed.setMirnaPk(miRNA.getPk());
			session.save(ed);
			
			count++;
			if (count %100 == 0) {
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
			if (con != null)
				con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}

	}

	public static void main(String[] args) throws Exception {

		PlantMirnaStemLoop plant = new PlantMirnaStemLoop();

		// String inputFile =
		// "/Users/esteban/Softw/miRNA/plant_mirna/all_stem_loop.txt";
		// plant.insertInTable(inputFile);

		plant.insertIntoSQLModel();

	}

}
