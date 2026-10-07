package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {

    private Long id;
    private String titulo;
    private Integer estoque;


    private AutorRequestDTO autor;

    public LivroDTO(Livro livro){
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.estoque = livro.getEstoque();
        this.autor = new AutorRequestDTO(livro.getAutor());
    }


}
