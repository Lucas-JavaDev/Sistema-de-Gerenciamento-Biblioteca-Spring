package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Entity.Emprestimo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDTO {

   private Long membroId;
   private Long livroId;



   public EmpDTO(Emprestimo emprestimo) {
       this.membroId = emprestimo.getMembro().getId();
       this.livroId = emprestimo.getLivro().getId();
   }

}
