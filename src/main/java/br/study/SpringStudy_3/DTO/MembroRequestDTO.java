package br.study.SpringStudy_3.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MembroRequestDTO {
    private String nome;
    private Long id;
}
