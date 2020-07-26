package br.com.agrego.poc.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cargo extends AbstractEntity {

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

	@NotBlank(message="Título do cargo é obrigatório")
	private String titulo;
	private String descricao;

	@JsonIgnore
	@OneToMany(mappedBy = "cargo", fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private Set<Colaborador> pessoas = new TreeSet<>();

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
	
	public Set<Colaborador> getPessoas() {
		return pessoas;
	}
	public void setPessoas(Set<Colaborador> pessoas) {
		this.pessoas = pessoas;
	}
	@Override
	public String toString() {
		return String.format("Cargo [id=%s, titulo=%s]", getId(), titulo);
	}

	
}
