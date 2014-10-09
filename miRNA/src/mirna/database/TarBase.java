package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de TarBase
 * 
 * @author Esteban López Camacho
 *
 */
public class TarBase implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public TarBase(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public void insertInTable(String tableName, Integer maxLines) throws Exception {
		
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
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null) && ((maxLines==null) || (count<maxLines))) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					//String mir = tokens[0];
					//String disease = tokens[1].replaceAll("'", "\\\\'");
					String id = tokens[0];
					String idV4 = tokens[1];
					String dataType = tokens[2];
					String supportType = tokens[3];
					String organism = tokens[4];
					String miRna = tokens[5];
					String hgncSymbol = tokens[6];
					String gene = tokens[7];
					String isoform = tokens[8];
					String ensembl = tokens[9];
					String chrLoc = tokens[10];
					String mre = tokens[11];
					String sss = tokens[12];
					String is = tokens[13].replaceAll("'", "\\\\'");
					String ds = tokens[14].replaceAll("'", "\\\\'");
					String validation = tokens[15];
					String paper = tokens[16].replaceAll("'", "\\\\'");
					String targetSeq = tokens[17];
					String mirnaSeq = tokens[18];
					String seqLocation = tokens[19];
					String pmid = tokens[20];
					String kegg = tokens[21];
					String proteinType = tokens[22];
					String difExprIn = tokens[23];
					String pathologyOrEvent = tokens[24];
					String misRegulation = tokens[25];
					String geneExpression = tokens[26];
					String tumourInvolv = tokens[27].replaceAll("'", "\\\\'");
					String bib = tokens[28].replaceAll("'", "\\\\'");
					String cellLineUsed = tokens[29];
					String hgncId = tokens[30];
					String swissProt = tokens[31];
					String aux = tokens[32];
					

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ id + "','"
							+ idV4 + "','"
							+ dataType + "','"
							+ supportType + "','"
							+ organism + "','"
							+ miRna + "','"
							+ hgncSymbol + "','"
							+ gene + "','"
							+ isoform + "','"
							+ ensembl + "','"
							+ chrLoc + "','"
							+ mre + "','"
							+ sss + "','"
							+ is + "','"
							+ ds + "','"
							+ validation + "','"
							+ paper + "','"
							+ targetSeq + "','"
							+ mirnaSeq + "','"
							+ seqLocation + "','"
							+ pmid + "','"
							+ kegg + "','"
							+ proteinType + "','"
							+ difExprIn + "','"
							+ pathologyOrEvent + "','"
							+ misRegulation + "','"
							+ geneExpression + "','"
							+ tumourInvolv + "','"
							+ bib + "','"
							+ cellLineUsed + "','"
							+ hgncId + "','"
							+ swissProt + "','"
							+ aux + "')";
					
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
	public void insertIntoSQLModel(String originTable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertIntoSQLModel(String originTable, Integer maxLines)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/TarBase_V5.0.txt";
		TarBase tarBase = new TarBase(inputFile);
		tarBase.insertInTable("tarBase");
		
	}

}