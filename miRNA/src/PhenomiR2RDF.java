import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import beans.BibliographyReference;
import beans.DataExpression;
import beans.Disease;
import beans.Gene;
import beans.MiRna;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class PhenomiR2RDF {
	
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0_out.txt");
		BufferedReader br = new BufferedReader(fr);
		OutputStream out= new FileOutputStream("C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/RDF_Phenomizer.txt");
		
		//int numLineas = 8;
		
		String resourceUri = "http://khaos.uma.es/mirna/resource/";
		String propertyUri = "http://khaos.uma.es/mirna/property/";
		
		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";
		
		Model model = ModelFactory.createDefaultModel();
		
		int count = 0;
		int dataExpressionCount = 1;
		
		
//		while ((br.readLine() != null) && (count<numLineas)){
		while (br.readLine() != null) {
			
			String line = br.readLine();
			System.out.println("linea =");
			System.out.println(line);
			count ++;
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
//				String field1=st.nextToken();
//				String field2=st.nextToken();
//				String field3=st.nextToken();
//				String field4=st.nextToken();
//				String field5=st.nextToken();
//				String field6=st.nextToken();			
//				String field7=st.nextToken();
//				String field8=st.nextToken();
//				String field9=st.nextToken();
////				String field10=st.nextToken();
////				String field11=st.nextToken();
////				String field12=st.nextToken();
////				String field13=st.nextToken();
				//Disease disease = new Disease(field3, field4, field5, Integer.parseInt(field1));
				Disease disease = new Disease(tokens[2], tokens[3], tokens[4], tokens[0]);
				//Gene gene = new Gene(tokens[12]);
				//MiRna miRna = new MiRna(field6, field7);
				MiRna miRna = new MiRna(tokens[5], tokens[6]);
				DataExpression dataExpression = new DataExpression(tokens[7], (tokens[8]), tokens[9], tokens[10], tokens[11], tokens[12]);
				//BibliographyReference bib = new BibliographyReference(field2);
				BibliographyReference bib = new BibliographyReference(tokens[1]);
//				Tissue tissue = new  Tissue();
				
				
				Resource diseaseResource = model.createResource(namespace + "Disease_" + disease.getPhenomicId())
						.addProperty(ResourceFactory.createProperty(namespace + "phenomicid"), disease.getPhenomicId())
						.addProperty(ResourceFactory.createProperty(namespace + "name"), disease.getName())
						.addProperty(ResourceFactory.createProperty(namespace + "diseaseClass"), disease.getDiseaseClass())
						.addProperty(ResourceFactory.createProperty(namespace + "bibliography"), bib.getPubmedId())
						.addProperty(RDF.type, ResourceFactory.createResource(namespace + "Disease"));;
						
					
				
				//Resource geneResource = model.createResource(resourceUri + "gene/" + gene.getName());
				
				//Resource bibliography = model.createResource(namespace + "Bibliography/" + bib.getPubmedId());
				
				Resource miRNA = model.createResource(namespace + "miRNA/" + miRna.getName())
						.addProperty(ResourceFactory.createProperty(namespace + "accessionNumber"), miRna.getAccessionNumber())
				.addProperty(RDF.type, ResourceFactory.createResource(namespace + "miRNA"));
						
						
				
				
				//model.createResource(namespace+ "miRNA/" + miRna.getName())
				model.createResource(namespace + "DataExpression_" + dataExpressionCount) //   dataExpression.getExpression())
				.addProperty(ResourceFactory.createProperty(namespace + "foldchangeMax"), dataExpression.getFoldchangeMin()) 
				.addProperty(ResourceFactory.createProperty(namespace + "foldchangeMin"), dataExpression.getFoldchangeMax())
				.addProperty(ResourceFactory.createProperty(namespace + "id"), dataExpression.getId())
				.addProperty(ResourceFactory.createProperty(namespace + "studyDesign"), dataExpression.getStudyDesign())
				.addProperty(ResourceFactory.createProperty(namespace + "method"), dataExpression.getMethod())	
				.addProperty(ResourceFactory.createProperty(namespace + "involves"), miRNA)
				.addProperty(ResourceFactory.createProperty(namespace + "relatedWith"), diseaseResource)
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
					throw e;
				}
				
			}else{
				
				
			}
				
				
			}
			
		
		
		

		
		model.write(out);
		
		br.close();
		fr.close();
		out.close();

//		String line;
//		int count;
//		try {
//			
//			boolean linea1 = true;
//			
//			
//
//			while((line=br.readLine())!=null) {
//				
//				StringTokenizer st = new StringTokenizer(line, "\t");
//				
//				if (linea1) {
//					line=br.readLine();
//					System.out.println(line);
//					System.out.println(st.countTokens());
//					linea1 = false;
//				}
//
//				
//
//				if (st.countTokens() == 10) {
//					String field1=st.nextToken();
////					System.out.println(field1);
//					String field2=st.nextToken();
////					System.out.println(field2);
//					String field3=st.nextToken();
////					System.out.println(field3);
//					String field4=st.nextToken();
////					System.out.println(field4);
//					String field5=st.nextToken();
////					System.out.println(field5);
//					String field6=st.nextToken();
////					System.out.println(field6);
//					String field7=st.nextToken();
////					System.out.println(field7);
//					String field8=st.nextToken();
////					System.out.println(field8);
//					String field9=st.nextToken();
////					System.out.println(field9);
//					String field10=st.nextToken();
////					System.out.println(field10);
//				} else {
////					System.out.println(line);
//				}
//
//				
//			}
	}

}