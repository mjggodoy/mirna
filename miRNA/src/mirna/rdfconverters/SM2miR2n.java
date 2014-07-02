package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

import beans.DataExpression;
import beans.MiRna;
import beans.Organism;
import beans.SmallMolecule;

public class SM2miR2n {

	private String csvInputFile;

	public SM2miR2n(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}

	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}

	public void insertInTable(String tableName, Integer maxLines)
			throws Exception {

		// Connection to the mirna database
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

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				MiRna miRna = new MiRna();
				miRna.setName(tokens[0]);
				miRna.setAccessionNumber(tokens[1]);

				SmallMolecule smallmolecule = new SmallMolecule();
				smallmolecule.setName(tokens[2]);
				smallmolecule.setFDA(tokens[3]);
				smallmolecule.setDB(tokens[4]);
				smallmolecule.setCID(tokens[5]);

				Organism organism = new Organism();
				organism.setSpecie(tokens[6]);

				DataExpression dataexpression = new DataExpression();
				dataexpression.setMethod(tokens[7]);
				dataexpression.setCondition(tokens[8]);
				dataexpression.setPubmedId(tokens[9]);
				dataexpression.setYear(tokens[10]);
				dataexpression.setDescription(tokens[11]);
				dataexpression.setSupport(tokens[12]);
				dataexpression.setProfile(tokens[13]);

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ tokens[0] + "','" + tokens[1] + "','" + tokens[2]
						+ "'," + tokens[3] + "','" + tokens[4] + "','"
						+ tokens[5] + "','" + tokens[6] + "','" + tokens[7]
						+ "','" + tokens[8] + "','" + tokens[9] + "','"
						+ tokens[10] + "','" + tokens[11] + "','" + tokens[12]
						+ "','" + tokens[13] + "',')";

				stmt.executeUpdate(query);

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
			throws Exception {

		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(rdfOutputFile);

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int dataExpressionCount = 1;

		while (br.readLine() != null) {

			String line = br.readLine();
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			if (line != null) {
				try {

					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[0]);
					miRna2.setAccessionNumber(tokens[1]);

					SmallMolecule smallmolecule = new SmallMolecule();
					smallmolecule.setName(tokens[2]);
					smallmolecule.setFDA(tokens[3]);
					smallmolecule.setDB(tokens[4]);
					smallmolecule.setCID(tokens[5]);

					Organism organism = new Organism();
					organism.setSpecie(tokens[6]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setMethod(tokens[7]);
					dataexpression.setCondition(tokens[8]);
					dataexpression.setPubmedId(tokens[9]);
					dataexpression.setYear(tokens[10]);
					dataexpression.setDescription(tokens[11]);
					dataexpression.setSupport(tokens[12]);
					dataexpression.setProfile(tokens[13]);

					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna2.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), miRna2.getName())

							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					Resource smallmolecule2 = model
							.createResource(
									namespace + "Smallmolecule"
											+ smallmolecule.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "FDA"), smallmolecule.getFDA())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "DB"), smallmolecule.getDB())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "CID"), smallmolecule.getCID())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "SmallMolecule"));

					model.createResource(
							namespace + "DataExpression_" + dataExpressionCount)
							// dataExpression.getExpression())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "method"),
									dataexpression.getMethod())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "condition"),
									dataexpression.getCondition())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "pubmedId"),
									dataexpression.getPubmedId())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "year"), dataexpression.getYear())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "description"),
									dataexpression.getDescription())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "support"),
									dataexpression.getSupport())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "profile"),
									dataexpression.getProfile())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesmiRNA"), miRNA)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesAmbientalFactor"),
									smallmolecule2)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "DataExpression"));

					dataExpressionCount++;

					// create a bag

					// select all the resources with a VCARD.FN property
					// whose value ends with "Smith"

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j = 0; j < tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					throw e;
				}

			}
			model.write(out);

		}
		
		out.close();
		fr.close();
		br.close();
	}

	public static void main(String[] args) throws Exception {

		String inputFile = "/Users/esteban/Softw/miRNA/SM2miR2n.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.rdf";
		int maxLines = 5000;

		SM2miR2n sm2mir2 = new SM2miR2n(inputFile);
		sm2mir2.insertInTable(inputFile);
		sm2mir2.buildRdf(outputFile, maxLines);

	}

}