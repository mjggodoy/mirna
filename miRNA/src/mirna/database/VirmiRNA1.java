package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.Hairpin;
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

public class VirmiRNA1 extends VirmiRNA{
	
	private final String tableName = "virmirna1";
	
	public VirmiRNA1() throws MiRnaException {
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
				String id_virus = tokens[0];
				String virus_name = tokens[1];
				String virus_full_name = tokens[2];
				String link_virus = tokens[3];
				String miRNA = tokens[4];
				String miRNA_sequence = tokens[5];
				String length = tokens[6];
				String GC_proportion = tokens[7];
				String arm = tokens[8];
				String pre_miRNA = tokens[9];
				String pre_miRNA_sequence = tokens[10];
				String cell_line = tokens[11];
				String method = tokens[12];
				String pubmed_id = tokens[13];
				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ id_virus + "','"
						+ virus_name + "','"
						+ virus_full_name + "','"
						+ link_virus + "','"
						+ miRNA + "','"
						+ miRNA_sequence + "','"
						+ length + "','"
						+ GC_proportion + "','"
						+ arm + "','"
						+ pre_miRNA + "','"
						+ pre_miRNA_sequence + "','"
						+ cell_line  + "','"
						+ method  + "','"
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
			
			String virus_id = rs.getString("id_virus");
			String virus_name = rs.getString("virus_name");
			String virus_full_name = rs.getString("virus_full_name");
			String resource = rs.getString("link_virus");
			String mirna_name = rs.getString("mirna");
			String mirna_seq = rs.getString("mirna_seq");
			String length = rs.getString("length");
			String gc_proportion = rs.getString("gc_proportion");
			String arm = rs.getString("arm");
			String pre_mirna = rs.getString("pre_mirna");
			String pre_mirna_seq = rs.getString("pre_mirna_seq");
			String cell_line = rs.getString("cell_line");
			String method = rs.getString("method");
			String pubmed = rs.getString("pubmed");

			Organism organism = new Organism();
			organism.setName(virus_name);
			organism.setName(virus_full_name);
			organism.setResource(resource);
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			mirna.setSequence(mirna_seq);
			mirna.setGC_proportion(gc_proportion);
			mirna.setLength(length);
			
			Gene gene = new Gene();
			gene.setArm(arm);
			
			Hairpin hairpin = new Hairpin();
			hairpin.setName(pre_mirna);
			hairpin.setSequence(pre_mirna_seq);
			
			ExpressionData expressiondata = new ExpressionData();
			expressiondata.setCellularLine(cell_line);
			expressiondata.setMethod(method);
			expressiondata.setProvenance("VirmiRNA");
			expressiondata.setProvenanceId(virus_id);
			
			System.out.println(organism);
			System.out.println(expressiondata);
			System.out.println(mirna);
			System.out.println(gene);
			System.out.println(hairpin);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}

}
