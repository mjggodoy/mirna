package mirna.mirbase;

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

import mirna.mirbase.bean.CommonMirna;
import mirna.mirbase.bean.DeadMirna;
import mirna.mirbase.bean.Mature;
import mirna.mirbase.bean.Mirna;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class RellenaMirna {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public RellenaMirna() throws IOException {
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
			
			String query = "select * from mirbase.busqueda";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int counter = 1;
			int limit = -1;
			
			
			while (rs.next() && limit!=0) {
				String name = rs.getString("name");
				String newName = rs.getString("new_name");
				
				boolean mirna = rs.getBoolean("mirna");
				boolean preMirna = rs.getBoolean("pre_mirna");
				boolean mature = rs.getBoolean("mature");
				boolean preMature = rs.getBoolean("pre_mature");
				boolean dead = rs.getBoolean("dead");
				boolean preDead = rs.getBoolean("pre_dead");
				
				boolean fromPlant = rs.getBoolean("from_plant");
				
				System.out.println(counter+" "+name+" "+newName+" "+mirna+" "+preMirna+
						" "+mature+" "+preMature+" "+dead+" "+preDead+" "+fromPlant);
				
				int oldPk = getOldPk(name); 
				
				boolean fromMirbase = false;
				
				if (mirna) {
					CommonMirna cm = getMirna(newName, false);
					if (cm==null) throw new Exception("No encuentro "+newName+ " en la tabla mirna de mirbase en los ids. principales");
//					System.out.println(cm);
					// inserta si no esta
					insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), false,
							false, cm.getMirbasePk(), oldPk, false);
					fromMirbase = true;
				}
				if (preMirna) {
					
					CommonMirna cm = getMirna(newName, true);
					if (cm==null) throw new Exception("No encuentro "+newName+ " en la tabla mirna de mirbase en los pre ids.");
//					System.out.println(cm);
					// inserta si no esta
					insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), false,
							false, cm.getMirbasePk(), oldPk, true);
					fromMirbase = true;
				}
				
				if (mature) {
					CommonMirna cm = getMature(newName);
					if (cm==null) throw new Exception("No encuentro "+newName+ " en la tabla mature de mirbase en los ids. principales");
//					System.out.println(cm);
					// inserta si no esta
					insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), true,
							false, cm.getMirbasePk(), oldPk, false);
					fromMirbase = true;
				}
				if (preMature) {
					List<Mature> matures = getMaturePre(newName);
					if (matures.size()==0) throw new Exception("No encuentro "+newName+ " en la tabla mature de mirbase en los pre ids.");
//					System.out.println(cm);
					// inserta si no esta
					for (CommonMirna cm : matures) {
						insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), true,
								false, cm.getMirbasePk(), oldPk, true);
					}
					fromMirbase = true;
				}
				
				if (dead) {
					List<DeadMirna> deads = getDead(newName, false);
//					System.out.println(cm);
					// inserta si no esta
					for (CommonMirna cm : deads) {
						insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), false,
								true, cm.getMirbasePk(), oldPk, false);
					}
					fromMirbase = true;
				}
				if (preDead) {
					List<DeadMirna> deads = getDead(newName, true);
//					System.out.println(cm);
					// inserta si no esta
					for (CommonMirna cm : deads) {
						insertIntoMirnaNew(cm.getId(), cm.getAcc(), cm.getPreviousId(), false,
								true, cm.getMirbasePk(), oldPk, true);
					}
					fromMirbase = true;
				}
				
				if (fromMirbase==false) {
					insertIntoMirnaNew(newName, null, null, false,
							false, null, oldPk, false);
				}
				
				counter++;
				limit--;
