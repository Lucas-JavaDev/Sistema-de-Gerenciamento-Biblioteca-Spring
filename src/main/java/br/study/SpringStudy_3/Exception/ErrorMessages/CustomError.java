package br.study.SpringStudy_3.Exception.ErrorMessages;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class CustomError {
    private Instant timestamp;
    private HttpStatus status;
    private String error;
    private String path;



}
