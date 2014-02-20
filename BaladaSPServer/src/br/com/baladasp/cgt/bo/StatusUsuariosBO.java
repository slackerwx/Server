package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.StatusUsuarios;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class StatusUsuariosBO extends AbstractCRUD {

	public StatusUsuariosBO() {
		classeBO = new BaseBO<StatusUsuarios>(StatusUsuarios.class);
		classeDAO = new AbstractSelectDAO<StatusUsuarios>(StatusUsuarios.class);
	}

	public void salvarStatus(StatusUsuarios statusUsuario) {
		save(statusUsuario);
	}

	public ArrayList<StatusUsuarios> consultaStatusTimeline() {
		String namedQuery = "StatusUsuarios.getAll";

		return (ArrayList<StatusUsuarios>) findAll(namedQuery);
	}
}