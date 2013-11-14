
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import beans.DataExpression;
import beans.Disease;
import beans.MiRna;
import beans.Organism;
import beans.SmallMolecule;
import beans.InteractionData;
import beans.Target;
import beans.Transcript;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class MicroCosm {

	public static void main(String[] args) throws Exception {

		FileReader fr = new FileReader(
				"C:/Users/usuario/Desktop/NewSearchingLine/miRcancer/miRcancer2013.txt");
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(
				"C:/Users/usuario/Desktop/NewSearchingLine/miRcancer/RDF_miRcancer2013fizer.txt");

		// int numLineas = 8;

		// String resourceUri = "http://khaos.uma.es/mirna/resource/";
		// String propertyUri = "http://khaos.uma.es/mirna/property/";

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int interactiondataCount = 1;

		while (br.readLine() != null) {

			String line = br.readLine();
			System.out.println("linea =");
			System.out.println(line);
			count++;
			System.out.println(count);
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			if (line != null) {
				try {

					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[2]);

					InteractionData interactionData = new InteractionData();
					interactionData.setFeature(tokens[4]);
					interactionData.setPhase(tokens[9]);
					interactionData.setPvalue_log(tokens[10]);
					interactionData.setMethod(tokens[3]);
					interactionData.setScore(tokens[8]);

					Transcript transcript2 = new Transcript();
					transcript2.setUTR5start(tokens[6]);
					transcript2.setUTR3end(tokens[7]);
					transcript2.setName(tokens[12]);
					transcript2.setExternalName(tokens[13]);
					transcript2.setChromosome(tokens[4]);

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

					Resource Transcript = model
							.createResource(
									namespace + "Transcript/"
											+ transcript2.getName())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "UTR5start"),
									transcript2.getUTR5start())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "UTR3end"), transcript2.getUTR3end())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "ExternalName"),
									transcript2.getExternalName())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "Chromosome"),
									transcript2.getChromosome())

							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Transcript"));

					model.createResource(
							namespace + "InteractionData_"
									+ interactiondataCount)
							// dataExpression.getExpression())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "feature"),
									interactionData.getFeature())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "phase"),
									interactionData.getPhase())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "Pvalue_log"),
									interactionData.getPvalue_log())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "method"),
									interactionData.getMethod())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "score"),
									interactionData.getScore())

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"), miRNA)

							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"),
									Transcript)

							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "InteractionData"));

					interactiondataCount++;

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
