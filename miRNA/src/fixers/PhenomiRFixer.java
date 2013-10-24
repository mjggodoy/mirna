package fixers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;

public class PhenomiRFixer {
	
	private int numOfTokens;
	private String separator;
	
	public PhenomiRFixer(int numOfTokens, String separator) {
		super();
		this.numOfTokens = numOfTokens;
		this.separator = separator;
	}
	
	public String getSeparator() {
		return separator;
	}
	
	public boolean checkCorrectness(String file) throws IOException {
		
		boolean res = true;
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		int countLine = 0;
		
		while((line=br.readLine())!=null) {
			
			String[] tokens = StringUtils.splitPreserveAllTokens(line, separator);
			countLine++;
			
			if (tokens.length!=numOfTokens) { //Número de tokens diferente a la longitud que es la fijada, en este caso 14
				System.out.println("Linea número " + countLine + " con " + tokens.length + " tokens!"); // Aviso del token con respecto al número de línea
				for (int i=0; i<tokens.length; i++) {
					
					System.out.println(" token[" + i + "] = " + tokens[i]);
				}
				res = false;
				break;
			}
			
		}
		br.close();
		fr.close();
		
		if (res) {
			System.out.println("Fichero " + file + " correcto.");
		}
		
		return res;
		
	}
	
	public void fixBadSeparatedLines(String fileIn, String fileOut) throws IOException {
		
		FileReader fr = new FileReader(fileIn);
		BufferedReader br = new BufferedReader(fr);
		
		PrintWriter pw = new PrintWriter(new File(fileOut));
		
		String line1, line2; // Trabajamos con dos líneas seguidas a la vez.
		
		line1 = br.readLine();
		
		while((line2=br.readLine())!=null) {
			
			if (line2.startsWith(",,")) {
				line2 = line2.substring(",,".length());
				line1 = line1.trim();
				line2 = line2.trim();
				line1 = line1 + separator + separator + line2;
				//line1 = line1.replaceAll(";", "\t");
				pw.println(line1);
				line1 = br.readLine();
			} else {
				//line1 = line1.replaceAll(";", "\t");
				pw.println(line1);
				line1 = line2;
			}
			
		}
	
		//line1 = line1.replaceAll(";", "\t");
		pw.println(line1);
		
		pw.close();
		br.close();
		fr.close();
		
	}
	
	public void replaceSeparator(String fileIn, String fileOut, String newSeparator) throws IOException {
		
		FileReader fr = new FileReader(fileIn);
		BufferedReader br = new BufferedReader(fr);
		
		PrintWriter pw = new PrintWriter(new File(fileOut));
		
		String line;
		
		while((line=br.readLine())!=null) {
			line = line.replaceAll(this.separator, newSeparator);
			pw.println(line);
		}
	
		pw.close();
		br.close();
		fr.close();
		
		this.separator = newSeparator;
		
		
	}
	
	//public static void main(String[] args) throws Exception {
		
		// TROZO DE CODIGO PARA EL 2.0
//		String inputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0.txt";
//		String outputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0_out.txt";
//		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(13, "\t");
//		boolean res = phenomiRFixer.checkCorrectness(inputFile);
//		if (res==false) {
//			System.out.println("VAMOS A ARREGLARLO!");
//			phenomiRFixer.fixBadSeparatedLines(inputFile, outputFile);
//			System.out.println("COMPROBAMOS?");
//			phenomiRFixer.checkCorrectness(outputFile);
//		}
		
		//TROZO DE CODIGO PARA EL 1.0
//		String inputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir1.0.txt";
//		String outputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir1.0_out.txt";
//		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(14, ";");
//		phenomiRFixer.checkCorrectness(inputFile);
//		System.out.println("El separador que estamos usando es: " + phenomiRFixer.getSeparator());
//		phenomiRFixer.replaceSeparator(inputFile, outputFile, "\t");
//		System.out.println("El separador que estamos usando es: " + phenomiRFixer.getSeparator());
//		phenomiRFixer.checkCorrectness(outputFile);
//		
//	}

}
