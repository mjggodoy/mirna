package mirna.mirbase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class MirbaseReport {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public MirbaseReport() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}
	
	public void execute() throws Exception {
		
		Statement stmt = null;
		
		PrintWriter pw = null;
		
		try {
			
			pw = new PrintWriter(new File("mirbase.txt"));
			
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
//			String name= "cel-mir-257";
//			MirbaseResult res3 = inDeadTable(name);
//			System.out.println(res3);
			
			stmt = (Statement) con.createStatement();
			
			String query = "select pk, name from mirna.mirna";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			//System.out.println("EMPIEZO EN LA PRIMERA LINEA");
			int counter = 0;
			while (rs.next()) {
				String name = rs.getString("name");
				String formattedName = format(name);
				Set<String> organisms = new HashSet<String>();
				if (formattedName.startsWith("mir")) {
					organisms = getOrganisms(name);
				}
				formattedName = cambioAPelo(formattedName);
				if (organisms.isEmpty()) {
					MirbaseCombinedResult res = busca(formattedName);

					// Segundo intento quitando el gga del principio
					if (res.res()==false) {
						if ( (formattedName.startsWith("gga-mdv1-"))
								|| (formattedName.startsWith("gga-ghr-")) ) {
							String newFormattedName = formattedName.substring(4);
							MirbaseCombinedResult newRes = busca(newFormattedName);
							if (newRes.res()) {
								formattedName = newFormattedName;
								res = newRes;
							}
						}
					}
					
					// Tercer intento quitando el gra del principio
					if (res.res()==false) {
						if (formattedName.startsWith("gra-ghr-")) {
							String newFormattedName = formattedName.substring(4);
							MirbaseCombinedResult newRes = busca(newFormattedName);
							if (newRes.res()) {
								formattedName = newFormattedName;
								res = newRes;
							}
						}
					}
					
					pw.println(name+"\t"+formattedName+"\t"+res+"\tfalse");
				} else {
					for (String organism : organisms) {
						String newFormattedName = organism+"-"+formattedName;
						MirbaseCombinedResult res = busca(newFormattedName);
						pw.println(name+"\t"+newFormattedName+"\t"+res+"\ttrue");
					}
				}
				counter++;
				if (counter%100==0) System.out.println(counter);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pw!=null) pw.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	private String cambioAPelo(String in) {
		if ("hsa-HSV1-miR-LAT".toLowerCase().equals(in.toLowerCase())) return "HSV1-miR-LAT";
		else if ("hsa-LNA_let-7b".toLowerCase().equals(in.toLowerCase())) return "LNA_let-7b";
		else if ("hsa-miR-10".toLowerCase().equals(in.toLowerCase())) return "hsa-miR-10-5p";
		else if ("hsa-miR-181a-1*".toLowerCase().equals(in.toLowerCase())) return "hsa-miR-181a-1";
		else if ("hsa-mir-9-*".toLowerCase().equals(in.toLowerCase())) return "hsa-mir-9*";
		else if ("mghv-miR-m1-9*".toLowerCase().equals(in.toLowerCase())) return "mghv-miR-m1-9";
		
		else return in;
	}
	
	
	
	
	private MirbaseCombinedResult busca(String formattedName) throws SQLException {
		MirbaseCombinedResult res = new MirbaseCombinedResult();
		res.mirna = inMirnaTable(formattedName);
		res.mature = inMatureTable(formattedName);
		res.dead = inDeadTable(formattedName);
		if (res.res()==false) {
			String newFormattedName = quitarSegundoGuion(formattedName);
			if (newFormattedName!=null) {
				formattedName = newFormattedName;
				res.mirna = inMirnaTable(formattedName);
				res.mature = inMatureTable(formattedName);
				res.dead = inDeadTable(formattedName);
			}
		}
		return res;
	}
	
	private MirbaseResult inMirnaTable(String name) throws SQLException {
		
		String query = "select * from mirbase.mirna where mirna_id=? or previous_mirna_id like ?";
		
		PreparedStatement stmt = null;
		MirbaseResult res = new MirbaseResult();
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, "%"+name+"%");
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				String mirnaId = rs.getString("mirna_id").trim();
				if (name.equals(mirnaId.toLowerCase())) res.id = true;
				
				String previousMirnaId = rs.getString("previous_mirna_id");
				String[] tokens = StringUtils.splitPreserveAllTokens(previousMirnaId, ";");

				for (String token : tokens) {
					if (name.equals(token.toLowerCase().trim())) res.preId = true;
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		return res;
	}
	
	private MirbaseResult inMatureTable(String name) throws SQLException {
		String query = "select * from mirbase.mirna_mature where mature_name=? or previous_mature_id like ?";
		
		PreparedStatement stmt = null;
		MirbaseResult res = new MirbaseResult();
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, "%"+name+"%");
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				String matureName = rs.getString("mature_name").trim();
				if (name.equals(matureName.toLowerCase())) res.id = true;
				
				String previous_mature_id = rs.getString("previous_mature_id");
				String[] tokens = StringUtils.splitPreserveAllTokens(previous_mature_id, ";");

				for (String token : tokens) {
					if (name.equals(token.toLowerCase().trim())) res.preId = true;
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		return res;
	}
	
	private MirbaseResult inDeadTable(String name) throws SQLException {
		String query = "select * from mirbase.dead_mirna where mirna_id=? or previous_id like ?";
		
		PreparedStatement stmt = null;
		MirbaseResult res = new MirbaseResult();
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, "%"+name+"%");
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				String mirnaId = rs.getString("mirna_id").trim();
				if (name.equals(mirnaId.toLowerCase())) res.id = true;
				
				String previousId = rs.getString("previous_id");
				String[] tokens = StringUtils.splitPreserveAllTokens(previousId, " ");

				for (String token : tokens) {
					if (name.equals(token.toLowerCase().trim())) res.preId = true;
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		return res;
	}
	
	private String format(String name) {
		if (name.endsWith("(18)")) name=name.substring(0,name.length()-4);
		if (name.endsWith("_star")) name=name.substring(0,name.length()-5)+"*";
		if (name.endsWith("star")) name=name.substring(0,name.length()-4)+"*";
		name = name.toLowerCase();
		return name;
	}
	
	private class MirbaseResult {
		public boolean id = false;
		public boolean preId = false;
		public String toString() {
			return id+"\t"+preId;
		}
		public boolean res() {
			return id || preId;
		}
	}
	
	private class MirbaseCombinedResult {
		public MirbaseResult mirna;
		public MirbaseResult mature;
		public MirbaseResult dead;
		public String toString() {
			return mirna+"\t"+mature+"\t"+dead;
		}
		public boolean res() {
			return mirna.res() || mature.res() || dead.res();
		}
	}
	
	private String quitarSegundoGuion(String word) {
		int idx = StringUtils.ordinalIndexOf(word, "-", 2);
		String newWord = null;
		if (idx!=-1) {
			newWord = word.substring(0, idx) + word.substring(idx+1);
		}
		return newWord;
	}
	
	@SuppressWarnings("unused")
	private List<String> getOrganism(int pk, String name) throws Exception {
		
		String query = "select a.* from mirna.organism a, mirna.mirna_has_organism b "
				+ "where b.mirna_pk=? and b.organism_pk=a.pk";
		List<String> organisms = new ArrayList<String>();
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, pk);
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				organisms.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		
		return organisms;
	}
	
	private Set<String> getOrganisms(String name) throws SQLException {
		Set<String> organisms = new HashSet<String>();
		organisms.addAll(getOrganisms("plant_mirna_mature_mirna", name));
		organisms.addAll(getOrganisms("plant_mirna_stem_loop", name));
		return organisms;
	}
	
	private Set<String> getOrganisms(String table, String name) throws SQLException {
		
		String query = "select specie from mirna_raw."+table
				+ " where mirna_id=?";
		Set<String> organisms = new HashSet<String>();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				organisms.add(rs.getString("specie"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		
		return organisms;
	}
	
	public static void main(String[] args) throws Exception {
		MirbaseReport mr = new MirbaseReport();
		mr.execute();
//		String palabra = "bna-mir156a";
//		System.out.println(StringUtils.ordinalIndexOf(palabra, "-", 2));
//		int idx = StringUtils.ordinalIndexOf(palabra, "-", 2);
//		String newPalabra = palabra.substring(0, idx) + palabra.substring(idx+1);
//		System.out.println(newPalabra);
	}

}
