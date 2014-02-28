package br.com.baladasp.cdp.usuario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.baladasp.cdp.constantes.EnumCategoriasTopDez;
import br.com.baladasp.cdp.estabelecimento.Categoria;

@NamedQueries({ @NamedQuery(name = "Usuario.findByIDTwitter", query = "from Usuario as u where u.IDTwitter like :parametro") })
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	private long IDTwitter;
	private String screenName;
	private String urlImagem;
	private String usuario;

	private int qtdCheckins;
	private int bares;
	private int baladas;
	private int restaurantes;

	public Usuario() {

	}

	public Usuario(String usuario, long IDTwitter, String screenName, String urlImagem) {
		super();
		this.usuario = usuario;
		this.IDTwitter = IDTwitter;
		this.screenName = screenName;
		this.urlImagem = urlImagem;
	}

	public long getIDTwitter() {
		return IDTwitter;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public int getQtdCheckins() {
		return qtdCheckins;
	}

	public int getBares() {
		return bares;
	}

	public int getBaladas() {
		return baladas;
	}

	public int getRestaurantes() {
		return restaurantes;
	}

	public void checkinUsuario(Categoria categoria) {
		if (categoria.getCategoria().equalsIgnoreCase(EnumCategoriasTopDez.BARES.toString())) {
			this.bares += 1;
		} else if (categoria.getCategoria().equalsIgnoreCase(EnumCategoriasTopDez.BALADAS.toString())) {
			this.baladas += 1;
		} else if (categoria.getCategoria().equalsIgnoreCase(EnumCategoriasTopDez.RESTAURANTES.toString())) {
			this.restaurantes += 1;
		}

		this.qtdCheckins += 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (IDTwitter ^ (IDTwitter >>> 32));
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
		Usuario other = (Usuario) obj;
		if (IDTwitter != other.IDTwitter)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", IDTwitter=" + IDTwitter + ", screenName=" + screenName + ", urlImagem="
				+ urlImagem + ", qtdCheckins=" + qtdCheckins + ", bares=" + bares + ", baladas=" + baladas + ", restaurantes="
				+ restaurantes + "]";
	}

}