package com.estudo.gmfood.api.exceptionhandler;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// diz que dentro desse componente podemos adicionar exception handler que as exceções de todos os controladores serão tratadas por aqui,
// que é uma implementação padrao que trata exception internas do spring mvc
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno no sistema. Tente novamente e se o problema persistir," +
            "entre em contato com o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("Um ou mais campos inválidos. Faça o preenchimento correto e tente novamente.");

        BindingResult bindingResult = ex.getBindingResult(); //acesso a quais propriedades foram violadas

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail, LocalDateTime.now())
                .userMessage(detail)
                .objects(problemObjects)
                .build();


        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail, LocalDateTime.now())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        // rootCause é um InvalidFormatException
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail, LocalDateTime.now())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = String.format("O Parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo %s", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.PARAETRO_INVALIDO, detail, LocalDateTime.now())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

//        ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));

        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' não existe. Corriga ou remova essa propriedade a tente novamente. ", path);

        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail, LocalDateTime.now())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();


        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));

        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail, LocalDateTime.now())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = String.format("Ocorreu um erro interno no sistema. Tente novamente e se o problema persistir," +
                "entre em contato com o administrador do sistema");

        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, detail, LocalDateTime.now())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                         WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage(), LocalDateTime.now())
                .userMessage(ex.getMessage())
                .build();


        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage(), LocalDateTime.now())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, detail, LocalDateTime.now())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, LocalDateTime timestamp) {

        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }

    private String joinPath(List<Reference> path) {
        return path.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
