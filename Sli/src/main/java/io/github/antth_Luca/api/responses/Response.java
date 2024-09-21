package io.github.antth_Luca.api.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Response<T> {
    private T data;
    private List<String> errors;
    private String message;
    private int statusCode;

    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

    public static <T> Response<T> success(T data, String message) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatusCode(200);  // HTTP 200 OK
        return response;
    }

    public static <T> Response<T> error(List<String> errors, String message, int statusCode) {
        Response<T> response = new Response<>();
        response.setErrors(errors);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        return response;
    }
}
