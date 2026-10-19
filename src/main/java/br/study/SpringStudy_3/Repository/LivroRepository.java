package br.study.SpringStudy_3.Repository;

import br.study.SpringStudy_3.Entity.Livro;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Livro l WHERE l.id = :id")
    Optional<Livro> findLivroById(Long id);
}
