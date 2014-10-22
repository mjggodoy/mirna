package mirna.database;

public interface IMirnaDatabaseLegacy {
	
	/**
	 * Método que inserta todos los datos en una tabla de una BD MySQL
	 * @param tableName Nombre de la tabla
	 * @throws Exception
	 */
	public void insertInTable(String tableName) throws Exception;
	
	/**
	 * Método que inserta un número determinado de datos en una tabla de una BD MySQL
	 * @param tableName Nombre de la tabla
	 * @param maxLines Número máximo de líneas a procesar (sin incluir la cabecera)
	 * @throws Exception
	 */
	public void insertInTable(String tableName, Integer maxLines) throws Exception;
	
	public void insertIntoSQLModel(String originTable) throws Exception;
	
	public void insertIntoSQLModel(String originTable, Integer maxLines) throws Exception;
	
}
