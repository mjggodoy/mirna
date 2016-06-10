package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Esteban on 23/05/2016.
 */
public class InsertMirnaHairpinSequencefromOldmirna {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertMirnaHairpinSequencefromOldmirna() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void execute() throws Exception {

		
		Statement stmt = null;
		ResultSet rs = null;


		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 a where a.mirbase_pk is null;";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);


			int limit = -1;

			while (rs.next() && limit!=0) {
				

				int pk = rs.getInt("pk"); // pk del mirna2.
				List<String> list = getSequence(pk);
				//System.out.println(list.get(0) + list.size());
				
				if(list.size() == 1){
					
					System.out.println("It's ok");
					
				} else if (list.size() == 0) {
					
					//System.out.println("Nothing!");
					
				}else{
					
					System.err.println("Something is wrong");
					
					for (String seq: list) {
						System.out.println(seq);
					}
					
					throw new Exception(list.size()+" secuencias para pk="+pk);
					
			}
							
				
				
				limit--;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	
	private boolean comprobacion(String sequence) throws Exception{
		
		boolean success = false;

		
		Statement stmt = null;
		ResultSet rs = null;
		
		con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

		stmt = con.createStatement();
		
		String query = "select * from mirna.sequence_hairpin;";
		//System.out.println("STARTING: " + query);
		
		// execute the query, and get a java resultset
		rs = stmt.executeQuery(query);

		int limit = -1;

		try {
		while (rs.next() && limit!=0) {
			
			
			String sequence2 = rs.getString("sequence");


			if(sequence.equals(sequence2)){
				
				success=true;
				
			}else{
				
				success=false;
			}
			
		}
		
		return success;

		
		
		}catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
				
	}
	

	private List<String> getSequence(int pk) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		String sequence = null;
		
		List<String> res = new ArrayList<String>();


		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna_pk_translation b, mirna.mirna_has_sequence c, mirna.sequence d "
					+ "where "+ pk +"= b.new_pk and b.old_pk = c.mirna_pk and c.sequence_pk = d.pk";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {

				sequence = rs.getString("sequence");
				counter++;
				res.add(sequence);

			}
			
			

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		return res;


	}

	private void inserta(String sequence, int mirnaPk) throws SQLException {

		String query = "insert into mirna.sequence_hairpin (sequence, mirna_pk) "
				+ "values(?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, sequence);
			stmt.setInt(2, mirnaPk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}

	public static void main(String[] args) throws Exception {
		InsertMirnaHairpinSequencefromOldmirna x = new InsertMirnaHairpinSequencefromOldmirna();
		x.execute();
	}


}
