package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

import beans.DataExpression;
import beans.Disease;
import beans.MiRna;

public class HMDD {
	
	
	private String csvInputFile;

	public HMDD(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}

	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}

	
	
	public void insertInTable(String tableName, Integer maxLines)
			throws Exception {
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

			while (((line = br.readLine()) != null)
					&& ((maxLines == null) || (count < maxLines))) {
		
				count++;

				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {
					
					MiRna miRna = new MiRna();
					miRna.setName(tokens[1]);

					
					Disease disease = new Disease();
					disease.setName(tokens[2]);
					
					DataExpression dataexpression = new DataExpression();
					dataexpression.setDescription(tokens[4]);
					dataexpression.setPubmedId(tokens[3]);
					dataexpression.setiddataexpression(tokens[0]);
					
					
					String query = "INSERT INTO " + tableName
							+ " VALUES (NULL, '" + tokens[0] + "','"
							+ tokens[1] + "','" + tokens[2] + "','" + tokens[3]
							+ "','" + tokens[4] + "','" + tokens[5] + "',')";

					stmt.executeUpdate(query);		
							
					
				
				}
		
			}
			
			fr.close();
			br.close();
			stmt.close();
			
		
		
			}catch (Exception e) {

			e.printStackTrace();
			System.out.println(line);
			for (int j = 0; j < tokens.length; j++) {
				System.out.println(j + ": " + tokens[j]);
			}
			e.printStackTrace();

		}
		
		

	
	}
	
	
	public void buildRdf(String rdfOutputFile, Integer maxLines)
			throws Exception {
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(rdfOutputFile);

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";
		
		
		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		String line;

		while ((line = br.readLine()) != null) {
			
			
			System.out.println("linea =");
			System.out.println(line);
			count++;
			System.out.println(count);
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			if (line!=null){
				
				try{
					
					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[1]);
					

					Disease disease = new Disease();
					disease.setName(tokens[2]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setDescription(tokens[5]);
					dataexpression.setPubmedId(tokens[4]);
					dataexpression.setiddataexpression(tokens[0]);

					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna2.getName())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					Resource disease2 = model.createResource(
							namespace + "Disease/" + disease.getName())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Disease"));

					model.createResource(
							namespace + "DataExpression_" + tokens[0])
							
								.addProperty(
									ResourceFactory.createProperty(namespace
											+ "Description"), dataexpression.getDescription())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "PubmedId"), dataexpression.getPubmedId())
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
				
				
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j = 0; j < tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					throw e;
				}
				
				model.write(out);

				
				
				}
			

			out.close();
			fr.close();
			br.close();
			
			
		}
	
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/AllEntries.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/AllEntries.rdf";
		String tableName = "HMDD";
		int maxLines = 11000;
		
		HMDD hmdd = new HMDD(inputFile);
		hmdd.insertInTable(tableName, maxLines);
		hmdd.buildRdf(outputFile, maxLines);
		
		
	}

}
