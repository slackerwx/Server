package br.com.baladasp.cgt.usuario;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;
import br.com.baladasp.util.Utils;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OperacaoAtividadeUsuario {

	protected String dataAtividade = Utils.formatarData(new Date());

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	protected long id;

	@ManyToOne
	protected AtividadeUsuario atividadeUsuario;

	@ManyToOne(cascade = CascadeType.ALL)
	protected Estabelecimento estabelecimento;

	public OperacaoAtividadeUsuario() {

	}

	public OperacaoAtividadeUsuario(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	
	public void setEstabelecimento(Estabelecimento estabelecimento){
		this.estabelecimento = estabelecimento;
	}

	public void setDataAtividade(String dataAtividade) {
		this.dataAtividade = dataAtividade;
	}

	public String getDataAtividade() {
		return dataAtividade;
	}

	public AtividadeUsuario getAtividadeUsuario() {
		return atividadeUsuario;
	}

	public void setAtividadeUsuario(AtividadeUsuario atividadeUsuario) {
		this.atividadeUsuario = atividadeUsuario;
	}

}
