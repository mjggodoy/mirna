package mirna.database;

public interface IMirnaDatabase {
	
	/**
	 * Método que inserta todos los datos en una tabla de una BD MySQL
	 * @param tableName Nombre de la tabla
	 * @throws Exception
	 */
	public void insertInTable(String inputFile) throws Exception;
	
	/**
	 * Método que inserta un número determinado de datos en una tabla de una BD MySQL
	 * @param tableName Nombre de la tabla
	 * @param maxLines Número máximo de líneas a procesar (sin incluir la cabecera)
	 * @throws Exception
	 */
	public void insertInTable(String inputFile, Integer maxLines) throws Exception;
	
	public void insertIntoSQLModel() throws Exception;
	
}
