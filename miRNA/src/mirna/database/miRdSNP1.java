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

/**
 * Código para procesar los datos de miRdSNP
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP1 extends miRdSNP {
	
	private final String tableName = "mirdsnp1";
	
	public miRdSNP1() throws MiRnaException { super(); }
	
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
				
				String pubmedid = tokens[0];
				String year = tokens[1];
				String month = tokens[2];
				String article_date = tokens[3];
				String journal = tokens[4].replaceAll("'", "\\\\'");
				String title = tokens[5].replaceAll("'", "\\\\'");
				String snpId = tokens[6];
				String disease = tokens[7].replaceAll("'", "\\\\'");
				String link = tokens[8];
				
				if (tokens.length>9) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ pubmedid + "','"
						+ year + "','"
						+ month + "','"
						+ article_date + "','"
						+ journal + "','"
						+ title + "','"
						+ snpId + "','"
						+ disease + "','"
						+ link + "')";
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
			
			String pubmedId = rs.getString("pubmed_id").toLowerCase().trim();
			String year = rs.getString("year").toLowerCase().trim();
			String journal = rs.getString("journal").toLowerCase().trim();
			String description = rs.getString("title").toLowerCase().trim();
			String snp_id = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();
			String resource = rs.getString("link").toLowerCase().trim();

			
			Disease disease = new Disease();
			disease.setName(disease_name);
			
			Gene gene = new Gene();
			gene.setYear(year);
			gene.setJournal(journal);
			gene.setDescription(description);
			gene.setResource(resource);
			gene.setPubmedId(pubmedId);
			
			SNP snp = new SNP();
			snp.setSNPid(snp_id);
			
			
			System.out.println(disease);
			System.out.println(snp);
			System.out.println(gene);

			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}

}