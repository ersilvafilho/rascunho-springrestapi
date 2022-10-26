package br.com.outlier.rascunhospringrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class GenericController<ENTITY, ID, REPO extends JpaRepository<ENTITY, ID>> {

	@Autowired
	REPO repository;

	@GetMapping
	public ResponseEntity<List<ENTITY>> listar() {
		return ResponseEntity.ok(repository.findAll());
	}

}
