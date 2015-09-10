package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "interaction_data")
public class InteractionData extends ModelClass {

	
	@Column(name = "score", nullable = true, length = 80, unique = true)
	protected String score;
	@Column(name = "pvalue_log", nullable = true, length = 80, unique = true)
	protected String pvalue_log;
	@Column(name = "miTG_score", nullable = true, length = 80, unique = true)
	protected String miTG_score;
	@Column(name = "method", nullable = true, length = 80, unique = true)
	protected String method;
	@Column(name = "feature", nullable = true, length = 80, unique = true)
	protected String feature;
	@Column(name = "phase", nullable = true, length = 80, unique = true)
	protected String phase;
	@Column(name = "rank", nullable = true, length = 80, unique = true)
	protected String rank;
	@Column(name = "provenance", nullable = true, length = 80, unique = true)
	protected String provenance;
	@Column(name = "reference", nullable = true, length = 80, unique = true)
	protected String reference;
	@Column(name = "cellular_line", nullable = true, length = 80, unique = true)
	protected String cellular_line;
	@Column(name = "pvalue_og", nullable = true, length = 80, unique = true)
	protected String pvalue_og;
	@Column(name = "type", nullable = true, length = 80, unique = true)
	protected String type;
	@Column(name = "mirna_pk", nullable = false, length = 80, unique = true)
	protected Integer mirna_pk;
	@Column(name = "target_pk", nullable = false, length = 80, unique = true)
	protected Integer target_pk;
	@Column(name = "gene_pk", nullable = true, length = 80, unique = true)
	protected Integer gene_pk;
	@Column(name = "expression_data_pk", nullable = true, length = 80, unique = true)
	protected Integer expression_data_pk;

	
	public InteractionData() {
		super();
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








	public String getReference() {
		return reference;
	}








	public void setReference(String reference) {
		this.reference = reference;
	}








	public String getCellular_line() {
		return cellular_line;
	}








	public void setCellular_line(String cellular_line) {
		this.cellular_line = cellular_line;
	}








	public String getPvalue_og() {
		return pvalue_og;
	}








	public void setPvalue_og(String pvalue_og) {
		this.pvalue_og = pvalue_og;
	}








	public String getType() {
		return type;
	}








	public void setType(String type) {
		this.type = type;
	}








	public Integer getMirna_pk() {
		return mirna_pk;
	}








	public void setMirna_pk(Integer mirna_pk) {
		this.mirna_pk = mirna_pk;
	}








	public Integer getTarget_pk() {
		return target_pk;
	}








	public void setTarget_pk(Integer target_pk) {
		this.target_pk = target_pk;
	}








	public Integer getGene_pk() {
		return gene_pk;
	}








	public void setGene_pk(Integer gene_pk) {
		this.gene_pk = gene_pk;
	}







	public Integer getExpression_data_pk() {
		return expression_data_pk;
	}








	public void setExpression_data_pk(Integer expression_data_pk) {
		this.expression_data_pk = expression_data_pk;
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
		
		
				
		if (this.cellular_line != null){
			if (id.cellular_line==null) res++;
			else if (!this.cellular_line.equals(id.cellular_line)) return -1;
		}
		
		
		
		if (this.type != null){
			if (id.type==null) res++;
			else if (!this.type.equals(id.type)) return -1;
		}
		
		
		
		if (this.reference != null){
			if (id.reference==null) res++;
			else if (!this.reference.equals(id.reference)) return -1;
		}
		
		if (this.mirna_pk != null){
			if (id.mirna_pk==null) res++;
			else if (!this.mirna_pk.equals(id.mirna_pk)) return -1;
			
		}
		
		if (this.target_pk != null){
			if (id.target_pk==null) res++;
			else if (!this.target_pk.equals(id.target_pk)) return -1;	
			
		}
		
		if(this.gene_pk != null){
			if(id.gene_pk==null) res++;
			else if(!this.gene_pk.equals(id.gene_pk)) return -1;
			
		}
		
		if(this.expression_data_pk != null){
			if(id.expression_data_pk==null) res++;
			else if(!this.expression_data_pk.equals(id.expression_data_pk)) return -1;
			
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
		if (id.getReference()!=null) this.reference = id.getReference();
		if (id.getType()!=null) this.type = id.getType();
		if (id.getFeature()!=null) this.feature = id.getFeature();
		if (id.getPhase()!=null) this.phase = id.getPhase();
		if (id.getMethod()!=null) this.rank = id.getMethod();
		if (id.getCellular_line()!=null) this.cellular_line = id.getCellular_line();
		if (id.getMiTG_score()!=null) this.miTG_score = id.getMiTG_score();
		if (id.getProvenance()!=null) this.provenance = id.getProvenance();
		if(id.getTarget_pk() != null) this.target_pk = id.getTarget_pk();
		if(id.getGene_pk() != null) this.gene_pk = id.getGene_pk();
		if(id.getMirna_pk() != null) this.mirna_pk = id.getMirna_pk();
		if(id.getExpression_data_pk() != null) this.expression_data_pk = id.getExpression_data_pk();


	}



	@Override
	public String toString() {
		return "InteractionData [score=" + score + ", pvalue_log=" + pvalue_log
				+ ", miTG_score=" + miTG_score + ", method=" + method
				+ ", feature=" + feature + ", phase=" + phase + ", rank="
				+ rank + ", provenance=" + provenance + ", reference="
				+ reference + ", cellular_line=" + cellular_line
				+ ", pvalue_og=" + pvalue_og + ", type=" + type + ", mirna_pk="
				+ mirna_pk + ", target_pk=" + target_pk + ", gene_pk="
				+ gene_pk + ", expressiondata_pk=" + expression_data_pk
				+ ", pk=" + pk + "]";
	}

}


