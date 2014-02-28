package br.com.baladasp.cgt.bo.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import br.com.baladasp.cgt.usuario.StatusUsuario;

//TODO criar Strategy ??
public class StatusUsuariosBOTest extends TestCase implements BOTest {

	StatusUsuariosBO statusUsuariosBO;
	StatusUsuario statusUsuario;

	protected void setUp() throws Exception {
		super.setUp();

		statusUsuariosBO = new StatusUsuariosBO();

		statusUsuario = new StatusUsuario(1, "UsuarioTest", "20/02/14 02:56", "", "#appbaladasp Teste");
		
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
		ArrayList<StatusUsuario> statusUsuarios = statusUsuariosBO.consultaStatusTimeline();

		assertNotNull(statusUsuarios);
		assertTrue(statusUsuarios.size() > 0);
	}

}
