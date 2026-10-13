package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Entity.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {

    private final List<LivroDTO> livros =  new ArrayList<>();


    private Long id;
    @NotBlank(message = "Nome requerido")
    @Size(min = 3, max = 90, message = "Nome precisa conter de 3 a 90 caracteres")
    private String nome;


    public AutorDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        for(Livro livro : autor.getLivros() ){
            livros.add(new LivroDTO(livro));
        }
    }


    public void addLivro(Livro livro){
        livros.add(new LivroDTO(livro));
    }





}
