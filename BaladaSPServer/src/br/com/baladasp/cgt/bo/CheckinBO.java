package br.com.baladasp.cgt.bo;

import java.util.ArrayList;

import br.com.baladasp.cdp.usuario.Checkin;
import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.cgd.dao.AbstractSelectDAO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CheckinBO extends AbstractCRUD {

	public CheckinBO() {
		classeBO = new BaseBO<Checkin>(Checkin.class);
		classeDAO = new AbstractSelectDAO<Checkin>(Checkin.class);
	}

	public void salvarCheckin(Checkin checkin) {
		save(checkin);
	}

	public ArrayList<Checkin> consultarDezUltimosCheckinsUsuario(Usuario usuario) {
		String namedQuery = "";
		return (ArrayList<Checkin>) findByParameterWithMaxResults(namedQuery, usuario);
	}

}