package br.com.baladasp.cdp.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

@NamedQueries({ @NamedQuery(name = "Checkin.findByUsuario", query = "from Checkin as a where a.usuario = :parametro order by cod_checkin DESC") })
@Entity
@Table(name = "tb_checkin")
public class Checkin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "cod_checkin")
	private long id;

	private String dataCheckin;

	@ManyToOne
	@JoinColumn(name = "IDTwitter", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "cod_estab", nullable = false)
	private Estabelecimento estabelecimento;

	public void setDataCheckin(String dataCheckin) {
		this.dataCheckin = dataCheckin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getDataCheckin() {
		return dataCheckin;
	}

	@Override
	public String toString() {
		return "Checkin [id=" + id + ", dataCheckin=" + dataCheckin + ", usuario=" + usuario + ", estabelecimento="
				+ estabelecimento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Checkin other = (Checkin) obj;
		if (id != other.id)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
