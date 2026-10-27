package br.study.SpringStudy_3.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroUpdateDTO {

    @Size(min = 3, max = 80, message = "Nome deve conter de 3 a 80 caracteres")
    private String nome;

    @Pattern(
            regexp = "(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})",
            message = "CPF deve conter 11 dígitos, com ou sem formatação padrão"// Aceita o padrao 123.456.789-00 e 12345678900
    )
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

}
