package mirna.integration.database;

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

import mirna.integration.beans.Hairpin;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Organism;
import mirna.integration.beans.Sequence;
import mirna.integration.beans.nToM.HairpinHasSequence;
import mirna.integration.beans.nToM.MirnaHasHairpin;
import mirna.integration.beans.nToM.MirnaHasOrganism;
import mirna.integration.exception.MiRnaException;

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

		Object oldSequence = session.createCriteria(Sequence.class)
				.add( Restrictions.eq("sequence", sequence.getSequence()) )
				.uniqueResult();
		if (oldSequence==null) {
			session.save(sequence);
			session.flush();  // to get the PK
		} else {
			Sequence sequenceToUpdate = (Sequence) oldSequence;
			sequenceToUpdate.update(sequence);
			session.update(sequenceToUpdate);
			sequence = sequenceToUpdate;
		}


		MirnaHasOrganism mirnaHasOrganism = 
				new MirnaHasOrganism(miRNA.getPk(), organism.getPk());


		// Relaciona Organism con Mirna (si no lo estaba ya)
		Object oldmirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
				.add( Restrictions.eq("mirna_pk", miRNA.getPk()) )
				.add( Restrictions.eq("organism_pk", organism.getPk()) )
				.uniqueResult();
		if (oldmirnaHasOrganism==null) {
			session.save(mirnaHasOrganism);

		}

		session.save(hairpin);
		session.flush();

		HairpinHasSequence hairpinHasSequence = 
				new HairpinHasSequence(hairpin.getPk(), sequence.getPk());


		// Relaciona hairpin con sequence (si no lo estaba ya)
		Object oldHairpinHasSequence = session.createCriteria(HairpinHasSequence.class)
				.add( Restrictions.eq("hairpin_pk", hairpin.getPk()) )
				.add( Restrictions.eq("sequence_pk", sequence.getPk()) )
				.uniqueResult();
		if (oldHairpinHasSequence==null) {
			session.save(hairpinHasSequence);

		}

		// Relaciona hairpin con mirna.

		MirnaHasHairpin mirnaHasHairpin = 
				new MirnaHasHairpin(miRNA.getPk(), hairpin.getPk());

		Object oldMirnaHasHairpin = session.createCriteria(MirnaHasHairpin.class)
				.add( Restrictions.eq("mirna_pk", miRNA.getPk()) )
				.add( Restrictions.eq("hairpin_pk", hairpin.getPk()) )
				.uniqueResult();
		if (oldMirnaHasHairpin==null) {
			session.save(mirnaHasHairpin);

		}

	}

	public static void main(String[] args) throws Exception {

		PlantMirnaStemLoop plant = new PlantMirnaStemLoop();
		plant.insertIntoSQLModel();

	}

}
