package mirna.integration.database;

public interface IMirnaDatabase {
	
	/**
	 * Método que inserta todos los datos en una tabla de una BD MySQL
	 * @param tableName Nombre de la tabla
	 * @throws Exception
	 */
	public void insertInTable(String inputFile) throws Exception;
	
	public void insertIntoSQLModel() throws Exception;
	
}
