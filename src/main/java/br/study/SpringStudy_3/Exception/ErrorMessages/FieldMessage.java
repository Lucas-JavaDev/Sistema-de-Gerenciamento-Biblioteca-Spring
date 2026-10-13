package br.study.SpringStudy_3.Exception.ErrorMessages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage {
    private String field;
    private String message;
}
