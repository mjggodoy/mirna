package mirna.integration.main;

import mirna.integration.database.HMDD;
import mirna.integration.database.MiRCancer;
import mirna.integration.database.Mir2Disease;
import mirna.integration.database.Phenomir;
import mirna.integration.utils.HibernateUtil;

public class SQLIntegration {
	
	public static void main(String[] args) throws Exception {
		
		MiRCancer miRCancer = new MiRCancer();
		miRCancer.insertIntoSQLModel();
		
		HMDD hmdd = new HMDD();
		hmdd.insertIntoSQLModel();
		
		Mir2Disease mir2disease = new Mir2Disease();
		mir2disease.insertIntoSQLModel();
		
		Phenomir phenomir = new Phenomir();
		phenomir.insertIntoSQLModel();
		
		HibernateUtil.closeSessionFactory();;
		
	}

}
