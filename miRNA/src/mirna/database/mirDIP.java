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
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class mirDIP extends MirnaDatabase {
	
	private final String tableName = "mirDIP";
	
	public mirDIP() throws MiRnaException {
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
	
				if (line != null) {
					
					String accesionnumber = tokens[0];
					String gene = tokens[1];
					String source = tokens[2];
					String rank = tokens[3];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ accesionnumber + "','"
							+ gene + "','"
							+ source + "','"
							+ rank + "')";
					
					stmt.executeUpdate(query);
	
				}
	
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
	public void insertIntoSQLModel()
			throws Exception {

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
			
			/*System.out.println(miRna);
			System.out.println(id);
			System.out.println(target);*/
			
			// FIN DE CAMBIAR ESTO
			
			// Inserta nueva InteractionData 
			// (y la relaciona con el MiRnt y gene correspondientes)
			
			id.setMirnaPk(miRna.getPk());
			id.setTargetPk(target.getPk());

			
			// Relaciona transcript con target.
			
			transcript.setTargetPk(target.getPk());
						
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		mirDIP mirdip = new mirDIP();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/mirDIP/mirDIP-All-Data-Version1.0.txt";
		//mirdip.insertInTable(inpuFile);
		
		mirdip.insertIntoSQLModel();
		
	}

}