package br.com.baladasp.cgt.usuario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

@NamedQueries({

@NamedQuery(name = "Checkin.findByAtividadeUsuario", query = "from Checkin as a where a.atividadeUsuario = :parametro order by cod_checkin DESC") })
@Entity
@Table(name = "tb_checkin")
public class Checkin extends OperacaoAtividadeUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Checkin() {

	}

	public Checkin(Estabelecimento estabelecimento) {
		super(estabelecimento);
	}

	@Override
	public String toString() {
		return "Checkin [id=" + id + ", dataCheckin=" + dataAtividade + ", usuario=" + atividadeUsuario + ", estabelecimento=" + estabelecimento
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((atividadeUsuario == null) ? 0 : atividadeUsuario.hashCode());
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
		if (atividadeUsuario == null) {
			if (other.atividadeUsuario != null)
				return false;
		} else if (!atividadeUsuario.equals(other.atividadeUsuario))
			return false;
		return true;
	}

}
