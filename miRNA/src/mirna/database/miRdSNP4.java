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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.SNP;
import mirna.beans.nToM.SnpHasDisease;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP4 extends miRdSNP {
	
	private final String tableName = "miRdSNP4";
	
	public miRdSNP4() throws MiRnaException { super(); }
	
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
				
				String gene_name = tokens[0];
				String refseq_name = tokens[1];
				String miRNA = tokens[2];
				String snp = tokens[3];
				String disease = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];
				String expConf = "";//tokens[6];
				
				if (tokens.length==7) {
					expConf = tokens[6];
					
					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ gene_name + "','"
						+ refseq_name + "','"
						+ miRNA + "','"
						+ snp + "','"
						+ disease + "','"
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
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			rs.next();
			// CAMBIAR ESTO:
			
			String gene_name  = rs.getString("gene").toLowerCase().trim();
			String ref_seq = rs.getString("refseq").toLowerCase().trim();
			String mirna_name = rs.getString("miR").toLowerCase().trim();
			String snp_id = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("diseases").toLowerCase().trim();
			String distance = rs.getString("distance").toLowerCase().trim();// I'm not going to use this.
			String exp_config = rs.getString("expConf").toLowerCase().trim(); //This database field is not to be used.
			
			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setGeneId(ref_seq);

			
			String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");

			
			List<Disease> diseaseList = new ArrayList<Disease>();

			for (String token : diseaseTokens) {

				Disease disease = new Disease();
				disease.setName(token);
				diseaseList.add(disease);
			}
				
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			
			SNP snp = new SNP();
			snp.setSnp_id(snp_id);
			
			InteractionData id = new InteractionData();
			id.setProvenance("mirdSNP");
			
			ExpressionData ed = new ExpressionData();
			ed.setProvenance("mirdSNP");
			
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
			
			snp.setGene_pk(gene.getPk());
			Object oldSnp = session.createCriteria(SNP.class)
					.add( Restrictions.eq("snp_id", snp.getSnp_id()))
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
			
			for (Disease disease: diseaseList){
			
			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush();  // to get the PK
				System.out.println("Salvo ESTE disease:");
				System.out.println(snp);

			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
				System.out.println("Recupero ESTE disease:");
				System.out.println(snp);

			}
			
			//Relaciona SNP con disease
			
			SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
			Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
					.add( Restrictions.eq("snpPk", snp.getPk()) )
					.add( Restrictions.eq("diseasePk", disease.getPk()) )
					.uniqueResult();
			if (oldSnphasDisease==null) {
				session.save(snpHasDisease);
			}
			
			ed.setDiseasePk(disease.getPk());

			
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
			
				
			// Relaciona interaction data y expression data/gene/mirna
			// Relaciona expressiondata y mirna
			// Relaciona expressiondata y disease
			
			ed.setMirnaPk(mirna.getPk());
			session.save(ed);
			id.setMirna_pk(mirna.getPk());
			id.setGene_pk(gene.getPk());
			id.setExpression_data_pk(ed.getPk());
			session.save(id);
			
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
	
		miRdSNP4 mirdsnp4 = new miRdSNP4();
		mirdsnp4.insertIntoSQLModel();
		
		
	}
	
}