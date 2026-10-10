package br.study.SpringStudy_3.Repository.Projection;

import br.study.SpringStudy_3.Service.Enum.EmpStatus;

public interface EmpProjection {

    String getNome();
    Long getId();
    EmpStatus getStatus();
}
