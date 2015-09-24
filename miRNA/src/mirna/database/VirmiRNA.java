package mirna.database;

import mirna.exception.MiRnaException;

public abstract class VirmiRNA extends MirnaDatabase {

	public VirmiRNA() throws MiRnaException { super(); }

	public static void main(String[] args) throws Exception {

		(new VirmiRNA1()).insertIntoSQLModel();
		(new VirmiRNA2()).insertIntoSQLModel();
		(new VirmiRNA3()).insertIntoSQLModel();



	}


}
