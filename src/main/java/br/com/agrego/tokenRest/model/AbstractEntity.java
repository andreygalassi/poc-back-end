package br.com.agrego.tokenRest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false,  columnDefinition = "bit default 1")
	private Boolean ativo=true;
	
	@Version
	@Column
	private Long versao;
	
	@Column(nullable=false,updatable=false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dtCriacao;
	
//	@Column(columnDefinition="TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Column(nullable=false,updatable=false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dtEdicao;
	
	@PrePersist 
    protected void onCreate() {
		dtCriacao = new Date();
		dtEdicao = dtCriacao;
    }
	
	@PreUpdate
    protected void onUpdate() {
		dtEdicao = new Date();
    }
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Boolean getAtivo() {
		return ativo;
	}


	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


	public Date getDtEdicao() {
		return dtEdicao;
	}


	public void setDtEdicao(Date dtEdicao) {
		this.dtEdicao = dtEdicao;
	}


	public Long getVersao() {
		return versao;
	}


	public Date getDtCriacao() {
		return dtCriacao;
	}
}
