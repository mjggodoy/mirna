package mirna.integration.utils.fixers;

import mirna.integration.utils.fixers.PhenomiRFixer;

public class PhenomiR3 {

	public static void main(String[] args) throws Exception {

		String inputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir1.0.txt";
		String outputFile = "C:/Users/usuario/Desktop/NewSearchingLine/phenomiR/phenomir1.0_out.txt";
		PhenomiRFixer phenomiRFixer = new PhenomiRFixer(14, ";");
		phenomiRFixer.checkCorrectness(inputFile);
		System.out.println("El separador que estamos usando es: "
				+ phenomiRFixer.getSeparator());
		phenomiRFixer.replaceSeparator(inputFile, outputFile, "\t");
		System.out.println("El separador que estamos usando es: "
				+ phenomiRFixer.getSeparator());
		phenomiRFixer.checkCorrectness(outputFile);

	}
}