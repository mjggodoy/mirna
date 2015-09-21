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
import mirna.beans.Organism;
import mirna.beans.Protein;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.beans.nToM.TranscriptProducesProtein;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de TarBase
 * 
 * @author Esteban López Camacho
 *
 */
public class TarBase extends MirnaDatabase {
	
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

	private final String tableName = "tarBase";

	public TarBase() throws MiRnaException { super(); }

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

	/* (non-Javadoc)
	 * @see mirna.database.IMirnaDatabase#insertIntoSQLModel()
	 */
	@Override
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

			String id_tarbase = rs.getString("id");
			//String idv4 = rs.getString("id_v4"); // This field is not going to be used!
			String dataType = rs.getString("data_type");
			//String supportType = rs.getString("support_type"); // This field is not going to be used!
			String specie = rs.getString("organism");
			String miRna = rs.getString("miRNA");
			String hgncSymbol = rs.getString("hgnc_symbol");
			String gene_name = rs.getString("gene");
			String isoform = rs.getString("isoform");
			String ensembl = rs.getString("ensembl");
			String chrLoc = rs.getString("chr_loc");
			String is = rs.getString("i_s");
			//String ds = rs.getString("d_s");
			String paper = rs.getString("paper");
			String targetSeq = rs.getString("target_seq");
			String mirnaSeq =rs.getString("mirna_seq");
			//String seqLocation = rs.getString("seq_location"); // This field is not going to be used!!
			String pmid = rs.getString("pmid");
			String kegg = rs.getString("kegg");
			String protein_type = rs.getString("protein_type");
			String different_expression = rs.getString("dif_expr_in");
			String pathology_or_event = rs.getString("pathology_or_event");
			String mis_regulation = rs.getString("mis_regulation");
			String gene_expression = rs.getString("gene_expression");
			//String type_tumour = rs.getString("tumour_involv");// This is not going to be used.
			String bib = rs.getString("bib");
			String cell_line_used = rs.getString("cell_line_used");
			String hgnc_id = rs.getString("hgnc_id");
			String swiss_prot = rs.getString("swiss_prot");
			//String aux = rs.getString("aux"); // I know... this is not going to be used, is it?

			InteractionData id = new InteractionData();
			id.setReference(paper);
			id.setProvenance("TarBase");

			InteractionData id2 = new InteractionData();
			id2.setProvenance("TarBase");

			Organism organism = new Organism();
			organism.setName(specie);

			MiRna mirna = new MiRna();

