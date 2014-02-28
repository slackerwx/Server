package br.com.baladasp.cgd.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class AbstractSelectDAO<T> extends BaseDAO<T> {

	public AbstractSelectDAO(Class<T> clazz) {
		super(clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> selectByParameter(String namedQuery, Object object) throws DAOException {
		Session session = getSession();

		return session.getNamedQuery(namedQuery).setParameter("parametro", object).list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> selectAll(String namedQuery) throws DAOException {
		Session session = getSession();

		return (ArrayList<T>) session.getNamedQuery(namedQuery).list();
	}

	@SuppressWarnings("unchecked")
	public T selectByParameterUniqueResult(String namedQuery, Object id) throws DAOException {
		Session session = getSession();

		return (T) session.getNamedQuery(namedQuery).setParameter("parametro", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> selectByParameterWithMaxResults(String namedQuery, Object object) throws DAOException {
		Session session = getSession();
		int pageSize = 10;

		return (ArrayList<T>) session.getNamedQuery(namedQuery).setParameter("parametro", object).setMaxResults(pageSize).list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> selectByParameterWithPagination(String namedQuery, Object object, int pageNum) throws DAOException {
		Session session = getSession();

		int pageSize = 10;

		return (ArrayList<T>) session.getNamedQuery(namedQuery).setParameter("parametro", object)
				.setFirstResult(pageNum * pageSize).setMaxResults(pageSize).list();
	}

}