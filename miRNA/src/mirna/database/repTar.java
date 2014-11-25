package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class repTar implements IMirnaDatabase {
	
	private String csvInputFile1;
	
	public repTar(String csvInputFile1) {
		this.csvInputFile1 = csvInputFile1;
	}
	
	@Override
	public void insertInTable(String tableName) throws Exception {
		insertInTable(tableName, null);
	}

	@Override
	public void insertIntoSQLModel(String originTable) throws Exception {
		
	}

	@Override
	public void insertIntoSQLModel(String originTable, Integer maxLines)
			throws Exception {
		
	}
		
	public void insertInTable(String tableName, Integer maxLines) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null, tokens2 = null, tokens3 = null, tokens4 = null, tokens5= null, tokens6 = null, tokens7 = null, tokens8 = null, tokens9 = null, tokens10 = null, tokens11 = null;
//		String specie = "";
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile1);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
				String gene_token = tokens[0];
					
				tokens2 = StringUtils.splitPreserveAllTokens(gene_token, ":::");
				
				String gene_symbol  = tokens2[0];
				String gene_accesion = tokens2[1];
				
				String mirna = tokens[1];
				
//				if(tokens[1].contains("hsa")){
//					
//					specie = "Homo sapiens (human)";
//					
//					
//				}else if (tokens[1].contains("hsa")){ //TODO:cambiar
//					
//					specie = "Mus musculus (mouse)";
//
//					
//					
//				}
				
				String sequence_start = tokens[2];
				tokens3 = StringUtils.splitPreserveAllTokens(sequence_start, ":");
				sequence_start = tokens3[1];
				
				String sequence_end = tokens[3];
				tokens4 = StringUtils.splitPreserveAllTokens(sequence_end, ":");
				sequence_end = tokens4[1];
				
				String minimal_free_energy = tokens[4];
				tokens5 = StringUtils.splitPreserveAllTokens(minimal_free_energy, ":");
				minimal_free_energy = tokens5[1];
				
				String normalized_free_energy = tokens[5];
				tokens6 = StringUtils.splitPreserveAllTokens(normalized_free_energy, ":");	
				normalized_free_energy = tokens6[1];
				
				String gu_proportion = tokens[6];	
				tokens7 = StringUtils.splitPreserveAllTokens(gu_proportion, ":");	
				gu_proportion = tokens7[1];		
				
				String binding_site_pattern = tokens[8].replaceAll("'", "\\\\'");	
				tokens8 = StringUtils.splitPreserveAllTokens(binding_site_pattern, ":");
				binding_site_pattern = tokens8[1];	
				
				String site_conservation_score = tokens[9];
				tokens9 = StringUtils.splitPreserveAllTokens(site_conservation_score, ":");
				site_conservation_score	= tokens9[1];
						
				String UTR_conservation_score = tokens[10];
				tokens10 = StringUtils.splitPreserveAllTokens(UTR_conservation_score, ":");
				UTR_conservation_score	= tokens10[1];
				
				String repeated_motifs = tokens[11];
				tokens11 = StringUtils.splitPreserveAllTokens(repeated_motifs, ":");
				repeated_motifs	= tokens11[1];
						
				String algorithm = tokens[12];
				
				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ gene_symbol + "','"
						+ gene_accesion + "','"
						+ mirna + "','"
						+ sequence_start + "','"
						+ sequence_end + "','"
						+ minimal_free_energy + "','"
						+ normalized_free_energy + "','"
						+ gu_proportion + "','"
						+ binding_site_pattern + "','"
						+ site_conservation_score + "','"
						+ UTR_conservation_score + "','"
						+ repeated_motifs + "','"
						+ algorithm + "')";
				
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
		
		
		
		
	public static void main(String[] args) throws Exception {
		
//		String inputFile1 = "/Users/esteban/Softw/miRNA/repTar/human_pred.txt";
//		repTar reptar1 = new repTar(inputFile1);
//		reptar1.insertInTable("repTar_human");
		String inputFile2 = "/Users/esteban/Softw/miRNA/repTar/mouse_pred.txt";
		repTar reptar2 = new repTar(inputFile2);
		reptar2.insertInTable("repTar_mouse");

		
		
	}


}
