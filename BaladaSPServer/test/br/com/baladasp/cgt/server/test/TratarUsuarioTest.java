package br.com.baladasp.cgt.server.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.cdp.usuario.ConstantesUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.EstabelecimentoBO;
import br.com.baladasp.cgt.bo.UsuarioBO;
import br.com.baladasp.cgt.usuario.AtividadeUsuario;
import br.com.baladasp.cgt.usuario.Avaliacao;
import br.com.baladasp.cgt.usuario.Checkin;
import br.com.baladasp.cgt.usuario.ConsultaCheckinAvaliacao;
import br.com.baladasp.cgt.usuario.StatusUsuario;
import br.com.baladasp.cgt.usuario.TratarUsuario;
import br.com.baladasp.util.Json;

public class TratarUsuarioTest extends TestCase {
	private static String TIPO_OBJETO = ConstantesUsuario.USUARIO;
	private static String OPERACAO_OBJETO = null;
	private String[] sendJson;

	private TratarUsuario tratarUsuario;
	private Usuario usuario;
	private Estabelecimento estabelecimento;

	private EstabelecimentoBO estabelecimentoBO = new EstabelecimentoBO();
	private UsuarioBO usuarioBO = new UsuarioBO();

	@Before
	public void setUp() throws Exception {
		usuario = new Usuario("Kleber", 19001928, "@klebercomk", "teste");

		estabelecimento = estabelecimentoBO.consultarEstabelecimentoID(5);
		assertNotNull(estabelecimento);

		tratarUsuario = new TratarUsuario();
	}

	@Test
	public void testTratarPerfil() throws UnsupportedEncodingException {
		OPERACAO_OBJETO = ConstantesUsuario.PERFIL;

		sendJson = Json.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, usuario);
		String jsonSerialized = criarJson(operacao(sendJson));

		usuario = (Usuario) Json.deserializar(jsonSerialized, Usuario.class);
		assertNotNull(usuario);
	}

	@Test
	public void testVerificarSeUsuarioJaAvaliouEstabelecimento() {
		OPERACAO_OBJETO = ConstantesUsuario.VERIFICACAO;

		ConsultaCheckinAvaliacao consultaCheckinAvaliacao = new ConsultaCheckinAvaliacao(usuario, estabelecimento.getNome());

		sendJson = Json.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, consultaCheckinAvaliacao);
		String jsonSerialized = criarJson(operacao(sendJson));

		String msg = (String) Json.deserializar(jsonSerialized, String.class);
		// assertEquals(ConstantesUsuario.MSG_NAO_AVALIADO, msg);
		assertEquals(ConstantesUsuario.MSG_JA_AVALIADO, msg);
	}

	@Test
	public void testTratarCheckin() {
		OPERACAO_OBJETO = ConstantesUsuario.CHECKIN;

		usuario = usuarioBO.consultarUsuarioIDTwitter(19001928);
		assertNotNull(usuario);

		Checkin checkin = new Checkin(estabelecimento);
		AtividadeUsuario atividadeUsuario = new AtividadeUsuario(usuario, checkin);
		sendJson = Json.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, atividadeUsuario);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		String msg = (String) Json.deserializar(jsonSerialized, String.class);
		assertEquals(ConstantesUsuario.MSG_OK_CHECKIN, msg);

	}

	@Test
	public void testTratarAvaliacao() {
		OPERACAO_OBJETO = ConstantesUsuario.AVALIACAO;

		usuario = usuarioBO.consultarUsuarioIDTwitter(19001928);
		assertNotNull(usuario);

		Avaliacao avaliacao = new Avaliacao(5, 5, 5, 5, 5, 5, 5, 5, "OK", estabelecimento);
		AtividadeUsuario atividadeUsuario = new AtividadeUsuario(usuario, avaliacao);
		sendJson = Json.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, atividadeUsuario);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		String msg = (String) Json.deserializar(jsonSerialized, String.class);
		assertEquals(ConstantesUsuario.MSG_OK_AVALIADO, msg);

	}

	@SuppressWarnings("unchecked")
	public void testTratarListStatus() {
		OPERACAO_OBJETO = ConstantesUsuario.LIST_STATUS;

		sendJson = Json.jsonToString(TIPO_OBJETO, OPERACAO_OBJETO, null);

		String jsonSerialized = criarJson(operacao(sendJson));
		assertNotNull(jsonSerialized);

		ArrayList<StatusUsuario> statusUsuarios = (ArrayList<StatusUsuario>) Json.deserializar(jsonSerialized, ArrayList.class);
		assertNotNull(statusUsuarios);

	}

	private Object operacao(String[] sendJson) {
		Object object = tratarUsuario.operacoes(sendJson);
		assertNotNull(object);

		return object;
	}

	private String criarJson(Object object) {
		String jsonSerialized = (String) object;
		assertNotNull(jsonSerialized);
		return jsonSerialized;
	}

}