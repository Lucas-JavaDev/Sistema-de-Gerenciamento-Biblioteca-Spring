package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Entity.Emprestimo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpDTO {

   private Long membroId;
   private Long livroId;
   private Integer loansUpdated;
   private Boolean hasActiveLoan;

   public EmpDTO(Long membroId, Long livroId) {
      this.membroId = membroId;
      this.livroId = livroId;
   }


   public EmpDTO(Emprestimo emprestimo) {
       this.membroId = emprestimo.getMembro().getId();
       this.livroId = emprestimo.getLivro().getId();
   }

}
