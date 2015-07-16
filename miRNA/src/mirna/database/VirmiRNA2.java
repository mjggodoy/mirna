package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.BiologicalProcess;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.beans.nToM.MirnaInvolvesBiologicalProcess;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class VirmiRNA2 extends VirmiRNA {
	
	private final String tableName = "virmirna2";
	
	public VirmiRNA2() throws MiRnaException {
		super();
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
				
//				int index1 = pubmed_id.indexOf("\">");
//				int index2 = pubmed_id.indexOf("</");
//				pubmed_id = pubmed_id.substring(index1+2, index2-2);

				
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
			
			String id_virus = rs.getString("avm_id");
			String mirna_name = rs.getString("mirna");
			String mirna_seq = rs.getString("mirna_sequence");
			String accesion_number = rs.getString("mirbase_id");
			String specie_target = rs.getString("specie");
			String organism_name = rs.getString("virus");
			String organism_name_full = rs.getString("virus_full_name");
			String resource_organism = rs.getString("taxonomy");
			String target1= rs.getString("target");
			String uniprot = rs.getString("uniprot");
			String target_process = rs.getString("target_process");
			String method = rs.getString("method");
			String cell_line = rs.getString("cell_line");
			String target_sequence = rs.getString("target_sequence");
			String target_region = rs.getString("target_region");
			String target_coords = rs.getString("target_coords");
			String send_match = rs.getString("seed_match");
			String target_resource = rs.getString("target_ref");
			String target_pubmedId = rs.getString("pubmed_id");
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			mirna.setAccessionNumber(accesion_number);
			
			Sequence sequence1 = new Sequence();
			sequence1.setSequence(mirna_seq);
			
			Sequence sequence2 = new Sequence();
			sequence2.setSequence(target_sequence);
			
			Organism organism = new Organism();
			organism.setName(organism_name_full);
			organism.setShortName(organism_name);
			organism.setResource(resource_organism);
			
			Organism organism2 = new Organism();
			organism2.setSpecie(specie_target);// inclu’ set/get en la clase organism.
			
			Target target = new Target();
			target.setRegion(target_region);
			target.setSeed_match(send_match);
			target.setCoordinates(target_coords);
			target.setTarget_ref(target_resource);
				
			BiologicalProcess biologicalprocess = new BiologicalProcess();
			biologicalprocess.setName(target_process);
			
			ExpressionData expressiondata = new ExpressionData();
			expressiondata.setCellularLine(cell_line);
			expressiondata.setMethod(method);
			expressiondata.setProvenanceId(id_virus);
			expressiondata.setProvenance("VirmiRNA");
			
			Gene gene = new Gene();
			gene.setName(target1);
			gene.setGeneId(uniprot);
			
			PubmedDocument pubmedDoc = new PubmedDocument();
			int index1 = target_pubmedId.indexOf(">");
			int index2 = target_pubmedId.indexOf("</a>");
			target_pubmedId =  target_pubmedId.substring(index1+1,index2);
			pubmedDoc.setId(target_pubmedId);
			
			InteractionData interactiondata = new InteractionData();
			
			/*System.out.println(mirna);
			System.out.println(organism);
			System.out.println(expressiondata);
			System.out.println(target);
			System.out.println(biologicalprocess);
			System.out.println(organism2);*/			
			
			// Inserta Sequence (o recupera su id. si ya existe)
			
			Object oldSequence1 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence1.getSequence()))
					.uniqueResult();
			if (oldSequence1 == null) {
				session.save(sequence1);
				session.flush(); // to get the PK
			} else {
				sequence1 = (Sequence) oldSequence1;
			}
			
			mirna.setSequencePk(sequence1.getPk());
			
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
			
			Object oldSequence2 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence2.getSequence()))
					.uniqueResult();
			if (oldSequence2 == null) {
				session.save(sequence2);
				session.flush(); // to get the PK
			} else {
				sequence2 = (Sequence) oldSequence2;
			}
			
			target.setSequence_pk(sequence2.getPk());
			// Inserta Target (o recupera su id. si ya existe)
			target.setOrganism_pk(organism2.getPk());
			Object oldTarget = session.createCriteria(Target.class)
					.add(Restrictions.eq("name", organism.getName()) )
					.uniqueResult();
			if (oldTarget==null) {
				session.save(target);
				session.flush(); // to get the PK
			} else {
				Target targetToUpdate = (Target) oldTarget;
				targetToUpdate.update(target);
				session.update(targetToUpdate);
				target = targetToUpdate;
			}
			
			// Inserta Gene (o recupera su id. si ya existe)
			gene.setOrganism(organism.getPk());

			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()) )
					.add(Restrictions.eq("organism", gene.getOrganism()))
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
			
			// Inserta BiologicalProcess (o recupera su id. si ya existe)

			Object oldBiologicalProcess = session.createCriteria(BiologicalProcess.class)
					.add(Restrictions.eq("name", gene.getName()) )
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
			
			
			// Relaciona expression data con mirna (o recupera su id. si ya existe)
			
			expressiondata.setMirnaPk(mirna.getPk());
			session.save(expressiondata);
			
			//Relaciona interaction data expressiondata
			
			interactiondata.setExpressionDataPk(expressiondata.getPk());
			interactiondata.setTargetPk(target.getPk());
			interactiondata.setMirnaPk(mirna.getPk());
			interactiondata.setMirnaPk(gene.getPk());

			
			// Relaciona interactionData con Target (o recupera su id. si ya existe)
			
			interactiondata.setTargetPk(target.getTargetPk());
			session.save(interactiondata);
			
			
			MirnaHasPubmedDocument mirnaHasPubmedDocument =
					new MirnaHasPubmedDocument(mirna.getPk(), pubmedDoc.getPk());
			
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
					new ExpressionDataHasPubmedDocument(expressiondata.getPk(), pubmedDoc.getPk());
			
			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
					.uniqueResult();
			if (oldMirnaHasPubmedDocument==null) {
				session.save(mirnaHasPubmedDocument);
			}
			
			// Relaciona PubmedDocument con ExpressionData
			session.save(expresDataHasPubmedDocument);
			
			
			MirnaInvolvesBiologicalProcess mirnaInvolvesBiologicalProcess =
					new MirnaInvolvesBiologicalProcess(mirna.getPk(), biologicalprocess.getPk());
			
			Object oldMirnInvolvesBiologicalProcess = session.createCriteria(MirnaInvolvesBiologicalProcess.class)
					.add( Restrictions.eq("mirnaPk", mirna.getPk()) )
					.add( Restrictions.eq("biologicalprocess", biologicalprocess.getPk()) )
					.uniqueResult();
			if (oldMirnInvolvesBiologicalProcess==null) {
				session.save(mirnaInvolvesBiologicalProcess);
			}
			
			session.save(mirnaInvolvesBiologicalProcess);
			
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
		VirmiRNA2 virmiRNA2 = new VirmiRNA2();
		virmiRNA2.insertIntoSQLModel();
	}

}
