package br.study.SpringStudy_3.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {


    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Senha requirida para login")
    @Size(min = 8, max = 70, message = "Senha deve conter no minimo 8 caracteres")
    private String password;

}
