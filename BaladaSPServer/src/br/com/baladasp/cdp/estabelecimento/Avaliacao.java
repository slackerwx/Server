package br.com.baladasp.cdp.estabelecimento;

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

import br.com.baladasp.cdp.usuario.Usuario;

@NamedQueries({
		@NamedQuery(name = "Avaliacao.findByEstabelecimento", query = "from Avaliacao as e where e.estabelecimento = :parametro"),
		@NamedQuery(name = "Avaliacao.findByUsuario", query = "from Avaliacao as a where a.usuario = :usuario order by cod_avaliacao DESC") })
@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	@Id
	@GeneratedValue
	@Column(name = "cod_avaliacao")
	private long avaliacao;

	private int preco;
	private int atendimento;
	private int cardapio;
	private int ambiente;
	private int estacionamento;
	private int geral;
	private int atracao;
	private int localizacao;
	private int mediaAvaliacao;
	private String comentario;
	private String dataAvaliacao;

	@ManyToOne
	@JoinColumn(name = "cod_estab", nullable = false)
	private Estabelecimento estabelecimento;

	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = false)
	private Usuario usuario;

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

	public Long getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Long avaliacao) {
		this.avaliacao = avaliacao;
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

	public void setAtendimento(int atendimento) {
		this.atendimento = atendimento;
	}

	public int getCardapio() {
		return cardapio;
	}

	public void setCardapio(int cardapio) {
		this.cardapio = cardapio;
	}

	public int getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(int ambiente) {
		this.ambiente = ambiente;
	}

	public int getEstacionamento() {
		return estacionamento;
	}

	public void setEstacionamento(int estacionamento) {
		this.estacionamento = estacionamento;
	}

	public int getGeral() {
		return geral;
	}

	public void setGeral(int geral) {
		this.geral = geral;
	}

	public int getAtracao() {
		return atracao;
	}

	public void setAtracao(int atracao) {
		this.atracao = atracao;
	}

	public int getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(int localizacao) {
		this.localizacao = localizacao;
	}

	public int getMedia() {
		return mediaAvaliacao;
	}

	public void setMedia(int total) {
		this.mediaAvaliacao = total;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (avaliacao ^ (avaliacao >>> 32));
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
		if (avaliacao != other.avaliacao)
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
		return "Avaliacao [avaliacao=" + avaliacao + ", preco=" + preco + ", atendimento=" + atendimento + ", cardapio="
				+ cardapio + ", ambiente=" + ambiente + ", estacionamento=" + estacionamento + ", geral=" + geral + ", atracao="
				+ atracao + ", localizacao=" + localizacao + ", total=" + mediaAvaliacao + ", comentario=" + comentario
				+ ", dataAvaliacao=" + dataAvaliacao + "]";
	}

}