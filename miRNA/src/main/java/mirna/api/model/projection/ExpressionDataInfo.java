package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Disease;
import mirna.api.model.ExpressionData;

@Projection(name = "expression_data_info", types = { ExpressionData.class })
public interface ExpressionDataInfo {
	
	public String getTitleReference();

	public String getFoldchangeMin();

	public String getFoldchangeMax();

	public String getProvenanceId();

	public String getProvenance();

	public String getStudyDesign();

	public String getMethod();

	public String getTreatment();

	public String getEvidence();

	public String getYear();

	public String getDescription();

	public String getCellularLine();

	public String getCondition();

	//public Integer getMirnaPk();

	public Integer getEnvironmentalFactorPk();

	public String getDataType();

	public String getDifferentExpressionLocation();

	//public Integer getInteractionDataPk();
	
	//public Set<MiRna> getMirnas();
	
	public Disease getDisease();
	
	public InteractionDataBasicInfo getInteractionData();

}
