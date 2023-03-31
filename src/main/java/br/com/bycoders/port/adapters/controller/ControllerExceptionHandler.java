package br.com.bycoders.port.adapters.controller;

import br.com.bycoders.domain.exception.CnabInvalidException;
import br.com.bycoders.port.adapters.controller.DTO.ApiErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorDTO> cnabInvalidException(CnabInvalidException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiErrorDTO(e.getMessage()));
    }

}
