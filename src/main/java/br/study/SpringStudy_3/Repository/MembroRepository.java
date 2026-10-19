package br.study.SpringStudy_3.Repository;

import br.study.SpringStudy_3.Entity.Membro;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Membro m WHERE m.id = :id")
    Optional<Membro> findMembroById(Long id);
}
