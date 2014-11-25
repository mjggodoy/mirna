package mirna.database;

import java.io.FileNotFoundException;
import java.util.Properties;

import mirna.exception.MiRnaException;

public abstract class MirnaDatabase implements IMirnaDatabase {
	
	protected String dbUrl;
	protected String dbUser;
	protected String dbPassword;
	
	public MirnaDatabase() throws MiRnaException {
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
			dbUrl = props.getProperty("url");
			dbUser = props.getProperty("user");
			dbPassword = props.getProperty("password");
		} catch (FileNotFoundException e) {
			throw new MiRnaException("FileNotFoundException:" + e.getMessage() + " " + e.toString());
		} catch (java.io.IOException e) {
			throw new MiRnaException("java.io.IOException:" + e.getMessage() + " " + e.toString());
		}
	}

}
