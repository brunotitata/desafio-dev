package br.com.bycoders.port.adapters.controller.DTO;

public class ApiErrorDTO {

    private String message;

    public ApiErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiErrorDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
