package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.beans.Disease;
import mirna.beans.EnvironmentalFactor;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.PubmedDocument;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasOrganism;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de miREnvironment
 * 
 * @author Esteban López Camacho
 *
 */
public class MiREnvironment extends NewMirnaDatabase {

	private final static String TABLE_NAME = "miREnvironment";

	public MiREnvironment() throws MiRnaException {
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

			while (((line = br.readLine()) != null)) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String mir = tokens[0];
				String name = tokens[1];
				String name2 = tokens[2];
				String name3 = tokens[3];
				String disease = tokens[4].replaceAll("'", "\\\\'");
				String enviromenentalFactor = tokens[5]
						.replaceAll("'", "\\\\'");
				String treatment = tokens[6].replaceAll("'", "\\\\'");
				String cellularLine = tokens[7].replaceAll("'", "\\\\'");
				String specie = tokens[8];
				String description = tokens[9].replaceAll("'", "\\\\'");
				String pubmedId = tokens[10];

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mir + "','" + name + "','" + name2 + "','" + name3
						+ "','" + disease + "','" + enviromenentalFactor
						+ "','" + treatment + "','" + cellularLine + "','"
						+ specie + "','" + description + "','" + pubmedId
						+ "')";

				stmt.executeUpdate(query);
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (line != null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con != null)
				con.close();
		}

	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String id = nullifyField(rs.getString("mir"));
		String name = nullifyField(rs.getString("name"));
		String name2 = nullifyField(rs.getString("name2")); 
		String name3 = nullifyField(rs.getString("name3"));
		String disease_name = nullifyField(rs.getString("disease").toLowerCase().trim());
		String environmentalFactor_name = rs.getString("enviromentalFactor")
				.toLowerCase().trim();
		String treatment = nullifyField(rs.getString("treatment").toLowerCase().trim());
		String cellularLine = nullifyField(rs.getString("cellularLine").toLowerCase().trim());
		String specie_name = nullifyField(rs.getString("specie"));
		String description = nullifyField(rs.getString("description"));
		String pubmedId = nullifyField(rs.getString("pubmedId"));

		Organism organism = new Organism();

		String[] organismTokens = StringUtils.splitPreserveAllTokens(specie_name, ";");
		List<Organism> organismList = new ArrayList<Organism>();

		for (String organismlist : organismTokens) {

			organism.setName(organismlist);
			organismList.add(organism);
			if (!createdObject(organismlist)) {
				organism = null;
			}
		}


		MiRna miRna = new MiRna();
		if(name != null) miRna.setName(name.trim());
		System.out.println(miRna.getName());