			if(organism.getName().equals("C. elegans")){

				miRna = "cel"+"-"+miRna;
				mirna.setName(miRna);
				
			}else if (organism.getName().equals("D.rerio")){ 
				
				miRna = "dre"+"-"+miRna;
				mirna.setName(miRna);


			}else if(organism.getName().equals("Human")){

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

			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setLocation(chrLoc);
			if (kegg.length()>0) gene.setKegg_id(kegg);
			if (gene_expression.length()>0) gene.setExpression_site(gene_expression);
			gene.setHgnc_symbol(hgncSymbol);
			gene.setGeneId(ensembl);

			Gene gene2 = new Gene();
			gene2.setName(gene_name);
			gene2.setLocation(chrLoc);
			if (kegg.length()>0) gene2.setKegg_id(kegg);
			if (gene_expression.length()>0) gene2.setExpression_site(gene_expression);
			gene2.setHgnc_symbol(hgncSymbol);
			gene2.setGeneId(hgnc_id);
			
			String[] proteinTokens = StringUtils.splitPreserveAllTokens(swiss_prot, ",");

			List<Protein> proteinList = new ArrayList<Protein>();

			for (String token : proteinTokens) {
				Protein protein = new Protein();
				protein.setUniprot_id(token);
				if (protein_type.length()>0) protein.setType(protein_type);
				proteinList.add(protein);
			}
			
			Target target = new Target();

			Transcript transcript = new Transcript();
			transcript.setIsoform(isoform);

			Disease disease = new Disease();
			disease.setName(pathology_or_event);

			ExpressionData ed = new ExpressionData();
			ed.setDataType(dataType);
			ed.setMethod(is);
			ed.setEvidence(mis_regulation);
			ed.setCellularLine(cell_line_used);
			ed.setDifferentExpressionLocation(different_expression);
			ed.setProvenanceId(id_tarbase);
			ed.setProvenance("TarBase");

			PubmedDocument pubmed = new PubmedDocument();
			pubmed.setId(pmid);
			if (bib.length()>0) pubmed.setDescription(bib);

			Sequence sequence_mirna = new Sequence();
			sequence_mirna.setSequence(mirnaSeq);

			Sequence sequence_target = new Sequence();
			sequence_target.setSequence(targetSeq);

			// Inserta Disease (o recupera su id. si ya existe)

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

			// Inserta Sequence (o recupera su id. si ya existe)
			if (sequence_mirna.getSequence().length()>0) {
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
				mirna.setSequencePk(sequence_mirna.getPk());
			}

			// Inserta MiRna (o recupera su id. si ya existe)
			mirna.setOrganismPk(organism.getPk());
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

			// Inserta Gene (o recupera su id. si ya existe)
			
			gene.setOrganism_pk(organism.getPk());
			Object oldGene = session.createCriteria(Gene.class)
					.add( Restrictions.eq("geneId", gene.getGeneId()) )
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

			// Inserta Gene (o recupera su id. si ya existe)

			gene2.setOrganism_pk(organism.getPk());
			Object oldGene2 = session.createCriteria(Gene.class)
					.add( Restrictions.eq("geneId", gene2.getGeneId()) )
					.uniqueResult();
			if (oldGene2==null) {
				session.save(gene2);
				session.flush(); // to get the PK
			} else {
				Gene geneToUpdate = (Gene) oldGene2;
				geneToUpdate.update(gene2);
				session.update(geneToUpdate);
				gene2 = geneToUpdate;
			}

			// Insertar Sequence (o recupera su id. si ya existe)
			
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

			//Inserta transcript (o recupera us id. si ya existe)

			transcript.setGeneId(gene.getPk());
			transcript.setGeneId(gene2.getPk());

			Object oldTranscript = session.createCriteria(Transcript.class)
					.add( Restrictions.eq("isoform", transcript.getIsoform()))
					.uniqueResult();
			if (oldTranscript==null) {
				session.save(transcript);
				session.flush(); // to get the PK
				System.out.println("Save transcript");
			} else {
				Transcript transcriptToUpdate = (Transcript) oldTranscript;
				transcriptToUpdate.update(transcript);
				session.update(transcriptToUpdate);
				transcript = transcriptToUpdate;
				System.out.println("Retrieve transcript");
			}
			
		
			// Inserta PubmedDocument (o recupera su id. si ya existe)
			
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

			// Insertar Protein (o recupera su id. si ya existe)

			for (Protein protein : proteinList) {
				Object oldProtein = session.createCriteria(Protein.class)
						.add( Restrictions.eq("uniprot_id", protein.getUniprot_id()   ) )
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
				
				//Inserta nuevo Transcript y lo relaciona con protein
	
				TranscriptProducesProtein transcriptProtein = new TranscriptProducesProtein(transcript.getPk(), protein.getPk());	
	
				Object transcriptProducesProtein = session.createCriteria(TranscriptProducesProtein.class)
						.add( Restrictions.eq("transcript_pk", transcript.getPk()) )
						.add( Restrictions.eq("protein_pk", protein.getPk()) )
						.uniqueResult();
				if (transcriptProducesProtein==null) {
					session.save(transcriptProtein);
				}
	
			}
			
			//Inserta nueva DataExpression (y la relaciona con MiRna y Disease correspondiente)

			ed.setMirnaPk(mirna.getPk());
			ed.setDiseasePk(disease.getPk());
			session.save(ed);

			//Inserta nueva DataExpression (y la relaciona con MiRna y Disease correspondiente)

			id.setMirna_pk(mirna.getPk());
			id.setGene_pk(gene.getPk());
			id.setTarget_pk(target.getPk());
			id.setExpression_data_pk(ed.getPk());
			session.save(id);
			
			// Inserta nueva InteractionData 
			// (y la relaciona con el MiRna, ExpressionData y Gene correspondiente)

			id2.setGene_pk(gene2.getPk());
			id2.setMirna_pk(mirna.getPk());
			id2.setExpression_data_pk(ed.getPk());
			session.save(id2);
			
			// Inserta nueva Target
			target.setTranscript_pk(transcript.getPk());
			target.setSequence_pk(sequence_target.getPk());
			session.save(target);
			
			//Inserta nuevo miRna y lo relaciona con pubmed
			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(mirna.getPk(), pubmed.getPk());
			
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(ed.getPk(), pubmed.getPk());
			
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmed.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}
			
			// Relaciona PubmedDocument con ExpressionData
			session.save(expresDataHasPubmedDocument);
			
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

		TarBase tarBase = new TarBase();

		//String inputFile = "/Users/esteban/Softw/miRNA/TarBase_V5.0.txt";
		//tarBase.insertInTable(inputFile);

		tarBase.insertIntoSQLModel();

		//select * from mirna.mirna where name='let-7';
		//select * from mirna.expression_data where mirna_pk=115

	}

}