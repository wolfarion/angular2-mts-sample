package com.cbgr.rest.config;

import com.google.common.base.Strings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Маппер для серверных ошибок.
 */
@Provider
public class ServerExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .type(MediaType.TEXT_PLAIN)
                .entity(getMessage(e))
                .build();
    }

    private static String getMessage(Exception e) {
        String exceptionMessage = e.getMessage();
         if (!Strings.isNullOrEmpty(exceptionMessage)) {
             return exceptionMessage;
        } else {
             StringWriter stackTraceWriter = new StringWriter();
             e.printStackTrace(new PrintWriter(stackTraceWriter));
             return stackTraceWriter.toString();
        }
    }
}
