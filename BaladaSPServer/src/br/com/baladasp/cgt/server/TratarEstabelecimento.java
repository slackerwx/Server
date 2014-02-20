package br.com.baladasp.cgt.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.baladasp.cdp.constantes.EnumEstabelecimento;
import br.com.baladasp.cdp.estabelecimento.Avaliacao;
import br.com.baladasp.cdp.estabelecimento.Categoria;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.EstabelecimentoBO;
import br.com.baladasp.cgt.bo.RankingBO;
import br.com.baladasp.cgt.util.AvaliacaoSerializer;
import br.com.baladasp.cgt.util.EstabelecimentoSerializer;
import br.com.baladasp.cgt.util.EstabelecimentosSerializer;
import br.com.baladasp.cgt.util.RankingSerializer;
import br.com.baladasp.cgt.util.UsuarioSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * jsonString[0] = Tipo de objeto
 * jsonString[1] = Tipo de operacao
 * jsonString[2] = Argumento
 */
public class TratarEstabelecimento {
	private Gson gsonSer = new GsonBuilder().registerTypeAdapter(Avaliacao.class, new AvaliacaoSerializer())
			.registerTypeAdapter(Estabelecimento.class, new EstabelecimentoSerializer())
			.registerTypeAdapter(Ranking.class, new RankingSerializer())
			.registerTypeAdapter(ArrayList.class, new EstabelecimentosSerializer())
			.registerTypeAdapter(Usuario.class, new UsuarioSerializer()).setPrettyPrinting().create();

	public Object operacoes(String[] receivedJsonString) throws UnsupportedEncodingException {
		Gson gsonUtils = new Gson();
		final String operacao = gsonUtils.fromJson(receivedJsonString[1], String.class);
		final String argumento = gsonUtils.fromJson(receivedJsonString[2], String.class);

		String jsonEstabs = "";

		if (operacao.equalsIgnoreCase(EnumEstabelecimento.TOPDEZ.toString())) {
			jsonEstabs = buscarTopDez(argumento);
		}

		if (operacao.equalsIgnoreCase(EnumEstabelecimento.PESQUISA.toString())) {
			int pageNum = gsonUtils.fromJson(receivedJsonString[3], Integer.class);
			jsonEstabs = buscaPorNome(argumento, pageNum);
		}

		return jsonEstabs;
	}

	// ==================================================================================
	// =================================Top 10===========================================
	// ==================================================================================
	private String buscarTopDez(final String argumento) {
		final String categoriaEstabelecimento = argumento;

		ArrayList<Ranking> rankList = new RankingBO().consultarRanking(new Categoria(categoriaEstabelecimento));
		Set<Estabelecimento> setEstabelecimentos = new LinkedHashSet<Estabelecimento>();

		if (rankList.size() != 0) {
			for (Ranking ranking : rankList) {
				Estabelecimento estabelecimento = ranking.getEstabelecimento();
				estabelecimento.setRanking(ranking);

				setEstabelecimentos.add(estabelecimento);
			}
			System.out.println("Busca Top 10: " + rankList.size());
		}

		/*
		 * TODO criar algoritmo de aleatoriedade
		 */
		if (setEstabelecimentos.size() >= 0 && setEstabelecimentos.size() < 10) {
			completarLista(categoriaEstabelecimento, setEstabelecimentos);
		}

		return gsonSer.toJson(new ArrayList<Estabelecimento>(setEstabelecimentos));
	}

	private void completarLista(final String categoriaEstabelecimento, Set<Estabelecimento> setEstabelecimentos) {
		Categoria categoria = new Categoria(categoriaEstabelecimento);
		ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) new EstabelecimentoBO()
				.consultarCategorias(categoria);

		for (Estabelecimento estabelecimento : estabelecimentos) {
			if (setEstabelecimentos.size() < 10) {
				setEstabelecimentos.add(estabelecimento);
			} else {
				break;
			}
		}
		System.out.println("Tamanho setEstabelecimentos: " + setEstabelecimentos.size());
	}

	// ==================================================================================
	// =================================Pesquisa Nome====================================
	// ==================================================================================
	private String buscaPorNome(final String argumento, int pageNum) {
		ArrayList<Estabelecimento> lstEstabs = null;
		if (argumento.matches("^[a-z A-Z]*$")) {
			final String nomeEstab = argumento;
			System.out.println("PAGE " + pageNum);

			lstEstabs = (ArrayList<Estabelecimento>) new EstabelecimentoBO().consultarEstabelecimentos(nomeEstab, pageNum);

			System.out.println("Busca por nome: " + lstEstabs.size());
		}
		return lstEstabs != null ? gsonSer.toJson(lstEstabs) : null;
	}

}