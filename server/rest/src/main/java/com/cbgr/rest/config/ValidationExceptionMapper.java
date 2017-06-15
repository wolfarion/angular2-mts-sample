package com.cbgr.rest.config;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Маппер для ошибок валидации.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        StringBuilder strBuilder = new StringBuilder();
        String message;
        if (e instanceof ConstraintViolationException) {
            for (ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations()) {
                strBuilder.append(cv.getMessage()).append(", ");
            }
            message = strBuilder.substring(0, strBuilder.length() - 2);
        } else {
            message = e.getMessage();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .type(MediaType.TEXT_PLAIN)
                .entity(message)
                .build();
    }
}
