package br.com.agrego.poc.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.agrego.poc.error.ErrorDetails;
import br.com.agrego.poc.error.ResourceNotFoundDetails;
import br.com.agrego.poc.error.ResourceNotFoundException;
import br.com.agrego.poc.error.ValidationErrorDetails;

/**
 * Captura e trata as excessões lançadas nos controlles/endpoints
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Recurso não encontrado")
                .detail(ex.getMessage())
                .exception(ex.getClass().getName())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> handleAuthorizationServiceException(InternalAuthenticationServiceException ex, WebRequest request) {
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("Acesso Negado")
                .detail(ex.getMessage())
                .exception(ex.getClass().getName())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthorizationServiceException(AuthenticationException ex, WebRequest request) {
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("Acesso Negado")
                .detail(ex.getMessage())
                .exception(ex.getClass().getName())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.FORBIDDEN.value())
                .title("Sem autenticação")
                .detail(ex.getMessage())
                .exception(ex.getClass().getName())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.FORBIDDEN);
    }
    

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        ValidationErrorDetails rnfDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Erro na validação de campo")
                .detail("Erro na validação de campo")
                .exception(ex.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();
        
        return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Padroniza o retorno de outras exceptions não tratadas
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Excessão interna")
                .detail(ex.getMessage())
                .exception(ex.getClass().getName())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .method(((ServletWebRequest)request).getRequest().getMethod())
                .build();
        
        return new ResponseEntity<>(errorDetails, headers, status);
    }

    /**
     * Padroniza o retorno de outras exceptions não tratadas
     */
//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//        ErrorDetails errorDetails = ErrorDetails.Builder
//                .newBuilder()
//                .timestamp(new Date().getTime())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .title("Excessão interna")
//                .detail(ex.getMessage())
//                .exception(ex.getClass().getName())
//                .path(((ServletWebRequest)request).getRequest().getRequestURI())
//                .method(((ServletWebRequest)request).getRequest().getMethod())
//                .build();
//        
//        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
