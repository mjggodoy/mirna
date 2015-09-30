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
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Sequence;
import mirna.beans.nToM.MatureHasSequence;
import mirna.beans.nToM.MirnaHasMature;
import mirna.beans.nToM.MirnaHasOrganism;
import mirna.exception.MiRnaException;

public class PlantMirnaMatureMirna extends NewMirnaDatabase {

	private final static String TABLE_NAME = "plant_mirna_mature_mirna";

	public PlantMirnaMatureMirna() throws MiRnaException { super(TABLE_NAME); }

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

				if(line.startsWith (">")){

					String specie1 = tokens[0];
					int index1 = specie1.indexOf(">");
					int index2 = specie1.indexOf("-");
					specie = specie1.substring(index1+1, index2);
					mirnaid = specie1.substring(index2+1);

					//					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
					//							+ specie2 + "','"
					//							+ mirnaid + "')";
					//					
					//					stmt.executeUpdate(query);

				}else{

					String sequence = tokens[0];
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ specie + "','"
							+ mirnaid + "','"
							+ sequence + "')";

					stmt.executeUpdate(query);

				}

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": (" + tokens[j].length() + ") " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}		

	}


	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String specie = rs.getString("specie").toLowerCase().trim();
		String mature_id = rs.getString("mirna_id").toLowerCase().trim();
		String sequence_mature = rs.getString("sequence").toLowerCase().trim();

		Organism organism = new Organism();
		organism.setName(specie);

		MiRna miRNA = new MiRna();
		miRNA.setName(mature_id);

		Sequence sequence = new Sequence();
		sequence.setSequence(sequence_mature);

		Mature mature = new Mature();

		ExpressionData ed = new ExpressionData();
		ed.setProvenance("PlantMirna");

		// Inserta Organism (o recupera su id. si ya existe)
		Object oldOrganism = session.createCriteria(Organism.class)
				.add( Restrictions.eq("name", organism.getName()) )
				.uniqueResult();
		if (oldOrganism==null) {
			session.save(organism);
			session.flush();  // to get the PK
		} else {
			Organism organismToUpdate = (Organism) oldOrganism;
			organismToUpdate.update(organism);
			session.update(organismToUpdate);
			organism = organismToUpdate;
		}

		// Inserta MiRna (o recupera su id. si ya existe)

		Object oldMiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name", miRNA.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(miRNA);
			session.flush();  // to get the PK
			System.out.println("SAVE");
		} else {
			MiRna miRnaToUpdate = (MiRna) oldMiRna;
			miRnaToUpdate.update(miRNA);
			session.update(miRnaToUpdate);
			miRNA = miRnaToUpdate;
			System.out.println("UPDATE");

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
			System.out.println(sequence);
		}
		
		// Relaciona expressiondata data con mirna

		ed.setMirnaPk(miRNA.getPk());
		session.save(ed);
		session.flush();

		// Relaciona Organism con Mirna (si no lo estaba ya)

		MirnaHasOrganism mirnaHasOrganism = 
				new MirnaHasOrganism(miRNA.getPk(), organism.getPk());

		Object oldmirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
				.add( Restrictions.eq("mirna_pk", miRNA.getPk()) )
				.add( Restrictions.eq("organism_pk", organism.getPk()) )
				.uniqueResult();
		if (oldmirnaHasOrganism==null) {
			session.save(mirnaHasOrganism);

		}

		session.save(mature);
		session.flush();
	
		//Relaciona Mature con Sequence
		MatureHasSequence matureHasSequence = 
				new MatureHasSequence(mature.getPk(), sequence.getPk());

		Object oldmatureHasSequence = session.createCriteria(MatureHasSequence.class)
				.add( Restrictions.eq("mature_pk", mature.getPk()) )
				.add( Restrictions.eq("sequence_pk", sequence.getPk()) )
				.uniqueResult();
		if (oldmatureHasSequence==null) {
			session.save(matureHasSequence);

		}

		//Relaciona MiRNA con Mature
		MirnaHasMature mirnaHasMature = 
				new MirnaHasMature(miRNA.getPk(), mature.getPk());

		Object oldmirnaHasMature = session.createCriteria(MirnaHasMature.class)
				.add( Restrictions.eq("mirna_pk", miRNA.getPk()) )
				.add( Restrictions.eq("mature_pk", mature.getPk()) )
				.uniqueResult();
		if (oldmirnaHasMature==null) {
			session.save(mirnaHasMature);

		}
	}

	public static void main(String[] args) throws Exception{

		PlantMirnaMatureMirna plant = new PlantMirnaMatureMirna();

		// /* 1. meter datos en mirna_raw */
		//	String inputFile = "/Users/esteban/Softw/miRNA/plant_mirna/all_mature.txt";
		//	plant.insertInTable(inputFile);

		/* 2. meter datos en mirna */
		plant.insertIntoSQLModel();

	}

}
