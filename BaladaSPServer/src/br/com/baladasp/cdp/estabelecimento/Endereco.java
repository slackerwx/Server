package br.com.baladasp.cdp.estabelecimento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2182841615936028101L;

	@Id
	@GeneratedValue(generator = "fk_endereco_cod_estab")
	@GenericGenerator(name = "fk_endereco_cod_estab", strategy = "foreign", parameters = @Parameter(name = "property", value = "estabelecimento"))
	@Column(name = "cod_estab", nullable = false)
	@Expose private long endereco;

	@Expose private String rua;
	@Expose private String numero;
	@Expose private String bairro;
	@Expose private String cep;
	
	@OneToOne
	@Expose private Regiao regiao;

	public Endereco() {

	}

	public Endereco(long endereco, String rua, String numero, String bairro, String cep, Regiao regiao) {
		super();
		this.endereco = endereco;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.regiao = regiao;
	}

	public Endereco(String rua, String numero, String bairro, String cep, Regiao regiao) {
		super();
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.regiao = regiao;
	}

	public Long getEndereco() {
		return endereco;
	}

	public void setEndereco(Long endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
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
		Endereco other = (Endereco) obj;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n\nEndereco " + "[rua=" + rua + ", numero=" + numero + ", bairro=" + bairro + ", regiao=" + regiao + "]";
	}

}