package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.integration.beans.Gene;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.beans.nToM.TranscriptHasGene;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
//import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Microcosm
 * 
 * @author Esteban López Camacho
 *
 */
public class Microcosm extends NewMirnaDatabase {

	private final static String TABLE_NAME = "microcosm_homo_sapiens";

	public Microcosm() throws MiRnaException { super(TABLE_NAME); }

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

			while ((line = br.readLine()) != null) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {

					if ((!"".equals(line)) && (!line.startsWith("##"))) {

						String group = tokens[0];
						String seq = tokens[1];
						String method = tokens[2];
						String feature = tokens[3];
						String chr = tokens[4];
						String start = tokens[5];
						String end = tokens[6];
						String strand = tokens[7];
						String phase = tokens[8];
						String score = tokens[9];
						String pvalueOg = tokens[10];
						String transcriptId = tokens[11];
						String externalName = tokens[12];

						String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
								+ group + "','"
								+ seq + "','"
								+ method + "','"
								+ feature + "','"
								+ chr + "','"
								+ start + "','"
								+ end + "','"
								+ strand + "','"
								+ phase + "','"
								+ score + "','"
								+ pvalueOg + "','"
								+ transcriptId + "','"
								+ externalName + "')";

						stmt.executeUpdate(query);

					}

				}

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
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String seq = nullifyField(rs.getString("seq"));
		String method = nullifyField(rs.getString("method"));
		String feature = nullifyField(rs.getString("feature").toLowerCase().trim());
		String chromosome = nullifyField(rs.getString("chr").toLowerCase().trim());
		String start = nullifyField(rs.getString("start").toLowerCase().trim());
		String end = nullifyField(rs.getString("end").toLowerCase().trim());
		String strand = nullifyField(rs.getString("strand").toLowerCase().trim());
		String phase = nullifyField(rs.getString("phase"));
		String score = nullifyField(rs.getString("score"));
		String pvalue_og = nullifyField(rs.getString("pvalue_og"));
		String transcriptId = nullifyField(rs.getString("transcript_id"));
		String genename = nullifyField(rs.getString("external_name"));

		MiRna miRna = new MiRna();
		miRna.setName(seq);

		InteractionData id = new InteractionData();
		id.setMethod(method);
		id.setFeature(feature);
		id.setPhase(phase);
		id.setScore(score);
		id.setPvalue_og(pvalue_og);
		id.setProvenance("Microcosm");

		Target target = new Target();
		target.setChromosome(chromosome);
		target.setPolarity(strand);
		target.setBinding_site_start(start);
		target.setBinding_site_end(end);

		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcriptId);

		Gene gene = new Gene();
		gene.setName(genename);

		if (!createdObject(genename)) { 
			
			gene=null;

		}

		// Inserta MiRna (o recupera su id. si ya existe)

		Object oldMiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name", miRna.getName()))
				.uniqueResult();
		if (oldMiRna == null) {
			//System.out.println("SALVANDO:");
			//System.out.println(miRna);
			session.save(miRna);
			session.flush(); // to get the PK
		} else {
			MiRna miRnaToUpdate = (MiRna) oldMiRna;
			miRnaToUpdate.update(miRna);
			session.update(miRnaToUpdate);
			miRna = miRnaToUpdate;
		}

		if(gene != null){
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()))
					//.add(Restrictions.ilike("name", gene.getName(), MatchMode.EXACT))
					.uniqueResult();	
			if (oldGene == null) {
				session.save(gene);
				session.flush(); // to get the PK
			} else {
				Gene geneToUpdate = (Gene) oldGene;
				geneToUpdate.update(gene);
				session.update(geneToUpdate);
				gene = geneToUpdate;
			}
		}
		// Inserta Transcript (o recupera su id. si ya existe)
		//transcript.setGeneId(gene.getPk());
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

		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK

		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Target)

		id.setMirna_pk(miRna.getPk());
		if(target != null) id.setTarget_pk(target.getPk());
		if(gene != null) id.setGene_pk(gene.getPk());
		session.save(id);

	}


	public static void main(String[] args) throws Exception {

		Microcosm microcosm = new Microcosm();
		microcosm.insertIntoSQLModel();

	}

}