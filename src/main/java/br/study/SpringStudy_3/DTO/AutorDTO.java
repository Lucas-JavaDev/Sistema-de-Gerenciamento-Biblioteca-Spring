package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Entity.Livro;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {

    private final List<LivroDTO> livros =  new ArrayList<>();


    private Long id;
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
