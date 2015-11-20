package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.beans.BiologicalProcess;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.Hairpin;
import mirna.beans.InteractionData;
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Protein;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasHairpin;
import mirna.beans.nToM.MirnaHasMature;
import mirna.beans.nToM.MirnaHasOrganism;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.beans.nToM.MirnaHasSequence;
import mirna.beans.nToM.MirnaInvolvesBiologicalProcess;
import mirna.beans.nToM.TranscriptHasGene;
import mirna.beans.nToM.TranscriptProducesProtein;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class VirmiRNA2 extends NewMirnaDatabase {

	private static final String TABLE_NAME = "virmirna2";

	public VirmiRNA2() throws MiRnaException {
		super(TABLE_NAME);
	}

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

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
				String AVM_id = tokens[0];
				String miRNA = tokens[1];
				String miRNA_sequence = tokens[2];
				String miRBase_id = tokens[3];
				String specie = tokens[4];
				String virus = tokens[5];
				String virus_full_name = tokens[6];
				String taxonomy = tokens[7];
				String target = tokens[8].replaceAll("'", "\\\\'");;
				String Uniprot = tokens[9];
				String target_process = tokens[10];
				String method = tokens[11].replaceAll("'", "\\\\'");;
				String cell_line = tokens[12];
				String target_sequence = tokens[13];
				String target_region = tokens[14].replaceAll("'", "\\\\'");
				String target_coordinates = tokens[15];
				String seed_match = tokens[16];
				String target_reference = tokens[17];
				String pubmed_id = tokens[18];

				int index = pubmed_id.indexOf("/");
				int index1 = pubmed_id.indexOf("\">");
				pubmed_id = pubmed_id.substring(index, index1+2);

				if ((tokens.length>20) || ((tokens.length==20) && (!"".equals(tokens[19])))) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ AVM_id + "','"
						+ miRNA + "','"
						+ miRNA_sequence + "','"
						+ miRBase_id + "','"
						+ specie + "','"
						+ virus + "','"
						+ virus_full_name + "','"
						+ taxonomy + "','"
						+ target + "','"
						+ Uniprot + "','"
						+ target_process + "','"
						+ method + "','"
						+ cell_line  + "','"
						+ target_sequence  + "','"
						+ target_region  + "','"
						+ target_coordinates  + "','"
						+ seed_match  + "','"
						+ target_reference + "','"
						+ pubmed_id + "')";


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
	public void processRow(Session session, ResultSet rs) throws Exception {

		//String pk = rs.getString("pk");
		String id_virus = nullifyField(rs.getString("avm_id"));
		String mirna_name = nullifyField(rs.getString("mirna"));
		String mirna_seq = nullifyField(rs.getString("mirna_sequence"));
		String accesion_number = nullifyField(rs.getString("mirbase_id"));
		String specie_mirna = nullifyField(rs.getString("specie"));
		@SuppressWarnings("unused")
		String organism_name = nullifyField(rs.getString("virus"));
		@SuppressWarnings("unused")
		String organism_name_full = nullifyField(rs.getString("virus_full_name"));
		@SuppressWarnings("unused")
		String resource_organism = nullifyField(rs.getString("taxonomy"));
		String target1= nullifyField(rs.getString("target"));
		String uniprot = nullifyField(rs.getString("uniprot"));
		String target_process = nullifyField(rs.getString("target_process"));
		String method = nullifyField(rs.getString("method"));
		String cell_line = nullifyField(rs.getString("cell_line"));
		String target_sequence = nullifyField(rs.getString("target_sequence"));
		String target_region = nullifyField(rs.getString("target_region"));
		String target_coords = nullifyField(rs.getString("target_coords"));
		String send_match = nullifyField(rs.getString("seed_match"));
		String target_resource = nullifyField(rs.getString("target_ref"));
		String target_pubmedId = nullifyField(rs.getString("pubmed_id"));

		if (accesion_number!=null) {
			accesion_number = accesion_number.substring(accesion_number.indexOf("=")+1);
		}

		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);
		if (!createdObject(mirna_name)) {
			mirna = null;
		}

		Hairpin hairpin = null;
		if(accesion_number!=null && accesion_number.startsWith("MI") && !accesion_number.contains("MIMAT")){
			hairpin = new Hairpin();
			hairpin.setAccession_number(accesion_number);
		}

		Mature mature = null;
		if(accesion_number!=null && accesion_number.contains("MIMAT")){
			mature = new Mature();
			mature.setAccession_number(accesion_number);
		}

		Sequence sequence1 = new Sequence();
		sequence1.setSequence(mirna_seq);
		if (!createdObject(mirna_seq)) {
			sequence1 = null;
		}

		Sequence sequence2 = new Sequence();
		sequence2.setSequence(target_sequence);
		if (!createdObject(target_sequence)) {
			sequence2 = null;
		}

		Organism organism1 = new Organism();
		organism1.setName(specie_mirna);// included (:P) set/get en la clase organism.
		if (!createdObject(specie_mirna)) {
			organism1 = null;
		}

		Target target = new Target();
		target.setRegion(target_region);
		target.setSeed_match(send_match);
		target.setCoordinates(target_coords);
		target.setTarget_ref(target_resource);
		if (!createdObject(target_region, send_match, target_coords, target_resource)) {
			target = null;
		}

		BiologicalProcess biologicalprocess = new BiologicalProcess();
		biologicalprocess.setName(target_process);
		if (!createdObject(target_process)) {
			biologicalprocess = null;
		}

		ExpressionData expressiondata = new ExpressionData();
		expressiondata.setCellularLine(cell_line);
		expressiondata.setMethod(method);
		expressiondata.setProvenanceId(id_virus);
		expressiondata.setProvenance("VirmiRNA");

		Gene gene = new Gene();
		gene.setName(target1);
		
		if (!createdObject(target1)) {
			gene = null;
		}

		Protein protein = new Protein();
		protein.setUniprot_id(uniprot);
		if (!createdObject(uniprot)) {
			protein = null;
		}

		Transcript transcript = new Transcript();

		//PubmedDocument pubmedDoc = new PubmedDocument();
		List<PubmedDocument> pubmedDocList = new ArrayList<PubmedDocument>();
		while(target_pubmedId.contains("<a")){
			int startIndex = target_pubmedId.indexOf("<a");
			int endIndex = target_pubmedId.indexOf("</a>");
			String link = target_pubmedId.substring(startIndex, endIndex);
			if (link.contains("http://www.ncbi.nlm.nih.gov/pubmed/")) {
				String pubmedIds = link.substring(link.indexOf(">")+1);
				String[] pubmedIdArray = StringUtils.splitPreserveAllTokens(pubmedIds, ",");
				for (String pid : pubmedIdArray) {
					PubmedDocument pd = new PubmedDocument();
					pd.setId(pid.trim());
					pubmedDocList.add(pd);
				}
			//}else if (link.contains("http://www.patentlens.net/patentlens/patents.html?patnums=")){
			//	pubmedDoc = null;
			}
			target_pubmedId = target_pubmedId.substring(link.length()+4);
		}
		/*
		if (pubmedDocList.size()>0) {
			String pua = pk + ": ";
			for (PubmedDocument pd : pubmedDocList) {
				pua += pd.getId() + ",";
			}
			System.out.println(pua);
		}
		*/

		InteractionData interactiondata = new InteractionData();

		// Inserta Sequence (o recupera su id. si ya existe)
		if(sequence1 != null){
			Object oldSequence1 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence1.getSequence()))
					.uniqueResult();
			if (oldSequence1 == null) {
				session.save(sequence1);
				session.flush(); // to get the PK
			} else {
				sequence1 = (Sequence) oldSequence1;
			}
		}

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
		}

		if(mirna !=null){
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

			if(sequence1 != null ){
				MirnaHasSequence mirnaHasSequence =
						new MirnaHasSequence(mirna.getPk(), sequence1.getPk());
				// Relaciona Sequence con Mirna (si no lo estaba ya)
				Object oldMirnaHasSequence = session.createCriteria(MirnaHasSequence.class)
						.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
						.add( Restrictions.eq("sequencePk", sequence1.getPk()) )
						.uniqueResult();
				if (oldMirnaHasSequence==null) {
					session.save(mirnaHasSequence);
				}
			}

		}
		
		// NO COPIAR ESTE CODIGO (ESPECIFICO PARA VIRMIRNA2)
		if(hairpin !=null){
			session.save(hairpin);
			session.flush(); // to get the PK
			
			MirnaHasHairpin mirnaHasHairpin = 
					new MirnaHasHairpin(mirna.getPk(), hairpin.getPk());
			session.save(mirnaHasHairpin);
		}

		// NO COPIAR ESTE CODIGO (ESPECIFICO PARA VIRMIRNA2)
		if(mature !=null){
			session.save(mature);
			session.flush(); // to get the PK
			
			MirnaHasMature mirnaHasMature = 
					new MirnaHasMature(mirna.getPk(), mature.getPk());
			session.save(mirnaHasMature);
		}

		if(organism1 != null){

			// Inserta Organism (o recupera su id. si ya existe)
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism1.getName()) )
					.uniqueResult();
			if (oldOrganism==null) {
				session.save(organism1);
				session.flush(); // to get the PK
			} else {
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism1);
				session.update(organismToUpdate);
				organism1 = organismToUpdate;
			}

			if(mirna != null){
				MirnaHasOrganism mirnaHasOrganism = 
						new MirnaHasOrganism(mirna.getPk(), organism1.getPk());

				// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
				Object oldmirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
						.add( Restrictions.eq("mirna_pk", mirna.getPk()) )
						.add( Restrictions.eq("organism_pk", organism1.getPk()) )
						.uniqueResult();
				if (oldmirnaHasOrganism==null) {
					session.save(mirnaHasOrganism);

				}
			}

		}

		if (sequence2!=null) {
			Object oldSequence2 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence2.getSequence()))
					.uniqueResult();
			if (oldSequence2 == null) {
				session.save(sequence2);
				session.flush(); // to get the PK
			} else {
				sequence2 = (Sequence) oldSequence2;
			}
		}

		if(gene != null){
			//System.out.println("Intento salvar gene: " + gene);
			// Inserta Gene (o recupera su id. si ya existe)
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()) )
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

		// Inserta BiologicalProcess (o recupera su id. si ya existe)
		if(biologicalprocess != null){
			Object oldBiologicalProcess = session.createCriteria(BiologicalProcess.class)
					.add(Restrictions.eq("name", biologicalprocess.getName()) )
					.uniqueResult();
			if (oldBiologicalProcess==null) {
				session.save(biologicalprocess);
				session.flush(); // to get the PK
			} else {
				BiologicalProcess biologicalProcessToUpdate = (BiologicalProcess) oldBiologicalProcess;
				biologicalProcessToUpdate.update(biologicalprocess);
				session.update(biologicalProcessToUpdate);
				biologicalprocess = biologicalProcessToUpdate;
			}

			if(mirna != null){
				MirnaInvolvesBiologicalProcess mirnaInvolvesBiologicalProcess =
						new MirnaInvolvesBiologicalProcess(mirna.getPk(), biologicalprocess.getPk());
				Object oldMirnInvolvesBiologicalProcess = session.createCriteria(MirnaInvolvesBiologicalProcess.class)
						.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
						.add( Restrictions.eq("biological_process_pk", biologicalprocess.getPk()) )
						.uniqueResult();
				if (oldMirnInvolvesBiologicalProcess==null) {
					session.save(mirnaInvolvesBiologicalProcess);
				}
			}
		}

		session.save(transcript);
		session.flush();

		if(gene != null){
			TranscriptHasGene transcriptGene = new TranscriptHasGene(transcript.getPk(), gene.getPk());	
			Object transcriptHasGene = session.createCriteria(TranscriptHasGene.class)
					.add( Restrictions.eq("transcriptPk", transcript.getPk()) )
					.add( Restrictions.eq("genePk", gene.getPk()) )
					.uniqueResult();
			if (transcriptHasGene==null) {
				session.save(transcriptGene);
			}
		}

		if(protein != null){
			TranscriptProducesProtein transcriptProtein = new TranscriptProducesProtein(transcript.getPk(), protein.getPk());	
			Object transcriptProducesProtein = session.createCriteria(TranscriptProducesProtein.class)
					.add( Restrictions.eq("transcript_pk", transcript.getPk()) )
					.add( Restrictions.eq("protein_pk", protein.getPk()) )
					.uniqueResult();
			if (transcriptProducesProtein==null) {
				session.save(transcriptProtein);
			}
		}


		// Relaciona target y sequence/ target y organism
		if (sequence2!=null) target.setSequence_pk(sequence2.getPk());
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush();

		if (target!=null) interactiondata.setTarget_pk(target.getPk());
		if (mirna!=null) interactiondata.setMirna_pk(mirna.getPk());
		if (gene!=null) interactiondata.setGene_pk(gene.getPk());
		interactiondata.setProvenance("VirmiRNA");
		session.save(interactiondata);
		session.flush(); // to get the pk.
		
		// Relaciona expression data con mirna (o recupera su id. si ya existe)
		if (mirna!=null) expressiondata.setMirnaPk(mirna.getPk());
		expressiondata.setInteraction_data_pk(interactiondata.getPk()); // fixed
		session.save(expressiondata);
		session.flush();

		// Inserta PubmedDoc (o recupera su id. si ya existe)
		
		for(PubmedDocument pubmedDoc : pubmedDocList){
			
			// Inserta PubmedDocument (o recupera su id. si ya existe)
			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add( Restrictions.eq("id", pubmedDoc.getId()) )
					.uniqueResult();
			if (oldPubmedDoc==null) {
				session.save(pubmedDoc);
				session.flush(); // to get the PK
			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmedDoc);
				session.update(pubmedDocToUpdate);
				pubmedDoc = pubmedDocToUpdate;
			}

			// Relaciona PubmedDocument con ExpressionData
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(expressiondata.getPk(), pubmedDoc.getPk());
			session.save(expresDataHasPubmedDocument);	

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			if(mirna !=null){
				MirnaHasPubmedDocument mirnaHasPubmedDocument =
						new MirnaHasPubmedDocument(mirna.getPk(), pubmedDoc.getPk());
				Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
						.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
						.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
						.uniqueResult();
				if (oldMirnaHasPubmedDocument==null) {
					session.save(mirnaHasPubmedDocument);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		VirmiRNA2 virmirRNA2 = new VirmiRNA2();
		virmirRNA2.insertIntoSQLModel();

	}

}
