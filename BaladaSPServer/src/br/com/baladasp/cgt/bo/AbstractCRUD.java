package br.com.baladasp.cgt.bo;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.baladasp.cgd.dao.AbstractSelectDAO;
import br.com.baladasp.cgd.dao.DAOException;

public class AbstractCRUD<T> {
	private Logger logger = Logger.getLogger(AbstractCRUD.class.getName());

	protected BaseBO<T> classeBO;
	protected AbstractSelectDAO<T> classeDAO;

	protected void save(T classe) {
		try {
			classeBO.beginTransaction();
			classeBO.save(classe);
			classeBO.commit();

			logger.info(classe.getClass().getSimpleName() + " Adicionado");
		} catch (BOException e) {
			try {
				classeBO.rollback();
			} catch (BOException e1) {
				e1.printStackTrace();
			}

			logger.error("Nao foi possivel adicionar: " + classe.getClass().getName());
			e.printStackTrace();
		}
	}

	protected void update(T classe) {
		try {
			classeBO.beginTransaction();
			classeBO.update(classe);
			classeBO.commit();

			logger.info(classe.getClass().getSimpleName() + " Atualizado");
		} catch (BOException e) {
			try {
				classeBO.rollback();
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			logger.error("Não foi possível atualizar: " + classe.getClass().getName());
			e.printStackTrace();
		}
	}

	protected void delete(T classe) {
		try {
			classeBO.beginTransaction();
			classeBO.delete(classe);
			classeBO.commit();

			logger.info(classe.getClass().getSimpleName() + " Excluido");
		} catch (BOException e) {
			try {
				classeBO.rollback();
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			logger.error("Não foi possível excluir: " + classe.getClass().getName());
			e.printStackTrace();
		}
	}

	protected Object findByID(long id) {
		Object object = null;
		try {
			classeBO.beginTransaction();
			object = classeBO.selectById(id);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + " Encontrado");
		} catch (BOException e) {
			try {
				classeBO.rollback();
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			logger.error("Nao foi possivel fazer a query.");
			e.printStackTrace();
		}
		return object;
	}

	protected List<T> findByParameterReturnList(String namedQuery, Object object) {
		List<T> list = null;
		try {
			classeBO.beginTransaction();
			list = classeDAO.selectByParameter(namedQuery, object);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();
				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected T findByParameterUniqueResult(String namedQuery, Object object) {
		Object objt = null;
		try {
			classeBO.beginTransaction();
			objt = classeDAO.selectByParameterUniqueResult(namedQuery, object);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();
				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return (T) objt;
	}

	protected List<T> findAll(String namedQuery) {
		List<T> list = null;
		try {
			classeBO.beginTransaction();
			list = classeDAO.selectAll(namedQuery);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();
				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}

	protected List<T> findByParameterWithMaxResults(String namedQuery, Object object) {
		List<T> list = null;
		try {
			classeBO.beginTransaction();
			list = classeDAO.selectByParameterWithMaxResults(namedQuery, object);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();

				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}

	protected List<T> findWithPagination(String namedQuery, int pageNum) {
		List<T> list = null;
		try {
			classeBO.beginTransaction();
			list = classeDAO.selectWithPagination(namedQuery, pageNum);
			classeBO.commit();

			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();

				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}
	protected List<T> findByParameterWithPagination(String namedQuery, Object object, int pageNum) {
		List<T> list = null;
		try {
			classeBO.beginTransaction();
			list = classeDAO.selectByParameterWithPagination(namedQuery, object, pageNum);
			classeBO.commit();
			
			logger.info(classeBO.getClass().getSimpleName() + namedQuery);
		} catch (BOException e) {
			try {
				classeBO.rollback();
				
				logger.error("Nao foi possivel selecionar: " + namedQuery);
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}

}