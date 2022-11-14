package br.com.outlier.rascunhospringrestapi.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import br.com.outlier.rascunhospringrestapi.model.entity.Curso;

@DataJpaTest
@ActiveProfiles("test2")
@AutoConfigureTestDatabase(replace = Replace.NONE) // Por padrão, spring utiliza o H2 nos testes. Força o spring a NAO
													// sobreeescrever as configurações do DB com H2, caso estejamos usando OUTRO
													// tipo de banco.
class CursoRepositoryTest2 {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TestEntityManager em;

	@Test
	public void givenIdShouldFindCurso() {
		em.persist(Curso.builder().nome("HTML 5").build());
		Long id = 1l;
		Optional<Curso> curso = cursoRepository.findById(id);
		Assert.notNull(curso.orElse(null), String.format("Objeto de id <%s> não foi encontrado.", id));
	}

	@Test
	public void givenIdShouldNotFindCurso() {
		Long id = 1l;
		Optional<Curso> curso = cursoRepository.findById(id);
		Assert.isNull(curso.orElse(null), String.format("Objeto de id <%s> foi encontrado e não deveria ter sido.", id));
	}

}
