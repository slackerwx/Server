package br.com.baladasp.cgt.bo;

import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UsuarioBO extends AbstractCRUD {

	public UsuarioBO() {
		classeBO = new BaseBO<Usuario>(Usuario.class);
		classeDAO = new AbstractSelectDAO<Usuario>(Usuario.class);
	}

	public void inserirUsuario(Usuario usuario) {
		save(usuario);
	}

	public void atualizarUsuario(Usuario usuario) {
		update(usuario);
	}

	public Usuario consultarUsuarioID(long id) {
		return (Usuario) findByID(id);
	}

	public Usuario consultarUsuarioIDTwitter(long id) {
		String namedQuery = "Usuario.findByIDTwitter";
		return (Usuario) findByParameterUniqueResult(namedQuery, id);
	}
	
	public void excluirUsuario(Usuario usuario) {
		delete(usuario);
	}

}