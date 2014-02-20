package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.estabelecimento.Avaliacao;
import br.com.baladasp.cdp.usuario.AtividadesUsuario;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings("rawtypes")
public class AtividadesUsuarioBO extends AbstractCRUD {

	@SuppressWarnings("unchecked")
	public AtividadesUsuarioBO() {
		classeBO = new BaseBO<Avaliacao>(AtividadesUsuario.class);
		classeDAO = new AbstractSelectDAO<AtividadesUsuario>(AtividadesUsuario.class);
	}

	@SuppressWarnings("unchecked")
	public void salvarAtividade(AtividadesUsuario atividade) {

		save(atividade);
	}

	public AtividadesUsuario consultarAtividade(long id) {
		return (AtividadesUsuario) findByID(id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AtividadesUsuario> consultarDezUltimasAtividadesUsuario(Usuario usuario) {
		String namedQuery = "AtividadesUsuario.findByUsuario";
		
		return (ArrayList<AtividadesUsuario>) findByParameterWithMaxResults(namedQuery, usuario);
	}
}
