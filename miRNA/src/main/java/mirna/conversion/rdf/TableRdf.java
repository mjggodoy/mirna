package mirna.conversion.rdf;

import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.List;

/**
 * Created by Esteban on 18/05/2016.
 */
public abstract class TableRdf {

	private final int BATCH_SIZE = 10000;

	private Session session = null;

	protected String ns = "http://khaos.uma.es/imirna/";
	protected String resourcePrefix = ns + "resource/";
	protected String folder = "/Users/mariajesus/Desktop/";

	protected abstract void processBean(ModelClass modelClass, OutputStream out);

	protected abstract String getQuery();

	protected abstract String getNtFile();

	protected abstract String getName();

	private List<ModelClass> getAllModelsIterable(int offset, int max) {
		String query = getQuery();
		return session.createQuery(query).setFirstResult(offset).setMaxResults(max).list();
	}

	public void execute() {

		int offset = 0;

		List<ModelClass> list;
		String ntFile = folder + getNtFile();
		String name = getName();

		System.out.println("Starting "+name);

		FileOutputStream fop = null;
		File file;

		SessionFactory sessionFactory;

		try {

			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.getCurrentSession();

			file = new File(ntFile);
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			session.getTransaction().begin();

			int size = 0;

			while ((list = getAllModelsIterable(offset, BATCH_SIZE)).size() > 0) {

				//session.getTransaction().begin();

				for (ModelClass object : list) {
					processBean(object, fop);
				}

				size += list.size();
				System.out.println(size+" rows converted.");

				session.flush();
				session.clear();
				//session.getTransaction().commit();
				offset += list.size();
			}

			fop.flush();
			fop.close();

			System.out.println(name + " ended!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (session != null) {
					session.close();
				}
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
