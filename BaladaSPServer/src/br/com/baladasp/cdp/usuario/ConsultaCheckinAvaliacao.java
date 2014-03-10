package br.com.baladasp.cdp.usuario;

import com.google.gson.annotations.Expose;


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
