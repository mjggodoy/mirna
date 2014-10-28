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
 * Código para procesar los datos de Mir2Disease
 * 
 * @author Esteban López Camacho
 *
 */
public class Mir2Disease implements IMirnaDatabase {
	
	private final String tableName = "mir2disease";
	
	public Mir2Disease() { }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		String query = "";
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count + " : " + line);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				String mirna = tokens[0];
				String disease = tokens[1].replaceAll("'", "\\\\'");
				String expression = tokens[2];
				String method = tokens[3];
				String date = tokens[4];
				String reference = tokens[5].replaceAll("'", "\\\\'");

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ disease + "','"
						+ expression + "','"
						+ method + "','"
						+ date + "','"
						+ reference + "')";
				
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
				
//				mirna -> miRNA.name
//				disease  -> Disease.name
//				expression	-> ExpressionData.evidence
//				method -> ExpressionData.method
//				date -> ExpressionData.year
//				description -> ExpressionData.description
				
				//int pk = rs.getInt("pk");
				String mirna = rs.getString("mirna").toLowerCase().trim();
				String diseaseField = rs.getString("disease").toLowerCase().trim();
				String evidence = rs.getString("expression");
				String method = rs.getString("method");
				String year = rs.getString("date");
				String description = rs.getString("reference");
				
				MiRna miRna = new MiRna();
				miRna.setName(mirna);
				
				Disease disease = new Disease();
				disease.setName(diseaseField);
				
				ExpressionData ed = new ExpressionData();
				ed.setDescription(description);
				ed.setEvidence(evidence);
				ed.setMethod(method);
				ed.setYear(year);
				ed.setProvenance("miR2Disease");
				
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

	public String specificFileFix(String csvInputFile) throws IOException {
		
		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;
		
		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {
			
			if (!line1.startsWith("hsa")) {
				line0 = line0 + " " + line1;
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
	
	public static void main(String[] args) throws Exception {
		
		Mir2Disease mir2disease = new Mir2Disease();
		
//		String inputFile = "/Users/esteban/Softw/miRNA/mir2disease_AllEntries.txt";
//		inputFile = mir2disease.specificFileFix(inputFile);
//		mir2disease.insertInTable(inputFile);
		
		mir2disease.insertIntoSQLModel();
	}

}