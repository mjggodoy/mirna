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
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Protein;
import mirna.beans.PubmedDocument;
import mirna.beans.Sequence;
import mirna.beans.Target;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class VirmiRNA3 extends VirmiRNA {
	
	private final String tableName = "virmirna3";
	
	public VirmiRNA3() throws MiRnaException {
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
			rs.next(); ///MUY IMPORTANTE: Target relacionado con organism
			// CAMBIAR ESTO:
			
			String id_virus = rs.getString("vmt_id");
			String virus_name = rs.getString("virus");
			String virus_full_name = rs.getString("virus_full_name");
			String taxonomy_resource = rs.getString("taxonomy");
			String mirna_name = rs.getString("mirna");
			String gene_name = rs.getString("gene");
			String uniprot_id = rs.getString("uniprot");
			String target_organism = rs.getString("organism");
			String cell_line = rs.getString("cell_line");
			String method = rs.getString("method");
			String target_sequence = rs.getString("sequence_target");
			String target_start = rs.getString("start_target");
			String target_end = rs.getString("end_target");
			String target_region = rs.getString("region_target");
			String target_reference = rs.getString("target_reference");
			String pmid = rs.getString("pubmed_id");
			
			Organism organism = new Organism();
			organism.setName(virus_name);
			organism.setName(virus_full_name);
			organism.setResource(taxonomy_resource);
			
			Organism organism2 = new Organism();
			organism2.setSpecie(target_organism);
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			
			ExpressionData expressiondata = new ExpressionData();
			expressiondata.setMethod(method);
			expressiondata.setCellularLine(cell_line);
			expressiondata.setProvenance("VirmiRNA");
			expressiondata.setProvenanceId(id_virus);
			
			PubmedDocument pubmedDoc = new PubmedDocument();
			pubmedDoc.setId(pmid);

			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setGeneId(uniprot_id);
			
			
			Target target = new Target();
			target.setStrand_start(target_start); 
			target.setStrand_end(target_end);
			target.setRegion(target_region);
			target.setExternalName(target_reference);
			
			
			Sequence sequence = new Sequence();
			sequence.setSequence(target_sequence);
			

			/*System.out.println(organism);
			System.out.println(mirna);
			System.out.println(expressiondata);
			System.out.println(gene);
			System.out.println(target);
			System.out.println(organism2);*/
			
			// FIN DE CAMBIAR ESTO
			
			//Inserta MiRna (o recupera su id. si ya existe)
			
			Object oldSequence = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence.getSequence()))
					.uniqueResult();
			if (oldSequence == null) {
				session.save(sequence);
				session.flush(); // to get the PK
			} else {
				sequence = (Sequence) oldSequence;
			}
			
			//Inserta Organism (o recupera su id. si ya existe)
			
			Object oldOrganism = session.createCriteria(MiRna.class)
					.add(Restrictions.eq("name", organism.getName()) )
					.uniqueResult();
			if (oldOrganism==null) {
				session.save(mirna);
				session.flush();  // to get the PK
			} else {
				Organism organismToUpdate = (Organism) oldOrganism;
				organismToUpdate.update(organism);
				session.update(organismToUpdate);
				organism = organismToUpdate;
			}
			
			
			mirna.setSequencePk(sequence.getPk());
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
			
			//Inserta Gene (o recupera su id. si ya existe)

			gene.setOrganism(organism.getPk());
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
		
			//Inserta Target (o recupera su id. si ya existe)

			target.setOrganism_pk(organism.getPk());
			Object oldTarget = session.createCriteria(Target.class)
					.add(Restrictions.eq("name", gene.getName()) )
					.uniqueResult();
			if (oldTarget==null) {
				session.save(target);
				session.flush();  // to get the PK
			} else {
				Target targetToUpdate = (Target) oldGene;
				targetToUpdate.update(target);
				session.update(targetToUpdate);
				target = targetToUpdate;
			}
		
			// Relaciona expression data con mirna  (o recupera su id. si ya existe)

			expressiondata.setMirnaPk(mirna.getPk());
			session.save(expressiondata);
			session.flush(); // No estoy segura si hacer un flush aqu’ y luego en el resto no.
			
		
			
		
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
			
			count++;
			if (count%100==0) {
				System.out.println(count);
				session.flush();
		        session.clear();
			}
				
			stmt.close();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
}
