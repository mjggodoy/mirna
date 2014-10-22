package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.ExpressionData;
import mirna.beans.Disease;
import mirna.beans.MiRna;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Código para transformar a RDF los datos de Phenomir 2.0
 * http://mips.helmholtz-muenchen.de/phenomir/
 * 
 * @author María Jesús García Godoy
 * 
 */
public class PhenomiR {

	private String csvInputFile;

	public PhenomiR(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}

	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}

	public void insertInTable(String tableName, Integer maxLines)
			throws Exception {
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

			while (((line = br.readLine()) != null)
					&& ((maxLines == null) || (count < maxLines))) {

				count++;

				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {

					ExpressionData dataexpression = new ExpressionData();
					dataexpression.setPhenomicId(tokens[0]);

					ExpressionData dataexpression2 = new ExpressionData();
					dataexpression2.setPubmedId(tokens[1]);

					Disease disease1 = new Disease();
					disease1.setName(tokens[2]);

					Disease disease2 = new Disease();
					disease2.setDiseaseSub(tokens[3]);

					Disease disease3 = new Disease();
					disease3.setDiseaseClass(tokens[4]);

					MiRna miRna = new MiRna();
					miRna.setName(tokens[5]);

					MiRna miRna2 = new MiRna();
					miRna2.setAccessionNumber(tokens[6]);

					ExpressionData dataexpression3 = new ExpressionData();
					dataexpression3.setExpression(tokens[7]);

					ExpressionData dataexpression4 = new ExpressionData();
					dataexpression4.setFoldchangeMin(tokens[8]);

					ExpressionData dataexpression5 = new ExpressionData();
					dataexpression5.setFoldchangeMax(tokens[9]);

					ExpressionData dataexpression6 = new ExpressionData();
					dataexpression6.setId(tokens[10]);

					ExpressionData dataexpression7 = new ExpressionData();
					dataexpression7.setStudyDesign(tokens[11]);

					ExpressionData dataexpression8 = new ExpressionData();
					dataexpression8.setMethod(tokens[12]);

					String query = "INSERT INTO " + tableName
							+ " VALUES (NULL, '" + tokens[0] + "','"
							+ tokens[1] + "','" + tokens[2] + "','" + tokens[3]
							+ "','" + tokens[4] + "','" + tokens[5] + "','"
							+ tokens[6] + "','" + tokens[7] + "','" + tokens[8]
							+ "','" + tokens[10] + "','" + tokens[11] + "','"
							+ tokens[12] + "',')";

					stmt.executeUpdate(query);

				}

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (line != null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con != null)
				con.close();
		}

	}

	public void buildRdf(String rdfOutputFile, Integer maxLines)
			throws IOException {

		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(rdfOutputFile);

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int dataExpressionCount = 2200;

		br.readLine();
		while ((br.readLine() != null)
				&& ((maxLines == null) || (count < maxLines))) {

			String line = br.readLine();
			count++;
			System.out.println(count);

			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");
			
			if (line != null) {

				try {

					Disease disease = new Disease();
					disease.setName(tokens[2]);
					disease.setDiseaseSub(tokens[3]);
					disease.setDiseaseClass(tokens[4]);

					MiRna miRna = new MiRna();
					miRna.setName(tokens[5]);
					miRna.setAccessionNumber(tokens[6]);

					ExpressionData dataExpression = new ExpressionData();

					dataExpression.setPhenomidId(tokens[0]);
					dataExpression.setPubmedId(tokens[1]);

					dataExpression.setProfile(tokens[7]);
					dataExpression.setFoldchangeMax(tokens[8]);
					dataExpression.setFoldchangeMin(tokens[9]);
					dataExpression.setId(tokens[10]);
					dataExpression.setMethod(tokens[11]);
					dataExpression.setStudyDesign(tokens[12]);

					Resource diseaseResource = model
							.createResource(
									namespace + "Disease_"
											+ disease.getPhenomicId())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "phenomicid"),
									disease.getPhenomicId())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), disease.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "diseaseClass"),
									disease.getDiseaseClass())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "diseasesub_id"),
									disease.getDiseaseSub())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Disease"));
					;

					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "accessionNumber"),
									miRna.getAccessionNumber())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					model.createResource(
							namespace + "DataExpression_" + dataExpressionCount)
							// dataExpression.getExpression())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "foldchangeMax"),
									dataExpression.getFoldchangeMin())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "foldchangeMin"),
									dataExpression.getFoldchangeMax())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "id"), dataExpression.getId())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "profile"),
									dataExpression.getProfile())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "studyDesign"),
									dataExpression.getStudyDesign())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "method"),
									dataExpression.getMethod())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesmiRNA"), miRNA)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "relatedDisease"),
									diseaseResource)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "DataExpression"));

					dataExpressionCount++;

					// //create a bag
					//
					//
					// // select all the resources with a VCARD.FN property
					// // whose value ends with "Smith"
					//

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j = 0; j < tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					// //throw e;
				}

			}

			model.write(out);

		}

		br.close();
		fr.close();
		out.close();

	}

	public static void main(String[] args) throws Exception {

		String inputFile = "/Users/esteban/Softw/miRNA/phenomir2.0.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/phenomir.rdf";
		Integer maxLines = 12000;

		PhenomiR phenomir = new PhenomiR(inputFile);
		phenomir.insertInTable("phenomir");
		phenomir.buildRdf(outputFile, maxLines);

	}

}