		if (miRna.getName() == null && name2 != null) {

			miRna.setName(name2.trim());
			System.out.println(miRna.getName());


			for(Organism organism1 : organismList){


				if("Homo sapiens".equals(organism1.getName())){

					miRna.setName("hsa"+"-"+name2.trim());
					//System.out.println("hsa2 "+ miRna.getName());

				}else if("Mouse".equals(organism1.getName()) && !miRna.getName().startsWith("mmu")){


					miRna.setName("mmu"+"-"+name2.trim());
					//System.out.println("mmu2 "+ miRna.getName());


				}else if(organism1.getName().equals("Rat") && !miRna.getName().startsWith("rno")){

					miRna.setName("rno"+"-"+name2.trim());
					//System.out.println("rno2 "+ miRna.getName());


				}else if(organism.getName().equals("C.elegans") && !miRna.getName().startsWith("cel")){

					miRna.setName("cel"+"-"+name2.trim());
					//System.out.println("cel2 "+ miRna.getName());


				}else if(organism.getName().equals("Chicken") && !miRna.getName().startsWith("gga")){

					miRna.setName("gga"+"-"+name2.trim());
					//System.out.println("gga "+miRna.getName());

				}else if(organism.getName().equals("Chimpanzee") && !miRna.getName().startsWith("ptr")){

					miRna.setName("ptr"+"-"+name2.trim());
					//System.out.println("ptr "+miRna.getName());

				}else if(organism.getName().equals("Dog") && !miRna.getName().startsWith("cfa")){

					miRna.setName("cfa"+"-"+name2.trim());
					//System.out.println("cfa2 "+miRna.getName());

				}else if(organism.getName().equals("Cow") && !miRna.getName().startsWith("bta")){

					miRna.setName("bta"+"-"+name2.trim());
					//System.out.println("bta2 "+miRna.getName()); 

				}else if(organism.getName().equals("Pig") && !miRna.getName().startsWith("ssc")){

					miRna.setName("ssc"+"-"+name2.trim());
					//System.out.println("ssc2 "+miRna.getName());

				} else if(organism.getName().equals("Zebrafish") && !miRna.getName().startsWith("dre")){

					miRna.setName("dre"+"-"+name2.trim());
					//System.out.println("dre2 "+miRna.getName());

				}else if(organism.getName().equals("rice") && !miRna.getName().startsWith("osa")){

					miRna.setName("osa"+"-"+name2.trim());
					//System.out.println("osa2 "+miRna.getName());

				}else if(organism.getName().equals("Cotton") && !miRna.getName().startsWith("gra")){

					miRna.setName("gra"+"-"+name2.trim());
					//System.out.println("gra2 "+ miRna.getName());

				}else if(organism.getName().equals("Maize") && !miRna.getName().startsWith("zma")){

					miRna.setName("zma"+"-"+name2.trim());
					//System.out.println("zma2 "+miRna.getName());

				}else if(organism.getName().equals("Chlamydomonas reinhardtii") && !miRna.getName().startsWith("cre")){

					miRna.setName("cre"+"-"+name2.trim());
					//System.out.println("cre2 "+miRna.getName());

				}else if(organism.getName().equals("Arabidopsis thaliana") && !miRna.getName().startsWith("ath")){

					miRna.setName("ath"+"-"+name2.trim());
					//System.out.println("ath2 "+miRna.getName());

				}else if(organism.getName().equals("grapevine") && !miRna.getName().startsWith("vvi")){

					miRna.setName("vvi"+"-"+name2.trim());
					//System.out.println("vvi2 "+miRna.getName());


				}else{

					miRna.setName(name2.trim());
					//System.out.println("Rest of organisms2 " + miRna.getName());

				}

			}
		}

