package br.edu.ifpr.paranavai.poswebsys;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.ifpr.paranavai.poswebsys.core.model.Cidade;
import br.edu.ifpr.paranavai.poswebsys.core.repository.CidadeRepository;
import br.edu.ifpr.paranavai.poswebsys.rh.model.Departamento;
import br.edu.ifpr.paranavai.poswebsys.rh.model.Pessoa;
import br.edu.ifpr.paranavai.poswebsys.rh.repository.DepartamentoRepository;
import br.edu.ifpr.paranavai.poswebsys.rh.repository.PessoaRepository;

@Component
public class PopulacaoInicialBanco implements CommandLineRunner {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private DepartamentoRepository departamentoRepo;

	@Override
	public void run(String... args) throws Exception {

		Cidade c1 = new Cidade();
		c1.setCodigo("1");
		c1.setNome("Forteleza");
		c1.setUf("CE");

		Cidade c2 = new Cidade();
		c2.setCodigo("2");
		c2.setNome("Maracanaú");
		c2.setUf("CE");

		cidadeRepo.save(c1);
		cidadeRepo.save(c2);
		cidadeRepo.flush();

		Departamento d1 = new Departamento();
		d1.setNome("Tecnologia da Informação");
		d1.setSigla("TI");

		departamentoRepo.save(d1);
		
		Departamento d2 = new Departamento();
		d2.setNome("Gestão de Projetos");
		d2.setSigla("TI");

		departamentoRepo.save(d2);
		
		Departamento d3 = new Departamento();
		d3.setNome("Gestão de Saúde");
		d3.setSigla("SUS");

		departamentoRepo.save(d3);
		
		Departamento d4 = new Departamento();
		d4.setNome("Tecnologia de Aviação");
		d4.setSigla("ITA");

		departamentoRepo.save(d4);
		departamentoRepo.flush();

		Pessoa p1 = new Pessoa();
		p1.setNome("Alvaro Fonteles");
		p1.setDataNascimento(LocalDate.of(1983, 7, 2));
		p1.setCpf("85405163066");
		p1.setEmail("alvarofonteles@gmail.com");
		p1.setTelefone("85996280751");
		p1.setCidade(c1);
		p1.setDepartamento(d1);

		Pessoa p2 = new Pessoa();
		p2.setNome("Aldo Fonteles");
		p2.setDataNascimento(LocalDate.of(1980, 12, 20));
		p2.setCpf("32472008007");
		p2.setEmail("aldofonteles@gmail.com");
		p2.setTelefone("32472008007");
		p2.setCidade(c2);
		p2.setDepartamento(d4);

		pessoaRepository.save(p1);
		pessoaRepository.save(p2);
	}

}
