package br.com.baladasp.cgd.dao;

import java.io.Serializable;

public interface DAO<T> {
	Serializable save(T object) throws DAOException;

	T saveOrUpdate(T object) throws DAOException;

	void update(T object) throws DAOException;

	void delete(T object) throws DAOException;

	T selectById(Serializable id) throws DAOException;
}
