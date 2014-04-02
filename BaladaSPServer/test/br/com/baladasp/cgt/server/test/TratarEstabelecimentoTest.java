package br.com.baladasp.cgt.server.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.baladasp.cdp.constantes.EnumCategoriasTopDez;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.estabelecimento.Ranking;
import br.com.baladasp.cdp.usuario.Avaliacao;
import br.com.baladasp.cgt.service.estabelecimento.EstabelecimentoServiceEnum;
import br.com.baladasp.cgt.service.estabelecimento.TratarEstabelecimentoImpl;
import br.com.baladasp.util.json.JsonAvaliacoes;
import br.com.baladasp.util.json.JsonEstabelecimento;
import br.com.baladasp.util.json.JsonRanking;

public class TratarEstabelecimentoTest extends TestCase implements Tratamento {
	private static String TIPO_OBJETO = EstabelecimentoServiceEnum.ESTABELECIMENTO.toString();
	private String[] sendJson;

	TratarEstabelecimentoImpl tratarEstabelecimento;
	private JsonEstabelecimento jsonEstabelecimento;
	private JsonRanking jsonRanking;
	private JsonAvaliacoes jsonAvaliacoes;
	private EstabelecimentoServiceEnum OPERACAO_OBJETO;

	protected void setUp() throws Exception {
		super.setUp();
		tratarEstabelecimento = new TratarEstabelecimentoImpl();
		jsonEstabelecimento = JsonEstabelecimento.getInstance();
		jsonRanking = JsonRanking.getInstance();
		jsonAvaliacoes = JsonAvaliacoes.getInstance();
	}

	@SuppressWarnings("unchecked")
	public void testBuscarTopDez() {
		OPERACAO_OBJETO = EstabelecimentoServiceEnum.TOP_DEZ;
		EnumCategoriasTopDez categoriaTopDez = EnumCategoriasTopDez.BARES;
		sendJson = jsonEstabelecimento.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, categoriaTopDez);

		Object object = operacao(sendJson);
		String jsonSerialized = criarJson(object);
		assertNotNull(jsonSerialized);

		ArrayList<Ranking> ranking = (ArrayList<Ranking>) jsonRanking.deserializar(jsonSerialized, ArrayList.class);
		assertNotNull(ranking);

		imprimirRanking(ranking);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuscaPorNome() {
		OPERACAO_OBJETO = EstabelecimentoServiceEnum.PESQUISAR_ESTABELECIMENTO;
		String nomeEstabelecimento = "Bar";
		int pagina = 0;

		// Testa 10 páginas de busca
		for (int i = 0; i < 10; i++) {
			sendJson = jsonEstabelecimento.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, nomeEstabelecimento, pagina);

			String jsonSerialized = criarJson(operacao(sendJson));
			assertNotNull(jsonSerialized);

			ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) jsonEstabelecimento
					.deserializar(jsonSerialized, ArrayList.class);
			assertNotNull(estabelecimentos);

			System.out.println("***********Página " + pagina + " ************************");
			imprimirEstabelecimentos(estabelecimentos);

			pagina++;

		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuscaAvaliacoesEstab() {
		OPERACAO_OBJETO = EstabelecimentoServiceEnum.AVALIACOES_ESTABELECIMENTO;
		long idEstab = 5;
		int pagina = 0;

		// Testa 10 páginas de busca
		for (int i = 0; i < 10; i++) {
			sendJson = jsonEstabelecimento.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, idEstab, pagina);

			String jsonSerialized = criarJson(operacao(sendJson));
			assertNotNull(jsonSerialized);

			ArrayList<Avaliacao> avaliacoes = (ArrayList<Avaliacao>) jsonAvaliacoes.deserializar(jsonSerialized,
					ArrayList.class);
			assertNotNull(avaliacoes);

			System.out.println("***********Página " + pagina + " ************************");
			imprimirAvaliacoes(avaliacoes);

			pagina++;
		}
	}

	private void imprimirAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
		for (Avaliacao avaliacao : avaliacoes) {
			System.out.println(avaliacao.getId());
		}
		avaliacoes.clear();
	}

	private void imprimirRanking(ArrayList<Ranking> ranking) {
		for (Ranking ranking2 : ranking) {
			System.out.println(ranking2);
		}
	}

	private void imprimirEstabelecimentos(ArrayList<Estabelecimento> estabelecimentos) {
		for (Estabelecimento estabelecimento : estabelecimentos) {
			System.out.println(estabelecimento.getId());
		}
		estabelecimentos.clear();
	}

	@Override
	public String criarJson(Object object) {
		String jsonSerialized = (String) object;
		assertNotNull(jsonSerialized);
		return jsonSerialized;
	}

	@Override
	public Object operacao(String[] sendJson) {
		Object object = null;

		try {
			object = tratarEstabelecimento.operacoes(sendJson);
			assertNotNull(object);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return object;
	}
}
