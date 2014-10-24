package mirna.utils.fixers;
import mirna.utils.fixers.PhenomiRFixer;


public class PhenomiR2 {
	

public static void main(String[] args) throws Exception {
		
		// TROZO DE CODIGO PARA EL 2.0
		String inputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir2.0.txt";
		String outputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir2.0_out.txt";
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