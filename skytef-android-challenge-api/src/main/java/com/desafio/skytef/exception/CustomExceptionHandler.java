package com.desafio.skytef.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handle(EntityNotFoundException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DuplicateCardNumberException.class)
    public ResponseError handleApiException(DuplicateCardNumberException ex) {
        return new ResponseError(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String mensagem = messageSource.getMessage(Objects.requireNonNull(fieldError), LocaleContextHolder.getLocale());
        ResponseError erro = new ResponseError(mensagem.toUpperCase());
        return handleExceptionInternal(ex, erro, headers, BAD_REQUEST, request);
    }
}
