package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Entity.Emprestimo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpDTO {

   private Long id;

   @NotNull(message = "ID do membro é obrigatório")
   @Positive(message = "ID do membro precisa ser positivo")
   private Long membroId;

   @NotNull(message = "ID do livro é obrigatório")
   @Positive(message = "ID do livro precisa ser positivo")
   private Long livroId;


   private Integer loansUpdated;
   private Boolean hasActiveLoan;



   public EmpDTO(Emprestimo emprestimo) {
      id =  emprestimo.getId();
      membroId = emprestimo.getMembro().getId();
      livroId = emprestimo.getLivro().getId();
   }

}
