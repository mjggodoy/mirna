package mirna.beans;

import mirna.exception.ConflictException;

public class InteractionData extends ModelClass {

	
	private String score;//ok
	private String pvalue_log;//ok
	private String miTG_score; //ok
	private String method; //ok
	private String feature; //ok
	private String phase; //ok
	private String rank;//ok
	private String provenance;//ok
	private String reference;//ok
	private String pubmedId;//ok
	private String cellularLine;//ok
	private String pvalue_og;//ok
	private String type;
	private String description;
	private String algorithm;
	private Integer mirnaPk;
	private Integer targetPk;
	private Integer genePk;
	private Integer expressionDataPk;

	
	public InteractionData() {
		super();
	}
	


	public InteractionData(int pk,String score, String pvalue_log, String miTG_score,
			String method, String feature, String phase, String rank,
			String provenance, String reference, String pubmedId,
			String cellularLine, String pvalue_og, String type, String description, String algorithm,
			int mirnaPk, int targetPk, int genePk, int expressionDataPk) {
		super(pk);
		this.score = score;
		this.pvalue_log = pvalue_log;
		this.miTG_score = miTG_score;
		this.method = method;
		this.feature = feature;
		this.phase = phase;
		this.rank = rank;
		this.provenance = provenance;
		this.reference = reference;
		this.pubmedId = pubmedId;
		this.cellularLine = cellularLine;
		this.pvalue_og = pvalue_og;
		this.type = type;
		this.description = description;
		this.algorithm = algorithm;
		this.mirnaPk = mirnaPk;
		this.targetPk = targetPk;
		this.genePk = genePk;
		this.expressionDataPk = expressionDataPk;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPvalue_og() {
		return pvalue_og;
	}

	public void setPvalue_og(String pvalue_og) {
		this.pvalue_og = pvalue_og;
	}

	public String getCellularLine() {
		return cellularLine;
	}

	public void setCellularLine(String cellularLine) {
		this.cellularLine = cellularLine;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPvalue_log() {
		return pvalue_log;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public void setPvalue_log(String pvalue_log) {
		this.pvalue_log = pvalue_log;
	}

	public String getMiTG_score() {
		return miTG_score;
	}

	public void setMiTG_score(String miTG_score) {
		this.miTG_score = miTG_score;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	
	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(int mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	public int getTargetPk() {
		return targetPk;
	}

	public void setTargetPk(int targetPk) {
		this.targetPk = targetPk;
	}
	
	
	public Integer getGenePk() {
		return genePk;
	}

	public void setGenePk(Integer genePk) {
		this.genePk = genePk;
	}


	public Integer getExpressionDataPk() {
		return expressionDataPk;
	}

	public void setExpressionDataPk(Integer expressionDataPk) {
		this.expressionDataPk = expressionDataPk;
	}



	public int checkConflict(InteractionData id) {
		
		int res = 0;
		
		if (this.pk!=null) {
			if (id.getPk()==null) res++; 
			else if (!this.pk.equals(id.getPk())) return -1;
		}
		if (this.score!=null) {
			if (id.getScore()==null) res++; 
			else if (!this.score.equals(id.getScore())) return -1;
		}
		if (this.pvalue_log != null){
			if (id.pvalue_log==null) res++;
			else if (!this.pvalue_log.equals(id.pvalue_log)) return -1;
		}
		if (this.rank != null){
			if (id.rank==null) res++;
			else if (!this.rank.equals(id.rank)) return -1;
		}
		if (this.provenance != null){
			if (id.provenance==null) res++;
			else if (!this.provenance.equals(id.provenance)) return -1;
		}
		
		if (this.pvalue_og != null){
			if (id.pvalue_og==null) res++;
			else if (!this.pvalue_og.equals(id.pvalue_og)) return -1;
		}
		
		if (this.miTG_score != null){
			if (id.miTG_score==null) res++;
			else if (!this.miTG_score.equals(id.miTG_score)) return -1;
		}
		
		if (this.method != null){
			if (id.method==null) res++;
			else if (!this.method.equals(id.method)) return -1;
		}
		
		if (this.phase != null){
			if (id.phase==null) res++;
			else if (!this.phase.equals(id.phase)) return -1;
		}
		
		if (this.feature != null){
			if (id.feature==null) res++;
			else if (!this.feature.equals(id.feature)) return -1;
		}
		
		if (this.algorithm != null){
			if (id.algorithm==null) res++;
			else if (!this.algorithm.equals(id.algorithm)) return -1;
		}
				
		if (this.cellularLine != null){
			if (id.cellularLine==null) res++;
			else if (!this.cellularLine.equals(id.cellularLine)) return -1;
		}
		
		if (this.pubmedId != null){
			if (id.pubmedId==null) res++;
			else if (!this.pubmedId.equals(id.pubmedId)) return -1;
		}
		
		if (this.type != null){
			if (id.type==null) res++;
			else if (!this.type.equals(id.type)) return -1;
		}
		
		if (this.description != null){
			if (id.description==null) res++;
			else if (!this.description.equals(id.description)) return -1;
		}
		
		if (this.reference != null){
			if (id.reference==null) res++;
			else if (!this.reference.equals(id.reference)) return -1;
		}
		
		if (this.mirnaPk != null){
			if (id.mirnaPk==null) res++;
			else if (!this.mirnaPk.equals(id.mirnaPk)) return -1;
			
		}
		
		if (this.targetPk != null){
			if (id.targetPk==null) res++;
			else if (!this.targetPk.equals(id.targetPk)) return -1;	
			
		}
		
		if(this.genePk != null){
			
			if(id.genePk==null) res++;
			else if(!this.targetPk.equals(id.genePk)) return -1;
			
		}
		
		if(this.expressionDataPk != null){
			
			if(id.getExpressionDataPk()==null) res++;
			else if(!this.expressionDataPk.equals(id.expressionDataPk)) return -1;
			
		}
		
		return res;
	}
	
	public void update(InteractionData id) throws ConflictException {
		this.update(id, true);
	}
	
	public void update(InteractionData id, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(id)==-1) throw new ConflictException(this, id);
		}
		if (id.getPk()!=null) this.pk = id.getPk();
		if (id.getScore()!=null) this.score = id.getScore();
		if (id.getPvalue_log()!=null) this.pvalue_log = id.getPvalue_log();
		if (id.getPvalue_og()!=null) this.pvalue_og = id.getPvalue_og();
		if (id.getRank()!=null) this.rank = id.getRank();
		if (id.getDescription()!=null) this.description = id.getDescription();
		if (id.getReference()!=null) this.reference = id.getReference();
		if (id.getAlgorithm()!=null) this.algorithm = id.getAlgorithm();
		if (id.getPubmedId()!=null) this.pubmedId = id.getPubmedId();
		if (id.getType()!=null) this.type = id.getType();
		if (id.getFeature()!=null) this.feature = id.getFeature();
		if (id.getPhase()!=null) this.phase = id.getPhase();
		if (id.getMethod()!=null) this.rank = id.getMethod();
		if (id.getCellularLine()!=null) this.cellularLine = id.getCellularLine();
		if (id.getMiTG_score()!=null) this.miTG_score = id.getCellularLine();
		if (id.getProvenance()!=null) this.provenance = id.getProvenance();
		if(id.getExpressionDataPk() != null) this.expressionDataPk = id.getExpressionDataPk();

	}



	@Override
	public String toString() {
		return "InteractionData [score=" + score + ", pvalue_log=" + pvalue_log
				+ ", miTG_score=" + miTG_score + ", method=" + method
				+ ", feature=" + feature + ", phase=" + phase + ", rank="
				+ rank + ", provenance=" + provenance + ", reference="
				+ reference + ", pubmedId=" + pubmedId + ", cellularLine="
				+ cellularLine + ", pvalue_og=" + pvalue_og + ", type=" + type
				+ ", description=" + description + ", algorithm=" + algorithm
				+ ", mirnaPk=" + mirnaPk + ", targetPk=" + targetPk + ", pk="
				+ pk + "]";
	}

}


