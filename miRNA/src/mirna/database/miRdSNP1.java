package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mirna.beans.Disease;
import mirna.beans.SNP;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.beans.nToM.SnpHasDisease;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

/**
 * Código para procesar los datos de miRdSNP
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP1 extends miRdSNP {
	
	private final String tableName = "miRdSNP1";
	
	public miRdSNP1() throws MiRnaException { super(); }
	
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
				
				//tokens = StringUtils.splitPreserveAllTokens(line, ",");
				tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				for (int i=0; i<tokens.length; i++) {
					tokens[i] = quitarComillas(tokens[i]);
				}
				
				String pubmedid = tokens[0];
				String year = tokens[1];
				String month = tokens[2];
				String article_date = tokens[3];
				String journal = tokens[4].replaceAll("'", "\\\\'");
				String title = tokens[5].replaceAll("'", "\\\\'");
				String snpId = tokens[6];
				String disease = tokens[7].replaceAll("'", "\\\\'");
				String link = tokens[8];
				
				if (tokens.length>9) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ pubmedid + "','"
						+ year + "','"
						+ month + "','"
						+ article_date + "','"
						+ journal + "','"
						+ title + "','"
						+ snpId + "','"
						+ disease + "','"
						+ link + "')";
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
	
	public void insertIntoSQLModel() throws Exception {

		Connection con = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();


		
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
			if(rs.next() && count<3){
				
			count++;
			// CAMBIAR ESTO:
			
			String pubmedId = rs.getString("pubmed_id").toLowerCase().trim();
			String year = rs.getString("year").toLowerCase().trim();
			String journal = rs.getString("journal").toLowerCase().trim();
			String description = rs.getString("title").toLowerCase().trim();
			String snp_id = rs.getString("snp_id").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();
			String resource = rs.getString("link").toLowerCase().trim();

			Disease disease = new Disease();
			disease.setName(disease_name);
			
			SNP snp = new SNP();
			snp.setSnp_id(snp_id);
			snp.setPubmed_id(pubmedId);
			snp.setResource(resource);
			snp.setJournal(journal);
			snp.setYear(year);
			snp.setDescription(description);
		
			
			// Inserta Disease (o recupera su id. si ya existe)

			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush();  // to get the PK
			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
			}
			
			Object oldSnp = session.createCriteria(SNP.class)
					.add( Restrictions.eq("snp_id", snp.getSnp_id()) )
					.uniqueResult();
			if (oldSnp==null) {
				session.save(snp); // mutation_pk puede ser nulo
				session.flush();  // to get the PK
			} else {
				SNP snptoUpdate = (SNP) oldSnp;
				snptoUpdate.update(snp); //incluir el update en la clase SNP
				session.update(snptoUpdate);
				snp = snptoUpdate;
			}
			
			// Relaciona SNP y Disease
			
			SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
			Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
					.add( Restrictions.eq("snpPk", snp.getPk()) )
					.add( Restrictions.eq("diseasePk", disease.getPk()) )
					.uniqueResult();
			if (oldSnphasDisease==null) {
				session.save(snpHasDisease);
			}
			
			count++;
			if (count%100==0) {
				System.out.println(count);
				session.flush();
		        session.clear();
			}
			}
			
			stmt.close();
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
		
		miRdSNP1 miRdSNP1 = new miRdSNP1();
		miRdSNP1.insertIntoSQLModel();
		
		
	}

}