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
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP4 extends miRdSNP {
	
	private final String tableName = "miRdSNP4";
	
	public miRdSNP4() throws MiRnaException { super(); }
	
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
				
				String gene_name = tokens[0];
				String refseq_name = tokens[1];
				String miRNA = tokens[2];
				String snp = tokens[3];
				String disease = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];
				String expConf = "";//tokens[6];
				
				if (tokens.length==7) {
					expConf = tokens[6];
					
					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ gene_name + "','"
						+ refseq_name + "','"
						+ miRNA + "','"
						+ snp + "','"
						+ disease + "','"
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
			
			String ref_seq = rs.getString("gene").toLowerCase().trim();
			String gene_name = rs.getString("refseq").toLowerCase().trim();
			String snp_id = rs.getString("miR").toLowerCase().trim();
			String mirna_name = rs.getString("snp").toLowerCase().trim();
			String disease_name = rs.getString("diseases").toLowerCase().trim();
			String distance = rs.getString("distance").toLowerCase().trim();
			String exp_config = rs.getString("exp_conf").toLowerCase().trim();
			
			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setGeneId(ref_seq);

			Disease disease = new Disease();
			disease.setName(disease_name);
				
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			
			SNP snp = new SNP();
			snp.setSNPid(snp_id);
			snp.setDistance(distance);
			
			System.out.println(disease);
			System.out.println(gene);
			System.out.println(mirna);
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