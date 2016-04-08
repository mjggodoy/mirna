package mirna.integration.database.virmirna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.Hairpin;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Organism;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.Sequence;
import mirna.integration.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.integration.beans.nToM.HairpinHasSequence;
import mirna.integration.beans.nToM.MirnaHasHairpin;
import mirna.integration.beans.nToM.MirnaHasOrganism;
import mirna.integration.beans.nToM.MirnaHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasSequence;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import mirna.integration.database.NewMirnaDatabase;

public class VirmiRNA1 extends NewMirnaDatabase{

	private static final String TABLE_NAME = "virmirna1";

	public VirmiRNA1() throws MiRnaException {
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
	public void processRow(Session session, ResultSet rs) throws Exception {

		String virus_id = nullifyField(rs.getString("id_virus"));
		String virus_name = nullifyField(rs.getString("virus_name"));
		String virus_full_name = nullifyField(rs.getString("virus_full_name"));
		String resource = nullifyField(rs.getString("link_virus"));
		String mirna_name = nullifyField(rs.getString("mirna"));
		String mirna_seq = nullifyField(rs.getString("mirna_seq"));
		String length = nullifyField(rs.getString("length"));
		String gc_proportion = nullifyField(rs.getString("gc_proportion"));
		String arm = nullifyField(rs.getString("arm")); 
		String pre_mirna = nullifyField(rs.getString("pre_mirna").toLowerCase());
		String pre_mirna_seq = nullifyField(rs.getString("pre_mirna_seq"));
		String cell_line = nullifyField(rs.getString("cell_line"));
		String method = nullifyField(rs.getString("method"));
		String pubmed = nullifyField(rs.getString("pubmed"));

		Organism organism = new Organism();
		organism.setShortName(virus_name);
		organism.setName(virus_full_name);
		organism.setResource(resource);
		if (!createdObject(virus_name, virus_full_name, resource)) {
			organism = null;
		}

		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);
		mirna.setArm(arm);
		if (!createdObject(mirna_name, arm)) {
			mirna = null;
		}

		Sequence sequence1 = new Sequence(); 
		sequence1.setSequence(mirna_seq);
		sequence1.setGC_proportion(gc_proportion);
		sequence1.setLength(length);
		if (!createdObject(mirna_seq,gc_proportion,length)) {
			sequence1 = null;
		}

		Hairpin hairpin = new Hairpin();
		hairpin.setName(pre_mirna);
		if (!createdObject(pre_mirna)) {
			hairpin = null;
		}

		Sequence sequence2 = new Sequence();
		sequence2.setSequence(pre_mirna_seq);
		if (!createdObject(pre_mirna_seq)) {
			sequence2 = null;
		}

		ExpressionData expressiondata = new ExpressionData();
		expressiondata.setCellularLine(cell_line);
		expressiondata.setMethod(method);
		expressiondata.setProvenance("VirmiRNA");
		expressiondata.setProvenanceId(virus_id);

		String[] pubmedDocTokens = StringUtils.splitPreserveAllTokens(pubmed, ",");
		List<PubmedDocument> pubmedList = new ArrayList<PubmedDocument>();
		if (!pubmed.startsWith("WO")) {
			for(String token: pubmedDocTokens){
				PubmedDocument pubmedDocument = new PubmedDocument();
				pubmedDocument.setId(token.trim());
				pubmedList.add(pubmedDocument);
			}
		}

		// Inserto la sequence de Mirna
		if(sequence1 !=null){
			Object oldSequence1 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence1.getSequence()))
					.uniqueResult();
			if (oldSequence1 == null) {
				session.save(sequence1);
				session.flush(); // to get the PK
			} else {
				Sequence sequenceUptoDate = (Sequence) oldSequence1;
				sequenceUptoDate.update(sequence1);
				session.update(sequenceUptoDate);
				sequence1 = sequenceUptoDate;
			}
		}

		// Inserto la sequence de hairpin (si existe)
		if(sequence2 !=null){
			Object oldSequence2 = session.createCriteria(Sequence.class)
					.add(Restrictions.eq("sequence", sequence2.getSequence()))
					.uniqueResult();
			if (oldSequence2 == null) {
				session.save(sequence2);
				session.flush(); // to get the PK
			} else {
				Sequence sequenceUptoDate = (Sequence) oldSequence2;
				sequenceUptoDate.update(sequence2);
				session.update(sequenceUptoDate);
				sequence2 = sequenceUptoDate;
			}
		}

		// Inserto el organism
		if(organism !=null){
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
		
		// Inserta Hairpin (si tengo o su nombre o su secuencia)
		if(hairpin !=null){
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

		} else {
			if (sequence2!=null) {
				hairpin = new Hairpin();
				session.save(hairpin);
				session.flush(); // to get the PK
			}
		}
		
		if(sequence2 != null){
			HairpinHasSequence hairpinHasSequence = 
					new HairpinHasSequence(hairpin.getPk(), sequence2.getPk());
			// Relaciona Organism con Mirna (si no lo estaba ya)
			Object oldHairpinHasSequence = session.createCriteria(HairpinHasSequence.class)
					.add( Restrictions.eq("hairpin_pk", hairpin.getPk()) )
					.add( Restrictions.eq("sequence_pk", sequence2.getPk()) )
					.uniqueResult();
			if (oldHairpinHasSequence==null) {
				session.save(hairpinHasSequence);

			}
		}

		if(mirna !=null){
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

			if(sequence1 !=null){
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

			if(organism !=null){
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

			if(hairpin !=null){
				MirnaHasHairpin mirnaHasHairpin = 
						new MirnaHasHairpin(mirna.getPk(), hairpin.getPk());
				Object oldMirnaHasHairpin = session.createCriteria(MirnaHasHairpin.class)
						.add( Restrictions.eq("mirna_pk", mirna.getPk()) )
						.add( Restrictions.eq("hairpin_pk", hairpin.getPk()) )
						.uniqueResult();
				if (oldMirnaHasHairpin==null) {
					session.save(mirnaHasHairpin);

				}
			}
		}

		// Relaciona expression data con mirna  (o recupera su id. si ya existe)
		expressiondata.setMirnaPk(mirna.getPk());
		session.save(expressiondata);
		session.flush(); // No estoy segura si hacer un flush aqui y luego en el resto no.

		for(PubmedDocument pubmedDoc : pubmedList){
			
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

	/*public static void main(String[] args) throws Exception {
		VirmiRNA1 virmirRNA1 = new VirmiRNA1();
		virmirRNA1.insertIntoSQLModel();
	}*/

}