package mirna.conversion.rdf;

import mirna.integration.beans.Disease;
import mirna.integration.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Esteban on 17/05/2016.
 */
public class PhenotypeRdf {

	public void execute() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Disease> diseaseList = session.createQuery("from Disease d").list();

		for (Disease d : diseaseList) {
			System.out.println(d);
		}

		//tx.

		sessionFactory.close();
	}

	private void processBean() {

		Model model=ModelFactory.createDefaultModel();

	}

}
