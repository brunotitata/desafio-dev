package br.com.bycoders.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CnabInvalidException extends RuntimeException{

    public CnabInvalidException(String message) {
        super(message);
    }
}
