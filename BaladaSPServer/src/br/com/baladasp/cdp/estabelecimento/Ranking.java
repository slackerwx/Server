package br.com.baladasp.cdp.estabelecimento;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.google.gson.annotations.Expose;

@NamedQueries({
		@NamedQuery(name = "Ranking.findByCategoria", query = "from Ranking as r where r.categoria = :parametro order by mediaavaliacoes desc"),
		@NamedQuery(name = "Ranking.findByEstabelecimento", query = "from Ranking as r where r.estabelecimento = :parametro") })
@Entity
@Table(name = "tb_ranking")
public class Ranking {

	@Id
	@GeneratedValue
	@Column(name = "cod_ranking")
	@Expose long id;

	@Expose float pontos;
	@Expose float mediaAvaliacoes;

	@OneToOne
	@Expose private Categoria categoria;
	@OneToOne(cascade = CascadeType.ALL)
	@Expose private Estabelecimento estabelecimento;

	public Ranking() {

	}

	public Ranking(long id, long pontos, Categoria categoria) {
		this.id = id;
		this.pontos = pontos;
		this.categoria = categoria;
	}

	public Ranking(Estabelecimento estabelecimento, float media, float pontos) {
		this.estabelecimento = estabelecimento;
		this.categoria = estabelecimento.getCategoria();
		this.mediaAvaliacoes = media;
		this.pontos = pontos;
	}

	// JSON
	public Ranking(long id, long pontos, Categoria categoria, float mediaAvaliacoes) {
		this.id = id;
		this.pontos = pontos;
		this.categoria = categoria;
		this.mediaAvaliacoes = mediaAvaliacoes;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void getCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPontos() {
		return pontos;
	}

	public void setPontos(float total) {
		this.pontos = total;
	}

	public float getMediaAvaliacoes() {
		return mediaAvaliacoes;
	}

	public void setMediaAvaliacoes(float mediaAvaliacoes) {
		this.mediaAvaliacoes = mediaAvaliacoes;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
		estabelecimento.setMediaAvaliacoes(mediaAvaliacoes);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Ranking other = (Ranking) obj;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ranking [id=" + id + ", pontos=" + pontos + ", mediaAvaliacoes=" + mediaAvaliacoes + ", categoria="
				+ categoria + ", estabelecimento=" + estabelecimento + "]";
	}

}