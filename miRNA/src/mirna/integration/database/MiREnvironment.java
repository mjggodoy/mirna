package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.integration.beans.Disease;
import mirna.integration.beans.EnvironmentalFactor;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.MiRna;
import mirna.integration.beans.Organism;
import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.integration.beans.nToM.MirnaHasOrganism;
import mirna.integration.beans.nToM.MirnaHasPubmedDocument;
import mirna.integration.exception.MiRnaException;

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

		@SuppressWarnings("unused")
		int pk = rs.getInt("pk");
		
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

		

		String[] organismTokens = StringUtils.splitPreserveAllTokens(specie_name, ";");
		List<Organism> organismList = new ArrayList<Organism>();

		for (String organismlist : organismTokens) {
			Organism organism = new Organism();
			organism.setName(organismlist);
			if (createdObject(organismlist)) {
				organismList.add(organism);
			}
		}


//		System.out.println(pk + " : "+ name + " : " + name2 + ": " + name3);
		
		List<MiRna> mirnaList = new ArrayList<MiRna>();
		MiRna miRna_ = new MiRna();
		if(name != null) miRna_.setName(name.trim());
//		System.out.println(miRna_.getName());
		
		if (miRna_.getName() == null && name2 != null) {
			miRna_.setName(name2.trim());
//			System.out.println(miRna_.getName());
		}
		
		if (miRna_.getName() == null && name3 != null) {
			miRna_.setName(name3.trim());
//			System.out.println(miRna_.getName());
		}
		
		if (miRna_.getName()!=null) {
			for (Organism o : organismList) {
				mirnaList.add(createMirnaWithOrganismInName(miRna_.getName(), o));
				//System.out.println(createMirnaWithOrganismInName(miRna_.getName(),o).getName() + " : " + o.getName());
			}
		}

	/*if (!createdObject(name3)) {
			miRna = null;
		}
	 */
		
		Disease disease = new Disease();
		disease.setName(disease_name);

		if (!createdObject(disease_name)) {
			disease = null;
		}

		EnvironmentalFactor environmentalFactor = new EnvironmentalFactor();
		environmentalFactor.setName(environmentalFactor_name);

		if (!createdObject(environmentalFactor_name)) {
			environmentalFactor = null;
		}

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
		

		for (int i=0; i<mirnaList.size(); i++) {
			Organism organism2 = organismList.get(i);
			MiRna miRna = mirnaList.get(i);
			
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
				mirnaList.set(i, miRna);
			}
			
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
				//organismList.set(i, organismToUpdate);
				organism2 = organismToUpdate;
			}
			
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
		}

		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Disease correspondiente)

		// y con enviromentalFactor
		
		for (MiRna miRna : mirnaList) {
			
			ExpressionData ed = new ExpressionData();
			ed.setTreatment(treatment);
			ed.setProvenanceId(id);
			ed.setDescription(description);
			ed.setCellularLine(cellularLine);
			ed.setProvenance("miREnvironment");
			
			if (miRna!=null) ed.setMirnaPk(miRna.getPk());
			if (disease!=null) ed.setDiseasePk(disease.getPk());
			if (environmentalFactor!=null) ed.setEnvironmentalFactorPk(environmentalFactor.getPk());
			session.save(ed);
			session.flush();
			
			if(pubmedDoc != null){

				// Relaciona miRNa con ExpressionData.
				ExpressionDataHasPubmedDocument expresDataHasPubmedDocument = new ExpressionDataHasPubmedDocument(
						ed.getPk(), pubmedDoc.getPk());

				// Relaciona PubmedDocument con ExpressionData
				session.save(expresDataHasPubmedDocument);
			}
		}
		
	}
	
	private MiRna createMirnaWithOrganismInName(String name, Organism organism) {
		
		MiRna miRna = new MiRna();
		miRna.setName(name);
		
		if ("Homo sapiens".equals(organism.getName()) && !name.startsWith("hsa")) {
			miRna.setName("hsa"+"-"+name.trim());
			//System.out.println("hsa2 "+ miRna.getName());
		
		} else if ("Mouse".equals(organism.getName()) && !name.startsWith("mmu")){
			miRna.setName("mmu"+"-"+name.trim());
			//System.out.println("mmu2 "+ miRna.getName());

		}else if(organism.getName().equals("Rat") && !miRna.getName().startsWith("rno")){
			miRna.setName("rno"+"-"+name.trim());
			//System.out.println("rno2 "+ miRna.getName());

		}else if(organism.getName().equals("C.elegans") && !miRna.getName().startsWith("cel")){
			miRna.setName("cel"+"-"+name.trim());
			//System.out.println("cel2 "+ miRna.getName());

		}else if(organism.getName().equals("Chicken") && !miRna.getName().startsWith("gga")){
			miRna.setName("gga"+"-"+name.trim());
			//System.out.println("gga "+miRna.getName());

		}else if(organism.getName().equals("Chimpanzee") && !miRna.getName().startsWith("ptr")){
			miRna.setName("ptr"+"-"+name.trim());
			//System.out.println("ptr "+miRna.getName());

		}else if(organism.getName().equals("Dog") && !miRna.getName().startsWith("cfa")){
			miRna.setName("cfa"+"-"+name.trim());
			//System.out.println("cfa2 "+miRna.getName());

		}else if(organism.getName().equals("Cow") && !miRna.getName().startsWith("bta")){
			miRna.setName("bta"+"-"+name.trim());
			//System.out.println("bta2 "+miRna.getName()); 

		}else if(organism.getName().equals("Pig") && !miRna.getName().startsWith("ssc")){
			miRna.setName("ssc"+"-"+name.trim());
			//System.out.println("ssc2 "+miRna.getName());

		} else if(organism.getName().equals("Zebrafish") && !miRna.getName().startsWith("dre")){
			miRna.setName("dre"+"-"+name.trim());
			//System.out.println("dre2 "+miRna.getName());

		}else if(organism.getName().equals("rice") && !miRna.getName().startsWith("osa")){
			miRna.setName("osa"+"-"+name.trim());
			//System.out.println("osa2 "+miRna.getName());

		}else if(organism.getName().equals("Cotton") && !miRna.getName().startsWith("gra")){
			miRna.setName("gra"+"-"+name.trim());
			//System.out.println("gra2 "+ miRna.getName());

		}else if(organism.getName().equals("Maize") && !miRna.getName().startsWith("zma")){
			miRna.setName("zma"+"-"+name.trim());
			//System.out.println("zma2 "+miRna.getName());

		}else if(organism.getName().equals("Chlamydomonas reinhardtii") && !miRna.getName().startsWith("cre")){
			miRna.setName("cre"+"-"+name.trim());
			//System.out.println("cre2 "+miRna.getName());

		}else if(organism.getName().equals("Arabidopsis thaliana") && !miRna.getName().startsWith("ath")){
			miRna.setName("ath"+"-"+name.trim());
			//System.out.println("ath2 "+miRna.getName());

		}else if(organism.getName().equals("grapevine") && !miRna.getName().startsWith("vvi")){
			miRna.setName("vvi"+"-"+name.trim());
			//System.out.println("vvi2 "+miRna.getName());

		}
		
		return miRna;
		
	}


	protected String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "NULL".equals(field.trim()) || "n/a".equals(field.trim()) || "N/A".equals(field.trim()) ? null : field.trim();
	}


	public static void main(String[] args) throws Exception {

		MiREnvironment mirEnvironment = new MiREnvironment();
		mirEnvironment.insertIntoSQLModel();

	}

}