package br.com.baladasp.cdp.estabelecimento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_promocao")
public class Promocao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1310268025185974714L;

	@Id
	@GeneratedValue
	@Column(name = "cod_promocao")
	private Long promocao;

	@ManyToOne
	@JoinColumn(name = "cod_estab")
	private Estabelecimento estabelecimento;

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Long getPromocao() {
		return promocao;
	}

	public void setPromocao(Long promocao) {
		this.promocao = promocao;
	}

	@Override
	public String toString() {
		return "Promocao " + "[promocao=" + promocao + "]";
	}

}