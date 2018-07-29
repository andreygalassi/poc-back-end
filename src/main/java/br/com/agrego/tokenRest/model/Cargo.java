package br.com.agrego.tokenRest.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cargo implements Serializable {
	 
	private static final long serialVersionUID = 1L;

	public static Cargo newInstance(String titulo){
		Cargo cargo = new Cargo();
		cargo.setTitulo(titulo);
		return cargo;
	}
	public static Cargo newInstance(Long id, String titulo){
		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setTitulo(titulo);
		return cargo;
	}
	
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank(message="Título do cargo é obrigatório")
	private String titulo;
	private String descricao;

	@JsonIgnore
	@OneToMany(mappedBy = "cargo", fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private Set<Pessoa> pessoas = new TreeSet<>();
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		Cargo other = (Cargo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Set<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	@Override
	public String toString() {
		return String.format("Cargo [id=%s, titulo=%s]", id, titulo);
	}

	
}
