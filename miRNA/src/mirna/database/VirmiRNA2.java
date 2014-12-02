package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.BiologicalProcess;
import mirna.beans.ExpressionData;
import mirna.beans.Gene;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Target;
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
			
			
			String mirna_name = rs.getString("mirna");
			String mirna_seq = rs.getString("mirna_seq");
			String accesion_number = rs.getString("mirbase_id");
			String specie_target = rs.getString("specie");
			String organism_name = rs.getString("virus");
			String organism_name_full = rs.getString("virus_full_name");
			String resource_organism = rs.getString("taxonomy");
			String target1 = rs.getString("target");
			String uniprot = rs.getString("uniprot");
			String target_process = rs.getString("target_process");
			String method = rs.getString("method");
			String cell_line = rs.getString("cell_line");
			String target_sequence = rs.getString("target_sequence");
			String target_region = rs.getString("target_region");
			String target_coords = rs.getString("target_coords");
			String send_match = rs.getString("seed_match");
			String target_resource = rs.getString("target_ref");
			String target_pubmedId = rs.getString("pubmed_id");
			
			MiRna mirna = new MiRna();
			mirna.setName(mirna_name);
			mirna.setSequence(mirna_seq);
			mirna.setAccessionNumber(accesion_number);
			
			Organism organism = new Organism();
			organism.setSpecie(specie_target);
			organism.setName(organism_name_full);
			organism.setName(organism_name);
			organism.setResource(resource_organism);
			
			Target target = new Target();
			target.setSequence(target_sequence);
			target.setRegion(target_region);
			target.setCoordinates(target_coords);
			target.setSeed_match(send_match);
			target.setResource(target_resource);
			target.setPubmed_id(target_pubmedId);
			
			BiologicalProcess biologicalprocess = new BiologicalProcess();
			biologicalprocess.setName(target_process);
			
			ExpressionData expressiondata = new ExpressionData();
			expressiondata.setCellularLine(cell_line);
			expressiondata.setCellularLine(method);
			
			Gene gene = new Gene();
			gene.setName(target1);
			gene.setGeneId(uniprot);
			
			
			System.out.println(mirna);
			System.out.println(organism);
			System.out.println(expressiondata);
			System.out.println(target);
			System.out.println(biologicalprocess);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}

}
