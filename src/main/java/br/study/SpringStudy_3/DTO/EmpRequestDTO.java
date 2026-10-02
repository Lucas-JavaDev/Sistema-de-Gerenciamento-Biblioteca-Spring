package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Service.Enum.EmpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EmpRequestDTO {
    private String nome;
    private Long membroId;
    private String livroEmprestado;
    private EmpStatus status;
}