//				if (counter%100==0) System.out.println(counter);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	private String cleanField(String field) {
		if (field!=null) {
			field = field.trim();
			if (field.equals("")) field = null;
		}
		return field;
	}
	
	private Mirna getMirna(String id, boolean pre) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Mirna mirna = null;
		
		if (id!=null) {
			try {
				
				String query = "select * from mirbase.mirna ";
				if (pre) {
					query += "where previous_mirna_id='"+id+"' "
							+ "or previous_mirna_id like '"+id+";%' "
							+ "or previous_mirna_id like '%;"+id+"' "
							+ "or previous_mirna_id like '%;"+id+";%'";
				} else query += "where mirna_id='"+id+"'";
				
				stmt = con.prepareStatement(query);
				//stmt.setString(1, id);
				
				// execute the query, and get a java resultset
				rs = stmt.executeQuery();
				
				int counter = 0;
				
				while (rs.next()) {
					
					int mirbasePk = rs.getInt("auto_mirna");
					String acc = cleanField(rs.getString("mirna_acc"));
					String mirnaId = cleanField(rs.getString("mirna_id"));
					String previousMirnaId = cleanField(rs.getString("previous_mirna_id"));
					String description = cleanField(rs.getString("description"));
					String sequence = cleanField(rs.getString("sequence"));
					String comment = cleanField(rs.getString("comment"));
					
					mirna = new Mirna(acc, mirnaId, previousMirnaId, mirbasePk, description, sequence, comment);
					counter++;
				}
				
				if (counter==0) {
					mirna = getMirna(quitarSegundoGuion(id), pre);
				} else if (counter>1) throw new Exception(counter + " mirnas encontrados para id="+id);
					
			} catch (SQLException e) {
				throw e;
			} finally {
				if (rs!=null) rs.close();
				if (stmt!=null) stmt.close();
			}
		}
		
		return mirna;
		
	}
	
	private Mature getMature(String id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Mature mature = null;
		
		if (id!=null) {
			try {
				
				
				String query = "select * from mirbase.mirna_mature "
						+"where mature_name='"+id+"'";
				
				stmt = con.prepareStatement(query);
				//stmt.setString(1, id);
				
				// execute the query, and get a java resultset
				rs = stmt.executeQuery();
				
				int counter = 0;
				
				while (rs.next()) {
					
					int mirbasePk = rs.getInt("auto_mature");
					String acc = cleanField(rs.getString("mature_acc"));
					String mirnaId = cleanField(rs.getString("mature_name"));
					String previousMirnaId = cleanField(rs.getString("previous_mature_id"));
					String evidence = cleanField(rs.getString("evidence"));
					String experiment = cleanField(rs.getString("experiment"));
					String similarity = cleanField(rs.getString("similarity"));
					
					if (mature!=null) {
						if (!mature.getAcc().equals(acc)) counter++;
					} else {					
						mature = new Mature(acc, mirnaId, previousMirnaId, mirbasePk, evidence, experiment, similarity);
						counter++;
					}
				}
				
				if (counter==0) {
					mature = getMature(quitarSegundoGuion(id));
				} else if (counter>1) {
					String exMsg = counter + " matures encontrados para id="+id+"\n"+query;
					throw new Exception(exMsg);
				}
					
			} catch (SQLException e) {
				throw e;
			} finally {
				if (rs!=null) rs.close();
				if (stmt!=null) stmt.close();
			}
		}
		
		return mature;
		
	}
	
	private List<Mature> getMaturePre(String id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Mature> matures = new ArrayList<Mature>();
		
		if (id!=null) {
			try {
				
				
				String query = "select * from mirbase.mirna_mature "
						+"where previous_mature_id='"+id+"' "
						+ "or previous_mature_id like '"+id+";%' "
						+ "or previous_mature_id like '%;"+id+"'"
						+ "or previous_mature_id like '%;"+id+";%'";
				
				stmt = con.prepareStatement(query);
				//stmt.setString(1, id);
				
				// execute the query, and get a java resultset
				rs = stmt.executeQuery();
				
				//int counter = 0;
				
				while (rs.next()) {
					
					int mirbasePk = rs.getInt("auto_mature");
					String acc = cleanField(rs.getString("mature_acc"));
					String mirnaId = cleanField(rs.getString("mature_name"));
					String previousMirnaId = cleanField(rs.getString("previous_mature_id"));
					String evidence = cleanField(rs.getString("evidence"));
					String experiment = cleanField(rs.getString("experiment"));
					String similarity = cleanField(rs.getString("similarity"));
					
					matures.add(new Mature(acc, mirnaId, previousMirnaId, mirbasePk, evidence, experiment, similarity));
				}
				
				if (matures.size()==0) {
					matures = getMaturePre(quitarSegundoGuion(id));
				}
					
			} catch (SQLException e) {
				throw e;
			} finally {
				if (rs!=null) rs.close();
				if (stmt!=null) stmt.close();
			}
		}
		return matures;
	}
	
	private List<DeadMirna> getDead(String id, boolean pre) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<DeadMirna> mirnas = new ArrayList<DeadMirna>();
		
		try {
			
			String query = "select * from mirbase.dead_mirna ";
			if (pre) query += "where previous_id=?";
			else query += "where mirna_id=?";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				int mirbasePk = 0;
				String acc = cleanField(rs.getString("mirna_acc"));
				String mirnaId = cleanField(rs.getString("mirna_id"));
				String previousMirnaId = cleanField(rs.getString("previous_id"));
				String forwardTo = cleanField(rs.getString("forward_to"));
				String comment = cleanField(rs.getString("comment"));
				
				mirnas.add(new DeadMirna(acc, mirnaId, previousMirnaId, mirbasePk, forwardTo, comment));
			}
			
			if (mirnas.size()==0) {
				mirnas = getDead(quitarSegundoGuion(id), pre);
//			} else if (counter>1) {
//				String exMsg = counter + " mirnas encontrados para id="+id;
//				if (pre) exMsg += " buscando en los preIds.";
//				throw new Exception(exMsg);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return mirnas;
		
	}
	
	private int getOldPk(String name) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int res = -1;
		
		try {
			
			String query = "select pk from mirna.mirna where name=?";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			int counter = 0;
			
			while (rs.next()) {
				res = rs.getInt("pk");
				counter++;
			}
			
			if (counter!=1) throw new Exception(counter + " mirnas encontrados en tabla antigua para name="+name);
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		return res;
	}
	
	private int estaYaMetido(String id, String acc, String preId, boolean mature,
			boolean dead, Integer mirbasePk) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int res = -1;
		
		try {
			
			// si no está metido
			
			String query = "select * from mirna_raw.mirna_new where id=? and accession_number=? "
					+ "and mature=? and dead=?";
//			if (preId==null) query += " and previous_id is null";
//			else query += " and previous_id=?";
			if (mirbasePk==null)  query += " and mirbase_pk=?";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setString(2, acc);
			stmt.setBoolean(3, mature);
			stmt.setBoolean(4, dead);
			if (mirbasePk==null) stmt.setNull(5, java.sql.Types.INTEGER);
			//else stmt.setInt(5, mirbasePk);
			
//			if (preId!=stmt.setString(3, preId);
			
//			System.out.println(stmt);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				res = rs.getInt("pk");
			}
			
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return res;
		
	}
	
	private void insertIntoMirnaNew(String id, String acc, String preId, boolean mature,
			boolean dead, Integer mirbasePk, int oldPk, boolean pre) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			
			int pk = estaYaMetido(id, acc, preId, mature, dead, mirbasePk);
			
			if (pk==-1) {
			
				String query = "insert into mirna_raw.mirna_new (id, accession_number,"
						+ "previous_id, mature, dead, mirbase_pk, old_pks, pre_old_pks) "
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
//				if (pre) query += "pre_old_pks";
//				else query += "old_pks";
//				query += ") values(?, ?, ?, ?, ?, ?, ?)";
				
				stmt = con.prepareStatement(query);
				stmt.setString(1, id);
				stmt.setString(2, acc);
				stmt.setString(3, preId);
				stmt.setBoolean(4, mature);
				stmt.setBoolean(5, dead);
				if (mirbasePk==null) stmt.setNull(6, java.sql.Types.INTEGER);
				else stmt.setInt(6, mirbasePk);
				stmt.setString(7, oldPk+",");
				
				if (pre) {
					stmt.setString(8, oldPk+",");
					stmt.setString(7, "");
				} else {
					stmt.setString(8, "");
					stmt.setString(7, oldPk+",");
				}
				
				System.out.println(stmt);
				
				// execute the query
				stmt.execute();
			
			} else {
				
				String columnName = "old_pks";
				if (pre) columnName = "pre_" +columnName;
				
				String query = "update mirna_raw.mirna_new set "
						+columnName+"= CONCAT("+columnName+", ?, ?) WHERE pk=?";
				stmt = con.prepareStatement(query);
				stmt.setString(2, ",");
				stmt.setString(1, String.valueOf(oldPk));
				stmt.setInt(3, pk);
				
				System.out.println(stmt);
				
				// execute the query, and get a java resultset
				stmt.execute();
				
			}
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.err.println("Se intentó meter a: id="+id+", acc="+acc+", preId="+preId+", mature="+mature+", dead="+dead+", mirbasePk="+mirbasePk);
			throw e;	
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
	}
	
//	private void updateMirnaNew(int id, String oldPks, boolean pre) throws SQLException {
//		PreparedStatement stmt = null;
//		
//		try {
//			
//			
//			String query = "update mirna_raw.mirna_new set ";
//			if (pre) query += "preOldPks=?";
//			else query += "oldPks=?";
//			query += " where pk=?";
//			
//			stmt = con.prepareStatement(query);
//			stmt.setString(1, oldPks);
//			stmt.setInt(2, id);
//			
//			System.out.println(stmt);
//			
//			// execute the query, and get a java resultset
//			stmt.execute();
//			
//		} catch (SQLException e) {
//			throw e;
//		} finally {
//			if (stmt!=null) stmt.close();
//		}
//		
//	}
	
	private String quitarSegundoGuion(String word) {
		int idx = StringUtils.ordinalIndexOf(word, "-", 2);
		String newWord = null;
		if (idx!=-1) {
			newWord = word.substring(0, idx) + word.substring(idx+1);
		}
		if (word.equals(newWord)) newWord = null;
		return newWord;
	}
	
	public static void main(String[] args) throws Exception {
		RellenaMirna x = new RellenaMirna();
		x.execute();
	}

}
