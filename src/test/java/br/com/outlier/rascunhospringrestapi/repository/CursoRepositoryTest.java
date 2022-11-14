package br.com.outlier.rascunhospringrestapi.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import br.com.outlier.rascunhospringrestapi.model.entity.Curso;

@DataJpaTest
@ActiveProfiles("test")
class CursoRepositoryTest {

	@Autowired
	private CursoRepository cursoRepository;

	@Test
	public void givenIdShouldFindCurso() {
		Long id = 1l;
		Optional<Curso> curso = cursoRepository.findById(id);
		Assert.notNull(curso.orElse(null), String.format("Objeto de id <%s> não foi encontrado.", id));
	}

	@Test
	public void givenIdShouldNotFindCurso() {
		Long id = 4l;
		Optional<Curso> curso = cursoRepository.findById(id);
		Assert.isNull(curso.orElse(null), String.format("Objeto de id <%s> foi encontrado e não deveria ter sido.", id));
	}

}
