package br.study.SpringStudy_3.Repository;

import br.study.SpringStudy_3.Entity.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro, Long> {


}
