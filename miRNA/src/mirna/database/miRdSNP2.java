package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.beans.Disease;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.SNP;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP2 extends miRdSNP {
	
	private final String tableName = "miRdSNP2";
	
	public miRdSNP2() throws MiRnaException { super(); }
	
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
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
				
				for (int i=0; i<tokens.length; i++) {
					tokens[i] = quitarComillas(tokens[i]);
				}
	
				String refseq_name = tokens[0];
				String gene_name = tokens[1];
				String SNPid = tokens[2];
				String miRNA_name = tokens[3];
				String disease_name = tokens[4].replaceAll("'", "\\\\'");;
				
				if (tokens.length>5) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ refseq_name + "','"
						+ gene_name + "','"
						+ SNPid + "','"
						+ miRNA_name + "','"
						+ disease_name + "')";
				
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
			//int count = 0;


			rs.next();
			// CAMBIAR ESTO:
			
			String ref_seq = rs.getString("refseq").toLowerCase().trim();
			String gene_name = rs.getString("gene").toLowerCase().trim();
			String snp_id = rs.getString("snp_id").toLowerCase().trim();
			String mirna_name = rs.getString("mirna").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();

			
			Disease disease = new Disease();
			disease.setName(disease_name);
			
			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setGeneId(ref_seq);
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			
			InteractionData id = new InteractionData();

			String[] snpTokens = StringUtils.splitPreserveAllTokens(snp_id, "|");
			
			List<SNP> snpList = new ArrayList<SNP>();
			
			for (String token : snpTokens) {
				SNP snp = new SNP();
				snp.setSNPid(token);
				
				snpList.add(snp);
			}
			
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
				MiRna miRnaToUpdate = (MiRna) oldDisease;
				miRnaToUpdate.update(mirna);
				session.update(miRnaToUpdate);
				mirna = miRnaToUpdate;
			}
			
			// Relaciona SNP y Disease
			// Relaciona SNP y Gene_id
			
			for (SNP snp : snpList) {
				
				System.out.println(snp);
				snp.setDisease_id(disease.getPk());
				snp.setGene_id(gene.getPk());
				session.save(snp);	
			}
			
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