package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Mutation;
import mirna.beans.SNP;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP3 extends miRdSNP {
	
	private final String tableName = "miRdSNP3";
	
	public miRdSNP3() throws MiRnaException { super(); }
	
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
	
				String geneName = tokens[0];
				String refseqId = tokens[1];
				String miR = tokens[2];
				String SNP = tokens[3];
				String diseases = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];
				
				String expConf = "";//tokens[6];
				
				if (tokens.length==7) {
					expConf = tokens[6];
					
					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}
					
				if (tokens.length>7) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ geneName + "','"
						+ refseqId + "','"
						+ miR + "','"
						+ SNP + "','"
						+ diseases + "','"
						+ distance + "','"
						+ expConf + "')";
				
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
			
            stmt.setFetchSize(Integer.MIN_VALUE);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			//int count = 0;


			rs.next();
			// CAMBIAR ESTO:
			
			String gene_name = rs.getString("gene").toLowerCase().trim();
			String ref_seq = rs.getString("refseq").toLowerCase().trim();
			String mirna_name = rs.getString("miR").toLowerCase().trim();
			String snp_id = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("diseases").toLowerCase().trim();
			String distance = rs.getString("distance").toLowerCase().trim();
			
			Disease disease = new Disease();
			disease.setName(disease_name);
			
			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setGeneId(ref_seq);
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			
			SNP snp = new SNP();
			snp.setSNPid(snp_id);
			snp.setDistance(distance);
			
			InteractionData id = new InteractionData();
			
			
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
			
			Object oldGene = session.createCriteria(Gene.class)
					.add( Restrictions.eq("name", gene.getName()) )
					.uniqueResult();
			if (oldGene==null) {
				session.save(gene);
				session.flush();  // to get the PK
			} else {
				Gene geneToUpdate = (Gene) oldGene;
				geneToUpdate.update(gene);
				session.update(geneToUpdate);
				gene = geneToUpdate;
			}
			
			Object oldMiRna = session.createCriteria(MiRna.class)
					.add( Restrictions.eq("name", mirna.getName()) )
					.uniqueResult();
			if (oldMiRna==null) {
				session.save(mirna);
				session.flush();  // to get the PK
			} else {
				
				MiRna mirnaToUpdate = (MiRna) oldMiRna;
				mirnaToUpdate.update(mirna);
				session.update(mirnaToUpdate);
				mirna = mirnaToUpdate;
			}
			
			Object oldSnp = session.createCriteria(SNP.class)
					.add( Restrictions.eq("name", snp.getSNPid()))
					.uniqueResult();
			if (oldSnp==null) {
				session.save(snp);
				session.flush();  // to get the PK
			} else {
				SNP snpToUpdate = (SNP) oldSnp;
				snpToUpdate.update(snp);
				session.update(snpToUpdate);
				snp = snpToUpdate;
			}
		
			// Relaciona SNP y Disease
			// Relaciona SNP y Gene_id
						
			snp.setDisease_id(disease.getPk());
			snp.setGene_id(gene.getPk());
						
			// Relaciona interaction data con mirna.
			id.setMirnaPk(mirna.getPk());
			session.save(id);
			
			
			stmt.close();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
}