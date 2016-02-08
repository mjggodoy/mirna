package mirna.integration.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import mirna.integration.beans.Gene;
import mirna.integration.utils.HibernateUtil;

public class HibernateTest {
	
	public static void main(String[] args) {
		
		try {
			
			//Get Session
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();// He cambiado de Annotation a SessionFactory method. Me daba error.,
			Session session = sessionFactory.getCurrentSession();
			
			//start transaction
			//Transaction tx = 
					session.beginTransaction();
			
			Object oldGene = session.createCriteria(Gene.class)
					.add(Restrictions.eq("name", "RETSAT"))
					.uniqueResult();
			if (oldGene == null) {
				System.out.println("NO ENCONTRADO!");
			} else {
				Gene gene = (Gene) oldGene;
				System.out.println(gene);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//terminate session factory, otherwise program won't end
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}
		
	}
	
	

}
