package old;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

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

public class sms2mir {

	public static void main(String[] args) throws Exception {

		FileReader fr = new FileReader(
				"C:/Users/usuario/Desktop/NewSearchingLine/miREnvironment/mirendata.txt");
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(
				"C:/Users/usuario/Desktop/NewSearchingLine/miREnvironment/RDF_mirendatafizer.txt");

		// int numLineas = 8;

		// String resourceUri = "http://khaos.uma.es/mirna/resource/";
		// String propertyUri = "http://khaos.uma.es/mirna/property/";

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int dataExpressionCount = 1;

		while (br.readLine() != null) {

			String line = br.readLine();
			System.out.println("linea =");
			System.out.println(line);
			count++;
			System.out.println(count);
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			// if (count==0) {
			// //System.out.println("NUMERO DE TOKENS: " + st.countTokens());
			// System.out.println("NUMERO DE TOKENS: " + tokens.length);
			// //int j=1;
			// //while (st.hasMoreTokens()) {
			// for (int j=1; j<=tokens.length; j++) {
			// //System.out.println(j + ":" + st.nextToken());
			// System.out.println(j + ":" + tokens[j-1]);
			// //j++;
			// }
			if (line != null) {
				try {

					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[1]);
					miRna2.setAccessionNumber(tokens[2]);

					SmallMolecule smallmolecule = new SmallMolecule();
					smallmolecule.setName(tokens[3]);
					smallmolecule.setFDA(tokens[4]);
					smallmolecule.setDB(tokens[5]);
					smallmolecule.setCID(tokens[6]);

					Organism organism = new Organism();
					organism.setSpecie(tokens[8]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setMethod(tokens[7]);
					dataexpression.setCondition(tokens[9]);
					dataexpression.setPubmedId(tokens[10]);
					dataexpression.setYear(tokens[11]);
					dataexpression.setDescription(tokens[12]);
					dataexpression.setSupport(tokens[13]);
					dataexpression.setProfile(tokens[14]);

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

			} else {

			}

			model.write(out);

			br.close();
			fr.close();
			out.close();

		}

	}

}