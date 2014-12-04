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
import mirna.beans.Gene;
import mirna.beans.MiRna;
import mirna.beans.SNP;
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
				String position_initial = tokens[1];
				String position_final = tokens[2];
				String disease_SNP = tokens[3];
				tokens2 = StringUtils.splitPreserveAllTokens(disease_SNP, ":");
				String SNP = tokens2[0];
				String disease = tokens2[1].replaceAll("'", "\\\\'");
				String orientation = tokens[5];
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ chromosome + "','"
						+ position_initial + "','"
						+ position_final + "','"
						+ SNP + "','"
						+ disease + "','"
						+ orientation + "')";
				
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
			
			String chromosome = rs.getString("chromosome").toLowerCase().trim();
			String position = rs.getString("start").toLowerCase().trim();
			String snp_name = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();
			String orientation = rs.getString("orientation").toLowerCase().trim();

			
			Disease disease = new Disease();
			disease.setName(disease_name);
					
			SNP snp = new SNP();
			snp.setChromosome(chromosome);
			snp.setSNPid(snp_name);
			snp.setPosition(position);
			snp.setOrientation(orientation);
			
			System.out.println(disease);
			System.out.println(snp);
			
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
}