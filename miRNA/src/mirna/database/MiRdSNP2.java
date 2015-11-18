package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.SNP;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.SnpHasDisease;
import mirna.beans.nToM.SnpHasGene;
import mirna.beans.nToM.TranscriptHasGene;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP2 extends MiRdSNP {

	private static final String TABLE_NAME = "miRdSNP2";

	public MiRdSNP2() throws MiRnaException { 
		super(TABLE_NAME); }

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

	public void processRow(Session session, ResultSet rs) throws Exception {

		String ref_seq = nullifyField(rs.getString("refseq"));
		String gene_name = nullifyField(rs.getString("gene").toLowerCase().trim());
		String snp_id = nullifyField(rs.getString("snp_id").toLowerCase().trim());
		String mirna_name = nullifyField(rs.getString("mirna").toLowerCase().trim());
		String disease_name = nullifyField(rs.getString("disease").toLowerCase().trim());

		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, "|");
		List<Disease> diseaseList = new ArrayList<Disease>();
		if (diseaseTokens!=null) {
			for (String token : diseaseTokens) {
				Disease disease = new Disease();
				disease.setName(token);
				diseaseList.add(disease);	
			}
		}

		Gene gene = new Gene();
		gene.setName(gene_name);
		if (!createdObject(gene_name)) {
			gene_name = null;
		}

		Target target = new Target();

		Transcript transcript = new Transcript();
		transcript.setTranscriptID(ref_seq);
		if (!createdObject(ref_seq)) {
			transcript = null;
		}

		String[] snpTokens = StringUtils.splitPreserveAllTokens(snp_id, "|");
		List<SNP> snpList = new ArrayList<SNP>();
		if (snpTokens!=null) {
			for (String token : snpTokens) {
				SNP snp = new SNP();
				snp.setSnp_id(token);
				snpList.add(snp);
			}
		}
		
		String[] mirnaTokens = StringUtils.splitPreserveAllTokens(mirna_name, "|");
		List<MiRna> mirnaList = new ArrayList<MiRna>();
		if (mirnaTokens!=null) {
			for (String token : mirnaTokens) {
				MiRna mirna_list = new MiRna();
				mirna_list.setName(token);
				mirnaList.add(mirna_list);
			}
		}

		// Insertamos gene
		if(gene !=null){
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
		}

		// Insertamos transcript
		if(transcript != null){
			Object oldTranscript = session.createCriteria(Transcript.class)
					.add(Restrictions.eq("transcriptID", transcript.getTranscriptID()))
					.uniqueResult();
			if (oldTranscript == null) {
				session.save(transcript);
				session.flush(); // to get the PK
			} else {
				Transcript transcriptToUpdate = (Transcript) oldTranscript;
				transcriptToUpdate.update(transcript);
				session.update(transcriptToUpdate);
				transcript = transcriptToUpdate;
			}

			// Insertamos target y lo relacionamos con Transcript
			target.setTranscript_pk(transcript.getPk());
			session.save(target);
			session.flush(); // to get the PK

			// Relacionamos gene con Transcript
			if(gene != null){
				TranscriptHasGene transcripthasGene =
						new TranscriptHasGene(transcript.getPk(), gene.getPk());
				Object oldTranscripthasGene = session.createCriteria(TranscriptHasGene.class)
						.add(Restrictions.eq("transcriptPk", transcript.getPk()))
						.add(Restrictions.eq("genePk", gene.getPk()))
						.uniqueResult();
				if (oldTranscripthasGene == null) {
					session.save(transcripthasGene);
				}
			}
		}
		
		// Insertamos diseases
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
		}
		
		// Insertamos SNPs
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
		}
		
		// Insertamos mirnas
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
		}
		
		// Relacionamos SNPs con gene y con Diseases
		for (SNP snp : snpList) {
			SnpHasGene snpHasGene = new SnpHasGene(snp.getPk(), gene.getPk());
			Object oldSnphasGene = session.createCriteria(SnpHasGene.class)
					.add( Restrictions.eq("snpPk", snp.getPk()) )
					.add( Restrictions.eq("genePk", gene.getPk()) )
					.uniqueResult();
			if (oldSnphasGene==null) {
				session.save(snpHasGene);
			}
			
			for (Disease disease : diseaseList) {
				SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
				Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
						.add( Restrictions.eq("snpPk", snp.getPk()) )
						.add( Restrictions.eq("diseasePk", disease.getPk()) )
						.uniqueResult();
				if (oldSnphasDisease==null) {
					session.save(snpHasDisease);
				}
			}
			
		}
		
		// Creamos InteractionDatas y ExpressionDatas
		for (MiRna mirna : mirnaList) {
			
			InteractionData id = new InteractionData();
			id.setProvenance("mirdSNP");
			id.setMirna_pk(mirna.getPk());
			id.setTarget_pk(target.getPk());
			id.setGene_pk(gene.getPk());
			session.save(id);
			session.flush();
			
			for (Disease disease : diseaseList) {
				ExpressionData ed = new ExpressionData();
				ed.setProvenance("mirdSNP");
				ed.setDiseasePk(disease.getPk());
				ed.setMirnaPk(mirna.getPk());
				ed.setInteraction_data_pk(id.getPk()); // Fixed
				session.save(ed);
			}
		}

	}

	public static void main(String[] args) throws Exception {
		MiRdSNP2 mirdsnp2 = new MiRdSNP2();
		mirdsnp2.insertIntoSQLModel();
	}

}