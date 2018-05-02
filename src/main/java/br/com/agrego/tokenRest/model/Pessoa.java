package br.com.agrego.tokenRest.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pessoa implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Pessoa newInstance(String nome, String dtNascimento, Cargo cargo) throws ParseException{
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setCargo(cargo);
		pessoa.setDtNascimento(sdf.parse(dtNascimento));
		
		return pessoa;
	}
		
	@Id
	@GeneratedValue
	private Long id;

	private String nome;
	private String sobreNome;
	private Date dtNascimento;

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private Cargo cargo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Pessoa [id=%s, nome=%s, sobreNome=%s, nascimento=%s, cargo=%s]", id, nome, sobreNome,
				dtNascimento, cargo);
	}
	
	
	

	
}
