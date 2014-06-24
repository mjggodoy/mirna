package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

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
 * Código para transformar a RDF los datos de Phenomir 2.0
 * http://mips.helmholtz-muenchen.de/phenomir/
 * 
 * @author María Jesús García Godoy
 *
 */
public class PhenomiR2RDF {
	
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0_out.txt");
		BufferedReader br = new BufferedReader(fr);
		OutputStream out= new FileOutputStream("C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/RDF_Phenomizer.txt");
		
		//int numLineas = 8;
		
		//String resourceUri = "http://khaos.uma.es/mirna/resource/";
		//String propertyUri = "http://khaos.uma.es/mirna/property/";
		
		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";
		
		Model model = ModelFactory.createDefaultModel();
		
		int count = 0;
		int dataExpressionCount = 2200;
		
		br.readLine();
//		while ((br.readLine() != null) && (count<numLineas)){
		while (br.readLine() != null) {
			
			String line = br.readLine();
			//System.out.println("linea =");
			//System.out.println(line);
			count ++;
			System.out.println(count);
			//StringTokenizer st = new StringTokenizer(line, "\t");
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");
			
		
//			if (count==0) {
//				//System.out.println("NUMERO DE TOKENS: " + st.countTokens());
//				System.out.println("NUMERO DE TOKENS: " + tokens.length);
//				//int j=1;
//				//while (st.hasMoreTokens()) {
//				for (int j=1; j<=tokens.length; j++) {
//					//System.out.println(j + ":" + st.nextToken());
//					System.out.println(j + ":" + tokens[j-1]);
//					//j++;
//				}
			if(line!=null){
				
				try {
//			
				Disease disease = new Disease();
				disease.setName(tokens[3]); 
				disease.setDiseaseClass(tokens[4]);
				
				MiRna miRna = new MiRna();
				miRna.setName(tokens[5]);
				miRna.setAccessionNumber(tokens[6]);
				
				DataExpression dataExpression = new DataExpression();
				dataExpression.setPhenomidId(tokens[1]);
				dataExpression.setPubmedId(tokens[2]);
				dataExpression.setFoldchangeMax(tokens[8]);
				dataExpression.setFoldchangeMin(tokens[9]);
				dataExpression.setId(tokens[10]);
				dataExpression.setMethod(tokens[11]);
				dataExpression.setStudyDesign(tokens[12]);
				dataExpression.setProfile(tokens[7]);
				
				
				
				Resource diseaseResource = model.createResource(namespace + "Disease_" + disease.getPhenomicId())
						.addProperty(ResourceFactory.createProperty(namespace + "phenomicid"), disease.getPhenomicId())
						.addProperty(ResourceFactory.createProperty(namespace + "name"), disease.getName())
						.addProperty(ResourceFactory.createProperty(namespace + "diseaseClass"), disease.getDiseaseClass())
						.addProperty(RDF.type, ResourceFactory.createResource(namespace + "Disease"));;
						
				
				Resource miRNA = model.createResource(namespace + "miRNA/" + miRna.getName())
						.addProperty(ResourceFactory.createProperty(namespace + "accessionNumber"), miRna.getAccessionNumber())
				.addProperty(RDF.type, ResourceFactory.createResource(namespace + "miRNA"));
						
						
				model.createResource(namespace + "DataExpression_" + dataExpressionCount) //   dataExpression.getExpression())
				.addProperty(ResourceFactory.createProperty(namespace + "foldchangeMax"), dataExpression.getFoldchangeMin()) 
				.addProperty(ResourceFactory.createProperty(namespace + "foldchangeMin"), dataExpression.getFoldchangeMax())
				.addProperty(ResourceFactory.createProperty(namespace + "id"), dataExpression.getId())
				.addProperty(ResourceFactory.createProperty(namespace + "profile"), dataExpression.getProfile())
				.addProperty(ResourceFactory.createProperty(namespace + "studyDesign"), dataExpression.getStudyDesign())
				.addProperty(ResourceFactory.createProperty(namespace + "method"), dataExpression.getMethod())	
				.addProperty(ResourceFactory.createProperty(namespace + "involvesmiRNA"), miRNA)
				.addProperty(ResourceFactory.createProperty(namespace + "relatedDisease"), diseaseResource)
				.addProperty(RDF.type, ResourceFactory.createResource(namespace + "DataExpression"));
				
				dataExpressionCount++;
				
				
				
				
				 //create a bag
			

				// select all the resources with a VCARD.FN property
				// whose value ends with "Smith"
				
				
				
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j=0; j<tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					//throw e;
				}
				
			}
				
		}
		
		model.write(out);
		
		br.close();
		fr.close();
		out.close();


	}

}