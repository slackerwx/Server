package br.com.baladasp.cdp.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.baladasp.cdp.estabelecimento.Avaliacao;

@NamedQueries({ @NamedQuery(name = "AtividadesUsuario.findByUsuario", query = "FROM AtividadesUsuario AS a WHERE a.usuario = :parametro") })
@Entity
@Table(name = "tb_atividades_usuario")
public class AtividadesUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "cod_atividade")
	private long id;

	private String tipoAtividade;
	private String dataAtividade;

	@OneToOne
	private Usuario usuario;

	@OneToOne
	private Checkin checkin;

	@OneToOne
	private Avaliacao avaliacao;

	public AtividadesUsuario(Usuario usuarioTwitter) {
		this.usuario = usuarioTwitter;
	}

	public AtividadesUsuario() {
	}

	public Usuario getUsuarioTwitter() {
		return usuario;
	}

	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public String getDataAtividade() {
		return dataAtividade;
	}

	public Checkin getCheckin() {
		return checkin;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setUsuario(Usuario usuarioTwitter) {
		this.usuario = usuarioTwitter;
	}

	public void setCheckin(Checkin checkin) {
		this.checkin = checkin;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

}
