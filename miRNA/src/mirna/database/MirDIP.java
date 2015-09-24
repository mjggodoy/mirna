package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class mirDIP extends MirnaDatabase {
	
	private final String tableName = "mirDIP";
	
	public mirDIP() throws MiRnaException {
		super();
	}
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					String microrna = tokens[0].replaceAll("'", "\\\\'");
					String gene_symbol = tokens[1].replaceAll("'", "\\\\'");
					String source = tokens[2].replaceAll("'", "\\\\'");
					String rank = tokens[3].replaceAll("'", "\\\\'");

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ microrna + "','"
							+ gene_symbol + "','"
							+ source + "','"
							+ rank + "')";
					
					stmt.executeUpdate(query);
	
				}
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	@Override
	public void insertIntoSQLModel()
			throws Exception {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

		Connection con = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_READ_ONLY); 
			stmt.setFetchSize(Integer.MIN_VALUE); 
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			
			// CAMBIAR ESTO:
			rs.next();
			
			
			String microrna = rs.getString("microrna");
			String geneSymbol = rs.getString("gene_symbol");
			String source = rs.getString("source");
			String rank = rs.getString("rank");
			
			MiRna miRna = new MiRna();
			miRna.setName(microrna);
			
			Gene gene = new Gene();
			gene.setName(geneSymbol);

			InteractionData id = new InteractionData();
			id.setRank(rank);
			id.setProvenance("mirDIP (" + source + ")");
			
			// Inserta MiRna (o recupera su id. si ya existe)
			Object oldMiRna = session.createCriteria(MiRna.class)
					.add( Restrictions.eq("name", miRna.getName()) )
					.uniqueResult();
			if (oldMiRna==null) {
				session.save(miRna);
				session.flush();  // to get the PK
			} else {
				MiRna miRnaToUpdate = (MiRna) oldMiRna;
				miRnaToUpdate.update(miRna);
				session.update(miRnaToUpdate);
				miRna = miRnaToUpdate;
			}
			
			// Inserta gene (o recupera su id. si ya existe)
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", gene.getName()))
					.uniqueResult();
			if (oldGene == null) {
				session.save(gene);
				session.flush(); // to get the PK
			} else {
				Gene geneToUpdate = (Gene) oldGene;
				geneToUpdate.update(gene);
				session.update(geneToUpdate);
				gene = geneToUpdate;
			}
			
			id.setMirna_pk(miRna.getPk());
			id.setGene_pk(gene.getPk());
			
			session.save(id);
			session.flush();
					
			count++;
			if (count%100==0) {
				session.flush();
		        session.clear();
			}
			
			rs.close();
			stmt.close();
			tx.commit();
			
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();

		} finally {
			if (con!=null) con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		mirDIP mirdip = new mirDIP();
		
		//String inputFile = "C:/Users/Esteban/Desktop/temp/mirDIP-All-Data-Version1.0.txt";
		//mirdip.insertInTable(inputFile);
		
		mirdip.insertIntoSQLModel();
		
	}

}