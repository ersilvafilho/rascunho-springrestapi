package br.com.outlier.rascunhospringrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.outlier.rascunhospringrestapi.model.entity.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>, JpaSpecificationExecutor<Resposta> {

}