		if (miRna.getName() == null && name3 != null) {

			miRna.setName(name3.trim());
			System.out.println(miRna.getName());

			for(Organism organism1 : organismList){

				if("Homo sapiens".equals(organism1.getName()) && !miRna.getName().startsWith("hsa")){

					miRna.setName("hsa"+"-"+name3.trim());
					System.out.println("hsa3 "+ miRna.getName());
				
				}else if ("Mouse".equals(organism1.getName()) && !miRna.getName().startsWith("mmu")){
					
					miRna.setName("mmu"+"-"+name3.trim());
					System.out.println("mmu3 "+ miRna.getName());
				
				}else if ("Rat".equals(organism1.getName()) && !miRna.getName().startsWith("rno")){

					miRna.setName("rno"+"-"+name3.trim());
					System.out.println("rno3 "+miRna.getName());
					
				}else if("C.elegans".equals(organism1.getName()) && !miRna.getName().startsWith("cel")){

					miRna.setName("cel"+"-"+name3.trim());
					System.out.println("cel "+ miRna.getName());
				
				}else if(organism1.getName().equals("Chicken") && !miRna.getName().startsWith("gga")){

					miRna.setName("gga"+"-"+name3.trim());
					System.out.println("gga3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Chimpanzee") && !miRna.getName().startsWith("ptr")){

					miRna.setName("ptr"+"-"+name3.trim());
					System.out.println("ptr3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Dog") && !miRna.getName().startsWith("cfa")){

					miRna.setName("cfa"+"-"+name3.trim());
					System.out.println("cfa3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Cow") && !miRna.getName().startsWith("bta")){

					miRna.setName("bta"+"-"+name3.trim());
					System.out.println("bta3 "+miRna.getName()); 

				}else if(organism1.getName().equals("Pig") && !miRna.getName().startsWith("ssc")){

					miRna.setName("ssc"+"-"+name3.trim());
					System.out.println("ssc3 "+miRna.getName());
					
				}else if(organism1.getName().equals("Zebrafish") && !miRna.getName().startsWith("dre")){

					miRna.setName("dre"+"-"+name3.trim());
					System.out.println("dre3 "+miRna.getName());
				
				}else if(organism1.getName().equals("rice") && !miRna.getName().startsWith("osa")){

					miRna.setName("osa"+"-"+name3.trim());
					System.out.println("osa3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Cotton") && !miRna.getName().startsWith("gra")){

					miRna.setName("gra"+"-"+name3.trim());
					System.out.println("gra3 "+ miRna.getName());
				
				}else if(organism1.getName().equals("Maize") && !miRna.getName().startsWith("zma") ){

					miRna.setName("zma"+"-"+name3.trim());
					System.out.println("zma3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Chlamydomonas reinhardtii") && !miRna.getName().startsWith("cre")){

					miRna.setName("cre"+"-"+name3.trim());
					System.out.println("cre3 "+miRna.getName());
				
				}else if(organism1.getName().equals("Arabidopsis thaliana") && !miRna.getName().startsWith("ath")){

					miRna.setName("ath"+"-"+name3.trim());
					System.out.println("ath3 "+miRna.getName());

				}else if(organism1.getName().equals("grapevine") && !miRna.getName().startsWith("vvi")){

					miRna.setName("vvi"+"-"+name3.trim());
					System.out.println("vvi3 "+miRna.getName());

				}else{
					
					miRna.setName(name3.trim());
					System.out.println("Rest of organisms3 " + miRna.getName());

				}

			}
		}


	}






	/*if (!createdObject(name3)) {
			miRna = null;
		}
	 */
	/*Disease disease = new Disease();
		disease.setName(disease_name);

		if (!createdObject(disease_name)) {
			disease = null;
		}

		EnvironmentalFactor environmentalFactor = new EnvironmentalFactor();
		environmentalFactor.setName(environmentalFactor_name);

		if (!createdObject(environmentalFactor_name)) {
			environmentalFactor = null;
		}

		ExpressionData ed = new ExpressionData();
		ed.setTreatment(treatment);
		ed.setProvenanceId(id);
		ed.setDescription(description);
		ed.setProvenanceId(cellularLine);
		ed.setProvenance("miREnvironment");

		PubmedDocument pubmedDoc = new PubmedDocument();
		pubmedDoc.setId(pubmedId);

		if (!createdObject(pubmedId)) {
			pubmedDoc = null;
		}

		// Inserta Disease (o recupera su id. si ya existe)
		if (disease!=null) {
			Object oldDisease = session.createCriteria(Disease.class)
					.add(Restrictions.eq("name", disease.getName())).uniqueResult();
			if (oldDisease == null) {
				session.save(disease);
				session.flush(); // to get the PK
			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
			}
		}

		if (environmentalFactor!=null) {

			// Inserta EnvironmentalFactor (o recupera su id. si ya existe)
			Object oldEnvironmentalFactor = session
					.createCriteria(EnvironmentalFactor.class)
					.add(Restrictions.eq("name", environmentalFactor.getName()))
					.uniqueResult();
			if (oldEnvironmentalFactor == null) {
				session.save(environmentalFactor);
				session.flush(); // to get the PK
			} else {
				EnvironmentalFactor environmentalFactorToUpdate = (EnvironmentalFactor) oldEnvironmentalFactor;
				environmentalFactorToUpdate.update(environmentalFactor);
				session.update(environmentalFactorToUpdate);
				environmentalFactor = environmentalFactorToUpdate;
			}

		}

		if (miRna!=null) {

			// Inserta MiRna (o recupera su id. si ya existe)
			Object oldMiRna = session.createCriteria(MiRna.class)
					.add(Restrictions.eq("name", miRna.getName())).uniqueResult();
			if (oldMiRna == null) {
				session.save(miRna);
				session.flush(); // to get the PK
			} else {
				MiRna miRnaToUpdate = (MiRna) oldMiRna;
				miRnaToUpdate.update(miRna);
				session.update(miRnaToUpdate);
				miRna = miRnaToUpdate;
			}
		}

		for (Organism organism2 : organismList) {
			if(organism2 != null){
				Object oldOrganism = session.createCriteria(Organism.class)
						.add(Restrictions.eq("name", organism2.getName()))
						.uniqueResult();
				if (oldOrganism == null) {
					session.save(organism2);
					session.flush(); // to get the PK
				} else {
					Organism organismToUpdate = (Organism) oldOrganism;
					organismToUpdate.update(organism2);
					session.update(organismToUpdate);
					organism2 = organismToUpdate;
				}

				if(miRna != null){

					MirnaHasOrganism mirnaHasOrganism = new MirnaHasOrganism(miRna.getPk(),
							organism2.getPk());

					// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
					Object oldmirnaHasOrganism = session
							.createCriteria(MirnaHasOrganism.class)
							.add(Restrictions.eq("mirna_pk", miRna.getPk()))
							.add(Restrictions.eq("organism_pk", organism2.getPk()))
							.uniqueResult();
					if (oldmirnaHasOrganism == null) {
						session.save(mirnaHasOrganism);
					}

				}
			}
		}
		// Inserta PubmedDocument (o recupera su id. si ya existe)

		if(pubmedDoc != null){

			Object oldPubmedDoc = session.createCriteria(PubmedDocument.class)
					.add(Restrictions.eq("id", pubmedDoc.getId())).uniqueResult();
			if (oldPubmedDoc == null) {
				session.save(pubmedDoc);
				session.flush(); // to get the PK

			} else {
				PubmedDocument pubmedDocToUpdate = (PubmedDocument) oldPubmedDoc;
				pubmedDocToUpdate.update(pubmedDoc);
				session.update(pubmedDocToUpdate);
				pubmedDoc = pubmedDocToUpdate;
			}

		}

		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Disease correspondiente)

		// y con enviromentalFactor

		if (miRna!=null) ed.setMirnaPk(miRna.getPk());
		if (disease!=null) ed.setDiseasePk(disease.getPk());
		if (ed!=null) ed.setEnvironmentalFactorPk(environmentalFactor.getPk());
		session.save(ed);

		if(miRna != null && pubmedDoc != null){
			// Relaciona miRNa con Document.
			MirnaHasPubmedDocument mirnaHasPubmedDocument = new MirnaHasPubmedDocument(
					miRna.getPk(), pubmedDoc.getPk());

			// Relaciona PubmedDocument con Mirna (si no lo estaba ya)
			Object oldMirnaHasPubmedDocument = session
					.createCriteria(MirnaHasPubmedDocument.class)
					.add(Restrictions.eq("mirnaPk", miRna.getPk()))
					.add(Restrictions.eq("pubmedDocumentPk", pubmedDoc.getPk()))
					.uniqueResult();
			if (oldMirnaHasPubmedDocument == null) {
				session.save(mirnaHasPubmedDocument);
			}
		}

		if(pubmedDoc != null){

			// Relaciona miRNa con ExpressionData.
			ExpressionDataHasPubmedDocument expresDataHasPubmedDocument = new ExpressionDataHasPubmedDocument(
					ed.getPk(), pubmedDoc.getPk());

			// Relaciona PubmedDocument con ExpressionData
			session.save(expresDataHasPubmedDocument);
		}
	}*/


	private String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "NULL".equals(field.trim()) || "n/a".equals(field.trim()) || "N/A".equals(field.trim()) ? null : field.trim();
	}


	public static void main(String[] args) throws Exception {

		MiREnvironment mirEnvironment = new MiREnvironment();
		mirEnvironment.insertIntoSQLModel();

	}

}