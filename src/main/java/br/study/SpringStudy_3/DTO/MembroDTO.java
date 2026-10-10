package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Membro;
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
    private String nome;

    private String cpf;
    private String email;

    public MembroDTO(Membro membro) {
        this.id = membro.getId();
        this.nome = membro.getNome();
        this.cpf = membro.getCpf();
        this.email = membro.getEmail();
    }


}
