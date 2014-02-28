package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;
import br.com.baladasp.cgt.usuario.Avaliacao;

@SuppressWarnings("rawtypes")
public class AvaliacaoBO extends AbstractCRUD {
	BaseBO<Avaliacao> AvaliacaoBO = new BaseBO<Avaliacao>(Avaliacao.class);

	@SuppressWarnings("unchecked")
	public AvaliacaoBO() {
		classeBO = new BaseBO<Avaliacao>(Avaliacao.class);
		classeDAO = new AbstractSelectDAO<Avaliacao>(Avaliacao.class);
	}

	@SuppressWarnings("unchecked")
	public void salvarAvaliacao(Avaliacao avaliacao) {
		save(avaliacao);
	}

	public Avaliacao consultarAvaliacao(long id) {
		return (Avaliacao) findByID(id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Avaliacao> consultarDezUltimasAvaliacoesUsuario(Usuario usuario) {

		String namedQuery = "Avaliacao.findByUsuario";

		return (ArrayList<Avaliacao>) findByParameterWithMaxResults(namedQuery, usuario);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Avaliacao> consultarAvaliacoesEstabelecimento(Estabelecimento estabelecimento) {
		String namedQuery = "Avaliacao.findByEstabelecimento";

		return (ArrayList<Avaliacao>) findByParameterReturnList(namedQuery, estabelecimento);
	}

}