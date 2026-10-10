package br.study.SpringStudy_3.Exception.ErrorMessages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ResourceNotFoundErrorMessage {
    private HttpStatus status;
    private String message;
}
