package mirna.utils;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	 
	private static SessionFactory sessionFactory;
	
	private static synchronized void buildSessionFactory() {
		if (sessionFactory == null) {
			try {
				// Create the SessionFactory from hibernate.cfg.xml
				Configuration configuration = new Configuration();
				configuration.configure(new File("hibernate.cfg.xml"));
				System.out.println("Hibernate Annotation Configuration loaded");
				
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Annotation serviceRegistry created");
				
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
	}
	
	public static void openSession() {
		Session session = sessionFactory.openSession();
		ThreadLocalSessionContext.bind(session);
	}
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}
	
	public static void closeSession() {
		Session session = ThreadLocalSessionContext.unbind(sessionFactory);
		if (session!=null) {
			session.close();
		}
	}
	
	public static void closeSessionFactory() {
		if ((sessionFactory!=null) && (sessionFactory.isClosed()==false)) {
			sessionFactory.close();
			sessionFactory = null;
		}
	}
}
