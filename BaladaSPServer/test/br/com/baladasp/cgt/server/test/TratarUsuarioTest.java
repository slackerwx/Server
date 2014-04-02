package br.com.baladasp.cgt.server.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.usuario.AtividadeUsuario;
import br.com.baladasp.cdp.usuario.Avaliacao;
import br.com.baladasp.cdp.usuario.Checkin;
import br.com.baladasp.cdp.usuario.ConstantesUsuario;
import br.com.baladasp.cdp.usuario.ConsultaCheckinAvaliacao;
import br.com.baladasp.cdp.usuario.StatusUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.EstabelecimentoBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.cgt.service.usuario.TratarUsuario;
import br.com.baladasp.cgt.service.usuario.TratarUsuarioImpl;
import br.com.baladasp.cgt.service.usuario.UsuarioServiceEnum;
import br.com.baladasp.util.json.JsonAtividadeUsuario;
import br.com.baladasp.util.json.JsonUsuario;

public class TratarUsuarioTest extends TestCase implements Tratamento, TratarUsuarioT {
	private static String TIPO_OBJETO = ConstantesUsuario.USUARIO;
	private static UsuarioServiceEnum OPERACAO_OBJETO = null;
	private String[] sendJson;

	private TratarUsuario tratarUsuario;
	private Usuario usuario;
	private Estabelecimento estabelecimento;

	private EstabelecimentoBO estabelecimentoBO = new EstabelecimentoBO();
	private UsuarioBO usuarioBO = new UsuarioBO();

	private JsonUsuario jsonUsuario;
	private JsonAtividadeUsuario jsonAtividadeUsuario;

	@Before
	public void setUp() throws Exception {
		usuario = new Usuario("Kleber", 19001928, "@klebercomk", "teste");

		estabelecimento = estabelecimentoBO.consultarEstabelecimentoID(6);
		assertNotNull(estabelecimento);

		jsonUsuario = JsonUsuario.getInstance();
		jsonAtividadeUsuario = JsonAtividadeUsuario.getInstance();
		tratarUsuario = new TratarUsuarioImpl();
	}

	@Override
	public void testTratarPerfil() {
		OPERACAO_OBJETO = UsuarioServiceEnum.PERFIL_USUARIO;

		sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, usuario);
		System.out.println("SEND JSON " + sendJson[0] + sendJson[1] + sendJson[2]);
		String jsonSerialized = criarJson(operacao(sendJson));

		usuario = (Usuario) jsonUsuario.deserializar(jsonSerialized, Usuario.class);
		assertNotNull(usuario);
	}

	@Override
	public void testVerificarSeUsuarioJaAvaliouEstabelecimento() {
		OPERACAO_OBJETO = UsuarioServiceEnum.VERIFICAR_CHECKIN_AVALIACAO;

		ConsultaCheckinAvaliacao consultaCheckinAvaliacao = new ConsultaCheckinAvaliacao(usuario,
				estabelecimento.getNome());

		sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, consultaCheckinAvaliacao);
		String jsonSerialized = criarJson(operacao(sendJson));

		String msg = (String) jsonUsuario.deserializar(jsonSerialized, String.class);
		// assertEquals(ConstantesUsuario.MSG_NAO_AVALIADO, msg);
		assertEquals(ConstantesUsuario.MSG_JA_AVALIADO, msg);
	}

	@Override
	public void testTratarCheckin() {
		OPERACAO_OBJETO = UsuarioServiceEnum.CHECKIN_ESTABELECIMENTO;

		usuario = usuarioBO.consultarUsuarioIDTwitter(19001928);
		assertNotNull(usuario);

		Checkin checkin = new Checkin(estabelecimento);
		AtividadeUsuario atividadeUsuario = new AtividadeUsuario(usuario, checkin);
		sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, atividadeUsuario);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		String msg = (String) jsonUsuario.deserializar(jsonSerialized, String.class);
		assertEquals(ConstantesUsuario.MSG_OK_CHECKIN, msg);

	}

	@Override
	public void testTratarAvaliacao() {
		OPERACAO_OBJETO = UsuarioServiceEnum.AVALIAR_ESTABELECIMENTO;

		usuario = usuarioBO.consultarUsuarioIDTwitter(19001928);
		assertNotNull(usuario);

		Avaliacao avaliacao = new Avaliacao(5, 5, 5, 5, 5, 5, 5, 5, "OK", estabelecimento);
		AtividadeUsuario atividadeUsuario = new AtividadeUsuario(usuario, avaliacao);
		sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, atividadeUsuario);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		String msg = (String) jsonUsuario.deserializar(jsonSerialized, String.class);
		assertEquals(ConstantesUsuario.MSG_OK_AVALIADO, msg);

	}

	@Override
	@SuppressWarnings("unchecked")
	public void testTratarListStatus() {
		OPERACAO_OBJETO = UsuarioServiceEnum.TIMELINE_TWITTER;

		int pageNum = 0;

		// Testa 10 p√°ginas de busca
		for (int i = 0; i < 10; i++) {
			sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, pageNum);

			String jsonSerialized = criarJson(operacao(sendJson));
			assertNotNull(jsonSerialized);

			ArrayList<StatusUsuario> statusUsuarios = (ArrayList<StatusUsuario>) jsonUsuario.deserializar(
					jsonSerialized, ArrayList.class);
			assertNotNull(statusUsuarios);

			if (statusUsuarios.size() == 0) {
				break;
			}
			pageNum++;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void testTratarAtividadesUsuario() {
		OPERACAO_OBJETO = UsuarioServiceEnum.ATIVIDADES_USUARIO;

		int pageNum = 0;

		for (int i = 0; i < 10; i++) {
			sendJson = jsonUsuario.jsonToStringArray(TIPO_OBJETO, OPERACAO_OBJETO, usuario, pageNum);

			String jsonSerialized = criarJson(operacao(sendJson));
			assertNotNull(jsonSerialized);

			ArrayList<AtividadeUsuario> atividadesUsuario = (ArrayList<AtividadeUsuario>) jsonAtividadeUsuario
					.deserializar(jsonSerialized, ArrayList.class);
			assertNotNull(atividadesUsuario);

			if (atividadesUsuario.size() == 0) {
				break;
			}
			System.out.println("***********Página " + pageNum + " ************************");
			imprimirAtividades(atividadesUsuario);
			pageNum++;
		}
	}

	private void imprimirAtividades(ArrayList<AtividadeUsuario> atividadesUsuario) {
		for (AtividadeUsuario atividadeUsuario : atividadesUsuario) {
			System.out.println("Atividade: " + atividadeUsuario.getId());
		}
		atividadesUsuario.clear();
	}

	@Override
	public Object operacao(String[] sendJson) {
		Object object = tratarUsuario.operacoes(sendJson);
		assertNotNull(object);

		return object;
	}

	@Override
	public String criarJson(Object object) {
		String jsonSerialized = (String) object;
		assertNotNull(jsonSerialized);
		return jsonSerialized;
	}
}
