package br.com.baladasp.cgt.bo;

import java.util.List;

import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;
import br.com.baladasp.cgd.dao.DAOException;
import br.com.baladasp.cgd.dao.EstabelecimentoDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EstabelecimentoBO extends AbstractCRUD {
	
	EstabelecimentoDAO estabDAO;

	public EstabelecimentoBO() {
		classeBO = new BaseBO<Estabelecimento>(Estabelecimento.class);
		classeDAO = new AbstractSelectDAO<Estabelecimento>(Estabelecimento.class);
	}

	public void adicionarEstabelecimento(Estabelecimento estabelecimento) {
		save(estabelecimento);
	}

	public void atualizarEstabelecimento(Estabelecimento estabelecimento) {
		update(estabelecimento);
	}

	public Estabelecimento consultarEstabelecimentoID(long id) {
		return (Estabelecimento) findByID(id);
	}

	//TODO Pensar num jeito de arrumar essa consulta
	public List<Estabelecimento> consultarEstabelecimentos(String nome, int pageNum) {
		List<Estabelecimento> ListEstab = null;

		try {
			estabDAO = new EstabelecimentoDAO();
			classeBO.beginTransaction();
			ListEstab = estabDAO.selectByName(nome, pageNum);
			classeBO.commit();
		} catch (BOException e) {
			try {
				classeBO.rollback();
				System.err.println("Nao foi possivel selecionar o estabelecimento.");
			} catch (BOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return ListEstab;
	}

	public List<Estabelecimento> consultarCategorias(Categoria categoria) {
		String namedQuery = "Estabelecimento.findByCategoria";

		return findByParameterReturnList(namedQuery, categoria);
	}

}