package mirna.ssh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
 
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
 
public class CTestDriver {
	
	public static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException {
		
		final JSch jsch = new JSch();
		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		session.setPassword( strSshPassword );
		
		final Properties config = new Properties();
		config.put( "StrictHostKeyChecking", "no" );
		session.setConfig( config );
		
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	}
	
	public static void main(String[] args) {
		try {
			String strSshUser = "root";              // SSH loging username
			String strSshPassword = "lnkdkhaos";     // SSH login password
			String strSshHost = "150.214.214.5";     // hostname or ip or SSH server
			int nSshPort = 22;                       // remote SSH host port number
			String strRemoteHost = "192.168.44.23";  // hostname or ip of your database server
			int nLocalPort = 3366;                   // local port number use to bind SSH tunnel
			int nRemotePort = 3306;                  // remote port number of your database
			String strDbUser = "mirna";              // database loging username
			String strDbPassword = "mirna";          // database login password
			
			CTestDriver.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);
			con.close();
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}