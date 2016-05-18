package mirna.integration.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PubmedIdExtractor{
	
	
public static void main(String[] args) throws IOException {
	
	
		
		
	FileReader fr = new FileReader("test2.txt");
	BufferedReader br = new BufferedReader(fr);

	String line;
	
	while((line = br.readLine()) !=null){
		
		while ((line.contains(","))) {
			
			int index1 = line.indexOf(",");
			String pubmed = line.substring(1,index1);
			System.out.println(pubmed);
			line = line.substring(pubmed.length()+2, line.length()-1); 
			//System.out.println(line);

			
        }	
		
		System.out.println(line);
		
							
	}
        
        br.close();
	}
	
	
}
	
	
	//public static void main(String[] args) throws IOException {
		
		/*FileReader fr = new FileReader("test.txt");
        BufferedReader br = new BufferedReader(fr);
        
		String line;
		br.readLine();
		
		while ((line = br.readLine()) !=null) {
			
			while (line.contains("<a")) {
				
//				System.out.println("LINE = " + line);
				
				int startIndex = line.indexOf("<a");
				int endIndex = line.indexOf("</a>");
				
				String link = line.substring(startIndex, endIndex);
				
				//System.out.println(link);
				
				if (link.contains("http://www.ncbi.nlm.nih.gov/pubmed/")) {
				
					String pubmedId = link.substring(link.indexOf(">")+1);
					//System.out.println(pubmedId);

					
					// HACER COSITAS CON PUBMEDID
					
//					System.out.println("LINK = " +link);
//					System.out.println("PUBMEDID = " + pubmedId);
				
				}

				
				line = line.substring(link.length()+4);
				System.out.println(line);
				
			}
			
			// para cada <a> hacer
				// cojo un a
				// proceso el a (saco el pubmed id)
			    // hago con el pubmed id lo que sea
			
			    // recorto la linea
			
			// fin para
			
		}
		
		br.close();
	}*/
	
	



