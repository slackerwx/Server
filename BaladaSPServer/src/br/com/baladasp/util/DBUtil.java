package br.com.baladasp.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("resource/hibernate.cfg.xml");
			return configuration.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Erro na inicializacao da SessionFactory" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		if (!session.isOpen())
			session = sessionFactory.openSession();
		return session;
	}

}