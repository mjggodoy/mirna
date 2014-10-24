package mirna.dao.mysql;

import org.apache.commons.lang.StringUtils;

public abstract class ModelDAOMySQLImpl {
	
	public String safe(String term) {
		return StringUtils.replace(term, "'", "\\'");
	}
	
}
