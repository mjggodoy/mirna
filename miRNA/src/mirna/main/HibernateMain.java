package mirna.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import mirna.beans.MiRna;
import mirna.utils.HibernateUtil;

public class HibernateMain {
	
	public static void main(String[] args) {
        MiRna mirna = new MiRna();
        mirna.setName("hibernate");
        mirna.setAccessionNumber("numero");
        
        //Get Session
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(mirna);
        //Commit transaction
        session.getTransaction().commit();
        System.out.println(mirna);
         
        //terminate session factory, otherwise program won't end
        sessionFactory.close();
    }

}
