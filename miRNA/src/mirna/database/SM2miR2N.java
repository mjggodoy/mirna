package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.EnvironmentalFactor;
import mirna.beans.ExpressionData;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.SmallMolecule;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de SM2miR2N
 * 
 * @author Esteban López Camacho
 *
 */
public class SM2miR2N extends MirnaDatabase {

	private final String tableName = "sm2mir2n";

	public SM2miR2N() throws MiRnaException {
		super();
	}

	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null;

		String query = "";

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 

			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);

			int count = 0;

			br.readLine();

			while ((line = br.readLine()) != null) {

				count++;
				System.out.println(count + " : " + line);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String mirna = tokens[0];
				String mirbase = tokens[1];
				String smallMolecule = tokens[2].replaceAll("'", "\\\\'");
				String fda = tokens[3];
				String db = tokens[4];
				String cid = tokens[5];
				String method = tokens[6];
				String species = tokens[7];
				String condition = tokens[8].replaceAll("'", "\\\\'");
				String pmid = tokens[9];
				String year = tokens[10];
				String reference = tokens[11].replaceAll("'", "\\\\'");
				String support = tokens[12].replaceAll("'", "\\\\'");
				String expression = tokens[13];

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ mirbase + "','"
						+ smallMolecule + "','"
						+ fda + "','"
						+ db + "','"
						+ cid + "','"
						+ method + "','"
						+ species + "','"
						+ condition + "','"
						+ pmid + "','"
						+ year + "','"
						+ reference + "','"
						+ support + "','"
						+ expression + "')";

				stmt.executeUpdate(query);

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			System.out.println("QUERY =");
			System.out.println(query);
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}

	}

	@Override
	public void insertIntoSQLModel() throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Connection con = null;

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the java resultset
			int count = 0;
			rs.next();
			// CAMBIAR ESTO:

			String name = rs.getString("mirna");
			String mirbase = rs.getString("mirbase");
			String small_molecule = rs.getString("small_molecule").toLowerCase().trim();
			String fda = rs.getString("fda").toLowerCase().trim();
			String db = rs.getString("db").toLowerCase().trim();
			String cid = rs.getString("cid").toLowerCase().trim();
			String method = rs.getString("method").toLowerCase().trim();
			String specie = rs.getString("species");
			String condition = rs.getString("condition_");
			String pmid = rs.getString("pmid");
			String year = rs.getString("year");
			String reference = rs.getString("reference");
			String support = rs.getString("support");
			String evidence = rs.getString("expression");

			MiRna miRna = new MiRna();
			miRna.setName(name);
			miRna.setAccessionNumber(mirbase);

			SmallMolecule smallmolecule = new SmallMolecule();
			smallmolecule.setFda(fda);
			smallmolecule.setCid(cid);
			smallmolecule.setDb(db);

			EnvironmentalFactor ef = new EnvironmentalFactor();
			ef.setName(small_molecule);

			ExpressionData ed = new ExpressionData();
			ed.setCondition(condition);
			ed.setEvidence(evidence);
			ed.setMethod(method);
			ed.setTitleReference(reference);
			ed.setYear(year);
			ed.setDescription(support);
			ed.setProvenance("SMS2miR2N");

			Organism organism = new Organism();
			organism.setName(specie);

			PubmedDocument pubmedDoc = new PubmedDocument();
			pubmedDoc.setId(pmid);

			// Inserta Organism (o recupera su id. si ya existe)
			// Relaciona mirna y organism

			// Inserta MiRna (o recupera su id. si ya existe)
			
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism.getName()) )
					.uniqueResult();
			if (oldOrganism==null) {
				
				session.save(organism);
				session.flush(); // to get the PK
				System.out.println("Save ORGANISM");
			} else {
				
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism);
				session.update(organismToUpdate);
				organism = organismToUpdate;
				System.out.println("Update ORGANISM");
			}

			miRna.setOrganismPk(organism.getPk());
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
		
			// Inserta EnvironmentalFactor (o recupera su id. si ya existe)

			Object oldEf = session.createCriteria(EnvironmentalFactor.class)
					.add( Restrictions.eq("name", ef.getName()) )
					.uniqueResult();
			if (oldEf==null) {
				session.save(ef);
				session.flush();  // to get the PK
			} else {
				EnvironmentalFactor efToUpdate = (EnvironmentalFactor) oldEf;
				efToUpdate.update(ef);
				session.update(efToUpdate);
				ef = efToUpdate;
			}

			// Inserta smallmolecule (o recupera su id. si ya existe)
			smallmolecule.setEnvironmental_factor_pk(ef.getPk());
			Object oldSmallMolecule = session.createCriteria(SmallMolecule.class)
					.add( Restrictions.eq("db", smallmolecule.getDb()) )
					.uniqueResult();
			if (oldSmallMolecule==null) {
				session.save(smallmolecule);
				session.flush();  // to get the PK
			} else {
				SmallMolecule smallmoleculeToUpdate = (SmallMolecule) oldSmallMolecule;
				smallmoleculeToUpdate.update(smallmolecule);
				session.update(smallmoleculeToUpdate);
				smallmolecule = smallmoleculeToUpdate;
			}


			// Inserta nueva DataExpression
			// (y la relaciona con el MiRna y SmallMolecule)

			ed.setMirnaPk(miRna.getPk());
			ed.setEnvironmentalFactorPk(smallmolecule.getPk()); 
			session.save(ed);			

			// Inserta pubmedDocument (o recupera su id. si ya existe)

			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add( Restrictions.eq("id", pubmedDoc.getId()) )
					.uniqueResult();
			if (oldPubmedDoc==null) {
				session.save(pubmedDoc);
				session.flush(); // to get the PK
			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmedDoc);
				session.update(pubmedDocToUpdate);
				pubmedDoc = pubmedDocToUpdate;
			}


			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(miRna.getPk(), pubmedDoc.getPk());
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(ed.getPk(), pubmedDoc.getPk());



			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)

			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", miRna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}

			// Relaciona PubmedDocument con ExpressionData

			session.save(expresDataHasPubmedDocument);

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

		SM2miR2N sm2 = new SM2miR2N();

		//String inputFile = "/Users/esteban/Softw/miRNA/SM2miR2n.txt";
		//sm2.insertInTable(inputFile);

		sm2.insertIntoSQLModel();

	}

}