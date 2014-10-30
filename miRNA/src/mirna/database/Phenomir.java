package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.dao.DiseaseDAO;
import mirna.dao.ExpressionDataDAO;
import mirna.dao.MiRnaDAO;
import mirna.dao.mysql.DiseaseDAOMySQLImpl;
import mirna.dao.mysql.ExpressionDataDAOMySQLImpl;
import mirna.dao.mysql.MiRnaDAOMySQLImpl;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class Phenomir implements IMirnaDatabase {
	
	private final String tableName = "phenomir";
	
	public Phenomir() {	}
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
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
					
					if (tokens.length<13) {
						count++;
						System.out.println(count);
						String line2 = br.readLine();
						if (line2.startsWith(",,")) line2 = line2.substring(2);
						line = line + "\t" + line2;
						tokens = StringUtils.splitPreserveAllTokens(line, "\t");
					}
					
					String phenomicid = tokens[0];
					String pmid = tokens[1];
					String disease = tokens[2];
					String diseasesubId = tokens[3];
					String class_ = tokens[4];
					String miRNA = tokens[5];
					String accession = tokens[6];
					String expression = tokens[7];
					String foldchangemin = tokens[8];
					String foldchangemax = tokens[9];
					String id = tokens[10];
					String name = tokens[11];
					String method = tokens[12];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ phenomicid + "','"
							+ pmid + "','"
							+ disease + "','"
							+ diseasesubId + "','"
							+ class_ + "','"
							+ miRNA + "','"
							+ accession + "','"
							+ expression + "','"
							+ foldchangemin + "','"
							+ foldchangemax + "','"
							+ id + "','"
							+ name + "','"
							+ method + "')";
					
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
	
	public String specificFileFix(String csvInputFile) throws IOException {
		
		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;
		
		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {
			
			if (line1.startsWith(",,")) {
				line0 = line0.trim() + "\t\t" + line1.substring(2);
				line1 = br.readLine();
			}
			
			pw.println(line0);
			line0 = line1;
			
		}
		
		if (line0!=null) pw.println(line0);
		
		br.close();
		pw.close();
		
		return newFile;
	}
	
	@Override
	public void insertIntoSQLModel() throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";

		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			MiRnaDAO miRnaDAO = new MiRnaDAOMySQLImpl();
			DiseaseDAO diseaseDAO = new DiseaseDAOMySQLImpl();
			ExpressionDataDAO dataExpressionDAO = new ExpressionDataDAOMySQLImpl();
			
			// iterate through the java resultset
			int count = 0;
			while (rs.next()) {
				
				count++;
				if (count%100==0) System.out.println(count);
				
				//int pk = rs.getInt("pk");
				String phenomicid = rs.getString("phenomicid");
				String pmid = rs.getString("pmid");
				String diseaseField = rs.getString("disease").toLowerCase().trim();
				String diseaseClass = rs.getString("class").toLowerCase().trim();
				String mirna = rs.getString("miRNA").toLowerCase().trim();
				String accession = rs.getString("accession").toLowerCase().trim();
				String evidence = rs.getString("expression");
				String foldchangemin = rs.getString("foldchangemin");
				String foldchangemax = rs.getString("foldchangemax");
				String studyDesign = rs.getString("name");
				String method = rs.getString("method");
				
//				phenomicid -> ExpressionData.phenomicid
//				pmid -> ExpressionData.pubmedId
//				disease -> disease.name
//				diseasesubId -> 
//				class_ -> disease.diseaseClass
//				miRNA -> miRNA.name
//				accession -> miRNA.accesionNumber
//				expression -> ExpressionData.evidence
//				foldchangemin -> ExpressionData.foldChangeMin
//				foldchangemax -> ExpressionData.foldChangeMax
//				id -> no correspondence (the meaning is not biologically useful)
//				name -> ExpressionData.studyDesign
//				method -> ExpressionData.method
				
				MiRna miRna = new MiRna();
				miRna.setName(mirna);
				miRna.setAccessionNumber(accession);
				
				Disease disease = new Disease();
				disease.setName(diseaseField);
				disease.setDiseaseClass(diseaseClass);
				
				ExpressionData ed = new ExpressionData();
				ed.setProvenanceId(phenomicid);
				ed.setPubmedId(pmid);
				ed.setEvidence(evidence);
				ed.setFoldchangeMin(foldchangemin);
				ed.setFoldchangeMax(foldchangemax);
				ed.setStudyDesign(studyDesign);
				ed.setMethod(method);
				ed.setProvenance("PhenomiR");
				
				// Inserta MiRna (o recupera su id. si ya existe)
				MiRna oldMiRna = miRnaDAO.findByName(miRna.getName());
				if (oldMiRna==null) {
					int key = miRnaDAO.create(miRna);
					miRna.setPk(key);
				} else {
					miRna.setPk(oldMiRna.getPk());
					int conflict = miRna.checkConflict(oldMiRna);
					if (conflict > 0) {
						//System.out.println(miRna);
						//System.out.println(oldMiRna);
						miRnaDAO.update(miRna);
					} else if (conflict == -1){
						String msg = "Conflicto detectado!"
								+ "\nEntrada en BD: " + oldMiRna
								+ "\nEntrada nueva: " + miRna;
						throw new MiRnaException(msg);
					}
				}
				
				// Inserta Disease (o recupera su id. si ya existe)
				Disease oldDisease = diseaseDAO.findByName(disease.getName());
				if (oldDisease==null) {
					int key = diseaseDAO.create(disease);
					disease.setPk(key);
				} else {
					disease.setPk(oldDisease.getPk());
					int conflict = disease.checkConflict(oldDisease);
					if (conflict > 0) {
						diseaseDAO.update(disease);
					} else if (conflict == -1){
						String msg = "Conflicto detectado!"
								+ "\nEntrada en BD: " + oldDisease
								+ "\nEntrada nueva: " + disease;
						throw new MiRnaException(msg);
					}
				}
				
				// Inserta nueva DataExpression
				// (y la relaciona con el MiRna y Disease correspondiente)
				int dataExpressionId = dataExpressionDAO.create(ed);
				dataExpressionDAO.newMiRnaInvolved(dataExpressionId, miRna.getPk());
				dataExpressionDAO.newRelatedDisease(dataExpressionId, disease.getPk());
				// DataExpression igual (?)
				
			}
			stmt.close();
		} catch (SQLException e) {
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

	
	public static void main(String[] args) throws Exception {
		
		Phenomir phenomir = new Phenomir();
		
//		String inputFile = "/Users/esteban/Softw/miRNA/phenomir-2.0.tbl";
//		inputFile = phenomir.specificFileFix(inputFile);
//		phenomir.insertInTable(inputFile);
		
		phenomir.insertIntoSQLModel();
		
	}

}