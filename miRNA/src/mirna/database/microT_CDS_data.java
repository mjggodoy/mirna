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
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

public class microT_CDS_data extends MirnaDatabase {
	
	private final String tableName = "microt_cds";
	
	public microT_CDS_data() throws MiRnaException { super(); }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		String[] tokens2 = null;

		String  query = "";
		
		try {
			
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			String transcript_id = null;
			String gene_id = null;
			String miRNA = null;
			String miTG_score = null;
			String region = null;
			//String location = null;
			String chromosome = null;
			String coordinates = null;
			
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				if (line != null && !line.startsWith("UTR3") && !line.startsWith("CDS")) {
					
					
					transcript_id = tokens[0].replaceAll("'", "\\\\'");
					gene_id = tokens[1].replaceAll("'", "\\\\'");
					miRNA = tokens[2].replaceAll("'", "\\\\'");
					miTG_score = tokens[3].replaceAll("'", "\\\\'");
					
			
	
				}else{
					
					region = tokens[0].replaceAll("'", "\\\\'");
					//location = tokens[1];
					
					tokens2 = StringUtils.splitPreserveAllTokens(tokens[1], ":");
					chromosome = tokens2[0].replaceAll("'", "\\\\'");
					coordinates = tokens2[1].replaceAll("'", "\\\\'");
					
					
					query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ transcript_id + "','"
							+ gene_id + "','"
							+ miRNA + "','"
							+ miTG_score + "','"
							+ region + "','"
							+ chromosome + "','"
							+ coordinates + "')";
					
					stmt.executeUpdate(query);
					
					chromosome = null;
					coordinates = null;
					
					
				}
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(query);
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


			
			// CAMBIAR ESTO:
			
			String transcriptId = rs.getString("transcriptId");
			String geneId  = rs.getString("gene_Id");
			String miRNA = rs.getString("name");
			String miTG_score = rs.getString("miTG_score");
			String region = rs.getString("region");
			String chromosome = rs.getString("chromosome");
			String coordinates1 = rs.getString("3UTRstart");
			String coordinates2 = rs.getString("3UTRend");
			String coordinates3 = rs.getString("CDsStart");
			String coordinates4 = rs.getString("CDsEnd");


		
			MiRna miRna = new MiRna();
			miRna.setName(miRNA);
			
			Gene gene = new Gene();
			gene.setName(geneId);
			
			InteractionData id = new InteractionData();
			id.setMiTG_score(miTG_score);
			
			Target target = new Target();
			target.setRegion(region);
			target.setRegion(chromosome);
				
			Transcript transcript = new Transcript();
			transcript.setId(transcriptId);;

			System.out.println(miRna);
			System.out.println(gene);
			System.out.println(id);
			System.out.println(target);


			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}

	public static void main(String[] args) throws Exception {
		
		microT_CDS_data microT_CDS_data = new microT_CDS_data();

		String inputFile = "/Users/esteban/Softw/miRNA/microalgo/microT_CDS_data.csv";
		microT_CDS_data.insertInTable(inputFile);
		
		//microT_CDS_data.insertIntoSQLModel();
		
	}
	

}
