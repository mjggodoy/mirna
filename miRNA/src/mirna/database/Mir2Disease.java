package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Mir2Disease
 * 
 * @author Esteban López Camacho
 *
 */
public class Mir2Disease extends NewMirnaDatabase {
	
	private final static String TABLE_NAME = "mir2disease";
	
	public Mir2Disease() throws MiRnaException { super(TABLE_NAME); }
	
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
	
			while ((line = br.readLine()) != null) {
	
				count++;
				System.out.println(count + " : " + line);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				String mirna = tokens[0];
				String disease = tokens[1].replaceAll("'", "\\\\'");
				String expression = tokens[2];
				String method = tokens[3];
				String date = tokens[4];
				String reference = tokens[5].replaceAll("'", "\\\\'");

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ disease + "','"
						+ expression + "','"
						+ method + "','"
						+ date + "','"
						+ reference + "')";
				
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
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String mirna = nullifyField(rs.getString("mirna").trim());
		String diseaseField = nullifyField(rs.getString("disease").trim());
		String evidence = nullifyField(rs.getString("expression").trim());
		String method = nullifyField(rs.getString("method").trim());
		String year = nullifyField(rs.getString("date").trim());
		String description = nullifyField(rs.getString("reference").trim());
		
		MiRna miRna = new MiRna();
		miRna.setName(mirna);
		
		Disease disease = new Disease();
		disease.setName(diseaseField);
		
		ExpressionData ed = new ExpressionData();
		ed.setDescription(description);
		ed.setEvidence(evidence);
		ed.setMethod(method);
		ed.setYear(year);
		ed.setProvenance("miR2Disease");
		
		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", miRna.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(miRna);
			session.flush();  // to get the PK
		} else {
			//System.out.println("Encuentro: " + );
			MiRna miRnaToUpdate = (MiRna) oldMiRna;
			miRnaToUpdate.update(miRna);
			session.update(miRnaToUpdate);
			miRna = miRnaToUpdate;
		}
		
		// Inserta Disease (o recupera su id. si ya existe)
		Object oldDisease = session.createCriteria(Disease.class)
				.add( Restrictions.eq("name", disease.getName()) )
				.uniqueResult();
		if (oldDisease==null) {
			session.save(disease);
			session.flush(); // to get the PK
		} else {
			Disease diseaseToUpdate = (Disease) oldDisease;
			diseaseToUpdate.update(disease);
			session.update(diseaseToUpdate);
			disease = diseaseToUpdate;
		}
		
		// Inserta nueva DataExpression
		// (y la relaciona con el MiRna y Disease correspondiente)
		ed.setMirnaPk(miRna.getPk());
		ed.setDiseasePk(disease.getPk());
		session.save(ed);
	}
	
	public String specificFileFix(String csvInputFile) throws IOException {
		
		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;
		
		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {
			
			if (!line1.startsWith("hsa")) {
				line0 = line0 + " " + line1;
				line1 = br.readLine();
			}
			
			pw.println(line0);
			line0 = line1;
			
		}
		
		if (line0!=null) pw.println(line0);
		
		br.close();
		pw.close();
		
		return newFile;
	}
	
	private String nullifyField(String field) {
		return "".equals(field.trim()) || "n_a".equals(field.trim()) || "NULL".equals(field.trim()) ? null : field.trim();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Mir2Disease mir2disease = new Mir2Disease();
		mir2disease.insertIntoSQLModel();

	}

}