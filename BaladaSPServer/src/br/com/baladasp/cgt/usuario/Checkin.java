package br.com.baladasp.cgt.usuario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

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
		return "Checkin [id=" + id + ", dataCheckin=" + dataAtividade +  ", estabelecimento=" + estabelecimento
				+ "]";
	}


}
