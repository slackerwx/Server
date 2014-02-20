package br.com.baladasp.cdp.estabelecimento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_musica")
public class Musica {
	@Id
	@GeneratedValue
	@Column(name = "cod_musica")
	private long id;

	private String musica;

	public Musica() {

	}

	public Musica(String musica) {
		this.musica = musica;

		if (musica.equalsIgnoreCase("rock")) {
			this.id = 1;
		} else if (musica.equalsIgnoreCase("pop rock")) {
			this.id = 2;
		} else if (musica.equalsIgnoreCase("mpb")) {
			this.id = 3;
		} else if (musica.equalsIgnoreCase("sertanejo")) {
			this.id = 4;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String musica) {
		this.musica = musica;
	}

	@Override
	public String toString() {
		return "Musica [id=" + id + ", Musica=" + musica + "]";
	}

}
