package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import beans.DataExpression;
import beans.Disease;
import beans.MiRna;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Código para transformar a RDF los datos de miRNA cancer
 * http://mircancer.ecu.edu
 * 
 * @author María Jesús García Godoy
 *
 */
public class MiRCancer {

	public static void main(String[] args) throws Exception {
		
		/*
		 * Variables a editar
		 */
		String inputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.rdf";
		Integer maxLines = 5;
		/*
		 * Fin de variables a editar
		 */

		FileReader fr = new FileReader(inputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(outputFile);

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

					DataExpression dataexpression = new DataExpression();
					dataexpression.setDescription(tokens[3]);
					dataexpression.setProfile(tokens[2]);

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

}