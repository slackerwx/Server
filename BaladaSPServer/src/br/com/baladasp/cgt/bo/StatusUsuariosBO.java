package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.StatusUsuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class StatusUsuariosBO extends AbstractCRUD {

	public StatusUsuariosBO() {
		classeBO = new BaseBO<StatusUsuario>(StatusUsuario.class);
		classeDAO = new AbstractSelectDAO<StatusUsuario>(StatusUsuario.class);
	}

	public void inserirStatus(StatusUsuario statusUsuario) {
		save(statusUsuario);
	}

	public ArrayList<StatusUsuario> consultaStatusTimeline(int pageNum) {
		String namedQuery = "StatusUsuarios.getAll";

		return (ArrayList<StatusUsuario>) findWithPagination(namedQuery, pageNum);
	}

	public void excluirStatus(StatusUsuario statusUsuario) {
		delete(statusUsuario);
	}
}