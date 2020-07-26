package br.com.agrego.poc.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * Captura os erros provenientes de consumo ou acesso a serviços/endpoints externos.
 * @author Andrey
 * @since 01/07/2018
 */
@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("Inside hasError");
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("Doing something with status code "+response.getStatusCode());
        System.out.println("Doing something with body "+ IOUtils.toString(response.getBody(),"UTF-8"));
        super.handleError(response);
    }
}
