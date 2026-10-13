package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {

    private Long id;

    @NotBlank(message = "Titulo requerido")
    @Size(min = 3, max = 90, message = "Titulo deve conter de 3 a 90 caracteres!")
    private String titulo;

    @Positive(message = "estoque não pode ser negativo")
    private Integer estoque;


    private AutorRequestDTO autor;

    public LivroDTO(Livro livro){
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.estoque = livro.getEstoque();
        this.autor = new AutorRequestDTO(livro.getAutor());
    }


}
