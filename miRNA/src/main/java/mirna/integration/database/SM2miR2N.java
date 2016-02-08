package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mirna.integration.beans.EnvironmentalFactor;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.Mature;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Organism;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.SmallMolecule;
import mirna.integration.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasMature;
import mirna.integration.beans.nToM.MirnaHasOrganism;
import mirna.integration.beans.nToM.MirnaHasPubmedDocument;
import mirna.integration.exception.MiRnaException;

/**
 * Código para procesar los datos de SM2miR2N
 * 
 * @author Esteban López Camacho
 *
 */
public class SM2miR2N extends NewMirnaDatabase {

	private final static String TABLE_NAME = "sm2mir2n";

	public SM2miR2N() throws MiRnaException {
		super(TABLE_NAME);
	}

	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null;

		String query = "";

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 

			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);

			int count = 0;

			br.readLine();

			while ((line = br.readLine()) != null) {

				count++;
				System.out.println(count + " : " + line);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String mirna = tokens[0];
				String mirbase = tokens[1];
				String smallMolecule = tokens[2].replaceAll("'", "\\\\'");
				String fda = tokens[3];
				String db = tokens[4];
				String cid = tokens[5];
				String method = tokens[6];
				String species = tokens[7];
				String condition = tokens[8].replaceAll("'", "\\\\'");
				String pmid = tokens[9];
				String year = tokens[10];
				String reference = tokens[11].replaceAll("'", "\\\\'");
				String support = tokens[12].replaceAll("'", "\\\\'");
				String expression = tokens[13];

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ mirbase + "','"
						+ smallMolecule + "','"
						+ fda + "','"
						+ db + "','"
						+ cid + "','"
						+ method + "','"
						+ species + "','"
						+ condition + "','"
						+ pmid + "','"
						+ year + "','"
						+ reference + "','"
						+ support + "','"
						+ expression + "')";

				stmt.executeUpdate(query);

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			System.out.println("QUERY =");
			System.out.println(query);
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}

	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String name = nullifyField(rs.getString("mirna"));
		String mirbase =  nullifyField(rs.getString("mirbase").toLowerCase().trim());
		String small_molecule = nullifyField(rs.getString("small_molecule").toLowerCase().trim());
		String fda = nullifyField(rs.getString("fda").toLowerCase().trim());
		String db = nullifyField(rs.getString("db").toLowerCase().trim());
		String cid = nullifyField(rs.getString("cid").toLowerCase().trim());
		String method = nullifyField(rs.getString("method").toLowerCase().trim());
		String specie = nullifyField(rs.getString("species"));
		String condition = nullifyField(rs.getString("condition_"));
		String pmid = nullifyField(rs.getString("pmid"));
		String year = nullifyField(rs.getString("year"));
		String reference = nullifyField(rs.getString("reference"));
		String support = nullifyField(rs.getString("support"));
		String evidence = nullifyField(rs.getString("expression"));

		Mature mature = new Mature();
		mature.setAccession_number(mirbase);
		if ((mirbase==null) || (!mirbase.startsWith("mimat"))) { 
			mature = null;
		}

		Organism organism = new Organism();
		organism.setName(specie.trim());
		if (!createdObject(specie)) { 
			organism = null;
		}

		MiRna miRna = new MiRna();

		if (organism.getName().equals("Populus trichocarpa")){

			String[] tokens = StringUtils.splitPreserveAllTokens(organism.getName(), " ");
			String genus = tokens[0];
			String specie_name = tokens[1];
			String substring_genus = genus.substring(0,1).toLowerCase();
			String substring_specie_name = specie_name.substring(0, 1) + specie_name.substring(3, 4);
			String name_organism = substring_genus + substring_specie_name;
			//System.out.println(substring_genus + substring_specie_name);
			miRna.setName(name_organism+"-"+name.trim());
			//System.out.println(miRna.getName());

		}else if (organism.getName().equals("Heifer")){

			miRna.setName("bta"+"-"+name.trim());

		}else if (organism.getName().equals("Physcomitrella patens")){ 	

			miRna.setName("ppt"+"-"+name.trim());

		}else if (organism.getName().equals("Gossypium hirsutum")){ 	

			miRna.setName("ghr"+"-"+name.trim());

		}else if(organism.getName().equals("Chinese yew") || 
				organism.getName().equals("Solanum") ){
			
			miRna.setName(name);
		
		}else{
			
			String[] tokens = StringUtils.splitPreserveAllTokens(organism.getName(), " ");
			String genus = tokens[0];
			String specie_name = tokens[1];
			//System.out.println(organism.getName() + " " + genus + " " + specie_name);
			String substring_genus = genus.substring(0,1).toLowerCase();
			String substring_specie_name = specie_name.substring(0, 2);
			//System.out.println(substring_genus + substring_specie_name);
			String name_organism = substring_genus + substring_specie_name;
			miRna.setName(name_organism+"-"+ name.trim());

		}

		if (!createdObject(name)) { 
			miRna = null;
		}
		
	
		SmallMolecule smallmolecule = new SmallMolecule();
		smallmolecule.setFda(fda);
		smallmolecule.setCid(cid);
		smallmolecule.setDb(db);

		if (!createdObject(fda, cid, db)) { 
			smallmolecule = null;
		}

		EnvironmentalFactor ef = new EnvironmentalFactor();
		ef.setName(small_molecule);
		if (!createdObject(small_molecule)) { 
			ef = null;
		}

		ExpressionData ed = new ExpressionData();
		ed.setCondition(condition);
		ed.setEvidence(evidence);
		ed.setMethod(method);
		ed.setTitleReference(reference);
		ed.setYear(year);
		ed.setDescription(support);
		ed.setProvenance("SMS2miR2N");

		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pmid);
		if (!createdObject(pmid)) { 
			pubmedDoc = null;
		}

		// Inserta Organism (o recupera su id. si ya existe)
		// Relaciona mirna y organism

		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name", miRna.getName()) )
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

		if(organism != null){
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

			MirnaHasOrganism mirnaHasOrganism = 
					new MirnaHasOrganism(miRna.getPk(), organism.getPk());

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldmirnaHasOrganism = session.createCriteria(MirnaHasOrganism.class)
					.add( Restrictions.eq("mirna_pk", miRna.getPk()) )
					.add( Restrictions.eq("organism_pk", organism.getPk()) )
					.uniqueResult();
			if (oldmirnaHasOrganism==null) {
				session.save(mirnaHasOrganism);
			}	
		}

		// Inserta EnvironmentalFactor (o recupera su id. si ya existe)
		if(ef !=null){

			Object oldEf = session.createCriteria(EnvironmentalFactor.class)
					.add( Restrictions.eq("name", ef.getName()) )
					.uniqueResult();
			if (oldEf==null) {
				session.save(ef);
				session.flush();  // to get the PK
			} else {
				EnvironmentalFactor efToUpdate = (EnvironmentalFactor) oldEf;
				efToUpdate.update(ef);
				session.update(efToUpdate);
				ef = efToUpdate;
			}
		}
		// Inserta smallmolecule (o recupera su id. si ya existe)
		if(ef != null) smallmolecule.setEnvironmental_factor_pk(ef.getPk());
		session.save(smallmolecule);

		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y SmallMolecule)

		if(miRna != null) ed.setMirnaPk(miRna.getPk());
		if(ef != null) ed.setEnvironmentalFactorPk(ef.getPk()); 
		session.save(ed);	


		// Inserta pubmedDocument (o recupera su id. si ya existe)

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

			if(miRna !=null){

				MirnaHasPubmedDocument mirnaHasPubmedDocument =
						new MirnaHasPubmedDocument(miRna.getPk(), pubmedDoc.getPk());


				// Relaciona PubmedDocument con Mirna (si no lo estaba ya)

				Object oldMirnaHasPubmedDocument = session.createCriteria(MirnaHasPubmedDocument.class)
						.add( Restrictions.eq("mirnaPk", miRna.getPk()) )
						.add( Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()) )
						.uniqueResult();
				if (oldMirnaHasPubmedDocument==null) {
					session.save(mirnaHasPubmedDocument);
				}

				if (mature!=null) {
					session.save(mature);
					session.flush();
					MirnaHasMature mhm = new MirnaHasMature(miRna.getPk(), mature.getPk());
					session.save(mhm);
				}

				// Relaciona PubmedDocument con ExpressionData

				ExpressionDataHasPubmedDocument expresDataHasPubmedDocument =
						new ExpressionDataHasPubmedDocument(ed.getPk(), pubmedDoc.getPk());
				session.save(expresDataHasPubmedDocument);
			}
		}



	}

	protected String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "NULL".equals(field.trim()) ? null : field.trim();
	}

	public static void main(String[] args) throws Exception {

		SM2miR2N sm2 = new SM2miR2N();
		sm2.insertIntoSQLModel();

	}

}