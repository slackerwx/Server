package br.com.baladasp.cgt.server.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.baladasp.cdp.constantes.EnumCategoriasTopDez;
import br.com.baladasp.cdp.constantes.EnumEstabelecimento;
import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cgt.server.TratarEstabelecimento;
import br.com.baladasp.util.JsonEstabelecimento;

public class TratarEstabelecimentoTest extends TestCase implements Tratamento {
	private static String TIPO_OBJETO = EnumEstabelecimento.ESTABELECIMENTO.toString();
	private String[] sendJson;

	TratarEstabelecimento tratarEstabelecimento;
	private JsonEstabelecimento jsonEstabelecimento;

	protected void setUp() throws Exception {
		super.setUp();
		tratarEstabelecimento = new TratarEstabelecimento();
		jsonEstabelecimento = new JsonEstabelecimento();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuscarTopDez() {
		EnumEstabelecimento OPERACAO_OBJETO = EnumEstabelecimento.TOPDEZ;
		EnumCategoriasTopDez categoriaTopDez = EnumCategoriasTopDez.BARES;
		sendJson = jsonEstabelecimento.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, categoriaTopDez);

		Object object = operacao(sendJson);
		String jsonSerialized = criarJson(object);
		assertNotNull(jsonSerialized);

		ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) jsonEstabelecimento
				.deserializar(jsonSerialized, ArrayList.class);
		assertNotNull(estabelecimentos);
		
		imprimirEstabelecimentos(estabelecimentos);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuscaPorNome() {
		String OPERACAO_OBJETO = EnumEstabelecimento.PESQUISA.toString();
		String nomeEstabelecimento = "Bar";
		int pagina = 0;
		sendJson = jsonEstabelecimento.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, nomeEstabelecimento, pagina);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) jsonEstabelecimento
				.deserializar(jsonSerialized, ArrayList.class);
		assertNotNull(estabelecimentos);
		
		imprimirEstabelecimentos(estabelecimentos);
	}

	private void imprimirEstabelecimentos(ArrayList<Estabelecimento> estabelecimentos) {
		for (Estabelecimento estabelecimento : estabelecimentos) {
			System.out.println(estabelecimento);
		}
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
