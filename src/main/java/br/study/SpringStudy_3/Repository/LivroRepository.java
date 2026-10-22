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

    boolean existsByAutor_Id(Long autorId);

    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
        FROM Emprestimo e
        WHERE e.livro.id = :id
    """)
    boolean existsEmprestimoByLivroId(Long id);

}
