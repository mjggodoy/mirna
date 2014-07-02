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

public class miREnvironment {

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
		int smallMoleculeCount = 1;
		String line;

		while ((line = br.readLine()) != null) {

		
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

					Disease disease = new Disease();
					disease.setName(tokens[4]);

					MiRna miRna2 = new MiRna();
					miRna2.setName(tokens[1]);
					miRna2.setSubName(tokens[2]);
					miRna2.setSubName(tokens[3]);

					SmallMolecule smallmolecule = new SmallMolecule();
					smallmolecule.setName(tokens[5]);

					DataExpression dataexpression = new DataExpression();
					dataexpression.setId(tokens[0]);
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
									namespace + "Disease_"
											+ disease.getName())
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
					

					Resource smallmolecule2 = model.createResource(
							
							namespace + "Smallmolecule_"
									+ smallMoleculeCount)
							
							
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), smallmolecule.getName())		
							.addProperty(
							RDF.type,
							ResourceFactory.createResource(namespace
									+ "SmallMolecule"));

					
					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna2.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "name"), miRna2.getName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "subname"), miRna2.getSubName())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "subname"), miRna2.getSubName())
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
											+ "id"), dataexpression.getId())
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
					smallMoleculeCount++;
					dataExpressionCount++;
					System.out.println();
					

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
			
		}

			br.close();
			fr.close();
			out.close();

		

	}

}