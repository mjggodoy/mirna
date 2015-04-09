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
import mirna.beans.Organism;
import mirna.beans.Protein;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de TarBase
 * 
 * @author Esteban López Camacho
 *
 */
public class TarBase extends MirnaDatabase {
	
	private final String tableName = "tarBase";
	
	public TarBase() throws MiRnaException { super(); }
	
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
			
			while ((line = br.readLine()) != null) {
	
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
			rs.next();
			// CAMBIAR ESTO:
			
			String id_tarbase = rs.getString("id");
			String idv4 = rs.getString("id_v4");
			String dataType = rs.getString("data_type");
			String supportType = rs.getString("support_type");
			String specie = rs.getString("organism");
			String miRna = rs.getString("miRNA");
			String hgncSymbol = rs.getString("hgnc_symbol");
			String gene_name = rs.getString("gene");
			String isoform = rs.getString("isoform");
			String ensembl = rs.getString("ensembl");
			String chrLoc = rs.getString("chr_loc");
			String is = rs.getString("i_s");
			String ds = rs.getString("d_s");
			String paper = rs.getString("paper");
			String targetSeq = rs.getString("target_seq");
			String mirnaSeq =rs.getString("mirna_seq");
			String seqLocation = rs.getString("seq_location");
			String pmid = rs.getString("pmid");
			String kegg = rs.getString("kegg");
			String protein_type = rs.getString("protein_type");
			String different_expression = rs.getString("dif_expr_in");
			String pathology_or_event = rs.getString("pathology_or_event");
			String mis_regulation = rs.getString("mis_regulation");
			String gene_expression = rs.getString("gene_expression");
			String type_tumour = rs.getString("tumour_involv");
			String bib = rs.getString("bib");
			String cell_line_used = rs.getString("cell_line_used");
			String hgnc_id = rs.getString("hgnc_id");
			String swiss_prot = rs.getString("swiss_prot");
			
			InteractionData id = new InteractionData();
			id.setReference(paper);
			id.setPubmedId(pmid);
			id.setDescription(bib);
						
			Organism organism = new Organism();
			organism.setSpecie(specie);
			
			MiRna mirna = new MiRna();
			mirna.setName(miRna);
			mirna.setSequence(mirnaSeq);
			
			Gene gene = new Gene();
			gene.setName(gene_name);
			gene.setLocation(seqLocation);
			gene.setLocation(chrLoc);
			gene.setKegg_id(kegg);
			gene.setExpression_site(gene_expression);
			gene.setHgnc_symbol(hgncSymbol);
			gene.setGeneId(hgnc_id);
			gene.setGeneId(ensembl);
			
			Protein protein = new Protein();
			protein.setSwiss_prot_id(swiss_prot);
			protein.setType(protein_type);
			
			Target target = new Target();
			target.setSequence(targetSeq);
		
			Transcript transcript = new Transcript();
			transcript.setIsoform(isoform);
			
			Disease disease = new Disease();
			disease.setName(pathology_or_event);
	
			ExpressionData ed = new ExpressionData();
			ed.setDataType(dataType);
			ed.setMethod(is);
			ed.setEvidence(mis_regulation);
			ed.setCellularLine(cell_line_used);
			ed.setProvenanceId(id_tarbase);
			ed.setProvenance("TarBase");
			
			
			ExpressionData ed2 = new ExpressionData();
			ed2.setMethod(ds);
			ed.setDataType(dataType);
			ed.setEvidence(mis_regulation);
			ed.setProvenanceId(id_tarbase);
			ed.setCellularLine(cell_line_used);
			ed.setProvenance("TarBase");
			ed.setDifferentExpressionLocation(different_expression);
					
			System.out.println(mirna);
			System.out.println(id);
			System.out.println(target);	
			System.out.println(gene);
			System.out.println(disease);
			System.out.println(transcript);
			System.out.println(ed);
			System.out.println(ed2);
			System.out.println(organism);
			System.out.println(protein);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		TarBase tarBase = new TarBase();

		//String inputFile = "/Users/esteban/Softw/miRNA/TarBase_V5.0.txt";
		//tarBase.insertInTable(inputFile);
		
		tarBase.insertIntoSQLModel();
		
	}

}