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
import mirna.beans.MiRna;
import mirna.dao.DiseaseDAO;
import mirna.dao.ExpressionDataDAO;
import mirna.dao.MiRnaDAO;
import mirna.dao.mysql.DiseaseDAOMySQLImpl;
import mirna.dao.mysql.ExpressionDataDAOMySQLImpl;
import mirna.dao.mysql.MiRnaDAOMySQLImpl;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de miRNA cancer
 * http://mircancer.ecu.edu
 * 
 * @author María Jesús García Godoy
 *
 */
public class MiRCancer implements IMirnaDatabase {
	
	private final String tableName = "miRCancer";
	
	public MiRCancer() { }
	
	public void insertInTable(String csvInputFile) throws Exception {
		this.insertInTable(csvInputFile, null);
	}
	
	public void insertInTable(String csvInputFile, Integer maxLines) throws Exception {
		
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
					
					String mirId = tokens[0];
					String cancer = tokens[1];
					String profile = tokens[2];
					String pubMedArticle = tokens[3].replaceAll("'", "\\\\'");
					
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ mirId + "','"
							+ cancer + "','"
							+ profile + "','"
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
	
	public void insertIntoSQLModel() throws Exception {
		this.insertIntoSQLModel(null);
	}
	
	public void insertIntoSQLModel(Integer maxLines) throws Exception {
		
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
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			MiRnaDAO miRnaDAO = new MiRnaDAOMySQLImpl();
			DiseaseDAO diseaseDAO = new DiseaseDAOMySQLImpl();
			ExpressionDataDAO dataExpressionDAO = new ExpressionDataDAOMySQLImpl();
			
			int count = 0;
			
			// iterate through the java resultset
			while ((rs.next()) && ((maxLines==null) || (count<maxLines))) {
				count++;
				int id = rs.getInt("pk");
				String cancer = rs.getString("cancer");
				String mirId = rs.getString("mirId");
				String evidence = rs.getString("profile");
				String pubmedArticle = rs.getString("pubmed_article").replaceAll("'", "\\\\'");;
				
				MiRna miRna = new MiRna();
				miRna.setName(mirId);
				
				Disease disease = new Disease();
				disease.setName(cancer);
				
				ExpressionData dataExpression = new ExpressionData();
				dataExpression.setDescription(pubmedArticle);
				dataExpression.setEvidence(evidence);
				dataExpression.setProvenance("miRCancer");
				
				// print the results
				System.out.format("%d, %s, %s, %s, %s\n", id, cancer, mirId, evidence, pubmedArticle.substring(0,20));
				
				// Inserta MiRna (o recupera su id. si ya existe)
				MiRna oldMiRna = miRnaDAO.findByName(miRna.getName());
				if (oldMiRna==null) {
					int key = miRnaDAO.create(miRna);
					miRna.setPk(key);
				} else {
					if (miRna.checkConflict(oldMiRna)) {
						miRna.setPk(oldMiRna.getPk());
						miRna.setName(oldMiRna.getName());
					} else {
						
						String msg = "Conflicto detectado!"
								+ "\nEntrada en BD: " + oldMiRna
								+ "\nEntrada nueva: " + miRna;
						throw new MiRnaException(msg);
					}
				}
				
				// Inserta Disease (o recupera su id. si ya existe)
				Disease oldDisease = diseaseDAO.findByName(disease.getName());
				if (oldDisease==null) {
					int key = diseaseDAO.create(disease);
					disease.setPk(key);
				} else {
					if (disease.checkConflict(oldDisease)) {
						disease.setPk(oldDisease.getPk());
						disease.setName(oldDisease.getName());
					} else {
						
						String msg = "Conflicto detectado!"
								+ "\nEntrada en BD: " + oldDisease
								+ "\nEntrada nueva: " + disease;
						throw new MiRnaException(msg);
					}
					
				}
				
				// Inserta nueva DataExpression
				// (y la relaciona con el MiRna y Disease correspondiente)
				int dataExpressionId = dataExpressionDAO.create(dataExpression);
				dataExpressionDAO.newMiRnaInvolved(dataExpressionId, miRna.getPk());
				dataExpressionDAO.newRelatedDisease(dataExpressionId, disease.getPk());
				// DataExpression igual (?)
				
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
	
	/*
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

					ExpressionData dataexpression = new ExpressionData();
					dataexpression.setDescription(tokens[3]);
					dataexpression.setEvidence(tokens[2]);

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
									dataexpression.getEvidence())
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
	
	*/
	
	public static void main(String[] args) throws Exception {
		
		MiRCancer miRCancer = new MiRCancer();
		
		// String inputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.txt";
		// miRCancer.insertInTable(inputFile);
		
		miRCancer.insertIntoSQLModel();
	}

}