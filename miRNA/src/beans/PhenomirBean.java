package beans;

// CAMBIO DE PRUEBA
public class PhenomirBean {
	
	//phenomicid	pmid	disease	diseasesub_id	class	miRNA	accession	expression	foldchangemin	foldchangemax	id	name	method
	
	private String phenomicid;
	private String pmid;
	private String disease;
	
	public PhenomirBean() {}

	public String getPhenomicid() {
		return phenomicid;
	}

	public void setPhenomicid(String phenomicid) {
		this.phenomicid = phenomicid;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	};
	
	
	
	

}
