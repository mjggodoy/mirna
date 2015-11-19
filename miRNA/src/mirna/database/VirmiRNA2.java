package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.beans.BiologicalProcess;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.Hairpin;
import mirna.beans.InteractionData;
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasOrganism;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.beans.nToM.MirnaHasSequence;
import mirna.beans.nToM.MirnaInvolvesBiologicalProcess;
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

		String id_virus = nullifyField(rs.getString("avm_id"));
		String mirna_name = nullifyField(rs.getString("mirna"));
		String mirna_seq = nullifyField(rs.getString("mirna_sequence"));
		String accesion_number = nullifyField(rs.getString("mirbase_id"));
		String specie_target = nullifyField(rs.getString("specie"));
		String organism_name = nullifyField(rs.getString("virus"));
		String organism_name_full = nullifyField(rs.getString("virus_full_name"));
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

		Hairpin hairpin = new Hairpin();

		if(accesion_number.startsWith("MI")){
		hairpin.setAccession_number(accesion_number);
		}
		
		if (!createdObject(accesion_number)) { 
			hairpin = null;
		}
		
		Mature mature = new Mature();

		if(accesion_number.contains("MIMAT")){
		mature.setAccession_number(accesion_number);
		}
		
		if (!createdObject(accesion_number)) { 
			mature = null;
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

		if (resource_organism!=null) {

			resource_organism = resource_organism.substring(resource_organism.indexOf("id=")+3);

		}

		Organism organism = new Organism();
		organism.setName(organism_name_full);
		organism.setShortName(organism_name);
		organism.setResource(resource_organism);
		//TODO: No cogemos ahora mismo los resource porque hay organism
		// con mismo nombre y distinto resource. Quiza arreglar cogiendo
		// el verdadero nombre de la resource.
		//organism.setResource(resource_organism);
		if (!createdObject(organism_name_full, organism_name, resource_organism)) {
			organism = null;
		}

		Organism organism2 = new Organism();
		organism2.setName(specie_target);// included (:P) set/get en la clase organism.
		if (!createdObject(specie_target)) {
			organism2 = null;
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
		gene.setGeneId(uniprot);
		gene.setName(target1);
		if (!createdObject(target1, uniprot)) {
			gene = null;
		}


		PubmedDocument pubmedDoc = new PubmedDocument();

		while(target_pubmedId.contains("<a")){

			int startIndex = target_pubmedId.indexOf("<a");
			int endIndex = target_pubmedId.indexOf("</a>");

			String link = target_pubmedId.substring(startIndex, endIndex);

			if (link.contains("http://www.ncbi.nlm.nih.gov/pubmed/")) {

				String pubmedId = link.substring(link.indexOf(">")+1);
				pubmedDoc.setId(pubmedId);

				if (!createdObject(pubmedId)) {
					pubmedDoc = null;
				}	


			}else if (link.contains("http://www.patentlens.net/patentlens/patents.html?patnums=")){

				pubmedDoc = null;

			}


			target_pubmedId = target_pubmedId.substring(link.length()+4);

		}


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


		if(organism != null){

			// Inserta Organism (o recupera su id. si ya existe)
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism.getName()) )
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

		}

		if(organism2 != null){

			Object oldOrganism2 = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism2.getName()) )
					.uniqueResult();
			if (oldOrganism2==null) {
				session.save(organism2);
				session.flush(); // to get the PK
			} else {
				Organism organismToUpdate2 = (Organism) oldOrganism2;
				organismToUpdate2.update(organism2);
				session.update(organismToUpdate2);
				organism2 = organismToUpdate2;
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
		}

		if(mirna != null && sequence1 != null ){
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
			// Inserta Gene (o recupera su id. si ya existe)
			if(organism != null) gene.setOrganism_pk(organism.getPk());
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()) )
					.add(Restrictions.eq("organism_pk", gene.getOrganism_pk()))
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
		}

		// Inserta PubmedDoc (o recupera su id. si ya existe)

		if(pubmedDoc !=null){
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
		}


		// Relaciona target y sequence/ target y organism
		if (sequence2!=null) target.setSequence_pk(sequence2.getPk());
		if (organism2!=null) target.setOrganism_pk(organism2.getPk());
		session.save(target);

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

		//Relaciona interaction data expressiondata


		// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
		if(pubmedDoc !=null && mirna !=null){

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

		if(pubmedDoc !=null){
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(expressiondata.getPk(), pubmedDoc.getPk());

			// Relaciona PubmedDocument con ExpressionData
			session.save(expresDataHasPubmedDocument);
		}


		if(mirna != null && biologicalprocess != null){
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

		if(mirna != null && organism != null){
			MirnaHasOrganism mirnaHasOrganism = 
					new MirnaHasOrganism(mirna.getPk(), organism.getPk());

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldmirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
					.add( Restrictions.eq("mirna_pk", mirna.getPk()) )
					.add( Restrictions.eq("organism_pk", organism.getPk()) )
					.uniqueResult();
			if (oldmirnaHasOrganism==null) {
				session.save(mirnaHasOrganism);

			}
		}

	}

	public static void main(String[] args) throws Exception {
		
		VirmiRNA2 virmirRNA2 = new VirmiRNA2();
		virmirRNA2.insertIntoSQLModel();
		
	}

}
