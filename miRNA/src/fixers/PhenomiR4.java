package fixers;
import fixers.PhenomiRFixer;


public class PhenomiR4 {
	

public static void main(String[] args) throws Exception {
		
		// TROZO DE CODIGO PARA EL 2.0
		String inputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0.txt";
		String outputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir2.0.txt";
		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(13, "\t");
		boolean res = phenomiRFixer.checkCorrectness(inputFile);
		if (res==false) {
			System.out.println("VAMOS A ARREGLARLO!");
			phenomiRFixer.fixBadSeparatedLines(inputFile, outputFile);
			System.out.println("COMPROBAMOS?");
			phenomiRFixer.checkCorrectness(outputFile);
	}
		
}
}