package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.PubmedDocument;
import mirna.beans.Target;
import mirna.beans.nToM.ExpressionDataHasPubmedDocument;
import mirna.beans.nToM.MirnaHasPubmedDocument;
import mirna.exception.MiRnaException;
import mirna.utils.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


/**
 * CÃ³digo para procesar los datos de miRDB
 * 
 * @author Esteban LÃ³pez Camacho
 *
 */
public class miRDB extends MirnaDatabase {
	
	private final String tableName = "miRDB";
	
	public miRDB() throws MiRnaException { super(); }
	
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
	
				
					
					String accesionNumber = tokens[0];
					String target = tokens[1];
					String score = tokens[2];
					

					

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ accesionNumber + "','"
							+ target + "','"
							+ score + "')";
					
					stmt.executeUpdate(query);
		
	
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
	public void insertIntoSQLModel() throws Exception {
		
		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();

		Connection con = null;
		
		//start transaction
		Transaction tx = session.beginTransaction();
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
            stmt.setFetchSize(Integer.MIN_VALUE);

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);


			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;


			while (rs.next()){// antes estaba para que s—lo se hiciera una vez, en forma de condici—n
			
			String miRNA = rs.getString("mir");
			String target_name = rs.getString("target");
			String score = rs.getString("score");
			
			MiRna miRna = new MiRna();
			miRna.setName(miRNA);
			
			InteractionData id = new InteractionData();
			id.setScore(score);
			
			Target target = new Target();
			target.setName(target_name);
			
			/*System.out.println(miRna);
			System.out.println(id);
			System.out.println(target);
			*/
			// FIN DE CAMBIAR ESTO
			
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
			
			// Inserta InteractionData (o recupera su id. si ya existe)
		
			Object oldInteractionData = session.createCriteria(InteractionData.class)
					.add( Restrictions.eq("id", id.getPk()) )
					.uniqueResult();
			if (oldInteractionData==null) {
				session.save(id);
				session.flush();  // to get the PK
			} else {
				InteractionData interactionDataToUpdate = (InteractionData) oldInteractionData;
				interactionDataToUpdate.update(id);
				session.update(interactionDataToUpdate);
				id = interactionDataToUpdate;
			}
			
			// Inserta Target (o recupera su id. si ya existe)

			Object oldTarget = session.createCriteria(Target.class)
					.add( Restrictions.eq("id", id.getPk()) )
					.uniqueResult();
			if (oldTarget==null) {
				session.save(id);
				session.flush();  // to get the PK
			} else {
				Target targetDataToUpdate = (Target) oldTarget;
				targetDataToUpdate.update(target);
				session.update(targetDataToUpdate);
				target = targetDataToUpdate;
			}
			
			// Inserta nueva InteractinData
			// (y la relaciona con el MiRna y Target correspondientes)
			
			id.setMirnaPk(miRna.getPk());
			id.setTargetPk(target.getPk());
			session.save(id);
			session.flush();
			
			count++;
			if (count%100==0) {
				System.out.println(count);
				session.flush();
		        session.clear();
			}
			
			stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
		tx.commit();
		sessionFactory.close();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		miRDB mirdb = new miRDB();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/MirTarget2_v4.0_prediction_result.txt";
		//mirdb.insertInTable(inputFile);
		
		mirdb.insertIntoSQLModel();
		
	}

}