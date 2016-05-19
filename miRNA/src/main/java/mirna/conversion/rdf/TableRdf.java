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

	protected String ns = "http://khaos.uma.es/imirna/";
	protected String resourcePrefix = ns + "resource/";
	protected String folder = "C:/temp/imirna/";

	protected abstract void processBean(ModelClass modelClass, OutputStream out);

	protected abstract String getQuery();

	protected abstract String getNtFile();

	protected abstract String getName();

	public void execute() {

		String query = getQuery();
		String ntFile = folder + getNtFile();
		String name = getName();

		System.out.println("Starting "+name);

		FileOutputStream fop = null;
		File file;

		SessionFactory sessionFactory;
		Session session = null;

		try {

			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			List<ModelClass> list = session.createQuery(query).list();

			file = new File(ntFile);
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			for (ModelClass object : list) {
				processBean(object, fop);
			}

			fop.flush();
			fop.close();

			System.out.println(name + " ended!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (session != null) session.close();
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
