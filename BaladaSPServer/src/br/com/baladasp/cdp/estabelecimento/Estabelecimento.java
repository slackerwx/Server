package br.com.baladasp.cdp.estabelecimento;

import java.io.Serializable;
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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.google.gson.annotations.Expose;

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
	@Expose	private long id;
	@Expose private String nome;
	@Expose private String descricaoEstabelecimento;
	@Expose private String urlLogo;
	@Expose	private String site;
	@Expose	private String facebook;
	@Expose	private String twitter;
	@Expose	private String youtube;
	@Expose	private String horarioFuncionamento;
	@Expose	private String telefone;
	@Expose	private String classificacaoEtaria;
	@Expose	private String evento;
	@Expose	private String publico;
	@Expose	private String decoracao;
	@Expose	private String ambiente;
	@Expose	private boolean delivery;
	@Expose	private boolean estacionamento;
	@Expose	private boolean wifi;
	@Expose	private int qtdAvaliacoes;
	
	//TODO implementar
	@Expose private float mediaAvaliacoes;
	
	//TODO implementar
	@Expose private int qtdCheckins;

	@OneToOne
	@Expose	private Musica musica;
	@OneToOne
	@Expose	private Categoria categoria;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn(name = "cod_estab")
	@Expose	private Endereco endereco;

	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Expose	private Set<Promocao> promocoes;

	public Estabelecimento() {

	}

	public Estabelecimento(long id, String nome, String descricaoEstabelecimento, String urlLogo, String site, String facebook, String twitter,
			String youtube, String horarioFuncionamento, String telefone, boolean delivery, boolean estacionamento, boolean wifi,
			String classificacaoEtaria, String evento, String publico, String decoracao, String ambiente, Musica musica, Categoria categoria,
			Endereco endereco, int qtdAvaliacoes) {

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
		this.endereco = endereco;
		this.qtdAvaliacoes = qtdAvaliacoes;
	}

	public int getQtdAvaliacoes() {
		return qtdAvaliacoes;
	}

	public void aumentarQtdAvaliacoes() {
		this.qtdAvaliacoes++;
	}
	
	public int getQtdCheckins(){
		return qtdCheckins;
	}
	
	public void aumentarQtdCheckins(){
		this.qtdCheckins++;
	}

	public String getHorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public void setHorarioFuncionamento(String horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
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

	public float getMediaAvaliacoes() {
		return mediaAvaliacoes;
	}
	
	public void setMediaAvaliacoes(float mediaAvaliacoes){
		this.mediaAvaliacoes = mediaAvaliacoes;
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

	@Override
	public String toString() {
		return "Estabelecimento [id=" + id + ", nome=" + nome + ", descricaoEstabelecimento=" + descricaoEstabelecimento + ", urlLogo=" + urlLogo
				+ ", site=" + site + ", facebook=" + facebook + ", twitter=" + twitter + ", youtube=" + youtube + ", horarioFuncionamento="
				+ horarioFuncionamento + ", telefone=" + telefone + ", classificacaoEtaria=" + classificacaoEtaria + ", evento=" + evento
				+ ", publico=" + publico + ", decoracao=" + decoracao + ", ambiente=" + ambiente + ", delivery=" + delivery + ", estacionamento="
				+ estacionamento + ", wifi=" + wifi + ", musica=" + musica + ", categoria=" + categoria + ", endereco=" + endereco
				+ ", qtdAvaliacoes=" + qtdAvaliacoes + ", promocoes=" + promocoes + "]";
	}

}
