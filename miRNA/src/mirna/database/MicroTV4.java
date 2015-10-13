package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MicroTV4 extends NewMirnaDatabase {
	
	private final static String TABLE_NAME = "microtv4";
	
	public MicroTV4() throws MiRnaException {
		super(TABLE_NAME);
		this.fetchSizeMin = true;
	}
	
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
					gene_id = tokens[1].replaceAll("'", "\\\\'");;
					miRNA = tokens[2].replaceAll("'", "\\\\'");;
					miTG_score = tokens[3].replaceAll("'", "\\\\'");;
	
				}else{
					
					region = tokens[0];
					//location = tokens[1];
					
					tokens2 = StringUtils.splitPreserveAllTokens(tokens[1], ":");
					chromosome = tokens2[0].replaceAll("'", "\\\\'");;
					coordinates = tokens2[1].replaceAll("'", "\\\\'");;
					
					
					query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ transcript_id + "','"
							+ gene_id + "','"
							+ miRNA + "','"
							+ miTG_score + "','"
							+ region + "','"
							+ chromosome + "','"
							+ coordinates + "')";
					
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
				System.out.println(query);
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
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String transcript_id = rs.getString("transcript_id");
		String gene_id = rs.getString("gene_id");
		String miRNA = rs.getString("miRNA");
		String miTG_score = rs.getString("miTG_score");
		String region = rs.getString("region");
		String chromosome = rs.getString("chromosome");
		String coordinates = rs.getString("coordinates");

		MiRna miRna = new MiRna();
		miRna.setName(miRNA);
		
		Gene gene = new Gene();
		gene.setName(gene_id);
		
		InteractionData id = new InteractionData();
		id.setMiTG_score(miTG_score);
		id.setProvenance("microTv4");
		
		Target target = new Target();
		target.setRegion(region);
		target.setChromosome(chromosome);
		target.setCoordinates(coordinates);
				
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcript_id);

					
		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", miRna.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(miRna);
			session.flush();  // to get the PK
		} else {
			miRna = (MiRna) oldMiRna;
		}
		
		// Inserta gene (o recupera su id. si ya existe)
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", gene.getName()))
				.uniqueResult();
		if (oldGene == null) {
			session.save(gene);
			session.flush(); // to get the PK
		} else {
			gene = (Gene) oldGene;
		}
		
		transcript.setGeneId(gene.getPk());
		// Inserta Transcript (o recupera su id. si ya existe)
		Object oldTranscript = session.createCriteria(Transcript.class)
				.add(Restrictions.eq("transcriptID", transcript.getTranscriptID()))
				.uniqueResult();
		if (oldTranscript == null) {
			session.save(transcript);
			session.flush(); // to get the PK
		} else {
			transcript = (Transcript) oldTranscript;
		}
		
		// Inserta Target
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK
		
		// Inserta nueva interactionData
		// (y la relaciona con el MiRna y Target correspondiente)
		
		id.setTarget_pk(target.getPk());
		id.setMirna_pk(miRna.getPk());
		//TODO: Solucionar fallo de compilacion ¡Pua!
		id.setGene_pk(gene.getPk());
		
		session.save(id);
		session.flush();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		MicroTV4 microtv4 = new MicroTV4();
		
		// /* 1. meter datos en mirna_raw */
		//String inputFile = "/Users/esteban/Softw/miRNA/microalgo/microtv4_data.csv";	
		//microtv4.insertInTable(inputFile));
						
		/* 2. meter datos en mirna */
		microtv4.insertIntoSQLModel();
		
	}

}
