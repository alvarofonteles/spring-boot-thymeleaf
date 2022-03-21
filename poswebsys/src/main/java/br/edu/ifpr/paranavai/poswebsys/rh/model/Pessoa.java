package br.edu.ifpr.paranavai.poswebsys.rh.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpr.paranavai.poswebsys.core.model.Cidade;
import lombok.Data;

@Entity
@Data
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "O campo nome não pode ser vazio")
	@Column(nullable = false)
	private String nome;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;

	@CPF
	@NotBlank
	@Column(nullable = false)
	private String cpf;

	@Email
	private String email;

	private String telefone;

	@ManyToOne(optional = false)
	@NotNull(message = "Selecione uma cidade")
	private Cidade cidade;

	@ManyToOne(optional = false)
	@NotNull(message = "Selecione um departamento")
	private Departamento departamento;

	public String getNomeCidade() {
		if (cidade != null) {
			return cidade.getNomeUF();
		}
		return "";
	}

	public String getNomeDepartamento() {
		if (departamento != null) {
			return departamento.getNome();
		}
		return "";
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + "]";
	}
}