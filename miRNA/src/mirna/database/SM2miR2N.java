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

/**
 * Código para procesar los datos de SM2miR2N
 * 
 * @author Esteban López Camacho
 *
 */
public class SM2miR2N extends MirnaDatabase {
	
	private final String tableName = "sm2mir2n";
	
	public SM2miR2N() throws MiRnaException {
		super();
	}
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		String query = "";
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
			
			br.readLine();
	
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count + " : " + line);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				String mirna = tokens[0];
				String mirbase = tokens[1];
				String smallMolecule = tokens[2].replaceAll("'", "\\\\'");
				String fda = tokens[3];
				String db = tokens[4];
				String cid = tokens[5];
				String method = tokens[6];
				String species = tokens[7];
				String condition = tokens[8].replaceAll("'", "\\\\'");
				String pmid = tokens[9];
				String year = tokens[10];
				String reference = tokens[11].replaceAll("'", "\\\\'");
				String support = tokens[12].replaceAll("'", "\\\\'");
				String expression = tokens[13];

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ mirbase + "','"
						+ smallMolecule + "','"
						+ fda + "','"
						+ db + "','"
						+ cid + "','"
						+ method + "','"
						+ species + "','"
						+ condition + "','"
						+ pmid + "','"
						+ year + "','"
						+ reference + "','"
						+ support + "','"
						+ expression + "')";
				
				stmt.executeUpdate(query);
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			System.out.println("QUERY =");
			System.out.println(query);
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

	public static void main(String[] args) throws Exception {
		
		SM2miR2N sm2 = new SM2miR2N();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/SM2miR2n.txt";
		//sm2.insertInTable(inputFile);
		
		sm2.insertIntoSQLModel();
		
	}

}