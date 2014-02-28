package br.com.baladasp.cgt.usuario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.baladasp.cdp.usuario.Usuario;
import br.com.baladasp.util.Utils;

@NamedQueries({ @NamedQuery(name = "AtividadeUsuario.findByUsuario", query = "FROM AtividadeUsuario AS a WHERE a.usuario = :parametro order by cod_atividade DESC") })
@Entity
@Table(name = "tb_atividades_usuario")
public class AtividadeUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "cod_atividade")
	private long id;

	private String tipoAtividade;
	private String dataAtividade = Utils.formatarData(new Date());

	@OneToOne(cascade = CascadeType.ALL)
	private Usuario usuario;

	@OneToOne(cascade = CascadeType.ALL)
	private Checkin checkin;

	@OneToOne(cascade = CascadeType.ALL)
	private Avaliacao avaliacao;

	public AtividadeUsuario() {

	}

	public AtividadeUsuario(Usuario usuarioTwitter, Checkin checkin) {
		this.usuario = usuarioTwitter;
		this.checkin = checkin;
		this.tipoAtividade = "Checkin";
		checkin.setAtividadeUsuario(this);
	}

	public AtividadeUsuario(Usuario usuarioTwitter, Avaliacao avaliacao) {
		this.usuario = usuarioTwitter;
		this.avaliacao = avaliacao;
		this.tipoAtividade = "Avaliacao";
		avaliacao.setAtividadeUsuario(this);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public <T> T getTipoAtividade() {

		if (tipoAtividade.equals("Checkin")) {
			return (T) checkin;
		} else if (tipoAtividade.equals("Avaliacao")) {
			return (T) avaliacao;
		}

		return null;
	}

	public String getTipoAtividadeString() {

		if (tipoAtividade.equals("Checkin")) {
			return "Checkin";
		} else if (tipoAtividade.equals("Avaliacao")) {
			return "Avaliacao";
		}

		return null;
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

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	@Override
	public String toString() {
		return "AtividadeUsuario [id=" + id + ", tipoAtividade=" + tipoAtividade + ", dataAtividade=" + dataAtividade + ", checkin=" + checkin
				+ "]";
	}

	public void checkinUsuario() {
		OperacaoAtividadeUsuario op = getTipoAtividade();
		usuario.checkinUsuario(op.getEstabelecimento().getCategoria());
	}

}
