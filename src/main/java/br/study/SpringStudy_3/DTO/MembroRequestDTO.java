package br.study.SpringStudy_3.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroRequestDTO {

    @NotBlank(message = "Nome requerido")
    @Size(min = 3, max = 90, message = "Nome requer 3 a 90 caracteres")
    private String nome;

    @NotBlank(message = "CPF requerido")
    @Pattern(
            regexp = "(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})",
            message = "CPF deve conter 11 dígitos, com ou sem formatação padrão"
    )
    private String cpf;

    @NotBlank(message = "Email requerido")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha requerida")
    @Size(min = 8, max = 70, message = "Senha deve conter no mínimo 8 caracteres")
    private String password;

}
