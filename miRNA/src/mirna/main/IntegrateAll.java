package mirna.main;

import mirna.database.*;
import mirna.exception.MiRnaException;

public class IntegrateAll {
	
	public static void main(String[] args) throws MiRnaException, Exception {
		(new HMDD()).insertIntoSQLModel();
	}

}
