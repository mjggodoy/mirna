package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP3 extends miRdSNP {
	
	private final String tableName = "mirdsnp3";
	
	public miRdSNP3() throws MiRnaException { super(); }
	
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
				
				//tokens = StringUtils.splitPreserveAllTokens(line, ",");
				tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				
				for (int i=0; i<tokens.length; i++) {
					tokens[i] = quitarComillas(tokens[i]);
				}
	
				String geneName = tokens[0];
				String refseqId = tokens[1];
				String miR = tokens[2];
				String SNP = tokens[3];
				String diseases = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];
				
				String expConf = "";//tokens[6];
				
				if (tokens.length==7) {
					expConf = tokens[6];
					
					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}
					
				if (tokens.length>7) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ geneName + "','"
						+ refseqId + "','"
						+ miR + "','"
						+ SNP + "','"
						+ diseases + "','"
						+ distance + "','"
						+ expConf + "')";
				
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
		
	public void insertIntoSQLModel() throws Exception {

		Connection con = null;
		
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
			//int count = 0;


			rs.next();
			// CAMBIAR ESTO:
			
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
			
			System.out.println(miRna);
			System.out.println(disease);
			System.out.println(ed);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
}