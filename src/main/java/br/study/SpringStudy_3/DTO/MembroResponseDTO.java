package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Entity.Membro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;

    public MembroResponseDTO(Membro membro) {
        id = membro.getId();
        nome = membro.getNome();
        cpf = membro.getCpf();
        email = membro.getEmail();
    }

}
