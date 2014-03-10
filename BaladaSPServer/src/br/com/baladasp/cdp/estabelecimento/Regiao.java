package br.com.baladasp.cdp.estabelecimento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "tb_regiao")
public class Regiao {

	@Id
	@GeneratedValue
	@Column(name = "cod_regiao")
	@Expose private long id;
	@Expose private String regiao;

	public Regiao() {

	}

	public Regiao(String regiao) {
		this.regiao = regiao;

		if (regiao.equalsIgnoreCase("norte")) {
			this.id = 1;
		} else if (regiao.equalsIgnoreCase("sul")) {
			this.id = 2;
		} else if (regiao.equalsIgnoreCase("leste")) {
			this.id = 3;
		} else if (regiao.equalsIgnoreCase("oeste")) {
			this.id = 4;
		} else if (regiao.equalsIgnoreCase("centro")) {
			this.id = 5;
		} else if (regiao.equalsIgnoreCase("abcd")) {
			this.id = 6;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((regiao == null) ? 0 : regiao.hashCode());
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
		Regiao other = (Regiao) obj;
		if (id != other.id)
			return false;
		if (regiao == null) {
			if (other.regiao != null)
				return false;
		} else if (!regiao.equals(other.regiao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Regiao [id=" + id + ", regiao=" + regiao + "]";
	}

}