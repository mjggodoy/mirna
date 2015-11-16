package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasOrganism;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class VirmiRNA3 extends NewMirnaDatabase {

	private static final String TABLE_NAME = "virmirna3";

	public VirmiRNA3() throws MiRnaException {
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
				String VMT_id = tokens[0];
				String virus = tokens[1];
				String virus_full_name = tokens[2];
				String taxonomy = tokens[3];
				String miRNA = tokens[4];
				String gene = tokens[5];
				String Uniprot = tokens[6];
				String organism  = tokens[7];
				String cell_line = tokens[8];
				String method = tokens[9];
				String sequence_target = tokens[10];
				String start_target = tokens[11];
				String end_target = tokens[12];
				String region_target = tokens[13];
				String target_reference = tokens[14];
				String pubmed_id = tokens[15];

				if (tokens.length>16) { //|| ((tokens.length==20) && (!"".equals(tokens[19])))) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ VMT_id + "','"
						+ virus + "','"
						+ virus_full_name + "','"
						+ taxonomy + "','"
						+ miRNA + "','"
						+ gene + "','"
						+ Uniprot + "','"
						+ organism + "','"
						+ cell_line + "','"
						+ method + "','"
						+ sequence_target + "','"
						+ start_target + "','"
						+ end_target + "','"
						+ region_target  + "','"
						+ target_reference  + "','"
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

		String id_virus = nullifyField(rs.getString("vmt_id"));
		String virus_name = nullifyField(rs.getString("virus"));
		String virus_full_name = nullifyField(rs.getString("virus_full_name"));
		String taxonomy_resource = nullifyField(rs.getString("taxonomy"));
		String mirna_name = nullifyField(rs.getString("mirna"));
		String gene_name = nullifyField(rs.getString("gene"));
		String uniprot_id = nullifyField(rs.getString("uniprot"));
		String target_organism = nullifyField(rs.getString("organism"));
		String cell_line = nullifyField(rs.getString("cell_line"));
		String method = nullifyField(rs.getString("method"));
		String target_sequence = nullifyField(rs.getString("sequence_target"));
		String target_start = nullifyField(rs.getString("start_target"));
		String target_end = nullifyField(rs.getString("end_target"));
		String target_region = nullifyField(rs.getString("region_target"));
		String target_reference = nullifyField(rs.getString("target_reference"));
		String pmid = nullifyField(rs.getString("pubmed_id"));

		Organism organism = new Organism();
		organism.setShortName(virus_name);
		organism.setName(virus_full_name);
		organism.setResource(taxonomy_resource);
		if (!createdObject(virus_name, virus_full_name, taxonomy_resource)) {
			organism = null;
		}

		Organism organism2 = new Organism();
		organism2.setName(target_organism);
		if (!createdObject(target_organism)) {
			organism = null;
		}

		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);
		if (!createdObject(mirna_name)) {
			mirna = null;
		}

		ExpressionData ed = new ExpressionData();
		ed.setMethod(method);
		ed.setCellularLine(cell_line);
		ed.setProvenanceId(id_virus);
		ed.setProvenance("VirmiRNA");

		Gene gene = new Gene();
		gene.setName(gene_name);
		gene.setGeneId(uniprot_id);
		if (!createdObject(gene_name, uniprot_id)) {
			gene = null;
		}

		Target target = new Target();
		target.setStrand_start(target_start); 
		target.setStrand_end(target_end);
		target.setRegion(target_region);
		target.setTarget_ref(target_reference);
		if (!createdObject(target_start, target_end, target_region, target_reference)) {
			target = null;
		}

		Sequence sequence = new Sequence();
		sequence.setSequence(target_sequence);
		if (!createdObject(target_sequence)) {
			sequence = null;
		}

		InteractionData id = new InteractionData();
		id.setProvenance("VirmiRNA");

		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pmid);
		if (!createdObject(pmid)) {
			pubmedDoc = null;
		}

		//Inserta Sequence (o recupera su id. si ya existe)

		if (sequence!=null) {
			Object oldSequence = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence.getSequence()))
					.uniqueResult();
			if (oldSequence == null) {
				session.save(sequence);
				session.flush(); // to get the PK
			} else {
				Sequence sequenceUpToDate = (Sequence) oldSequence;
				sequenceUpToDate.update(sequenceUpToDate);
				session.update(sequenceUpToDate);
				sequence = sequenceUpToDate;
			}
		}

		//Inserta Organism (o recupera su id. si ya existe)

		if(organism != null){
			Object oldOrganism = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism.getName()) )
					.uniqueResult();
			if (oldOrganism==null) {
				session.save(organism);
				session.flush();  // to get the PK
			} else {
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism);
				session.update(organismToUpdate);
				organism = organismToUpdate;
				System.out.println("AQUI");
			}
		}
		//Inserta Organism (o recupera su id. si ya existe)

		if(organism2 != null){
			Object oldOrganism2 = session.createCriteria(Organism.class)
					.add(Restrictions.eq("name", organism2.getName()) )
					.uniqueResult();
			if (oldOrganism2==null) {
				session.save(organism2);
				session.flush();  // to get the PK
			} else {
				Organism organismToUpdate2 = (Organism) oldOrganism2;
				organismToUpdate2.update(organism2);
				session.update(organismToUpdate2);
				organism2 = organismToUpdate2;
				System.out.println("AQUI");
			}
		}

		//Inserta mirna (o recupera su id. si ya existe)

		if(mirna != null){
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

		//Inserta Gene (o recupera su id. si ya existe)

		if (gene!=null) {
			if(organism2 != null) gene.setOrganism_pk(organism2.getPk());
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()) )
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

		if (pubmedDoc!=null) {

			// Inserta PubmedDocument (o recupera su id. si ya existe)
			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add( Restrictions.eq("id", pubmedDoc.getId()) )
					.uniqueResult();
			if (oldPubmedDoc==null) {
				session.save(pubmedDoc);
				session.flush(); // to get the PK
				System.out.println("SAVE pubmed document");

			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmedDoc);
				session.update(pubmedDocToUpdate);
				pubmedDoc = pubmedDocToUpdate;
				System.out.println("UPDATE pubmed document");
			}

		}
		//Inserta Target (o recupera su id. si ya existe)

		if(organism2 != null)target.setOrganism_pk(organism2.getPk());
		if(sequence != null)target.setSequence_pk(sequence.getPk()) ;
		session.save(target);

		// Relaciona interaction data con mirna  (o recupera su id. si ya existe)		
		if(target != null)id.setTarget_pk(target.getPk());
		if(mirna != null) id.setMirna_pk(mirna.getPk());
		if(gene != null) id.setGene_pk(gene.getPk());
		session.save(id);
		session.flush();

		// Relaciona expression data con mirna  (o recupera su id. si ya existe)

		if(mirna != null) ed.setMirnaPk(mirna.getPk());
		ed.setInteraction_data_pk(id.getPk()); // fixed
		session.save(ed);

		if(mirna != null &&  pubmedDoc != null ){

			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(mirna.getPk(), pubmedDoc.getPk());

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}
		}

		ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
				new ExpressionDataHasPubmedDocument(ed.getPk(), pubmedDoc.getPk());
		// Relaciona PubmedDocument con ExpressionData
		session.save(expresDataHasPubmedDocument);



		if(mirna != null && organism != null){
			MirnaHasOrganism mirnaHasOrganism = 
					new MirnaHasOrganism(mirna.getPk(), organism.getPk());

			// Relaciona Organism con Mirna (si no lo estaba ya)
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
		
		VirmiRNA3 virmirRNA3 = new VirmiRNA3();
		virmirRNA3.insertIntoSQLModel();
		
	}



}
