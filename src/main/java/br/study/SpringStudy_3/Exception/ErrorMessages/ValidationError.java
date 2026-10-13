package br.study.SpringStudy_3.Exception.ErrorMessages;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
public class ValidationError extends CustomError {
    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, HttpStatus status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public ValidationError() {
    }


    public void addError(String Field, String message) {
        errors.add(new FieldMessage(Field, message));
    }
}
