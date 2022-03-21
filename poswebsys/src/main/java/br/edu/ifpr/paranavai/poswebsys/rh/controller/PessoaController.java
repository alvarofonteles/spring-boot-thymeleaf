package br.edu.ifpr.paranavai.poswebsys.rh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.ifpr.paranavai.poswebsys.core.model.Cidade;
import br.edu.ifpr.paranavai.poswebsys.core.repository.CidadeRepository;
import br.edu.ifpr.paranavai.poswebsys.rh.model.Departamento;
import br.edu.ifpr.paranavai.poswebsys.rh.model.Pessoa;
import br.edu.ifpr.paranavai.poswebsys.rh.model.dtos.AutoCompleteDTO;
import br.edu.ifpr.paranavai.poswebsys.rh.repository.DepartamentoRepository;
import br.edu.ifpr.paranavai.poswebsys.rh.repository.PessoaRepository;

@Controller
@Valid
@RequestMapping("/rh/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private DepartamentoRepository departamentoRepo;

	private List<Departamento> departamentosFiltrados = new ArrayList<>();
	private List<Cidade> cidadesFiltradas = new ArrayList<>();

	@GetMapping
	private String listaPessoas(Model model) {
		model.addAttribute("listaPessoas", pessoaRepository.findAll());
		return "/rh/pessoas/index";
	}

	@GetMapping("/nova")
	public String novaPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("cidades", cidadeRepo.findAll());
		return "rh/pessoas/form";
	}

	@PostMapping("/salvar")
	public String salvarPessoa(@Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("cidades", cidadeRepo.findAll());
			return "rh/pessoas/form";
		}

		pessoaRepository.save(pessoa);
		return "redirect:/rh/pessoas";
	}

	@GetMapping("/{id}")
	public String alterarPessoa(@PathVariable("id") long id, Model model) {
		Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
		if (!pessoaOpt.isPresent()) {
			throw new IllegalArgumentException("Pessoa inválida.");
		}

		model.addAttribute("pessoa", pessoaOpt.get());
		model.addAttribute("cidades", cidadeRepo.findAll());

		return "rh/pessoas/form";
	}

	@GetMapping("/excluir/{id}")
	public String excluirPessoa(@PathVariable("id") long id) {
		Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
		if (pessoaOpt.isEmpty()) {
			throw new IllegalArgumentException("Pessoa inválida.");
		}

		pessoaRepository.delete(pessoaOpt.get());
		return "redirect:/rh/pessoas";
	}

	@RequestMapping("/departamentosNomeAutoComplete")
	@ResponseBody
	public List<AutoCompleteDTO> departamentosNomeAutoComplete(
			@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<AutoCompleteDTO> sugestoes = new ArrayList<>();
		try {
			if (term.length() >= 3) {
				departamentosFiltrados = departamentoRepo.searchByNome(term);
			}

			for (Departamento departamento : departamentosFiltrados) {
				if (departamento.getNome().toLowerCase().contains(term.toLowerCase())) {

					AutoCompleteDTO dto = new AutoCompleteDTO();
					dto.setLabel(departamento.getNome());
					dto.setValue(Long.toString(departamento.getId()));
					sugestoes.add(dto);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sugestoes;
	}

	@RequestMapping("/cidadesNomeAutoComplete")
	@ResponseBody
	public List<AutoCompleteDTO> cidadesNomeAutoComplete(
			@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<AutoCompleteDTO> sugestoes = new ArrayList<>();

		try {
			if (term.length() >= 3) {
				cidadesFiltradas = cidadeRepo.searchByNome(term);
			}

			for (Cidade cidade : cidadesFiltradas) {
				if (cidade.getNome().toLowerCase().contains(term.toLowerCase())) {

					AutoCompleteDTO dto = new AutoCompleteDTO();
					dto.setLabel(cidade.getNomeUF());
					dto.setValue(Long.toString(cidade.getId()));
					sugestoes.add(dto);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sugestoes;
	}
}
