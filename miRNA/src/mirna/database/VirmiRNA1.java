package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.Hairpin;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class VirmiRNA1 extends VirmiRNA{
	
	private final String tableName = "virmirna1";
	
	public VirmiRNA1() throws MiRnaException {
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
				String id_virus = tokens[0];
				String virus_name = tokens[1];
				String virus_full_name = tokens[2];
				String link_virus = tokens[3];
				String miRNA = tokens[4];
				String miRNA_sequence = tokens[5];
				String length = tokens[6];
				String GC_proportion = tokens[7];
				String arm = tokens[8];
				String pre_miRNA = tokens[9];
				String pre_miRNA_sequence = tokens[10];
				String cell_line = tokens[11];
				String method = tokens[12];
				String pubmed_id = tokens[13];
				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ id_virus + "','"
						+ virus_name + "','"
						+ virus_full_name + "','"
						+ link_virus + "','"
						+ miRNA + "','"
						+ miRNA_sequence + "','"
						+ length + "','"
						+ GC_proportion + "','"
						+ arm + "','"
						+ pre_miRNA + "','"
						+ pre_miRNA_sequence + "','"
						+ cell_line  + "','"
						+ method  + "','"
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

			if (rs.next()) {
				// CAMBIAR ESTO:
				
				String virus_id = rs.getString("id_virus");
				String virus_name = rs.getString("virus_name");
				String virus_full_name = rs.getString("virus_full_name");
				String resource = rs.getString("link_virus");
				String mirna_name = rs.getString("mirna");
				String mirna_seq = rs.getString("mirna_seq");
				String length = rs.getString("length");
				String gc_proportion = rs.getString("gc_proportion");
				String arm = rs.getString("arm");
				String pre_mirna = rs.getString("pre_mirna");
				String pre_mirna_seq = rs.getString("pre_mirna_seq");
				String cell_line = rs.getString("cell_line");
				String method = rs.getString("method");
				String pubmed = rs.getString("pubmed");
				
				while ((pubmed.contains(","))) {
					
					int index1 = pubmed.indexOf(",");
					String pubmed1 = pubmed.substring(1,index1);
					System.out.println(pubmed);
					pubmed = pubmed.substring(pubmed1.length()+2, pubmed.length()-1); 
					System.out.println(pubmed);
					
		        }	
	
				Organism organism = new Organism();
				organism.setShortName(virus_name);
				organism.setName(virus_full_name);
				organism.setResource(resource);
				
				MiRna mirna = new MiRna();
				mirna.setName(mirna_name);
				mirna.setGC_proportion(gc_proportion);
				mirna.setLength(length);
				
				Sequence sequence1 = new Sequence();
				sequence1.setSequence(mirna_seq);
				
				Gene gene = new Gene();
				gene.setArm(arm);
				
				Hairpin hairpin = new Hairpin();
				hairpin.setName(pre_mirna);
				
				Sequence sequence2 = new Sequence();
				sequence2.setSequence(pre_mirna_seq);
				
				ExpressionData expressiondata = new ExpressionData();
				expressiondata.setCellularLine(cell_line);
				expressiondata.setMethod(method);
				expressiondata.setProvenance("VirmiRNA");
				expressiondata.setProvenanceId(virus_id);
			
				PubmedDocument pubmedDoc = new PubmedDocument();
				
				//Insertar c—digo
				
				while(pubmed != null){
					
					int index = pubmed.indexOf(",");
					
					String pubmed1 = pubmed.substring(0, index);
					String pubmed2 = pubmed.substring(index);
					pubmedDoc.setId(pubmed1);
					pubmedDoc.setId(pubmed2);
				}
				
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
				gene.setOrganism(organism.getPk());
				Object oldGene = session.createCriteria(Gene.class)
						.add(Restrictions.isNull("name"))
						.add(Restrictions.eq("arm", gene.getArm()))
						.add(Restrictions.eq("organism", gene.getOrganism()))
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
				
				Object oldSequence2 = session.createCriteria(Sequence.class)
						.add(Restrictions.eq("sequence", sequence2.getSequence()))
						.uniqueResult();
				if (oldSequence2 == null) {
					session.save(sequence2);
					session.flush(); // to get the PK
				} else {
					sequence2 = (Sequence) oldSequence2;
				}
				hairpin.setSequence_pk(sequence2.getPk());
				hairpin.setMirnaPk(mirna.getPk());
				
				// Inserta Hairpin (o recupera su id. si ya existe)
				
				Object oldHairpin = session.createCriteria(Hairpin.class)
						.add(Restrictions.eq("name", hairpin.getName()))
						.uniqueResult();
				if (oldHairpin == null) {
					session.save(hairpin);
					session.flush(); // to get the PK
				} else {
					Hairpin hairpinToUpdate = (Hairpin) oldHairpin;
					hairpinToUpdate.update(hairpin);
					session.update(hairpinToUpdate);
					hairpin = hairpinToUpdate;
				}
				
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
				
				// Relaciona expression data con mirna  (o recupera su id. si ya existe)
	
				expressiondata.setMirnaPk(mirna.getPk());
				session.save(expressiondata);
				session.flush(); // No estoy segura si hacer un flush aquï¿½ y luego en el resto no.
				
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
				
			}
			
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
		
		VirmiRNA1 virmiRNA1 = new VirmiRNA1();
		virmiRNA1.insertIntoSQLModel();
	}

}
