package br.com.baladasp.cgt.bo.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.UsuarioBO;

public class UsuarioBOTest extends TestCase implements BOTest {
	UsuarioBO usuarioBO;
	Usuario usuario;

	public UsuarioBOTest(String name) {
		super(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();

		usuarioBO = new UsuarioBO();
		usuario = new Usuario();

		//usuario.setIDTwitter(00000002);
		//usuario.setScreenName("UsuarioTest");
		//usuario.setUrlImagem("https://test.img");
		//usuario.setUsuario("Usuario Test");
	}

	// TODO Criar Mock ?
	@Override
	public void testInsert() {
		usuarioBO.inserirUsuario(usuario);
	}

	@Override
	public void testUpdate() {
		//usuario.setScreenName("UsuarioTest2");
		usuarioBO.atualizarUsuario(usuario);
	}

	@Override
	public void testSelectById() {
		long id = 0;
		Usuario user = usuarioBO.consultarUsuarioID(id);

		assertNotNull(user);
	}

	@Test
	public void testConsultarUsuarioIDTwitter() {
		long id = 00000000;
		Usuario user = usuarioBO.consultarUsuarioIDTwitter(id);

		assertNotNull(user);
	}

	@Override
	public void testDelete() {
		usuarioBO.excluirUsuario(usuario);
	}
	 

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("Finalizando...");
	}
}
