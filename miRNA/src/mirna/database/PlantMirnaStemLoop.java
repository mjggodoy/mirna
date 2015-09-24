package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.beans.ExpressionData;
import mirna.beans.Hairpin;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Sequence;
import mirna.exception.MiRnaException;

public class PlantMirnaStemLoop extends NewMirnaDatabase {

	private final static String TABLE_NAME = "plant_mirna_stem_loop";

	public PlantMirnaStemLoop() throws MiRnaException {
		super(TABLE_NAME);
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
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
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
		
		miRNA.setOrganismPk(organism.getPk());
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
			System.out.println("Mirna " + miRNA.getName());
		}
		
		Object oldSequence = session.createCriteria(Sequence.class)
				.add( Restrictions.eq("sequence", sequence.getSequence()) )
				.uniqueResult();
		if (oldSequence==null) {
			//System.out.println("LENGTH = " + sequence.getSequence().length());
			session.save(sequence);
			session.flush();  // to get the PK
		} else {
			Sequence sequenceToUpdate = (Sequence) oldSequence;
			sequenceToUpdate.update(sequence);
			session.update(sequenceToUpdate);
			sequence = sequenceToUpdate;
			System.out.println(sequence);
		}
		
		hairpin.setMirnaPk(miRNA.getPk());
		hairpin.setSequence_pk(sequence.getPk());
		// Inserta Hairpin (o recupera su id. si ya existe)
		Object oldHairpin = session.createCriteria(Hairpin.class)
				.add( Restrictions.eq("sequence_pk", hairpin.getSequence_pk() ) )
				.uniqueResult();
		if (oldHairpin==null) {
			session.save(hairpin);
			session.flush();  // to get the PK
			System.out.println("HAIRPIN has been saved");

		} else {

			Hairpin hairpinToUpdate = (Hairpin) oldHairpin;
			hairpinToUpdate.update(hairpin);
			session.update(hairpinToUpdate);
			hairpin = hairpinToUpdate;
			System.out.println("HAIRPIN has been updated");
		}
		
		// Relaciona expressiondata data con mirna
		ed.setMirnaPk(miRNA.getPk());
		session.save(ed);
		
	}
	
	public static void main(String[] args) throws Exception {

		PlantMirnaStemLoop plant = new PlantMirnaStemLoop();

		// /* 1. meter datos en mirna_raw */
		// String inputFile = "/Users/esteban/Softw/miRNA/plant_mirna/all_stem_loop.txt";
		// plant.insertInTable(inputFile);

		/* 2. meter datos en mirna */
		plant.insertIntoSQLModel();

	}

}
