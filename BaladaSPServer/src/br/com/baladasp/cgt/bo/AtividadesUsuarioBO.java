package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.AtividadeUsuario;
import br.com.baladasp.cdp.usuario.Avaliacao;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings("rawtypes")
public class AtividadesUsuarioBO extends AbstractCRUD {

	@SuppressWarnings("unchecked")
	public AtividadesUsuarioBO() {
		classeBO = new BaseBO<Avaliacao>(AtividadeUsuario.class);
		classeDAO = new AbstractSelectDAO<AtividadeUsuario>(AtividadeUsuario.class);
	}

	@SuppressWarnings("unchecked")
	public void salvarAtividade(AtividadeUsuario atividade) {
		atividade.checkinUsuario();
		save(atividade);
	}

	public AtividadeUsuario consultarAtividade(long id) {
		return (AtividadeUsuario) findByID(id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AtividadeUsuario> consultarDezUltimasAtividadesUsuario(Usuario usuario) {
		String namedQuery = "AtividadeUsuario.findByUsuario";

		return (ArrayList<AtividadeUsuario>) findByParameterWithMaxResults(namedQuery, usuario);
	}
}
