package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroPutDTO {

    private Long id;

    @NotBlank(message = "Titulo requerido")
    @Size(min = 3, max = 90, message = "Titulo deve conter de 3 a 90 caracteres!")
    private String titulo;

    @PositiveOrZero(message = "estoque não pode ser negativo")
    @NotNull(message = "estoque obrigatorio. (0 ou mais)")
    private Integer estoque;



}
