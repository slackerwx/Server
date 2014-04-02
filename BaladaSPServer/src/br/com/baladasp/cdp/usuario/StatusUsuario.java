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

import com.google.gson.annotations.Expose;

@NamedQueries({ @NamedQuery(name = "StatusUsuarios.getAll", query = "from StatusUsuario order by createdAt DESC") })
@Entity
@Table(name = "tb_status_usuarios")
public class StatusUsuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	@Expose private long id;

	@Expose private String createdAt;
	@Expose private String nomeEstabelecimento;
	@Expose private String linkImagem;
	@Expose private String mediaURL;

	@OneToOne(optional=false)
	@Expose
	private Usuario usuario;

	public StatusUsuario() {

	}

	public StatusUsuario(String date, String nomeEstabelecimento, String linkImagem, String mediaURL, Usuario usuario) {
		super();
		this.createdAt = date;
		this.nomeEstabelecimento = nomeEstabelecimento;
		this.linkImagem = linkImagem;
		this.mediaURL = mediaURL;
		this.usuario = usuario;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setText(String text) {
		this.nomeEstabelecimento = text;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getNomeEstabelecimento() {

		return this.nomeEstabelecimento;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}

	@Override
	public String toString() {
		return "StatusUsuario [id=" + id + ", createdAt=" + createdAt + ", text=" + nomeEstabelecimento + ", usuario=" + usuario + "]";
	}

}
