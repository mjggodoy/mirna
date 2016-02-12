package mirna.mirbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BetterPkTranslation {
	
	private class NewMirna {
		private int pk;
		private String id;
		private String prevId;
		private boolean dead;
		public NewMirna(int pk, String id, String prevId, boolean dead) {
			this.pk = pk;
			this.id = id;
			this.prevId = prevId;
			this.dead = dead;
		}
		public int getPk() {
			return pk;
		}
		public String getId() {
			return id;
		}
		public String getPrevId() {
			return prevId;
		}
		public boolean getDead() {
			return dead;
		}
	}
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	private List<String> exceptions = Arrays.asList("hsa-mir-550", "hsa-mir-453", "kshv-miR-K12-5", "hsa-miR-378", "hsa-miR-500", "hsv2-miR-H10", "kshv-miR-K12-8", "kshv-miR-K12-3");
	
	public BetterPkTranslation() throws IOException {
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
			
			stmt = (Statement) con.createStatement();
			
			String query = "select a.old_pk, b.name, count(*) as counter from mirna.mirna_pk_translation a, mirna.mirna b where a.old_pk=b.pk group by old_pk HAVING counter > 1";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				int oldPk = rs.getInt("old_pk");
				String oldName = rs.getString("name");
				
				if (!exceptions.contains(oldName)) {
				
					String fixedOldName = fix(oldName);
					
					List<NewMirna> newNames = getRelatedNewMirnas(oldPk);
	//				System.out.println(oldPk+" -> "+newPks);
					
					int encontrado = -1;
					boolean buscaEnDead = false;
					
					/*
					 * BUSQUEDA NORMAL: Busco en los ids, formateando un poco el nombre antiguo y teniendo en cuenta las mayusculas y minusculas
					 */
					List<String> searchList = new ArrayList<String>();
					for (NewMirna nm : newNames) {
						String newName = nm.getId();
						searchList.add(newName);
						if (newName.equals(fixedOldName)) {
							if (encontrado!=-1) {
								buscaEnDead = true;
	//							throw new Exception("Encontrados más de uno para "+fixedOldName +" ("+oldName+") en "+searchList);
							} else {
								encontrado = nm.getPk();
							}
						}
					}
					
					/*
					 * BUSCO DESCARTANDO DEADs: Si en los ids encuentro más de uno, descarto los deads.
					 */
					if (buscaEnDead) {
						encontrado = -1;
						for (NewMirna nm : newNames) {
							String newName = nm.getId();
							if (newName.equals(fixedOldName)) {
								boolean dead = nm.getDead();
								if (dead==false) {
									if (encontrado!=-1) {
										throw new Exception("Encontrados más de uno (incluso descartando muertos) para "+fixedOldName +" ("+oldName+") en "+searchList);
									} else {
										encontrado = nm.getPk();
									}
								}
							}
						}
					}
					
					/*
					 * SI NO HE ENCONTRADO NADA, BUSCO EN LOS PRE_IDs
					 */
					String exMsg = "No encontrado "+fixedOldName +" ("+oldName+") en "+searchList;
					searchList = new ArrayList<String>();
					if (encontrado==-1) {
						for (NewMirna nm : newNames) {
							String newName = nm.getPrevId();
							searchList.add(newName);
							if (newName!=null) {
								if ((fixedOldName.equals(newName)) ||
										(newName.endsWith(";"+fixedOldName)) ||
										(newName.startsWith(fixedOldName+";"))) {
									if (encontrado!=-1) {
										throw new Exception(exMsg+"\nPero encontrados más de uno para "+fixedOldName +" ("+oldName+") en "+searchList);
									} else {
										encontrado = nm.getPk();
									}
								}
							}
						}
					}
					
					/*
					 * SI SE DA ESTE CASO, NO LO HE ENCONTRADO EN NINGUN SITIO.
					 */
					if (encontrado==-1) {
						exMsg += "\nTampoco encontrado "+fixedOldName +" ("+oldName+") en "+searchList;
						throw new Exception(exMsg);
					}
				
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
		}
	}
	
	private List<NewMirna> getRelatedNewMirnas(int oldPk) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<NewMirna> res = new ArrayList<NewMirna>(); 
		
		try {
			
			String query = "select a.name, b.old_pk, b.new_pk, c.id, c.previous_id, c.dead "
					+ "from mirna.mirna a, mirna.mirna_pk_translation b, mirna.mirna2 c "
					+ "where a.pk=b.old_pk and b.new_pk=c.pk and old_pk=?";
			//System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			stmt = con.prepareStatement(query);
			stmt.setInt(1, oldPk);
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int newPk = rs.getInt("new_pk");
				String id = rs.getString("id");
				String prevId = rs.getString("previous_id");
				boolean dead = rs.getBoolean("dead");
				res.add(new NewMirna(newPk, id, prevId, dead));
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		return res;
		
	}
	
	private String fix(String name) {
		String res = name;
		if (name.endsWith("(18)")) {
			res = name.substring(0, name.length()-4);
		}
		if (name.endsWith("star")) {
			res = name.substring(0, name.length()-4);
		}
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		BetterPkTranslation x = new BetterPkTranslation();
		x.execute();
	}

}
