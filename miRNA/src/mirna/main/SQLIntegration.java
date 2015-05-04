package mirna.main;

import mirna.database.HMDD;
import mirna.database.MiRCancer;
import mirna.database.Mir2Disease;
import mirna.database.Phenomir;
import mirna.utils.HibernateUtil;

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
