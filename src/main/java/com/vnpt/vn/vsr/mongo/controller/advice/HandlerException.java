package com.vnpt.vn.vsr.mongo.controller.advice;

import com.vnpt.vn.vsr.mongo.model.payload.response.DataResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class HandlerException extends ResponseEntityExceptionHandler {

    //400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, new DataResponse(1, "Thất bại", errors), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info(ex.getClass().getName());
        return handleExceptionInternal(ex, new DataResponse(1, "Thất bại", ex.getBody().getDetail()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final String error = ex.getValue() + " giá trị cho tham số: " + ex.getPropertyName() + " nên là kiểu: " + ex.getRequiredType();
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final String error = ex.getRequestPartName() + " bị thiếu";
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final String error = "Tham số: " + ex.getParameterName() + " bị thiếu";
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final String error = "Tham số: " + ex.getName() + " nên có kiểu dữ liệu là: " + ex.getRequiredType().getName();
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final String error = "Không có hàm xử lý cho " + ex.getHttpMethod() + " " + ex.getRequestURL();
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", error), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append("Phương thức:");
        builder.append(ex.getMethod());
        builder.append(" không hỗ trợ. Phương thúc hỗ trợ là: ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", builder.toString()), new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append("Phương thức:");
        builder.append(ex.getContentType());
        builder.append(" không hỗ trợ. Phương thúc hỗ trợ là: ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", builder.substring(0, builder.length() - 2)), new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        return new ResponseEntity<>(new DataResponse(1, "Thất bại", "Lỗi server"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
