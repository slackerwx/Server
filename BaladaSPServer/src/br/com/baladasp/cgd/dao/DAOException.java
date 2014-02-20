package br.com.baladasp.cgd.dao;

import org.hibernate.HibernateException;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(HibernateException e) {
		super(e);
	}
}