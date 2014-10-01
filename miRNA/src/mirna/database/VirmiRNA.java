package mirna.database;

public class VirmiRNA {
	
	
	
protected String csvInputFile;
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile;
		VirmiRNA1 virmirRNA;
		
		inputFile = "/Users/esteban/Softw/miRNA/vmr.tsv";
		virmirRNA = new VirmiRNA1(inputFile);
		virmirRNA.insertInTable("virmirRNA_1");

		inputFile = "/Users/esteban/Softw/miRNA/avm.tsv";
		virmirRNA = new VirmiRNA2(inputFile);
		virmirRNA.insertInTable("virmirRNA_2");
		
		inputFile = "/Users/esteban/Softw/miRNA/vmt.tsv";
		virmirRNA = new VirmirRNA3(inputFile);
		virmirRNA.insertInTable("virmirRNA_3");

		
	}
	
	
}
