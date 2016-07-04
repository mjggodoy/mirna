package mirna.pubmed;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Esteban on 30/06/2016.
 */
public class ExtractPubmedDocumentInfo {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private String apiCall = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi" +
			"?db=pubmed" +
			"&retmode=json" +
			"&tool=imirna" +
			"&email=esteban@lcc.uma.es" +
			"&id=";

	private String fileName = "C:/temp/pubmed_info.txt";

	private Connection con = null;

	public ExtractPubmedDocumentInfo() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void execute() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		PrintWriter pw = null;

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = (Statement) con.createStatement();

			String query = "select * from mirna.pubmed_document";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;
			int counter = 0;
			int startPoint = 0;

			pw = new PrintWriter(new FileOutputStream(
					new File(fileName),
					true /* append = true */));

			while (rs.next() && limit!=0) {

				int id = rs.getInt("id");

				if (counter>=startPoint) {
					PubmedDocInfo pdi = getPubmedDocInfo(id);
					pw.println(counter + "\t" + pdi);
				}

				limit--;
				counter++;
				if (counter % 100 == 0) System.out.println(counter);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
			if (pw!=null) pw.close();
		}
	}

	public PubmedDocInfo getPubmedDocInfo(int id) throws Exception {

		String sURL = apiCall+id;
		PubmedDocInfo pdi = new PubmedDocInfo();

		try {
			JSONObject json = new JSONObject(IOUtils.toString(new URL(sURL), Charset.forName("UTF-8")));
			JSONObject jsonInfo = json.getJSONObject("result").getJSONObject(String.valueOf(id));

			pdi.id = String.valueOf(id);
			pdi.title = jsonInfo.getString("title");
			pdi.authors = "";

			JSONArray authorsJson = jsonInfo.getJSONArray("authors");
			for (int i=0; i<authorsJson.length(); i++) {
				JSONObject authorJson = authorsJson.getJSONObject(i);
				if (!pdi.authors.isEmpty()) pdi.authors +=", ";
				pdi.authors += authorJson.getString("name");
			}

		} catch (Exception e) {
			System.err.println("Exception en pubmed id="+id);
			pdi.id = String.valueOf(id);
			pdi.title = "ERROR";
			pdi.authors = "";
		}
		return pdi;
	}

	public void readInfoFromFile() throws IOException, SQLException {

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			int maxTitleSize = 0;
			int maxAuthorsSize = 0;

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line=br.readLine())!=null) {

				String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String counter = tokens[0];
				int id = Integer.valueOf(tokens[1]);
				String title = tokens[2];
				String authors = tokens[3];

				if (maxTitleSize < title.length()) maxTitleSize = title.length();
				if (maxAuthorsSize < authors.length()) maxAuthorsSize = authors.length();

				System.out.println(counter);

				if (!"ERROR".equals(title)) updatePubmedDocumentInfo(title, authors, id);

			}

			System.out.println("MAX TITLE SIZE = "+maxTitleSize);
			System.out.println("MAX AUTHORS SIZE = "+maxAuthorsSize);

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}

	}

	private void updatePubmedDocumentInfo(String title, String authors, int id) throws SQLException {
		String query = "update mirna.pubmed_document set title=?, authors=? where id=?";
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, title);
			stmt.setString(2, authors);
			stmt.setInt(3, id);
			stmt.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
	}

	private class PubmedDocInfo {
		public String id;
		public String title;
		public String authors;
		public String toString() {
			return id+"\t"+title+"\t"+authors;
		}
	}

	public static void main(String[] args) throws Exception {
		ExtractPubmedDocumentInfo x = new ExtractPubmedDocumentInfo();
		//x.execute();
		//System.out.println(x.getPubmedDocInfo(10233956));
		x.readInfoFromFile();
	}

}
