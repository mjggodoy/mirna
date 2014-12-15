package mirna.database;

import mirna.exception.MiRnaException;

public abstract class VirmiRNA extends MirnaDatabase {
	
	public VirmiRNA() throws MiRnaException { super(); }
	
	public static void main(String[] args) throws Exception {
		
		VirmiRNA virmiRNA1 = new VirmiRNA1();
		VirmiRNA virmiRNA2 = new VirmiRNA2();
		VirmiRNA virmiRNA3 = new VirmiRNA3();

//		String inputFile1 = "/Users/esteban/Softw/miRNA/VIRmiRNA/vmr.tsv";
//		String inputFile2 = "/Users/esteban/Softw/miRNA/VIRmiRNA/avm.tsv";
//		String inputFile3 = "/Users/esteban/Softw/miRNA/VIRmiRNA/vmt.tsv";
//		virmiRNA1.insertInTable(inputFile1);
//		virmiRNA2.insertInTable(inputFile2);
//		virmiRNA3.insertInTable(inputFile3);
		
		virmiRNA1.insertIntoSQLModel();
		//virmiRNA2.insertIntoSQLModel();
		//virmiRNA3.insertIntoSQLModel();

	}
	
	
}
