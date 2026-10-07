package br.study.SpringStudy_3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpMemberLoanStatusDTO {
    private Long membroId;
    private boolean hasActiveLoan;
}
