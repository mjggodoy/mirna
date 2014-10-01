package mirna.database;


/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public abstract class miRdSNP implements IMirnaDatabase {
	
	protected String csvInputFile;
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile;
		miRdSNP mirdsnp;
		
		inputFile = "/Users/esteban/Softw/miRNA/mirdsnp-dsnps-v11.03.txt";
		mirdsnp = new miRdSNP1(inputFile);
		mirdsnp.insertInTable("mirdsnp1");

		inputFile = "/Users/esteban/Softw/miRNA/mirdsnp-dsnp-generated-mir-targets-v11.03.txt";
		mirdsnp = new miRdSNP2(inputFile);
		mirdsnp.insertInTable("mirdsnp2");
		
		inputFile = "/Users/esteban/Softw/miRNA/mirdsnp-by-gene-v11.03.txt";
		mirdsnp = new miRdSNP3(inputFile);
		mirdsnp.insertInTable("mirdsnp3");

		inputFile = "/Users/esteban/Softw/miRNA/mirdsnp-snp-mir-distance-v11.03.txt";
		mirdsnp = new miRdSNP4(inputFile);
		mirdsnp.insertInTable("mirdsnp4");
		
		inputFile = "/Users/esteban/Softw/miRNA/mirdsnp-snp.txt";
		mirdsnp = new miRdSNP4(inputFile);
		mirdsnp.insertInTable("mirdsnp5");

	}
	
	

}