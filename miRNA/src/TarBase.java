
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import beans.DataExpression;
import beans.Disease;
import beans.Gene;
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

public class TarBase {

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
					miRna2.setName(tokens[3]);

					InteractionData interactionData = new InteractionData();
					interactionData.setMiTG_score(tokens[4]);
				

					Transcript transcript2 = new Transcript();
					transcript2.setTranscriptID(tokens[1]);
					
					Gene gene2 = new Gene();
					gene2.setName(tokens[2]);
					
					
					

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

					
					Resource transcript = model
							.createResource(
									namespace + "Transcript/"
											+ transcript2.getTranscriptID())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Transcript"));
					
//					Resource gene = model
//							.createResource(
//									namespace +"Gene/" +
//											gene2.getName());
							
	
					model.createResource(
							namespace + "InteractionData_"
									+ interactiondataCount)
							
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "MiTG_score"),
											interactionData.getMiTG_score())
											
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"), miRNA)
											
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"), transcript)				
											
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "InteractionData"));
					
					
					model.createResource(
							namespace + "Gene"
									+ gene2.getName())
							
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "producesTranscript"), transcript)
											
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Gene"));				
					
					
					

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
