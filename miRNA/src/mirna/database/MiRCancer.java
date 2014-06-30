package mirna.database;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.dao.DataExpressionDAO;
import mirna.dao.DiseaseDAO;
import mirna.dao.MiRnaDAO;
import mirna.dao.mysql.DataExpressionDAOMySQLImpl;
import mirna.dao.mysql.DiseaseDAOMySQLImpl;
import mirna.dao.mysql.MiRnaDAOMySQLImpl;

import org.apache.commons.lang.StringUtils;

import beans.DataExpression;
import beans.Disease;
import beans.MiRna;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Código para procesar los datos de miRNA cancer
 * http://mircancer.ecu.edu
 * 
 * @author María Jesús García Godoy
 *
 */
public class MiRCancer implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public MiRCancer(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public void insertInTable(String tableName, Integer maxLines) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna";
		
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
					
					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[0]);

					Disease disease = new Disease();
					disease.setName(tokens[1]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setDescription(tokens[3]);
					dataexpression.setProfile(tokens[2]);
					
					String pubMedArticle = tokens[3].replaceAll("'", "\\\\'");
					
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ tokens[0] + "','"
							+ tokens[1] + "','"
							+ tokens[2] + "','"
							+ pubMedArticle + "')";
					
					stmt.executeUpdate(query);
	
				}
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (SQLException e) {
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
	
	public void insertIntoSQLModel(String originTable) throws Exception {
		this.insertIntoSQLModel(originTable, null);
	}
	
	public void insertIntoSQLModel(String originTable, Integer maxLines) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna";

		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + originTable;
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			MiRnaDAO miRnaDAO = new MiRnaDAOMySQLImpl();
			DiseaseDAO diseaseDAO = new DiseaseDAOMySQLImpl();
			DataExpressionDAO dataExpressionDAO = new DataExpressionDAOMySQLImpl();
			
			int count = 0;
			
			// iterate through the java resultset
			while ((rs.next()) && ((maxLines==null) || (count<maxLines))) {
				count++
				int id = rs.getInt("id");
				String cancer = rs.getString("cancer");
				String mirId = rs.getString("mirId");
				String profile = rs.getString("profile");
				String pubmedArticle = rs.getString("pubmed_article").replaceAll("'", "\\\\'");;
				
				MiRna miRna = new MiRna();
				miRna.setName(mirId);
				
				Disease disease = new Disease();
				disease.setName(cancer);
				
				DataExpression dataExpression = new DataExpression();
				dataExpression.setDescription(pubmedArticle);
				dataExpression.setProfile(profile);
				
				// print the results
				System.out.format("%d, %s, %s, %s, %s\n", id, cancer, mirId, profile, pubmedArticle.substring(0,20));
				
				if (miRnaDAO.findByName(miRna.getName()).size()==0) miRnaDAO.create(miRna);
				if (diseaseDAO.findByName(disease.getName()).size()==0) diseaseDAO.create(disease);
				dataExpressionDAO.create(dataExpression);

				
			}
			stmt.close();
		} catch (SQLException e) {
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
	
	public void buildRdf(String rdfOutputFile) throws Exception {
		this.buildRdf(rdfOutputFile, null);
	}

	public void buildRdf(String rdfOutputFile, Integer maxLines) throws Exception {

		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(rdfOutputFile);

		// int numLineas = 8;

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int dataExpressionCount = 1;

		String line;
		br.readLine();
		
		while (((line = br.readLine()) != null) && ((maxLines==null) || (count<maxLines))) {

			count++;
			System.out.println(count);
			
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			if (line != null) {
				try {

					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[0]);

					Disease disease = new Disease();
					disease.setName(tokens[1]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setDescription(tokens[3]);
					dataexpression.setProfile(tokens[2]);

					Resource miRNA = model
							.createResource(namespace + URLEncoder.encode(
									"miRNA/" + miRna2.getName(), "UTF-8"))
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), miRna2.getName())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					Resource disease2 = model.createResource(
							namespace + URLEncoder.encode("Disease/" + disease.getName(), "UTF-8"))
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Disease"));

					model.createResource(
							namespace + "DataExpression_" + dataExpressionCount)
							// dataExpression.getExpression())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "description"),
									dataexpression.getDescription())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "profile"),
									dataexpression.getProfile())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesmiRNA"), miRNA)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "relatedDisease"), disease2)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "DataExpression"));

					dataExpressionCount++;

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j = 0; j < tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					e.printStackTrace();
				}

			}

			//model.write(out, "N-TRIPLES");
			model.write(out);

		}
		out.close();
		fr.close();
		br.close();

	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.rdf";
		Integer maxLines = 5;
		
		MiRCancer miRCancer = new MiRCancer(inputFile);
		//miRCancer.buildRdf(outputFile, maxLines);
		//miRCancer.insertInTable("MiRnaCancer");
		miRCancer.insertIntoSQLModel("MiRnaCancer", maxLines);
	}

}