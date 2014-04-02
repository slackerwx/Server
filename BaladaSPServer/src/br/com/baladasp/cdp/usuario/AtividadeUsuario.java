package br.com.baladasp.cdp.usuario;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.google.gson.annotations.Expose;

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
	@Expose private long id;

	@Expose private String tipoAtividade;
	@Temporal(TemporalType.TIMESTAMP)
	@Expose private Calendar dataAtividade = Calendar.getInstance();

	@OneToOne(cascade = CascadeType.ALL)
	@Expose private Usuario usuario;

	@OneToOne(cascade = CascadeType.ALL)
	@Expose private Checkin checkin;

	@OneToOne(cascade = CascadeType.ALL)
	@Expose private Avaliacao avaliacao;

	public AtividadeUsuario() {

	}

	public AtividadeUsuario(Usuario usuarioTwitter, Checkin checkin) {
		this.usuario = usuarioTwitter;
		this.checkin = checkin;
		this.tipoAtividade = "Checkin";
	}

	public AtividadeUsuario(Usuario usuarioTwitter, Avaliacao avaliacao) {
		this.usuario = usuarioTwitter;
		this.avaliacao = avaliacao;
		avaliacao.setUsuario(usuarioTwitter);
		this.tipoAtividade = "Avaliacao";
	}

	public long getId() {
		return id;
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

	public Calendar getDataAtividade() {
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

	public void checkinUsuario() {
		OperacaoAtividadeUsuario op = getTipoAtividade();
		usuario.checkinUsuario(op.getEstabelecimento().getCategoria());
	}

	@Override
	public String toString() {
		return "AtividadeUsuario [id=" + id + ", tipoAtividade=" + tipoAtividade + ", usuario=" + usuario
				+ ", checkin=" + checkin + ", avaliacao=" + avaliacao + "]";
	}

}
