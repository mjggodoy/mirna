package mirna.database;

import mirna.exception.MiRnaException;


/**
 * Código para procesar los datos de miRdSNP
 * 
 * @author Esteban López Camacho
 *
 */
public abstract class miRdSNP extends MirnaDatabase {
	
	public miRdSNP() throws MiRnaException { super(); }
	
	protected String quitarComillas(String token) {
		if (token.startsWith("\"") && token.endsWith("\"")) {
			token = token.substring(1, token.length()-1);
		}
		return token;
	}
	
	public static void main(String[] args) throws Exception {
		
		miRdSNP mirdsnp1 = new miRdSNP1();
		miRdSNP mirdsnp2 = new miRdSNP2();
		miRdSNP mirdsnp3 = new miRdSNP3();
		miRdSNP mirdsnp4 = new miRdSNP4();
		miRdSNP mirdsnp5 = new miRdSNP5();
		
		String inputFile1 = "/Users/esteban/Softw/miRNA/miRdSNP/mirdsnp-dsnps-v11.03.csv";
		String inputFile2 = "/Users/esteban/Softw/miRNA/miRdSNP/mirdsnp-by-gene-v11.03.csv";
		String inputFile3 = "/Users/esteban/Softw/miRNA/miRdSNP/mirdsnp-snp-mir-distance-v11.03.csv";
		String inputFile4 = "/Users/esteban/Softw/miRNA/miRdSNP/mirdsnp-dsnp-generated-mir-targets-v11.03.csv";
		String inputFile5 = "/Users/esteban/Softw/miRNA/miRdSNP/mirdsnp-dsnps.bed";
		mirdsnp1.insertInTable(inputFile1);
		mirdsnp2.insertInTable(inputFile2);
		mirdsnp3.insertInTable(inputFile3);
		mirdsnp4.insertInTable(inputFile4);
		mirdsnp5.insertInTable(inputFile5);

	}
	
}
