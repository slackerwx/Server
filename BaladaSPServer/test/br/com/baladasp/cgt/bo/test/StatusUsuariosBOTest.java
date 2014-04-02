package br.com.baladasp.cgt.bo.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.baladasp.cdp.usuario.StatusUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.bo.UsuarioBO;

public class StatusUsuariosBOTest extends TestCase implements BOTest {

	StatusUsuariosBO statusUsuariosBO;
	StatusUsuario statusUsuario;

	protected void setUp() throws Exception {
		super.setUp();

		statusUsuariosBO = new StatusUsuariosBO();
		
		UsuarioBO usuarioBO = new UsuarioBO();
		Usuario usuario = usuarioBO.consultarUsuarioID(0);

		statusUsuario = new StatusUsuario("20/02/14 02:56", "PicNic", "http://", "test",usuario);
		
	}

	@Override
	public void testInsert() {
		statusUsuariosBO.inserirStatus(statusUsuario);
	}

	@Override
	public void testUpdate() {}

	@Override
	public void testDelete() {
		statusUsuariosBO.excluirStatus(statusUsuario);
	}

	@Override
	public void testSelectById() {}

	@Test
	public void testConsultaStatusTimeline() {
		int pageNum = 0;
		ArrayList<StatusUsuario> statusUsuarios = statusUsuariosBO.consultaStatusTimeline(pageNum);

		assertNotNull(statusUsuarios);
		assertTrue(statusUsuarios.size() > 0);
	}

}
