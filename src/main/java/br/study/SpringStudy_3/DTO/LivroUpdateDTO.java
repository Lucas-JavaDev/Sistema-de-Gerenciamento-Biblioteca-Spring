package br.study.SpringStudy_3.DTO;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroUpdateDTO {


    private Long id;

    @Size(min = 3, max = 90, message = "titulo deve conter entre 3 a 90 caracteres")
    private String titulo;

    @PositiveOrZero(message = "O estoque deve ser positivo")
    private Integer estoque;


}
