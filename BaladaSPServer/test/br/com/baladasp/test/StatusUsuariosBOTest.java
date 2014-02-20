package br.com.baladasp.test;

import br.com.baladasp.cdp.usuario.StatusUsuarios;
import br.com.baladasp.cgt.bo.StatusUsuariosBO;
import junit.framework.TestCase;

public class StatusUsuariosBOTest extends TestCase implements BOTest {

	StatusUsuariosBO statusUsuariosBO;
	StatusUsuarios statusUsuarios;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		statusUsuariosBO = new StatusUsuariosBO();
		statusUsuarios = new StatusUsuarios();
		
	}

	@Override
	public void testInsert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testSelectById() {
		// TODO Auto-generated method stub

	}

}
