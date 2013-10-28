import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import beans.BibliographyReference;
import beans.DataExpression;
import beans.Disease;
import beans.MiRna;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class PhenomiR2RDF {
	
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("C:/Users/Esteban/Dropbox/NewSearchingLine/phenomir2.0_out.txt");
		BufferedReader br = new BufferedReader(fr);
		
		int numLineas = 8;
		
		String resourceUri = "http://khaos.uma.es/mirna/resource/";
		String propertyUri = "http://khaos.uma.es/mirna/property/";
		
		Model model = ModelFactory.createDefaultModel();
		
		for (int i=0; i<numLineas; i++) {
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, "\t");
			if (i==0) {
				System.out.println("NUMERO DE TOKENS: " + st.countTokens());
				int j=1;
				while (st.hasMoreTokens()) {
					System.out.println(j + ":" + st.nextToken());
					j++;
				}
			} else {
				
				String field1=st.nextToken();
				String field2=st.nextToken();
				String field3=st.nextToken();
				String field4=st.nextToken();
				String field5=st.nextToken();
				String field6=st.nextToken();
				String field7=st.nextToken();
				String field8=st.nextToken();
				String field9=st.nextToken();
//				String field10=st.nextToken();
//				String field11=st.nextToken();
//				String field12=st.nextToken();
//				String field13=st.nextToken();
				Disease disease = new Disease(field3, field4, field5, Integer.parseInt(field1));
				//Gene gene = new Gene();
				MiRna miRna = new MiRna(field6, field7);
//				DataExpression dataExpression = new DataExpression(field8, Double.parseDouble(field9), Double.parseDouble(field10), field12, field13);
				BibliographyReference bib = new BibliographyReference(field2);
//				Tissue tissue = new  Tissue();
				
				
				Resource diseaseResource = model.createResource(resourceUri + "disease/" + disease.getPhenomicId())
					.addProperty(ResourceFactory.createProperty(propertyUri + "phenomicid"), String.valueOf(disease.getPhenomicId()))
					.addProperty(ResourceFactory.createProperty(propertyUri + "name"), disease.getName());
				
				model.createResource(resourceUri + "mirna/" + miRna.getName())
				.addProperty(ResourceFactory.createProperty(propertyUri + "name"), miRna.getName())
				.addProperty(ResourceFactory.createProperty(propertyUri + "relatedWith"), diseaseResource);
				
			}
			
			
			
		}
		
		model.write(System.out);
		
		br.close();
		fr.close();

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
