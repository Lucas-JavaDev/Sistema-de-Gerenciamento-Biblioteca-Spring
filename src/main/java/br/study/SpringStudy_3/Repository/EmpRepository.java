package br.study.SpringStudy_3.Repository;

import br.study.SpringStudy_3.Entity.Emprestimo;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Entity.Membro;
import br.study.SpringStudy_3.Service.Enum.EmpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpRepository extends JpaRepository<Emprestimo, Long> {



    Optional<Emprestimo> findByLivroAndStatus(Livro livro, EmpStatus status);

    Optional<Emprestimo> findByMembroAndStatus(Membro membro, EmpStatus status);


    @Query("""
            SELECT COUNT(e)
            FROM Emprestimo e
            WHERE e.membro.id = :memberId
              AND (e.status = :status OR e.status = :secStatus)
            """)
    Long countEmprestimo(Long memberId, EmpStatus status, EmpStatus secStatus);

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM Emprestimo e
            WHERE e.livro = :livro
              AND e.membro = :membro
              AND e.status IN (:status, :secStatus)
            """)
    boolean existsByLivroAndMembroAndStatus(
           Livro livro,
           Membro membro,
           EmpStatus status,
           EmpStatus secStatus
    );

    List<Emprestimo> findByStatus(EmpStatus status);

    Optional<Emprestimo> findByMembroAndLivroAndStatus(Membro membro, Livro livro, EmpStatus status);
}
