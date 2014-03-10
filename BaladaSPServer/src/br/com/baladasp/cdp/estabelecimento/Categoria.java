package br.com.baladasp.cdp.estabelecimento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import br.com.baladasp.cdp.constantes.EnumCategoriasTopDez;

@Entity
@Table(name = "tb_categoria")
public class Categoria {
	@Id
	@GeneratedValue
	@Column(name = "cod_categoria")
	@Expose private long idCategoria;

	@Expose  private String categoria;

	public Categoria() {

	}

	public Categoria(long id, String categoria) {
		this.idCategoria = id;
		this.categoria = categoria;
	}

	public Categoria(String categoria) {
		this.categoria = categoria;

		if (categoria.equalsIgnoreCase(EnumCategoriasTopDez.BARES.toString())) {
			this.idCategoria = 1;
		} else if (categoria.equalsIgnoreCase(EnumCategoriasTopDez.BALADAS.toString())) {
			this.idCategoria = 2;
		} else if (categoria.equalsIgnoreCase(EnumCategoriasTopDez.RESTAURANTES.toString())) {
			this.idCategoria = 3;
		}
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", categoria=" + categoria + "]";
	}

}
