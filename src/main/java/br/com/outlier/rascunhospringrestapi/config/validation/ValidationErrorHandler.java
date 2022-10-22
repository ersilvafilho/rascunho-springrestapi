package br.com.outlier.rascunhospringrestapi.config.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException methodArgumentNotValidException) {

		List<ValidationErrorDTO> errorsList = new ArrayList<>();

		methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fe -> {
			String mensagem = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
			errorsList.add(ValidationErrorDTO.builder().message(mensagem).field(fe.getField()).build());
		});

		return ResponseEntity.badRequest().body(Map.of("responseContent", Map.of("validationErrors", errorsList)));
	}

}
