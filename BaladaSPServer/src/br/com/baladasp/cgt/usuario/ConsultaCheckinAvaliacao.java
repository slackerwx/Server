package br.com.baladasp.cgt.usuario;

import br.com.baladasp.cdp.usuario.Usuario;

public class ConsultaCheckinAvaliacao {

	private String nomeEstabelecimento;
	private Usuario usuarioTwitter;

	public ConsultaCheckinAvaliacao(Usuario usuarioTwitter, String nomeEstabelecimento) {
		super();
		this.nomeEstabelecimento = nomeEstabelecimento;
		this.usuarioTwitter = usuarioTwitter;
	}

	public String getNomeEstabelecimento() {
		return nomeEstabelecimento;
	}

	public Usuario getUsuarioTwitter() {
		return usuarioTwitter;
	}

}
