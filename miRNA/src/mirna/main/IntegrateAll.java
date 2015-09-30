package mirna.main;

import mirna.database.*;
import mirna.exception.MiRnaException;

public class IntegrateAll {
	
	public static void main(String[] args) throws MiRnaException, Exception {
		(new HMDD()).insertIntoSQLModel();
		(new Microcosm()).insertIntoSQLModel();
		(new MicroTCdsData()).insertIntoSQLModel();
		(new MicroTV4()).insertIntoSQLModel();
		(new Mir2Disease()).insertIntoSQLModel();
		(new MiRCancer()).insertIntoSQLModel();
		(new MiREnvironment()).insertIntoSQLModel();
		(new SM2miR2N()).insertIntoSQLModel();
		(new MiRDB()).insertIntoSQLModel();
		(new PlantMirnaMatureMirna()).insertIntoSQLModel();
		(new PlantMirnaStemLoop()).insertIntoSQLModel();
		(new Phenomir()).insertIntoSQLModel();
		(new RepTar("repTar_Human")).insertIntoSQLModel();
		(new RepTar("repTar_Mouse")).insertIntoSQLModel();
		(new TarBase()).insertIntoSQLModel();
		


	}

}
