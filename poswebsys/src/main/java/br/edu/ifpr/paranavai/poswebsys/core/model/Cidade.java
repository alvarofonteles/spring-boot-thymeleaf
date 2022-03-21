package br.edu.ifpr.paranavai.poswebsys.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(unique = true, nullable = false)
	private String codigo;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotBlank
	@Column(nullable = false)
	private String uf;

	@Override
	public String toString() {
		return "Cidade [nome=" + nome + ", UF=" + uf + "]";
	}

	public String getNomeUF() {
		return String.format("%1$s (%2$s)", getNome(), getUf());
	}

}