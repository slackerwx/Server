package br.com.baladasp.cdp.usuario;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.google.gson.annotations.Expose;

import br.com.baladasp.cdp.estabelecimento.Estabelecimento;

@NamedQueries({
		@NamedQuery(name = "Avaliacao.findByEstabelecimento", query = "from Avaliacao as e where e.estabelecimento = :parametro"),
		})
@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao extends OperacaoAtividadeUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose private int preco;
	@Expose private int atendimento;
	@Expose private int cardapio;
	@Expose private int ambiente;
	@Expose private int estacionamento;
	@Expose private int geral;
	@Expose private int atracao;
	@Expose private int localizacao;
	@Expose private String comentario;
	@Expose private float mediaAvaliacao;
	@Expose private float totalDePontos;

	public Avaliacao() {

	}

	public Avaliacao(Estabelecimento estabelecimento) {
		super(estabelecimento);
	}

	public Avaliacao(int preco, int atendimento, int cardapio, int ambiente, int estacionamento, int geral, int atracao, int localizacao,
			String comentario, Estabelecimento estabelecimento) {
		super();
		this.preco = preco;
		this.atendimento = atendimento;
		this.cardapio = cardapio;
		this.ambiente = ambiente;
		this.estacionamento = estacionamento;
		this.geral = geral;
		this.atracao = atracao;
		this.localizacao = localizacao;
		this.comentario = comentario;
		this.estabelecimento = estabelecimento;
		this.totalDePontos = ambiente + atendimento + atracao + cardapio + estacionamento + geral + localizacao + preco;
		this.mediaAvaliacao = totalDePontos / 8;

	}

	// JSON
	public Avaliacao(int preco, int atendimento, int cardapio, int ambiente, int estacionamento, int geral, int atracao, int localizacao,
			String comentario) {
		super();
		this.preco = preco;
		this.atendimento = atendimento;
		this.cardapio = cardapio;
		this.ambiente = ambiente;
		this.estacionamento = estacionamento;
		this.geral = geral;
		this.atracao = atracao;
		this.localizacao = localizacao;
		this.comentario = comentario;
		this.totalDePontos = ambiente + atendimento + atracao + cardapio + estacionamento + geral + localizacao + preco;
		this.mediaAvaliacao = totalDePontos / 8;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}

	public int getAtendimento() {
		return atendimento;
	}

	public int getCardapio() {
		return cardapio;
	}

	public int getAmbiente() {
		return ambiente;
	}

	public int getEstacionamento() {
		return estacionamento;
	}

	public int getGeral() {
		return geral;
	}

	public int getAtracao() {
		return atracao;
	}

	public int getLocalizacao() {
		return localizacao;
	}

	public String getComentario() {
		return comentario;
	}

	public Calendar getDataAvaliacao() {
		return dataAtividade;
	}

	public float getTotaldePontos() {
		return totalDePontos;
	}

	public float getMediaAvaliacao() {
		return mediaAvaliacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
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
		Avaliacao other = (Avaliacao) obj;
		if (id != other.id)
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Avaliacao [id=" + id + ", preco=" + preco + ", atendimento=" + atendimento + ", cardapio=" + cardapio + ", ambiente=" + ambiente
				+ ", estacionamento=" + estacionamento + ", geral=" + geral + ", atracao=" + atracao + ", localizacao=" + localizacao
				+ ", comentario=" + comentario + ", mediaAvaliacao=" + mediaAvaliacao + ", totalDePontos=" + totalDePontos + ", dataAtividade="
				+ dataAtividade + ", estabelecimento=" + estabelecimento + "]";
	}

}