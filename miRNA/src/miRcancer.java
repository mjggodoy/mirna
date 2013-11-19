
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

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class miRcancer {

	public static void main(String[] args) throws Exception {

		FileReader fr = new FileReader(
				"C:/Users/usuario/Desktop/NewSearchingLine/miRcancer/miRCancerJune2013.txt");
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(
				"C:/Users/usuario/Desktop/NewSearchingLine/miRcancer/RDF_miRcancer2013fizer.txt");

		// int numLineas = 8;

		// String resourceUri = "http://khaos.uma.es/mirna/resource/";
		// String propertyUri = "http://khaos.uma.es/mirna/property/";

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";

		Model model = ModelFactory.createDefaultModel();

		int count = 0;
		int dataExpressionCount = 1;

		String line;
		
		while ((line = br.readLine()) != null) {

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
							.createResource(
									namespace + "miRNA/" + miRna2.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), miRna2.getName())
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
					throw e;
				}

			} else {

			}

			

			
			model.write(out);
		

		}
		out.close();
		fr.close();
		br.close();

	}

}