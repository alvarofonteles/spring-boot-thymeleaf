package br.edu.ifpr.paranavai.poswebsys.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpr.paranavai.poswebsys.core.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Query("select c from Cidade c where lower(c.nome) like lower(concat(:termo, '%'))")
	List<Cidade> searchByNome(@Param("termo") String termo);
}