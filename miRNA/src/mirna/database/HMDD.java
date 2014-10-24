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
import mirna.dao.DiseaseDAO;
import mirna.dao.ExpressionDataDAO;
import mirna.dao.MiRnaDAO;
import mirna.dao.mysql.DiseaseDAOMySQLImpl;
import mirna.dao.mysql.ExpressionDataDAOMySQLImpl;
import mirna.dao.mysql.MiRnaDAOMySQLImpl;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de HMDD
 * 
 * @author Esteban López Camacho
 *
 */
public class HMDD implements IMirnaDatabase {
	
	private final String tableName = "hmdd";
	
	public HMDD() { }
	
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
					
					String id = tokens[0];
					String mir = tokens[1];
					String disease = tokens[2].replaceAll("'", "\\\\'");
					String pubmedid = tokens[3];
					String description = tokens[4].replaceAll("'", "\\\\'");

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ id + "','"
							+ mir + "','"
							+ disease + "','" 
							+ pubmedid + "','"
							+ description + "')";
					
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
				String id = rs.getString("id");
				String mir = rs.getString("mir").toLowerCase().trim();
				String diseaseField = rs.getString("disease").toLowerCase().trim();
				String pubmedid = rs.getString("pubmedid");
				String description = rs.getString("description");
				
				MiRna miRna = new MiRna();
				miRna.setName(mir);
				
				Disease disease = new Disease();
				disease.setName(diseaseField);
				
				ExpressionData dataExpression = new ExpressionData();
				dataExpression.setDescription(description);
				dataExpression.setPubmedId(pubmedid);
				dataExpression.setProvenanceId(id);
				dataExpression.setProvenance("hmdd");
				
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
				int dataExpressionId = dataExpressionDAO.create(dataExpression);
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
		
		HMDD hmdd = new HMDD();
		
		// String inputFile = "/Users/esteban/Softw/miRNA/hmdd/alldata.txt";
		// hmdd.insertInTable(inputFile);
		
		hmdd.insertIntoSQLModel();
		
	}

}