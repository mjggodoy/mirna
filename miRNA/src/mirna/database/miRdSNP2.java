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
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Mutation;
import mirna.beans.SNP;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.SnpHasDisease;
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

			int count = 0;
			rs.next();
			// CAMBIAR ESTO:
			count ++;
			String ref_seq = rs.getString("refseq").toLowerCase().trim();
			String gene_name = rs.getString("gene").toLowerCase().trim();
			String snp_id = rs.getString("snp_id").toLowerCase().trim();
			String mirna_name = rs.getString("mirna").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();


			String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, "|");
			List<Disease> diseaseList = new ArrayList<Disease>();

			for (String token : diseaseTokens) {

				Disease disease = new Disease();
				disease.setName(token);
				diseaseList.add(disease);
			}

			Gene gene = new Gene();
			gene.setName(gene_name);


			Transcript transcript = new Transcript();
			transcript.setTranscriptID(ref_seq);
			
			Target target = new Target();

			Mutation mutation = new Mutation();


			String[] mirnaTokens = StringUtils.splitPreserveAllTokens(mirna_name, "|");
			List<MiRna> mirnaList = new ArrayList<MiRna>();

			for (String token : mirnaTokens) {

				MiRna mirna_list = new MiRna();
				mirna_list.setName(token);
				mirnaList.add(mirna_list);
			}

			ExpressionData ed = new ExpressionData();
			ed.setProvenance("miRdSNP");

			InteractionData id = new InteractionData();
			id.setProvenance("miRdSNP");


			String[] snpTokens = StringUtils.splitPreserveAllTokens(snp_id, "|");
			List<SNP> snpList = new ArrayList<SNP>();

			for (String token : snpTokens) {

				SNP snp = new SNP();
				snp.setSnp_id(token);
				snpList.add(snp);
			}
			
			
			
			// ======
			
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

			transcript.setGeneId(gene.getPk());
			Object oldTranscript = session.createCriteria(Transcript.class)
					.add( Restrictions.eq("transcriptID", transcript.getTranscriptID()) )
					.uniqueResult();
			if (oldTranscript==null) {
				session.save(transcript);
				session.flush();  // to get the PK
			} else {
				Transcript transcriptToUpdate = (Transcript) oldGene;
				transcriptToUpdate.update(transcript);
				session.update(transcriptToUpdate);
				transcript = transcriptToUpdate;
			}

			for(SNP snp : snpList){

				mutation.setGene_pk(gene.getPk());
				session.save(mutation);
				session.flush();
				snp.setMutation_pk(mutation.getPk());
				
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
				
				
			}
			
			target.setTranscript_pk(transcript.getPk());
			session.save(target);
			session.flush();
		
			for (MiRna mirna : mirnaList) {

				Object oldMiRna = session.createCriteria(MiRna.class)
						.add( Restrictions.eq("name", mirna.getName()) )
						.uniqueResult();
				if (oldMiRna==null) {
					session.save(mirna);
					session.flush();  // to get the PK
				} else {
					MiRna miRnaToUpdate = (MiRna) oldMiRna;
					miRnaToUpdate.update(mirna);
					session.update(miRnaToUpdate);
					mirna = miRnaToUpdate;
				}

				id.setMirna_pk(mirna.getPk());
				id.setTarget_pk(target.getPk());
				session.save(id);

			}

				// SI ESTO ES IMPORTANTE; HAZLO ANTES DE METER EL SNP EN LA BD

			
			for(SNP snp : snpList){
				
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
				
				for (Disease disease : diseaseList) {

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

					SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
					Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
							.add( Restrictions.eq("snpPk", snp.getPk()) )
							.add( Restrictions.eq("diseasePk", disease.getPk()) )
							.uniqueResult();
					if (oldSnphasDisease==null) {
						session.save(snpHasDisease);
					}

					ed.setDiseasePk(disease.getPk());
					session.save(ed);

				}
			}

			id.setGene_pk(gene.getPk());
			id.setExpression_data_pk(ed.getPk());

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

		miRdSNP2 miRdSNP2 = new miRdSNP2();
		miRdSNP2.insertIntoSQLModel();


	}

}