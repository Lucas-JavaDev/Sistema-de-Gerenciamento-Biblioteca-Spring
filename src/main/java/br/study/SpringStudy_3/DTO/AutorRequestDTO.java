package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Entity.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorRequestDTO {
    private Long id;
    private String nome;

    public AutorRequestDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
    }
}
