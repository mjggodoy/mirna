package mirna.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PubmedIdExtractor{
	
	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader("test.txt");
        BufferedReader br = new BufferedReader(fr);
        
		String line;
		br.readLine();
		
		while ((line = br.readLine()) !=null) {
			
			while (line.contains("<a")) {
				
//				System.out.println("LINE = " + line);
				
				int startIndex = line.indexOf("<a");
				int endIndex = line.indexOf("</a>");
				
				String link = line.substring(startIndex, endIndex);
				
				if (link.contains("http://www.ncbi.nlm.nih.gov/pubmed/")) {
				
					String pubmedId = link.substring(link.indexOf(">")+1);
					
					// HACER COSITAS CON PUBMEDID
					
//					System.out.println("LINK = " +link);
//					System.out.println("PUBMEDID = " + pubmedId);
				
				}

				
				line = line.substring(link.length()+4);
				
			}
			
			// para cada <a> hacer
				// cojo un a
				// proceso el a (saco el pubmed id)
			    // hago con el pubmed id lo que sea
			
			    // recorto la linea
			
			// fin para
			
		}
		
		br.close();
	}
	
	
}


