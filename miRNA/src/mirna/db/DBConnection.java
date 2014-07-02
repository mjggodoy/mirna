package mirna.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import mirna.exception.MiRnaException;

public interface DBConnection {
	
	public void closeDBConnection() throws MiRnaException;
	
	//public void closeStatement(PreparedStatement stmt) throws MiRnaException;
	
	public int insert(String expression) throws SQLException;
	
	public void update(String expression) throws SQLException;
	
	public List<Map<String, Object>> query(String expression) throws SQLException;
	
	//public void shutdown() throws SQLException;

}
