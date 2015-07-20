package mirna.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;






public class Volumenes{
	
	
	public int volumenCalculation(int count){
		
		int i=1;
				while (i< 2048) { 
				i=2*i;
				count++;
				}
				return count;
	}
	
	public static String indexCalculation1(String target_pubmedId){
		
		int index1 = target_pubmedId.indexOf(">");
		int index2 = target_pubmedId.indexOf("</a>");
		target_pubmedId =  target_pubmedId.substring(index1+1,index2);
		return target_pubmedId;
	}
	
	
	public static String[] indexCalculation2(String  target_pubmedId){
	
		int index1 = target_pubmedId.indexOf(",");
		int index4 = target_pubmedId.indexOf("pubmed/");
		int index2 = target_pubmedId.indexOf(">");
		int index3 = target_pubmedId.indexOf("</a>");
		String target_pubmedId1 =  target_pubmedId.substring(0,index1);
		//System.out.println(target_pubmedId1);
		String target_pubmedId2 =  target_pubmedId.substring(index1+1);
		//System.out.println(target_pubmedId2);
		target_pubmedId1 =  target_pubmedId1.substring(index4+7,index3-2);
		//System.out.println(target_pubmedId1);
		target_pubmedId2 =  target_pubmedId2.substring(index4+7,index3-2);
		//System.out.println(target_pubmedId2);
		return new String[] {target_pubmedId1, target_pubmedId2};

	}
	
	
	public static String[] indexCalculation3(String  target_pubmedId){
		
		int index1 = target_pubmedId.indexOf(",");
		int index4 = target_pubmedId.indexOf("pubmed/");
		int index2 = target_pubmedId.indexOf(">");
		int index3 = target_pubmedId.indexOf("</a>");
		String target_pubmedId1 =  target_pubmedId.substring(0,index1);
		//System.out.println(target_pubmedId1);
		String target_pubmedId2 =  target_pubmedId.substring(index1+1);
		//System.out.println(target_pubmedId2);
		target_pubmedId1 =  target_pubmedId1.substring(index4+7,index2-1);
		//System.out.println(target_pubmedId1);
		target_pubmedId2 =  target_pubmedId2.substring(index4+14,index3-2);
		//System.out.println(target_pubmedId2);
		return new String[] {target_pubmedId1, target_pubmedId2};

		
	}
	
	
	public static String[] indexCalculation4(String target_pubmedId){
		
		int index1 = target_pubmedId.indexOf(">");
		int index2 = target_pubmedId.indexOf("</a>");
		target_pubmedId =  target_pubmedId.substring(index1+1,index2);
		String target_pubmedId1 = target_pubmedId.substring(0,8);
		//System.out.println(target_pubmedId1);
		String target_pubmedId2 = target_pubmedId.substring(10,18);
		//System.out.println(target_pubmedId2);
		return new String[] {target_pubmedId1, target_pubmedId2};
	}
	
	
	public static String indexCalculation5(String target_pubmedId){
		
		int index1 = target_pubmedId.indexOf(">");
		int index2 = target_pubmedId.indexOf("</a>");
		target_pubmedId =  target_pubmedId.substring(index1+1,index2);
		System.out.println(target_pubmedId);
		return target_pubmedId;
		
	}
	
	
	/*public static void main(String[] args) {
		
		String entrada = "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/23418453\">23418453</a>" ;
		String entrada1 = "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/21821155\"></a>,<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/19524505\">19524505</a>";
		String entrada2 = "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/22676898\">22676898</a>&nbsp;,&nbsp; <a href=\"http://www.ncbi.nlm.nih.gov/pubmed/20643939\">20643939</a>";
		String entrada3 = "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/22676898\">18668040</a>&nbsp;,&nbsp;";
		String entrada4 = "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/21565290, 20728471\">21565290, 20728471</a>";
		String entrada5 = "<a href=\"http://www.patentlens.net/patentlens/patents.html?patnums=WO_2010_101663_A2\">WO/2010/101663/A2</a>";
		
		String salida = indexCalculation1(entrada);
		//System.out.println(salida);
		
		String salida2 = indexCalculation1(entrada3);
		//System.out.println(salida2);
		
		String [] salida5 = indexCalculation4(entrada4);
		//System.out.println(salida5[0] + " " + salida5[1]);
		
		
		String[] salida3 = indexCalculation2(entrada1);		
		//System.out.println(salida3[0] + " " + salida3[1]);
		
		String[] salida4 = indexCalculation3(entrada2);	
		//System.out.println(salida4[0] + " " + salida4[1]);
		
		String salida6 = indexCalculation5(entrada5);
		System.out.println(salida6);
		
		
		
	}*/
	
	
	public static void main(String[] args) throws IOException {
		
		
		FileReader fr = new FileReader("/Users/mariajesus/Desktop/Test/test.txt");
        BufferedReader br = new BufferedReader(fr);
        
		String line;
		br.readLine();
		while((line = br.readLine()) !=null){
		
		if(!line.contains(">,<") && !line.contains(",") && !line.contains("</a>&nbsp;,&nbsp; <a")
				&& !line.contains("&nbsp;,&nbsp;") && !line.contains("</a>,<a") && !line.contains("</a>, <a")){
				
				int index1 = line.indexOf(">");
				int index2 = line.indexOf("</a>");
				line =  line.substring(index1+1,index2);
				//System.out.println(line + " 1cond");
				
		}
			
			
		if(line.contains("</a>&nbsp;,&nbsp; <a")){
			
			int index1 = line.indexOf(",");
			int index4 = line.indexOf("pubmed/");
			int index2 = line.indexOf(">");
			int index3 = line.indexOf("</a>");
			String target_pubmedId1 =  line.substring(0,index1);
			String target_pubmedId2 =  line.substring(index1+1);
			target_pubmedId1 =  target_pubmedId1.substring(index4+7,index2-1);
			target_pubmedId2 =  target_pubmedId2.substring(index4+14,index3-3);
    		String[] salida4 = {target_pubmedId1, target_pubmedId2};
    		//System.out.println(salida4[0]+ " " + salida4[1] + " 2cond");
		
		}if(line.contains("&nbsp;,&nbsp;")){
			
			int index1 = line.indexOf(">");
			int index2 = line.indexOf("</a>");
			line =  line.substring(index1+1,index2);
			//System.out.println(line + " 3cond");
			
		}if(line.contains(",") && !line.contains(">,<")){
			
			int index1 = line.indexOf("pubmed/");
			int index2 = line.indexOf(">");
			line =  line.substring(index1+7,index2-1);
			//System.out.println(line + " 4cond");
			
    		
		}if (line.contains("</a>,<a")){
			
			int index1 = line.indexOf(",");
    		int index4 = line.indexOf("pubmed/");
    		int index3 = line.indexOf("</a>");
    		String target_pubmedId1 =  line.substring(0,index1);
    		String target_pubmedId2 =  line.substring(index1+1);
    		target_pubmedId1 =  target_pubmedId1.substring(index4+7,index3-2);
    		target_pubmedId2 =  target_pubmedId2.substring(index4+7,index3-2);
    		String[] salida3 = {target_pubmedId1, target_pubmedId2};
    		//System.out.println(salida3[0]+ " " + salida3[1] + " 5cond");
			
    
		
		}else{
			System.out.println("Fail" + line);
			
		}
	}
		
		br.close();
	}
	
	
}


