package mirna.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP {
	
	private String csvInputFile1;
	private String cvsInputFile2;
	private String cvsInputFile3;
	private String cvsInputFile4;
	
	public miRdSNP(String csvInputFile1, String csvInputFile2, String csvInputFile3, String csvInputFile4) {
		this.csvInputFile1 = csvInputFile1;
		this.cvsInputFile2 = csvInputFile2;
		this.cvsInputFile3 = cvsInputFile3;
		this.cvsInputFile4 = cvsInputFile4;
		
	}
	
	public void insertInTablemirdsnpFile1(String tableName, Integer maxLines) throws Exception {
		this.insertInTablemirdsnpFile1(tableName, null);
	}
	
	public void insertInTablemirdsnpFile2(String tableName, Integer maxLines) throws Exception {
		this.insertInTablemirdsnpFile2(tableName, null);
	}
	
	public void insertInTablemirdsnpFile3(String tableName, Integer maxLines) throws Exception {
		this.insertInTablemirdsnpFile3(tableName, null);
	}
	
	public void insertInTablemirdsnpFile4(String tableName, Integer maxLines) throws Exception {
		this.insertInTablemirdsnpFile4(tableName, null);
	}
	
	public void insertInTablemirdsnpFile1(String tableName) throws Exception {
		
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
			
			FileReader fr = new FileReader(csvInputFile1);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				
					String pubmedid = tokens[0];
					String year = tokens[1];
					String month = tokens[2];
					String article_date = tokens[3];
					String journal = tokens[4];
					String title = tokens[5];
					String snpId = tokens[6];
					String disease = tokens[7];
					String link = tokens[8];
				
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
	
	
	public void insertInTablemirdsnpFile2(String tableName) throws Exception {
		
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(cvsInputFile2);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				
					String gene = tokens[0];
					String refseq_name = tokens[1];
					String name_miRNA = tokens[2];
					String SNPid = tokens[3];
					String disease_name = tokens[4];
					
				
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ gene + "','"
							+ refseq_name + "','"
							+ name_miRNA + "','"
							+ SNPid + "','"
							+ disease_name + "')";
					
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
		
		
	public void insertInTablemirdsnpFile3(String tableName) throws Exception {
		

		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(cvsInputFile3);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				
					String refseq_name = tokens[0];
					String gene_name = tokens[1];
					String snps = tokens[2];
					String miRNA = tokens[3];
					String disease = tokens[4];
					
				
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ refseq_name + "','"
							+ gene_name + "','"
							+ snps + "','"
							+ miRNA + "','"
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
		
		
public void insertInTablemirdsnpFile4(String tableName) throws Exception {
		

		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(cvsInputFile4);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				
					String gene_name = tokens[0];
					String refseq_name = tokens[1];
					String miRNA = tokens[2];
					String snp = tokens[3];
					String disease = tokens[4];
					String distance = tokens[5];
					String expertimental_confirmation = tokens[6];

					
				
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ gene_name + "','"
							+ refseq_name + "','"
							+ miRNA + "','"
							+ snp + "','"
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
		
	
	public void insertIntoSQLModel(String originTable) throws Exception {
	this.insertIntoSQLModel(originTable, null);
	}

	public void insertIntoSQLModel(String originTable, Integer maxLines) throws Exception {
	
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile1 = "/Users/esteban/Softw/miRNA/mirdsnp-dsnps-v11.03.txt";
		String inputFile2 = "/Users/esteban/Softw/miRNA/mirdsnp-dsnp-generated-mir-targets-v11.03.txt";
		String inputFile3 = "/Users/esteban/Softw/miRNA/mirdsnp-by-gene-v11.03.txt";
		String inputFile4 = "/Users/esteban/Softw/miRNA/mirdsnp-snp-mir-distance-v11.03.txt";
		miRdSNP mirdsnp = new miRdSNP(inputFile1, inputFile2, inputFile3, inputFile4);
		mirdsnp.insertInTablemirdsnpFile1("mirdsnp1");
		mirdsnp.insertInTablemirdsnpFile2("mirdsnp2");
		mirdsnp.insertInTablemirdsnpFile3("mirdsnp3");
		mirdsnp.insertInTablemirdsnpFile4("mirdsnp4");



		
	}

}