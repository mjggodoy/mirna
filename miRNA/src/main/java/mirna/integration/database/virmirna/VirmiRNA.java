package mirna.integration.database.virmirna;

import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;

public abstract class VirmiRNA extends NewMirnaDatabase {

	protected VirmiRNA(String tableName) throws MiRnaException { 
		super(tableName); 
	}

	public static void main(String[] args) throws Exception {

		//(new VirmiRNA1()).insertIntoSQLModel();
		//(new VirmiRNA2()).insertIntoSQLModel();
		//(new VirmiRNA3()).insertIntoSQLModel();

	}
}
