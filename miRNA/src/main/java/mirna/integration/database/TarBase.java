package mirna.integration.database;

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

import mirna.integration.beans.Disease;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.Gene;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Organism;
import mirna.integration.beans.Protein;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.Sequence;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasOrganism;
import mirna.integration.beans.nToM.MirnaHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasSequence;
import mirna.integration.beans.nToM.TranscriptHasGene;
import mirna.integration.beans.nToM.TranscriptProducesProtein;
import mirna.integration.exception.MiRnaException;

/**
 * Código para procesar los datos de TarBase
 * 
 * @author Esteban López Camacho
 *
 */
public class TarBase extends NewMirnaDatabase {

	/*
	 * Valores que pueden ser vacios en la BD
	 * mirna_seq OK
	 * kegg
	 * protein_type
	 * gene_expression
	 * tumour_involv
	 * bib
	 * aux
	 */

	private final static String TABLE_NAME = "tarBase";

	public TarBase() throws MiRnaException { super(TABLE_NAME); }

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

			while ((line = br.readLine()) != null) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {

					//String mir = tokens[0];
					//String disease = tokens[1].replaceAll("'", "\\\\'");
					String id = tokens[0];
					String idV4 = tokens[1];
					String dataType = tokens[2];
					String supportType = tokens[3];
					String organism = tokens[4];
					String miRna = tokens[5];
					String hgncSymbol = tokens[6];
					String gene = tokens[7];
					String isoform = tokens[8];
					String ensembl = tokens[9];
					String chrLoc = tokens[10];
					String mre = tokens[11];
					String sss = tokens[12];
					String is = tokens[13].replaceAll("'", "\\\\'");
					String ds = tokens[14].replaceAll("'", "\\\\'");
					String validation = tokens[15];
					String paper = tokens[16].replaceAll("'", "\\\\'");
					String targetSeq = tokens[17];
					String mirnaSeq = tokens[18];
					String seqLocation = tokens[19];
					String pmid = tokens[20];
					String kegg = tokens[21];
					String proteinType = tokens[22];
					String difExprIn = tokens[23];
					String pathologyOrEvent = tokens[24];
					String misRegulation = tokens[25];
					String geneExpression = tokens[26];
					String tumourInvolv = tokens[27].replaceAll("'", "\\\\'");
					String bib = tokens[28].replaceAll("'", "\\\\'");
					String cellLineUsed = tokens[29];
					String hgncId = tokens[30];
					String swissProt = tokens[31];
					String aux = tokens[32];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ id + "','"
							+ idV4 + "','"
							+ dataType + "','"
							+ supportType + "','"
							+ organism + "','"
							+ miRna + "','"
							+ hgncSymbol + "','"
							+ gene + "','"
							+ isoform + "','"
							+ ensembl + "','"
							+ chrLoc + "','"
							+ mre + "','"
							+ sss + "','"
							+ is + "','"
							+ ds + "','"
							+ validation + "','"
							+ paper + "','"
							+ targetSeq + "','"
							+ mirnaSeq + "','"
							+ seqLocation + "','"
							+ pmid + "','"
							+ kegg + "','"
							+ proteinType + "','"
							+ difExprIn + "','"
							+ pathologyOrEvent + "','"
							+ misRegulation + "','"
							+ geneExpression + "','"
							+ tumourInvolv + "','"
							+ bib + "','"
							+ cellLineUsed + "','"
							+ hgncId + "','"
							+ swissProt + "','"
							+ aux + "')";

					stmt.executeUpdate(query);
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

		String id_tarbase = nullifyField(rs.getString("id"));
		//String idv4 = nullifyField(rs.getString("id_v4")); // This field is not going to be used!
		String dataType = nullifyField(rs.getString("data_type"));
		//String supportType = nullifyField(rs.getString("support_type")); // This field is not going to be used!
		String specie = nullifyField(rs.getString("organism"));
		String miRna = nullifyField(rs.getString("miRNA"));
		String hgncSymbol = nullifyField(rs.getString("hgnc_symbol"));
		String gene_name = nullifyField(rs.getString("gene"));
		String isoform = nullifyField(rs.getString("isoform"));
		String ensembl = nullifyField(rs.getString("ensembl"));
		String chrLoc = nullifyField(rs.getString("chr_loc"));
		String is = nullifyField(rs.getString("i_s"));
		//String ds = nullifyField(rs.getString("d_s"));
		String paper = nullifyField(rs.getString("paper"));
		String targetSeq = nullifyField(rs.getString("target_seq"));
		String mirnaSeq =nullifyField(rs.getString("mirna_seq"));
		//String seqLocation = nullifyField(rs.getString("seq_location")); // This field is not going to be used!!
		String pmid = nullifyField(rs.getString("pmid"));
		String kegg = nullifyField(rs.getString("kegg"));
		String protein_type = nullifyField(rs.getString("protein_type"));
		String different_expression = nullifyField(rs.getString("dif_expr_in"));
		String pathology_or_event = nullifyField(rs.getString("pathology_or_event"));
		String mis_regulation = nullifyField(rs.getString("mis_regulation"));
		String gene_expression = nullifyField(rs.getString("gene_expression"));
		//String type_tumour = nullifyField(rs.getString("tumour_involv"));// This is not going to be used.
		String bib = nullifyField(rs.getString("bib"));
		String cell_line_used = nullifyField(rs.getString("cell_line_used"));
		String hgnc_id = nullifyField(rs.getString("hgnc_id"));
		String swiss_prot = nullifyField(rs.getString("swiss_prot"));
		//String aux = nullifyField(rs.getString("aux")); // I know... this is not going to be used, is it?

		InteractionData id = new InteractionData();
		id.setReference(paper);
		id.setProvenance("TarBase");

		Organism organism = new Organism();
		organism.setName(specie);

		MiRna mirna = new MiRna();

		if(organism.getName().equals("C. elegans")){

			miRna = "cel"+"-"+miRna;
			mirna.setName(miRna);

		}else if (organism.getName().equals("D.rerio")){ 

			miRna = "dre"+"-"+miRna;
			mirna.setName(miRna);

		}else if(organism.getName().equals("Homo sapiens")){

			miRna = "hsa"+"-"+miRna;
			mirna.setName(miRna);

		}else if (organism.getName().equals("Drosophila")){

			miRna = "dme"+"-"+miRna;
			mirna.setName(miRna);

		}else if (organism.getName().equals("Mouse")){

			miRna = "mmu"+"-"+miRna;
			mirna.setName(miRna);

		}else if(organism.getName().equals("Rat")){

			miRna = "rno"+"-"+miRna;
			mirna.setName(miRna);

		}else{

			mirna.setName(miRna);
		}
		
		boolean vePaTarget = false;
		//boolean relacionaGeneConOrganism = true;
		String nombreTransformer = mirna.getName();
		if(nombreTransformer.contains("Edited-")) {
			mirna.setName(nombreTransformer.replace("Edited-", ""));
		}
		
		if (nombreTransformer.contains("HSV1-")) {
			vePaTarget = true;
		}
		
		/*
		if (nombreTransformer.contains("LNA_")) {
			vePaTarget = true;
		}
		if (nombreTransformer.contains("K12")) {
			vePaTarget = false;
			relacionaGeneConOrganism = false;
		}
		*/
	
		//System.out.println(mirna.getName() + " : " + organism.getName() + " : " + vePaTarget);

		Gene gene = new Gene();
		gene.setName(gene_name);
		gene.setLocation(chrLoc);
		if (kegg != null) {

			gene.setKegg_id(kegg);

		}

		if (gene_expression != null){
			gene.setExpression_site(gene_expression);
			//String[] tokens = StringUtils.splitPreserveAllTokens(gene_expression, ",");
			//for(String token: tokens){
			//	gene.setExpression_site(token);
			//	if (!createdObject(token.trim(), gene_expression));
			//}
		}

		gene.setHgnc_symbol(hgncSymbol);
		gene.setEnsembl_id(ensembl);
		gene.setHgnc_id(hgnc_id);
		if (!createdObject(gene_name, chrLoc, kegg, gene_expression, hgncSymbol, ensembl, hgnc_id)) {
			gene = null;
		}

		String[] proteinTokens = StringUtils.splitPreserveAllTokens(swiss_prot, ",");

		List<Protein> proteinList = new ArrayList<Protein>();

		if(proteinTokens !=null){

			for (String token : proteinTokens) {


				Protein protein = new Protein();
				protein.setUniprot_id(token.trim());
				protein.setType(protein_type);
				proteinList.add(protein);

				if (!createdObject(token.trim(), protein_type)){

					protein = null;

				}

			}
		}

		Target target = new Target();

		Transcript transcript = new Transcript();
		transcript.setIsoform(isoform);
		if (!createdObject(isoform)) {
			transcript = null;
		}

		Disease disease = new Disease();
		disease.setName(pathology_or_event);
		if (!createdObject(pathology_or_event)) {
			disease = null;
		}
		
		ExpressionData ed = new ExpressionData();
		ed.setDataType(dataType);
		ed.setMethod(is);
		ed.setEvidence(mis_regulation);
		ed.setCellularLine(cell_line_used);
		ed.setDifferentExpressionLocation(different_expression);
		ed.setProvenanceId(id_tarbase);
		ed.setDescription(bib);
		ed.setProvenance("TarBase");

		PubmedDocument pubmed = new PubmedDocument();
		pubmed.setId(pmid);
		if (!createdObject(pmid)) {
			pubmed = null;
		}

		Sequence sequence_mirna = new Sequence();
		sequence_mirna.setSequence(mirnaSeq);
		if (!createdObject(mirnaSeq)) {
			sequence_mirna = null;
		}

		Sequence sequence_target = new Sequence();
		sequence_target.setSequence(targetSeq);
		if (!createdObject(targetSeq)) {
			sequence_target = null;
		}

		// INSERCIONES EN BD

		// Inserta Disease (o recupera su id. si ya existe)
		if (disease != null) {
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
		}

		// Insertar Organism (o recupera su id. si ya existe)
		Object oldOrganism = session.createCriteria(Organism.class)
				.add( Restrictions.eq("name", organism.getName()) )
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

		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name", mirna.getName()) )
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

		if (vePaTarget==false) {
			MirnaHasOrganism mirnaHasOrganism = 
					new MirnaHasOrganism(mirna.getPk(), organism.getPk());
			// Relaciona organism con Mirna (si no lo estaba ya)
			Object oldMirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
					.add( Restrictions.eq("mirna_pk", mirna.getPk()) )
					.add( Restrictions.eq("organism_pk", organism.getPk()) )
					.uniqueResult();
			if (oldMirnaHasOrganism==null) {
				session.save(mirnaHasOrganism);
			}
		}

		// Inserta Sequence (o recupera su id. si ya existe)
		if (sequence_mirna!=null) {
			Object oldSequence1 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence_mirna.getSequence()))
					.uniqueResult();
			if (oldSequence1 == null) {
				session.save(sequence_mirna);
				session.flush(); // to get the PK
			} else {
				Sequence sequenceToUpdate = (Sequence) oldSequence1;
				sequenceToUpdate.update(sequence_mirna);
				session.update(sequenceToUpdate);
				sequence_mirna = sequenceToUpdate;
			}

