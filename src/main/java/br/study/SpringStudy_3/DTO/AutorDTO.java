package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Autor;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {
    private Long id;
    private String nome;


    public AutorDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
    }

}
