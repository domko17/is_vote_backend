package com.tuke.fei.kpi.isvote.modules.error;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.tuke.fei.kpi.isvote.modules.ApiException;
import com.tuke.fei.kpi.isvote.modules.error.dto.ErrorResponseDto;

import java.util.stream.Collectors;

import static com.tuke.fei.kpi.isvote.modules.ApiException.FaultType;
import static com.tuke.fei.kpi.isvote.modules.ApiException.FaultType.*;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final FaultType UNEXPECTED_ERROR_TYPE = GENERAL_ERROR;
    private static final String INVALID_JSON_TEXT =
            "Json payload incorrect, the following attributes do not match validation: ";
    private static final String BODY_NOT_READABLE =
            "Request body is not readable, problematic fields: ";
    private static final String UNEXPECTED_ERROR_TEXT =
            "Unexpected error occurred. Please contact administrator for more info";
    private static final String ACCESS_DENIED_TEXT = "Access denied";

    @ExceptionHandler(value = ApiException.class)
    protected ResponseEntity<Object> handleApiException(ApiException ex) {
        log.error("Handling ApiException", ex);
        return createResponse(ex.getFaultType(), ex.getErrorMsg());
    }

    @ExceptionHandler(value = DataAccessException.class)
    protected ResponseEntity<Object> handleDbException(DataAccessException ex) {
        log.error("Handling DbException", ex);
        return createResponse(DB_ERROR, ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        log.error("Handling unexpected exception", ex);
        return createResponse(UNEXPECTED_ERROR_TYPE, UNEXPECTED_ERROR_TEXT);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    private ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        log.error("Handling access denied exception");
        return createResponse(ACCESS_DENIED, ACCESS_DENIED_TEXT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        log.warn("Handling MethodArgumentNotValid with message {}", ex.getMessage());
        return handleValidationError(ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationError(BindingResult bindingResult) {
        String message = bindingResult.getFieldErrors().stream().map(FieldError::getField)
                .collect(Collectors.joining(", ", INVALID_JSON_TEXT + "[", "]"));
        return createResponse(FaultType.INVALID_PARAMS, message);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.warn("Handling HttpMessageNotReadable with message {}", ex.getMessage());
        StringBuilder message = new StringBuilder(BODY_NOT_READABLE);
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            for (JsonMappingException.Reference reference : ife.getPath()) {
                message.append(reference.getFieldName()).append(" ");
            }
        }
        return createResponse(FaultType.INVALID_PARAMS, message.toString());
    }

    private ResponseEntity<Object> createResponse(FaultType faultType, String message) {
        return ResponseEntity.status(faultType.getHttpStatus())
                .body(new ErrorResponseDto(faultType, message));
    }

}