			MirnaHasSequence mirnaHasSequence =
					new MirnaHasSequence(mirna.getPk(), sequence_mirna.getPk());
			// Relaciona Sequence con Mirna (si no lo estaba ya)
			Object oldMirnaHasSequence = session.createCriteria(MirnaHasSequence.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("sequencePk", sequence_mirna.getPk()) )
					.uniqueResult();
			if (oldMirnaHasSequence==null) {
				session.save(mirnaHasSequence);
			}
			
		}

		// Inserta Gene (o recupera su id. si ya existe)
		if (gene!=null) {
			//if (relacionaGeneConOrganism) gene.setOrganism_pk(organism.getPk());
			Object oldGene = session.createCriteria(Gene.class)
					.add( Restrictions.eq("name", gene.getName()) )
					.uniqueResult();
			if (oldGene==null) {
				session.save(gene);
				session.flush(); // to get the PK
			} else {
				Gene geneToUpdate = (Gene) oldGene;
				geneToUpdate.update(gene);
				session.update(geneToUpdate);
				gene = geneToUpdate;
			}
		}

		// Insertar Sequence (o recupera su id. si ya existe)
		if (sequence_target!=null) {
			Object oldSequence2 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence_target.getSequence()))
					.uniqueResult();
			if (oldSequence2 == null) {
				session.save(sequence_target);
				session.flush(); // to get the PK
			} else {
				Sequence sequenceToUpdate = (Sequence) oldSequence2;
				sequenceToUpdate.update(sequence_target);
				session.update(sequenceToUpdate);
				sequence_target = sequenceToUpdate;
			}
		}

		if (transcript!=null) {

			//Inserta transcript (o recupera us id. si ya existe)
			Object oldTranscript = session.createCriteria(Transcript.class)
					.add( Restrictions.eq("isoform", transcript.getIsoform()))
					.uniqueResult();
			if (oldTranscript==null) {
				session.save(transcript);
				session.flush(); // to get the PK
			} else {
				Transcript transcriptToUpdate = (Transcript) oldTranscript;
				transcriptToUpdate.update(transcript);
				session.update(transcriptToUpdate);
				transcript = transcriptToUpdate;
			}
			
			if (gene!=null) {
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

		// Inserta nueva Target
		if (transcript!=null) target.setTranscript_pk(transcript.getPk());
		if (sequence_target!=null) target.setSequence_pk(sequence_target.getPk());
		if (vePaTarget) target.setOrganism_pk(organism.getPk());
		session.save(target);

		// Inserta PubmedDocument (o recupera su id. si ya existe)
		if (pubmed!=null) {
			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add( Restrictions.eq("id", pubmed.getId()) )
					.uniqueResult();
			if (oldPubmedDoc==null) {
				session.save(pubmed);
				session.flush(); // to get the PK
			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmed);
				session.update(pubmedDocToUpdate);
				pubmed= pubmedDocToUpdate;
			}
		}

		// Insertar Protein (o recupera su id. si ya existe)
		for (Protein protein : proteinList) {
			if(protein !=null){
				Object oldProtein = session.createCriteria(Protein.class)
						.add( Restrictions.eq("uniprot_id", protein.getUniprot_id()) )
						.uniqueResult();
				if (oldProtein==null) {
					session.save(protein);
					session.flush(); // to get the PK
				} else {
					Protein proteinToUpdate = (Protein) oldProtein;
					proteinToUpdate.update(protein);
					session.update(proteinToUpdate);
					protein = proteinToUpdate;
				}


				if (transcript!=null) {
					//Relaciona transcript con protein
					TranscriptProducesProtein transcriptProtein = new TranscriptProducesProtein(transcript.getPk(), protein.getPk());	
					Object transcriptProducesProtein = session.createCriteria(TranscriptProducesProtein.class)
							.add( Restrictions.eq("transcript_pk", transcript.getPk()) )
							.add( Restrictions.eq("protein_pk", protein.getPk()) )
							.uniqueResult();
					if (transcriptProducesProtein==null) {
						session.save(transcriptProtein);
					}
				}
			}

		}

		//Inserta nueva InteractionData (y la relaciona con lo que toque)
		if (mirna!=null) id.setMirna_pk(mirna.getPk());
		if (gene!=null) {id.setGene_pk(gene.getPk());}
		id.setTarget_pk(target.getPk());
		session.save(id);
		session.flush();

		//Inserta nueva DataExpression (y la relaciona con MiRna y Disease correspondiente)
		ed.setMirnaPk(mirna.getPk());
		ed.setInteraction_data_pk(id.getPk()); // Fixed
		if (disease!=null) ed.setDiseasePk(disease.getPk());
		session.save(ed);
		session.flush();



		if (pubmed!=null) {
			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(mirna.getPk(), pubmed.getPk());

			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(ed.getPk(), pubmed.getPk());

			// Relaciona mirna con PubmedDocument
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmed.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}

			// Relaciona PubmedDocument con ExpressionData
			session.save(expresDataHasPubmedDocument);
		}

	}

	protected String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "_".equals(field.trim()) ? null : field.trim();
	}

	public static void main(String[] args) throws Exception {

		TarBase tarBase = new TarBase();
		tarBase.insertIntoSQLModel();

	}

}