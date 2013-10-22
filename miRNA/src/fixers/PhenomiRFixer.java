package fixers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;

public class PhenomiRFixer {
	
	private String inputFile;
	private String outputFile;
	
	private int numOfTokens = 13;
	
	public PhenomiRFixer(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	
	private boolean checkCorrectness(String file) throws IOException {
		
		boolean res = true;
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		int countLine = 0;
		
		while((line=br.readLine())!=null) {
			
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");
			countLine++;
			
			if (tokens.length!=numOfTokens) {
				System.out.println("Linea número " + countLine + " con " + tokens.length + " tokens!");
				for (int i=0; i<tokens.length; i++) {
					System.out.println(" token[" + i + "] = " + tokens[i]);
				}
				res = false;
				break;
			}
			
		}
		br.close();
		fr.close();
		
		return res;
		
	}
	
	private void fixBadSeparatedLines(String fileIn, String fileOut) throws IOException {
		
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
				line1 = line1 + "\t\t" + line2;
				pw.println(line1);
				line1 = br.readLine();
			} else {
				pw.println(line1);
				line1 = line2;
			}
			
		}
		
		pw.println(line1);
		
		pw.close();
		br.close();
		fr.close();
		
	}
	
	public void execute() throws Exception {
		
		if (inputFile.equals(outputFile)) {
			throw new Exception("Fichero de entrada igual que el de salida");
		}
		
		if (checkCorrectness(inputFile)) {
			System.out.println("Fichero de entrada ya correcto.");
		} else {
			fixBadSeparatedLines(inputFile, outputFile);
			if (checkCorrectness(outputFile)) {
				System.out.println("Todo correcto!");
			} else {
				System.out.println("Después de la transformación.... sigue fallando. Vaya.....");
			}
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		String inputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0.txt";
		String outputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0_out.txt";
		
		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(inputFile, outputFile);
		phenomiRFixer.execute();
	}

}
