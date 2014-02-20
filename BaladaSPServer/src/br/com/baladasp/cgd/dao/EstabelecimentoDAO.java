package br.com.baladasp.cgd.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

public class EstabelecimentoDAO extends BaseDAO<Estabelecimento> {

	/*
	 * Nao sei se é a melhor opção usar SQL nativo, mas foi a que funcionou
	 */
	@SuppressWarnings("unchecked")
	public List<Estabelecimento> selectByName(String nameExcerpt, int pageNum) throws DAOException {
		int pageSize = 10;

		Session session = getSession();
		String hql = "SELECT * FROM tb_estabelecimento WHERE ucase(nome) like ? ";
		Query q = session.createSQLQuery(hql).addEntity(Estabelecimento.class)
				.setString(0, "%" + nameExcerpt.toUpperCase() + "%").setFirstResult(pageNum * pageSize).setMaxResults(pageSize);

		return (List<Estabelecimento>) q.list();
	}

}