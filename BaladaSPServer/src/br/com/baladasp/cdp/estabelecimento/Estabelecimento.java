package br.com.baladasp.cdp.estabelecimento;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({ @NamedQuery(name = "Estabelecimento.findByCategoria", query = "FROM Estabelecimento AS e WHERE e.categoria = :parametro") })
@Entity
@Table(name = "tb_estabelecimento")
public class Estabelecimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "cod_estab")
	private long id;
	private String nome;
	private String descricaoEstabelecimento;
	private String urlLogo;
	private String site;
	private String facebook;
	private String twitter;
	private String youtube;
	private String horarioFuncionamento;
	private String telefone;
	private String classificacaoEtaria;
	private String evento;
	private String publico;
	private String decoracao;
	private String ambiente;
	private boolean delivery;
	private boolean estacionamento;
	private boolean wifi;

	@OneToOne
	private Musica musica;
	@OneToOne
	private Categoria categoria;
	@OneToOne
	private Ranking ranking;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn(name = "cod_estab")
	private Endereco endereco;

	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Avaliacao> avaliacoes;

	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Promocao> promocoes;

	public Estabelecimento() {

	}

	public Estabelecimento(long id, String nome, String descricaoEstabelecimento, String urlLogo, String site, String facebook,
			String twitter, String youtube, String horarioFuncionamento, String telefone, boolean delivery,
			boolean estacionamento, boolean wifi, String classificacaoEtaria, String evento, String publico, String decoracao,
			String ambiente, Musica musica, Categoria categoria, Ranking ranking) {

		this.id = id;
		this.nome = nome;
		this.descricaoEstabelecimento = descricaoEstabelecimento;
		this.urlLogo = urlLogo;
		this.site = site;
		this.facebook = facebook;
		this.twitter = twitter;
		this.youtube = youtube;
		this.horarioFuncionamento = horarioFuncionamento;
		this.telefone = telefone;
		this.classificacaoEtaria = classificacaoEtaria;
		this.evento = evento;
		this.publico = publico;
		this.decoracao = decoracao;
		this.ambiente = ambiente;
		this.delivery = delivery;
		this.estacionamento = estacionamento;
		this.wifi = wifi;
		this.musica = musica;
		this.categoria = categoria;
		this.ranking = ranking;
	}

	public String getHorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public void setHorarioFuncionamento(String horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
	}

	public List<Avaliacao> getAvaliacao() {
		return avaliacoes;
	}

	public void setAvaliacao(List<Avaliacao> avaliacao) {
		this.avaliacoes = avaliacao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Promocao> getPromocao() {
		return promocoes;
	}

	public void setPromocao(Set<Promocao> promocao) {
		this.promocoes = promocao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String gethorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public void sethorarioFuncionamento(String horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

	public boolean isEstacionamento() {
		return estacionamento;
	}

	public void setEstacionamento(boolean estacionamento) {
		this.estacionamento = estacionamento;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public String getDecoracao() {
		return decoracao;
	}

	public void setDecoracao(String decoracao) {
		this.decoracao = decoracao;
	}

	public String getClassificacaoEtaria() {
		return classificacaoEtaria;
	}

	public void setClassificacaoEtaria(String classificacaoEtaria) {
		this.classificacaoEtaria = classificacaoEtaria;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Musica getMusica() {
		return musica;
	}

	public void setMusica(Musica musica) {
		this.musica = musica;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public String getDescricaoEstabelecimento() {
		return descricaoEstabelecimento;
	}

	public void setDescricaoEstabelecimento(String descricaoEstabelecimento) {
		this.descricaoEstabelecimento = descricaoEstabelecimento;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Estabelecimento other = (Estabelecimento) obj;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}