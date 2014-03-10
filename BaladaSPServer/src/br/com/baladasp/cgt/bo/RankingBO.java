package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings("rawtypes")
public class RankingBO extends AbstractCRUD {

	@SuppressWarnings("unchecked")
	public RankingBO() {
		classeBO = new BaseBO<Ranking>(Ranking.class);
		classeDAO = new AbstractSelectDAO<Ranking>(Ranking.class);
	}

	@SuppressWarnings("unchecked")
	public void salvarRanking(Ranking ranking) {
		Ranking rankAtualizado = consultarRanking(ranking.getEstabelecimento());

		if (rankAtualizado == null) {
			save(ranking);
		} else {
			float total = ranking.getPontos() + rankAtualizado.getPontos();
			rankAtualizado.setPontos(total);
			rankAtualizado.setMediaAvaliacoes(ranking.getMediaAvaliacoes());
			atualizarRanking(rankAtualizado);
		}
	}

	@SuppressWarnings("unchecked")
	private void atualizarRanking(Ranking ranking) {
		update(ranking);
	}

	public Ranking consultarRanking(Estabelecimento estabelecimento) {
		String namedQuery = "Ranking.findByEstabelecimento";

		return (Ranking) findByParameterUniqueResult(namedQuery, estabelecimento);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Ranking> consultarRanking(Categoria categoria) {
		String namedQuery = "Ranking.findByCategoria";

		return (ArrayList<Ranking>) findByParameterReturnList(namedQuery, categoria);
	}

}
