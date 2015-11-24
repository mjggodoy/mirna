package mirna.integration.database.reptar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.integration.beans.Complex;
import mirna.integration.beans.Gene;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.beans.nToM.TranscriptHasGene;
import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;

public class RepTar_human extends NewMirnaDatabase {

	private  static final String TABLE_NAME = "repTar_human";
	
	public RepTar_human() throws MiRnaException {
		super(TABLE_NAME);
		this.fetchSizeMin = true;

	}

	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null, tokens2 = null, tokens3 = null, tokens4 = null, tokens5= null, tokens6 = null, tokens7 = null, tokens8 = null, tokens9 = null, tokens10 = null, tokens11 = null;
		//		String specie = "";

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
				String gene_token = tokens[0];

				tokens2 = StringUtils.splitPreserveAllTokens(gene_token, ":::");

				String gene_symbol  = tokens2[0];
				String gene_accesion = tokens2[3];

				String mirna = tokens[1];

				String sequence_start = tokens[2];
				tokens3 = StringUtils.splitPreserveAllTokens(sequence_start, ":");
				sequence_start = tokens3[1];

				String sequence_end = tokens[3];
				tokens4 = StringUtils.splitPreserveAllTokens(sequence_end, ":");
				sequence_end = tokens4[1];

				String minimal_free_energy = tokens[4];
				tokens5 = StringUtils.splitPreserveAllTokens(minimal_free_energy, ":");
				minimal_free_energy = tokens5[1];

				String normalized_free_energy = tokens[5];
				tokens6 = StringUtils.splitPreserveAllTokens(normalized_free_energy, ":");	
				normalized_free_energy = tokens6[1];

				String gu_proportion = tokens[6];	
				tokens7 = StringUtils.splitPreserveAllTokens(gu_proportion, ":");	
				gu_proportion = tokens7[1];		

				String binding_site_pattern = tokens[8].replaceAll("'", "\\\\'");	
				tokens8 = StringUtils.splitPreserveAllTokens(binding_site_pattern, ":");
				binding_site_pattern = tokens8[1];	

				String site_conservation_score = tokens[9];
				tokens9 = StringUtils.splitPreserveAllTokens(site_conservation_score, ":");
				site_conservation_score	= tokens9[1];

				String UTR_conservation_score = tokens[10];
				tokens10 = StringUtils.splitPreserveAllTokens(UTR_conservation_score, ":");
				UTR_conservation_score	= tokens10[1];

				String repeated_motifs = tokens[11];
				tokens11 = StringUtils.splitPreserveAllTokens(repeated_motifs, ":");
				repeated_motifs	= tokens11[1];

				String algorithm = tokens[12];


				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ gene_symbol + "','"
						+ gene_accesion + "','"
						+ mirna + "','"
						+ sequence_start + "','"
						+ sequence_end + "','"
						+ minimal_free_energy + "','"
						+ normalized_free_energy + "','"
						+ gu_proportion + "','"
						+ binding_site_pattern + "','"
						+ site_conservation_score + "','"
						+ UTR_conservation_score + "','"
						+ repeated_motifs + "','"
						+ algorithm + "')";

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
	protected void processRow(Session session, ResultSet rs) throws Exception {
	
		String gene_symbol = rs.getString("gene_symbol");
		String gene_accesion = rs.getString("gene_accesion");
		String mirna_name = rs.getString("mirna").toLowerCase().trim();
		String binding_site_start_position = rs.getString("sequence_start").toLowerCase().trim();
		String binding_site_end_position = rs.getString("sequence_end").toLowerCase().trim();
		String minimal_free_energy = rs.getString("minimal_free_energy").toLowerCase().trim();
		String normalized_free_energy = rs.getString("normalized_free_energy").toLowerCase().trim();
		String site_conservation_score = rs.getString("site_conservation_score");
		String binding_site_pattern = rs.getString("binding_site_pattern");
		String gu_proportion = rs.getString("gu_proportion");
		String UTR3_conservation_score = rs.getString("UTR_conservation_score");
		String repeated_motifs = rs.getString("repeated_motifs");
		@SuppressWarnings("unused")
		String algorithm = rs.getString("algorithm");

		MiRna miRna = new MiRna();
		miRna.setName(mirna_name);

		Gene gene = new Gene();
		//gene.setAccessionumber(gene_accesion);
		gene.setName(gene_symbol);

		Target target = new Target();
		target.setBinding_site_start(binding_site_start_position);
		target.setBinding_site_end(binding_site_end_position);
		target.setGu_proportion(gu_proportion);
		target.setUtr3_conservation_score(UTR3_conservation_score);
		target.setSite_conservation_score(site_conservation_score);
		target.setRepeated_motifs(repeated_motifs);
		
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(gene_accesion);

		Complex complex = new Complex();
		complex.setMinimal_free_energy(minimal_free_energy);
		complex.setNormalized_minimal_free_energy(normalized_free_energy);
		complex.setBinding_site_pattern(binding_site_pattern);

		InteractionData id = new InteractionData();
		id.setProvenance("repTar");

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

		// Relaciona interaction data con mirna y target
		id.setMirna_pk(miRna.getPk());
		id.setTarget_pk(target.getPk());
		id.setGene_pk(gene.getPk());
		session.save(id);
		session.flush();

		// Relaciona Complex con InteractionData/Target/miRNA
		complex.setInteraction_data_pk(id.getPk());
		complex.setTarget_pk(target.getPk());
		complex.setMirna_pk(miRna.getPk());
		session.save(complex);
		session.flush();
		
	}
	
}
