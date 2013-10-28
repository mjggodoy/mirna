package beans;

public class MiRna {
	
//	private String hairpin;
	private String name;
	private String accessionNumber;
//	private String specie;
	
	public MiRna(String name, String accessionNumber) {
		super();
		this.name = name;
		this.accessionNumber = accessionNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	
}
