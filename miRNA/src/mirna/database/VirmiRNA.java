package mirna.database;

public abstract class VirmiRNA implements IMirnaDatabase {
	
	protected String csvInputFile;
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile;
		VirmiRNA virmirRNA;
		
//		inputFile = "/Users/esteban/Softw/miRNA/VIRmiRNA/vmr.tsv";
//		virmirRNA = new VirmiRNA1(inputFile);
//		virmirRNA.insertInTable("virmirna1");

		inputFile = "/Users/esteban/Softw/miRNA/VIRmiRNA/avm.tsv";
		virmirRNA = new VirmiRNA2(inputFile);
		virmirRNA.insertInTable("virmirna2");
//		
//		inputFile = "/Users/esteban/Softw/miRNA/VIRmiRNA/vmt.tsv";
//		virmirRNA = new VirmiRNA3(inputFile);
//		virmirRNA.insertInTable("virmirna3");

		
	}
	
	
}
