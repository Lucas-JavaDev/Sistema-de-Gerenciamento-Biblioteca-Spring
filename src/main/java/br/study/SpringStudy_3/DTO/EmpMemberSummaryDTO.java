package br.study.SpringStudy_3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpMemberSummaryDTO {
    private String nome;
    private Long emprestimosAtivos;
}
