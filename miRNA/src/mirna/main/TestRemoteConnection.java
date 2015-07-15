package mirna.main;

import java.sql.SQLException;

import mirna.beans.MiRna;
import mirna.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class TestRemoteConnection {
	
	public static void main(String[] args) throws SQLException {
		
		// Get session
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", "hsa-let-7a") )
				.uniqueResult();
		
		System.out.println(oldMiRna);
		
		HibernateUtil.closeSession();
		HibernateUtil.closeSessionFactory();
		
	}

}
