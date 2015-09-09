package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.EnvironmentalFactor;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
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
 * CÃ³digo para procesar los datos de miREnvironment
 * 
 * @author Esteban LÃ³pez Camacho
 *
 */
public class miREnvironment extends MirnaDatabase {
	
	private final String tableName = "miREnvironment";
	
	public miREnvironment() throws MiRnaException {
		super();
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
	
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
					String mir = tokens[0];
					String name = tokens[1];
					String name2 = tokens[2];
					String name3 = tokens[3];
					String disease = tokens[4].replaceAll("'", "\\\\'");
					String enviromenentalFactor = tokens[5].replaceAll("'", "\\\\'");
					String treatment = tokens[6].replaceAll("'", "\\\\'");
					String cellularLine = tokens[7].replaceAll("'", "\\\\'");
					String specie = tokens[8];
					String description = tokens[9].replaceAll("'", "\\\\'");
					String pubmedId = tokens[10];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ mir + "','"
							+ name + "','"
							+ name2 + "','"
							+ name3 + "','"
							+ disease + "','"
							+ enviromenentalFactor + "','"
							+ treatment + "','"
							+ cellularLine + "','"
							+ specie + "','"
							+ description + "','"
							+ pubmedId + "')";
					
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
			if (rs.next()) {
			// CAMBIAR ESTO
			
			String id = rs.getString("mir");
			String name = rs.getString("name");
			String name2 = rs.getString("name2");
			String name3 = rs.getString("name3").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();
			String environmentalFactor_name = rs.getString("enviromentalFactor").toLowerCase().trim();
			String treatment = rs.getString("treatment").toLowerCase().trim();
			String cellularLine = rs.getString("cellularLine").toLowerCase().trim();
			String specie_name = rs.getString("specie");
			String description = rs.getString("description");
			String pubmedId = rs.getString("pubmedId");

			
			MiRna miRna = new MiRna();
			miRna.setName(name);
			
			MiRna miRna2 = new MiRna();//ƒCHALE UN VISTADO QUE A Mê ME DA CONFLICTO CUANDO LO INTENTƒ PROGRAMAR.
			miRna2.setName(name2);
			
			MiRna miRna3 = new MiRna();//ƒCHALE UN VISTADO QUE A Mê ME DA CONFLICTO CUANDO LO INTENTƒ PROGRAMAR.
			miRna2.setName(name3);
			
			Disease disease = new Disease();
			disease.setName(disease_name);
			
			EnvironmentalFactor environmentalFactor = new EnvironmentalFactor();
			environmentalFactor.setName(environmentalFactor_name);
			
			ExpressionData ed = new ExpressionData();
			ed.setTreatment(treatment);
			ed.setCellularLine(cellularLine);
			ed.setDescription(description);
			ed.setProvenanceId(id);
			ed.setProvenance("miREnvironment");
			
			Organism organism = new Organism();
			organism.setName(specie_name);
			
			PubmedDocument pubmedDoc = new PubmedDocument();
			pubmedDoc.setId(pubmedId);
			
			// FIN DE CAMBIAR ESTO
					
			
			// Inserta Disease (o recupera su id. si ya existe)
			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush(); // to get the PK
			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
			}
			
			// Inserta EnvironmentalFactor (o recupera su id. si ya existe)
			
			Object oldEnvironmentalFactor = session.createCriteria(EnvironmentalFactor.class)
					.add(Restrictions.eq("name", environmentalFactor.getName()) )
					.uniqueResult();
			if (oldEnvironmentalFactor==null) {
				session.save(environmentalFactor);
				session.flush(); // to get the PK
			} else {
				EnvironmentalFactor environmentalFactorToUpdate = (EnvironmentalFactor) oldEnvironmentalFactor;
				environmentalFactorToUpdate.update(environmentalFactor);
				session.update(environmentalFactorToUpdate);
				environmentalFactor = environmentalFactorToUpdate;
			}
			
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism.getName()) )
					.uniqueResult();
			if (oldOrganism==null) {
				session.save(organism);
				session.flush(); // to get the PK
			} else {
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism);
				session.update(organismToUpdate);
				organism = organismToUpdate;
			}
			
			miRna.setOrganismPk(organism.getPk());
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
				System.out.println("mirna "+ miRna);
				session.update(miRnaToUpdate);
				miRna = miRnaToUpdate;
			}
			
			
			// Inserta PubmedDocument (o recupera su id. si ya existe)
			
			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add( Restrictions.eq("id", pubmedDoc.getId()) )
					.uniqueResult();
			if (oldPubmedDoc==null) {
				session.save(pubmedDoc);
				session.flush(); // to get the PK
				System.out.println("Save Pubmed document");

			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmedDoc);
				session.update(pubmedDocToUpdate);
				pubmedDoc = pubmedDocToUpdate;
				System.out.println("Updated Pubmed document");
			}
			
			// Inserta nueva DataExpression
			// (y la relaciona con el MiRna y Disease correspondiente)
			
			// y con enviromentalFactor
			
			ed.setMirnaPk(miRna.getPk());
			ed.setDiseasePk(disease.getPk());
			ed.setEnvironmentalFactorPk(environmentalFactor.getPk());
			session.save(ed);
			
			// Relaciona miRNa con Organism. No estoy segura. La entidad-relacion es de one-to-many segun el modelo.
			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(miRna.getPk(), pubmedDoc.getPk());
			
	
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(ed.getPk(), pubmedDoc.getPk());
			
			
			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", miRna.getPk()) )
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
			
			}
		} catch (SQLException e) {
			//TODO: Meter Rollback
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}
				
	}
	
	public static void main(String[] args) throws Exception {
		
		miREnvironment mirEnvironment = new miREnvironment();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/mirendata.txt";
		//mirEnvironment.insertInTable(inputFile);
		
		mirEnvironment.insertIntoSQLModel();
		
	}

}