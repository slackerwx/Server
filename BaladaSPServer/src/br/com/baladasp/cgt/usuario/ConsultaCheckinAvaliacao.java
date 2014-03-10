package br.com.baladasp.cgt.usuario;

import com.google.gson.annotations.Expose;

import br.com.baladasp.cdp.usuario.Usuario;

public class ConsultaCheckinAvaliacao {

	@Expose private String nomeEstabelecimento;
	@Expose private Usuario usuarioTwitter;

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
