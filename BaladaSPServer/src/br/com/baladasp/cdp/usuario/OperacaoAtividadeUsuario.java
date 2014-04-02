package br.com.baladasp.cdp.usuario;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

import com.google.gson.annotations.Expose;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OperacaoAtividadeUsuario {

	@Temporal(TemporalType.TIMESTAMP)
	@Expose protected Calendar dataAtividade = Calendar.getInstance();

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	@Expose protected long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@Expose protected Estabelecimento estabelecimento;

	public OperacaoAtividadeUsuario() {

	}

	public OperacaoAtividadeUsuario(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	
	public void setEstabelecimento(Estabelecimento estabelecimento){
		this.estabelecimento = estabelecimento;
	}

	public void setDataAtividade(Calendar dataAtividade) {
		this.dataAtividade = dataAtividade;
	}

	public Calendar getDataAtividade() {
		return dataAtividade;
	}

}
