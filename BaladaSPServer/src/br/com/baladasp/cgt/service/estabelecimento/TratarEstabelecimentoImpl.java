package br.com.baladasp.cgt.service.estabelecimento;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.Avaliacao;
import br.com.baladasp.cgt.bo.AvaliacaoBO;
import br.com.baladasp.cgt.bo.EstabelecimentoBO;
import br.com.baladasp.cgt.bo.RankingBO;
import br.com.baladasp.cgt.util.json.serializer.AvaliacoesSerializer;
import br.com.baladasp.cgt.util.json.serializer.EstabelecimentosSerializer;
import br.com.baladasp.cgt.util.json.serializer.RankingSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Argumento
 */
public class TratarEstabelecimentoImpl implements TratarEstabelecimento {
	private Gson gsonSer = new GsonBuilder().registerTypeAdapter(ArrayList.class, new EstabelecimentosSerializer())
			.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	private Gson gsonSerRanking = new GsonBuilder().registerTypeAdapter(ArrayList.class, new RankingSerializer())
			.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	private Gson gsonSerAvaliacoes = new GsonBuilder().registerTypeAdapter(ArrayList.class, new AvaliacoesSerializer())
			.setPrettyPrinting().create();

	private Gson gsonUtils = new Gson();

	private ArrayList<Avaliacao> avaliacoes;

	@Override
	public Object operacoes(String[] receivedJsonString) throws UnsupportedEncodingException {

		final EstabelecimentoServiceEnum operacao = (EstabelecimentoServiceEnum) gsonUtils.fromJson(
				receivedJsonString[1], EstabelecimentoServiceEnum.class);

		final String argumento = gsonUtils.fromJson(receivedJsonString[2], String.class);

		String jsonEstabs = "";

		System.out.println(operacao);

		switch (operacao) {
		case TOP_DEZ:
			jsonEstabs = buscarTopDez(argumento);
			break;
		case PESQUISAR_ESTABELECIMENTO:
			// Paginacao
			int pageNum = gsonUtils.fromJson(receivedJsonString[3], Integer.class);
			String nomeEstab = argumento;
			jsonEstabs = buscaPorNome(nomeEstab, pageNum);
			break;
		case AVALIACOES_ESTABELECIMENTO:
			// Paginao
			int page = gsonUtils.fromJson(receivedJsonString[3], Integer.class);
			long idEstab = Long.valueOf(argumento);
			jsonEstabs = buscaAvaliacoesEstab(idEstab, page);
			break;
		default:
			break;
		}

		return jsonEstabs;
	}

	// ==================================================================================
	// =================================Top 10===========================================
	// ==================================================================================
	@Override
	public String buscarTopDez(final String argumento) {
		final String categoriaEstabelecimento = argumento;

		System.out.println(categoriaEstabelecimento);
		ArrayList<Ranking> rankList = new RankingBO().consultarRanking(new Categoria(categoriaEstabelecimento));

		/*
		 * TODO criar algoritmo de aleatoriedade p/ Top Dez
		 */
		// if (setEstabelecimentos.size() >= 0 && setEstabelecimentos.size() < 10) {
		// completarListaTopDez(categoriaEstabelecimento, rankList);
		// }

		return gsonSerRanking.toJson(rankList);
	}

	// Aqui está OK
	// private void completarListaTopDez(final String categoriaEstabelecimento, ArrayList<Ranking> rankList) {
	// Categoria categoria = new Categoria(categoriaEstabelecimento);
	// ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) new EstabelecimentoBO()
	// .consultarCategorias(categoria);
	//
	// for (Estabelecimento estabelecimento : estabelecimentos) {
	// if (rankList.size() < 10) {
	// //Cast p/ Ranking (?)
	// rankList.add(estabelecimento);
	// } else {
	// break;
	// }
	// }
	// }

	// ==================================================================================
	// =================================Pesquisa Nome====================================
	// ==================================================================================
	@Override
	public String buscaPorNome(final String argumento, int pageNum) {
		ArrayList<Estabelecimento> lstEstabs = null;
		if (argumento.matches("^[a-z A-Z]*$")) {
			final String nomeEstab = argumento;
			System.out.println("PAGE " + pageNum);

			lstEstabs = (ArrayList<Estabelecimento>) new EstabelecimentoBO().consultarEstabelecimentos(nomeEstab,
					pageNum);

			System.out.println("Busca por nome: " + lstEstabs.size());
		}
		return lstEstabs != null ? gsonSer.toJson(lstEstabs) : null;
	}

	private String buscaAvaliacoesEstab(long idEstab, int page) {
		AvaliacaoBO avaliacaoBO = new AvaliacaoBO();

		System.out.println("PAGE " + page);
		
		// TODO usar DI nessa merda.
		EstabelecimentoBO estabelecimentoBO = new EstabelecimentoBO();
		//TODO usar Singleton ?
		Estabelecimento estabelecimento = estabelecimentoBO.consultarEstabelecimentoID(idEstab);

		avaliacoes = avaliacaoBO.consultarAvaliacoesEstabelecimento(estabelecimento, page);

		return avaliacoes != null ? gsonSerAvaliacoes.toJson(avaliacoes) : null;
	}
}