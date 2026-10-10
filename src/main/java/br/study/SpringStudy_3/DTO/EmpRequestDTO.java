package br.study.SpringStudy_3.DTO;

import br.study.SpringStudy_3.Service.Enum.EmpStatus;
import br.study.SpringStudy_3.Repository.Projection.EmpProjection;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpRequestDTO {
    private String nome;
    private Long membroId;
    private Long livroId;
    private String livroEmprestado;
    private EmpStatus status;
    private List<EmpProjection> livros;

    public EmpRequestDTO(String nome, Long membroId, String livroEmprestado, EmpStatus status) {
        this.nome = nome;
        this.membroId = membroId;
        this.livroEmprestado = livroEmprestado;
        this.status = status;
    }

    public EmpRequestDTO(Long membroId, Long livroId) {
        this.membroId = membroId;
        this.livroId = livroId;
    }

    public EmpRequestDTO(String nome, List<EmpProjection> livros) {
        this.nome = nome;
        this.livros = livros;
    }

}
