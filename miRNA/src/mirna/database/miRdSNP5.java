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

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP5 extends miRdSNP {
	
	private final String tableName = "miRdSNP5";
	
	public miRdSNP5() throws MiRnaException { super(); }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null, tokens2 = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			br.readLine();
			br.readLine();


			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				String chromosome = tokens[0];
				String start_sequence = tokens[1];
				String end_sequence = tokens[2];
				String disease_SNP = tokens[3];
				tokens2 = StringUtils.splitPreserveAllTokens(disease_SNP, ":");
				String SNP = tokens2[0];
				String disease = tokens2[1].replaceAll("'", "\\\\'");
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ chromosome + "','"
						+ start_sequence + "','"
						+ end_sequence + "','"
						+ SNP + "','"
						+ disease + "')";
				
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