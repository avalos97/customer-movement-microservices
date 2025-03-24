package com.devsu.res.customer_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import org.apache.logging.log4j.util.Strings;

/**
 * Objeto que contiene la información detallada de un error.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    
    /**
     * Código de error de la aplicación.
     */
    private String errorCode;
    
    /**
     * URL que generó el error.
     */
    private String url = "Not available";
    
    /**
     * Método HTTP que generó el error.
     */
    private String reqMethod = "Not available";
    
    /**
     * Marca de tiempo (timestamp) del error.
     */
    private Instant timestamp;

        public ErrorDetails setUrl(String url) {
        if (Strings.isNotBlank(url)) {
            this.url = url;
        }
        return this;
    }

    public ErrorDetails setReqMethod(String method) {
        if (Strings.isNotBlank(method)) {
            this.reqMethod = method;
        }
        return this;
    }

    public ErrorDetails setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
