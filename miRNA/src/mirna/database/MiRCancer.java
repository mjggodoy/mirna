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
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
			
			while ((line = br.readLine()) != null) {
	
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
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";

		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		
		//start transaction
        Transaction tx = session.beginTransaction();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			while (rs.next()) {
				
				String cancer = rs.getString("cancer").trim();
				String mirId = rs.getString("mirId").trim();
				String evidence = rs.getString("profile").trim();
				String pubmedArticle = rs.getString("pubmed_article").trim();
				
				MiRna miRna = new MiRna();
				miRna.setName(mirId);
				
				Disease disease = new Disease();
				disease.setName(cancer);
				
				ExpressionData expressionData = new ExpressionData();
				expressionData.setDescription(pubmedArticle);
				expressionData.setEvidence(evidence);
				expressionData.setProvenance("miRCancer");
				
				// Inserta MiRna (o recupera su id. si ya existe)
				Object oldMiRna = session.createCriteria(MiRna.class)
						.add( Restrictions.eq("name", miRna.getName()) )
						.uniqueResult();
				if (oldMiRna==null) {
					session.save(miRna);
					session.flush();  // to get the PK
				} else {
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
				expressionData.setMirnaPk(miRna.getPk());
				expressionData.setDiseasePk(disease.getPk());
				session.save(expressionData);
				// ExpressionData igual (?)
				
				count++;
				if (count%100==0) {
					System.out.println(count);
					session.flush();
			        session.clear();
				}
				
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
		
		tx.commit();
		sessionFactory.close();
		
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