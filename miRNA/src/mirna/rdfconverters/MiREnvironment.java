package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import mirna.beans.DataExpression;
import mirna.beans.Disease;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.SmallMolecule;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class MiREnvironment {

	private String csvInputFile;

	public MiREnvironment(String csvInputFile) {
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

					Disease disease = new Disease();
					disease.setName(tokens[4]);

					MiRna miRna = new MiRna();
					miRna.setName(tokens[1]);
					miRna.setSubName(tokens[2]);
					miRna.setSubName(tokens[3]);

					SmallMolecule smallmolecule = new SmallMolecule();
					smallmolecule.setName(tokens[5]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setmirenvironmentID(tokens[0]);
					dataexpression.setTreatment(tokens[6]);
					dataexpression.setPubmedId(tokens[10]);
					dataexpression.setDescription(tokens[9]);
					dataexpression.setCellularLine(tokens[7]);

					String query = "INSERT INTO " + tableName
							+ " VALUES (NULL, '" + tokens[0] + "','"
							+ tokens[1] + "','" + tokens[2] + "','" + tokens[3]
							+ "','" + tokens[4] + "','" + tokens[5] + "','"
							+ tokens[6] + "','" + tokens[7] + "','" + tokens[8]
							+ "','" + tokens[9] + "','" + tokens[10] + "',')";

					stmt.executeUpdate(query);

				}
			}

			fr.close();
			br.close();
			stmt.close();

		} catch (Exception e) {
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

			if (line != null) {
				try {

					Disease disease = new Disease();
					disease.setName(tokens[4]);

					MiRna miRna = new MiRna();
					miRna.setName(tokens[1]);
					miRna.setSubName(tokens[2]);
					miRna.setSubName(tokens[3]);

					SmallMolecule smallmolecule = new SmallMolecule();
					smallmolecule.setName(tokens[5]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setmirenvironmentID(tokens[0]);
					dataexpression.setTreatment(tokens[6]);
					dataexpression.setPubmedId(tokens[10]);
					dataexpression.setDescription(tokens[9]);
					dataexpression.setCellularLine(tokens[7]);

					Organism organism = new Organism();
					organism.setSpecie(tokens[8]);

					Resource organism2 = model.createResource(
							namespace + "Organism/" + organism.getSpecie())

					.addProperty(
							RDF.type,
							ResourceFactory.createResource(namespace
									+ "Organism"));

					Resource diseaseResource = model
							.createResource(
									namespace + "Disease_" + disease.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), disease.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "hasOrganism"), organism2)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Disease"));

					Resource smallmolecule2 = model
							.createResource(

									namespace + "Smallmolecule_"
											+ smallmolecule.getName())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), smallmolecule.getName())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "SmallMolecule"));

					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), miRna.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "subname"), miRna.getSubName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "hasOrganism"), organism2)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					model.createResource(
							namespace + "DataExpression_" + tokens[0])
							// dataExpression.getExpression())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "id"),
									dataexpression.getmirenvironmentID())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "treatment"),
									dataexpression.getTreatment())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "pubmedId"),
									dataexpression.getPubmedId())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "description"),
									dataexpression.getDescription())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "cellular_line"),
									dataexpression.getCellularLine())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesmiRNA"), miRNA)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "relatedDisease"),
									diseaseResource)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesAmbientalFactor"),
									smallmolecule2)
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "hasOrganism"), organism2)
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "DataExpression"));

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

		String inputFile = "/Users/esteban/Softw/miRNA/mirendata.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/mirendata.rdf";
		Integer maxLines = 12000;

		MiREnvironment mirenvironment = new MiREnvironment(inputFile);
		mirenvironment.buildRdf(outputFile, maxLines);
		mirenvironment.insertInTable("miren");

	}
}