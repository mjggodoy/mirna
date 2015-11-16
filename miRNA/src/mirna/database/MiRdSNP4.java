package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP4 extends MiRdSNP {

	private static final String TABLE_NAME = "miRdSNP4";

	public MiRdSNP4() throws MiRnaException { super(TABLE_NAME); }

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

	public void processRow(Session session, ResultSet rs) throws Exception{

		String gene_name  = nullifyField(rs.getString("gene").toLowerCase().trim());
		String ref_seq = nullifyField(rs.getString("refseq").toLowerCase().trim());
		String mirna_name = nullifyField(rs.getString("miR").toLowerCase().trim());
		String snp_id = nullifyField(rs.getString("snp").toLowerCase().trim());
		String disease_name = nullifyField(rs.getString("diseases").toLowerCase().trim());
		String distance = nullifyField(rs.getString("distance").toLowerCase().trim());// I'm not going to use this.
		String exp_config = nullifyField(rs.getString("expConf").toLowerCase().trim()); //This database field is not to be used.

		Gene gene = new Gene();
		gene.setName(gene_name);
		if (!createdObject(gene_name)){	
			gene = null;
		}

		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");


		List<Disease> diseaseList = new ArrayList<Disease>();

		for (String token : diseaseTokens) {

			Disease disease = new Disease();
			disease.setName(token);
			diseaseList.add(disease);
			if (!createdObject(token)){	
				disease = null;
			}
		}

		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);
		if (!createdObject(mirna_name)){	
			mirna = null;
		}
		
		Target target = new Target();
		
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(ref_seq);
		if (!createdObject(ref_seq)){	
			transcript = null;
		}
		
		SNP snp = new SNP();
		snp.setSnp_id(snp_id);
		if (!createdObject(snp_id)){	
			snp = null;
		}

		InteractionData id = new InteractionData();
		id.setProvenance("mirdSNP");

		ExpressionData ed = new ExpressionData();
		ed.setProvenance("mirdSNP");
		
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
		
		
		if(snp !=null){

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
		
		
		SnpHasGene snpHasGene = new SnpHasGene(snp.getPk(), gene.getPk());
		Object oldSnphasGene = session.createCriteria(SnpHasDisease.class)
				.add( Restrictions.eq("snpPk", snp.getPk()) )
				.add( Restrictions.eq("genePk", gene.getPk()) )
				.uniqueResult();
		if (oldSnphasGene==null) {
			session.save(snpHasGene);
		}
		}
		
		if(transcript !=null){
		
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
		
		if(gene !=null){

		
		TranscriptHasGene transcripthasGene =
				new TranscriptHasGene(transcript.getPk(), gene.getPk());
		Object oldTranscripthasGene = session.createCriteria(TranscriptHasGene.class)
				.add(Restrictions.eq("transcriptPk", transcript.getPk()))
				.add(Restrictions.eq("genePk", gene.getPk()))
				.uniqueResult();
		if (oldTranscripthasGene == null) {
	        session.save(transcripthasGene);
		}
		
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK
		}
		
		}
		
		for (Disease disease: diseaseList){

			if(disease != null){
			
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
			
			if(snp !=null){

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
			}
		
		}
		
		
		if(mirna !=null){

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
		
		id.setMirna_pk(mirna.getPk());
		id.setGene_pk(gene.getPk());
		id.setTarget_pk(target.getPk());
		session.save(id);
		session.flush();

		ed.setMirnaPk(mirna.getPk());
		ed.setInteraction_data_pk(id.getPk()); // Fixed
		session.save(ed);
		
		}
	}
	
	protected String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "_".equals(field.trim()) ? null : field.trim();	}

	
	
	public static void main(String[] args) throws Exception {
		
		MiRdSNP4 miRdSNP4= new MiRdSNP4();
		miRdSNP4.insertIntoSQLModel();
		
		
	}

}