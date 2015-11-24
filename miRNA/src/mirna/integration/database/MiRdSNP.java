package mirna.integration.database;

import mirna.integration.exception.MiRnaException;


/**
 * Código para procesar los datos de miRdSNP
 * 
 * @author Esteban López Camacho
 *
 */
public abstract class MiRdSNP extends NewMirnaDatabase {

	protected MiRdSNP(String tableName) throws MiRnaException { super(tableName); }

	protected String quitarComillas(String token) {
		if (token.startsWith("\"") && token.endsWith("\"")) {
			token = token.substring(1, token.length()-1);
		}
		return token;
	}

	public static void main(String[] args) throws Exception {

		//(new MiRdSNP1()).insertIntoSQLModel();
		//(new MiRdSNP2()).insertIntoSQLModel();
		//(new MiRdSNP3()).insertIntoSQLModel();
		(new MiRdSNP4()).insertIntoSQLModel();
		//(new MiRdSNP5()).insertIntoSQLModel();

	}

}
