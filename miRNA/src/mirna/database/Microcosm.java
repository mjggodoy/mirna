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

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Microcosm
 * 
 * @author Esteban López Camacho
 *
 */
public class Microcosm implements IMirnaDatabase {
	
	private final String tableName = "microcosm_homo_sapiens";
	
	public Microcosm() { }
	
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
	
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					if ((!"".equals(line)) && (!line.startsWith("##"))) {
						
						String group = tokens[0];
						String seq = tokens[1];
						String method = tokens[2];
						String feature = tokens[3];
						String chr = tokens[4];
						String start = tokens[5];
						String end = tokens[6];
						String strand = tokens[7];
						String phase = tokens[8];
						String score = tokens[9];
						String pvalueOg = tokens[10];
						String transcriptId = tokens[11];
						String externalName = tokens[12];

						String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
								+ group + "','"
								+ seq + "','"
								+ method + "','"
								+ feature + "','"
								+ chr + "','"
								+ start + "','"
								+ end + "','"
								+ strand + "','"
								+ phase + "','"
								+ score + "','"
								+ pvalueOg + "','"
								+ transcriptId + "','"
								+ externalName + "')";
						
						stmt.executeUpdate(query);
						
					}
						
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
	
	@Override
	public void insertIntoSQLModel() throws Exception {
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";

		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			//int count = 0;


			
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

	public static void main(String[] args) throws Exception {
		
		Microcosm microcosm = new Microcosm();
		
		//1
//		String inputFile = "/Users/esteban/Softw/miRNA/microcosm/v5.txt.homo_sapiens";
//		microcosm.insertInTable(inputFile);
		
		//2
		microcosm.insertIntoSQLModel();
		
	}

}