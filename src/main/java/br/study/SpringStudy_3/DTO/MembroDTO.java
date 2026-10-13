package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Membro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroDTO {


    private Long id;

    @NotBlank(message = "Nome requerido")
    @Size(min = 3, max = 90, message = "Nome requer 3 a 90 caracteres")
    private String nome;

    @NotBlank(message = "CPF requerido")
    @Size(min = 11, max = 11, message = "CPF deve conter 11 digitos")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

    public MembroDTO(Membro membro) {
        this.id = membro.getId();
        this.nome = membro.getNome();
        this.cpf = membro.getCpf();
        this.email = membro.getEmail();
    }


}
