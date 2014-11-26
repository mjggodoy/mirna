package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

public class VirmiRNA2 extends VirmiRNA {
	
	private final String tableName = "virmirna2";
	
	public VirmiRNA2() throws MiRnaException {
		super();
	}
	
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
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
				String AVM_id = tokens[0];
				String miRNA = tokens[1];
				String miRNA_sequence = tokens[2];
				String miRBase_id = tokens[3];
				String specie = tokens[4];
				String virus = tokens[5];
				String virus_full_name = tokens[6];
				String taxonomy = tokens[7];
				String target = tokens[8].replaceAll("'", "\\\\'");;
				String Uniprot = tokens[9];
				String target_process = tokens[10];
				String method = tokens[11].replaceAll("'", "\\\\'");;
				String cell_line = tokens[12];
				String target_sequence = tokens[13];
				String target_region = tokens[14].replaceAll("'", "\\\\'");
				String target_coordinates = tokens[15];
				String seed_match = tokens[16];
				String target_reference = tokens[17];
				String pubmed_id = tokens[18];
				
				if ((tokens.length>20) || ((tokens.length==20) && (!"".equals(tokens[19])))) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
				
//				int index1 = pubmed_id.indexOf("\">");
//				int index2 = pubmed_id.indexOf("</");
//				pubmed_id = pubmed_id.substring(index1+2, index2-2);

				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ AVM_id + "','"
						+ miRNA + "','"
						+ miRNA_sequence + "','"
						+ miRBase_id + "','"
						+ specie + "','"
						+ virus + "','"
						+ virus_full_name + "','"
						+ taxonomy + "','"
						+ target + "','"
						+ Uniprot + "','"
						+ target_process + "','"
						+ method + "','"
						+ cell_line  + "','"
						+ target_sequence  + "','"
						+ target_region  + "','"
						+ target_coordinates  + "','"
						+ seed_match  + "','"
						+ target_reference + "','"
						+ pubmed_id + "')";
				
				
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


			rs.next();
			// CAMBIAR ESTO:
			
			String seq = rs.getString("seq");
			String method = rs.getString("method");
			String feature = rs.getString("feature").toLowerCase().trim();
			String chromosome = rs.getString("chr").toLowerCase().trim();
			String start = rs.getString("start").toLowerCase().trim();
			String end = rs.getString("end").toLowerCase().trim();
			String strand = rs.getString("strand").toLowerCase().trim();
			String phase = rs.getString("phase");
			String score = rs.getString("score");
			String pvalue_og = rs.getString("pvalue_og");
			String transcriptId = rs.getString("transcript_id");
			String externalName = rs.getString("external_name");
			
			MiRna miRna = new MiRna();
			miRna.setName(seq);
			
			InteractionData id = new InteractionData();
			id.setMethod(method);
			id.setFeature(feature);
			id.setPhase(phase);
			id.setScore(score);
			id.setPvalue_og(pvalue_og);
			
			Target target = new Target();
			target.setChromosome(chromosome);
			target.setStart_strand(start);
			target.setEnd_strand(end);
			target.setPolarity(strand);
			
			Transcript transcript = new Transcript();
			transcript.setId(transcriptId);
			transcript.setExternalName(externalName);
			
			System.out.println(miRna);
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

}
