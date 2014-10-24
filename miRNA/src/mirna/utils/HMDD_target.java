package mirna.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.MiRna;
import mirna.beans.Target;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class HMDD_target {

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
		int dataExpressionCount = 1;
		int dataTarget = 1;

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
					
					Target target2 = new Target();
					target2.setName(tokens[3]);
					
					
					Disease disease = new Disease();
					disease.setName(tokens[4]);

					ExpressionData dataexpression = new ExpressionData();
					dataexpression.setDescription(tokens[6]);
					dataexpression.setPubmedId(tokens[5]);

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
							namespace + "DataExpression_" + dataExpressionCount)
							
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
					
					model.createResource(
							namespace + "Target_" + dataTarget)
							
								.addProperty(
									ResourceFactory.createProperty(namespace
											+ "Target"), target2.getName())
								.addProperty(
									ResourceFactory.createProperty(namespace
											+ "microRNAtarget"), miRNA)
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

			br.close();
			fr.close();
			out.close();

		}

	}

}