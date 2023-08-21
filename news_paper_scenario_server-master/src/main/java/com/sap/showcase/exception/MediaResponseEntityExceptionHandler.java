package com.sap.showcase.exception;

import javax.persistence.EntityNotFoundException;
//import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MediaResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {
	public MediaResponseEntityExceptionHandler() {
        super();
    }
    
//    @ExceptionHandler({ ConstraintViolationException.class })
//    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
//        final String bodyOfResponse = "ConstraintViolationException has occured : "+ex.getMessage() + "\n== In Handler ConstraintViolationException ==" +ex ;
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        final String bodyOfResponse = "You cannot Create/Delete this Entity : Please check Dependent Entity";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "This a bad Request : "+ex.getMessage() + "== ex ==" +ex.getMessage() ;
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String bodyOfResponse = "This is another bad Request : "+ex.getMessage() + "== ex ==" +ex.getMessage() ;
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    /*
     * handling 404 response codes
     */
    @ExceptionHandler(value = { EntityNotFoundException.class, ResourceNotFoundException.class,NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Entity could not be found : "+ex.getMessage() ;
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /*
     * handling 409 response codes
     */
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        String bodyOfResponse = "Something went wrong in Db : " +ex.getMessage();
        if(ex.getMessage().matches("EmptyResultDataAccessException"))
        	bodyOfResponse = "Please check the Entity that you are tring to delete : "+ex.getMessage();
        else if(ex.getMessage().matches("JpaObjectRetrievalFailureException"))
        	bodyOfResponse = "Something went wrong in Db : " +ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /*
     * handling 500 and 412 response codes
     */
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final String bodyOfResponse = "Oops Something went wrong : "+ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
	
}
