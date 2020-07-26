package br.com.agrego.poc.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;

@Entity
public class Colaborador  extends AbstractEntity {
	 
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
//	public static Pessoa newInstance(String nome, String dtNascimento, Cargo cargo) throws ParseException{
//		Pessoa pessoa = new Pessoa();
//		pessoa.setNome(nome);
//		pessoa.setCargo(cargo);
//		pessoa.setDtNascimento(sdf.parse(dtNascimento));
//		
//		return pessoa;
//	}
	
	public static Colaborador newInstance(
			String nome, String sobrenome, 
			String email, String salario, 
			String dtNascimento, Cargo cargo) throws ParseException{
		Colaborador bean = new Colaborador();
		bean.setNome(nome);
		bean.setSobreNome(sobrenome);
		bean.setEmail(email);
		if (salario!=null) bean.setSalario(new BigDecimal(salario));
		bean.setCargo(cargo);
		bean.setDtNascimento(sdf.parse(dtNascimento));
		
		return bean;
	}
	
	@NotBlank(message="Nome do cargo é obrigatório")
	private String nome;

	private String sobreNome;
	
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	@Temporal(value = TemporalType.DATE)
	private Date dtAdmissao;
	
	@Column(precision = 20, scale = 4)
	@DecimalMin(value="0.0000",message="Salário deve ser um valor positivo.")
	private BigDecimal salario;
	
	@Column(name="EMAIL")
	@Email(message="Não é um email válido")
	private String email;
	
	@Transient
	private Integer idade;

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private Cargo cargo;

	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO_CIVIL")
	private EnumEstadoCivil estadoCivil;
	
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
	public String toString() {
		return String.format("Pessoa [id=%s, nome=%s, sobreNome=%s, nascimento=%s, cargo=%s]", getId(), nome, sobreNome,
				dtNascimento, cargo);
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	
	

	
}
