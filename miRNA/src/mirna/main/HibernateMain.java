package mirna.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mirna.beans.MiRna;
import mirna.utils.HibernateUtil;

public class HibernateMain {
	
	public static void main(String[] args) {
		
		 //Get Session
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();

		
        MiRna mirna = new MiRna();
        mirna.setName("hibernate3");
        mirna.setAccessionNumber("numero");
        
        //start transaction
        Transaction tx = session.beginTransaction();
        //Save the Model object
        session.save(mirna);
       
        System.out.println(mirna);
		
		Object object = session.createCriteria(MiRna.class)
			.add( Restrictions.eq("name", "hibernate") )
			.uniqueResult();
		
		
		MiRna mirna1 = (MiRna) object;
		System.out.println("Selected MiRna details");
		System.out.println(mirna1);
		//Update Student Details
		System.out.println("Updating MiRna details ");
		mirna1.setName("hibernate2");
		session.update(mirna1);
		
		
		//Commit transaction
        tx.commit();
		
		//terminate session factory, otherwise program won't end
        sessionFactory.close();
		
    }

}
