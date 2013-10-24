package fixers;

import fixers.PhenomiRFixer;

public class PhenomiR1 {
	
public static void main(String[] args) throws Exception {
		
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
		String inputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir1.0.txt";
		String outputFile = "/Users/mariajesus/Desktop/NewSearchingLine/phenomir1.0_out.txt";
		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(14, ";");
		phenomiRFixer.checkCorrectness(inputFile);
		System.out.println("El separador que estamos usando es: " + phenomiRFixer.getSeparator());
		phenomiRFixer.replaceSeparator(inputFile, outputFile, "\t");
		System.out.println("El separador que estamos usando es: " + phenomiRFixer.getSeparator());
		phenomiRFixer.checkCorrectness(outputFile);
		
	}


}
