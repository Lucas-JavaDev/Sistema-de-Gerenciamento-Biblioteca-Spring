package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.EmpDTO;
import br.study.SpringStudy_3.DTO.EmpRequestDTO;
import br.study.SpringStudy_3.Entity.Emprestimo;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Entity.Membro;
import br.study.SpringStudy_3.Exception.*;
import br.study.SpringStudy_3.Repository.EmpRepository;
import br.study.SpringStudy_3.Repository.LivroRepository;
import br.study.SpringStudy_3.Repository.MembroRepository;
import br.study.SpringStudy_3.Repository.Projection.EmpProjection;
import br.study.SpringStudy_3.Service.Enum.EmpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {


    private final MembroRepository membroRepository;
    private final LivroRepository livroRepository;
    private final EmpRepository emprestimoRepository;

    public EmprestimoService(MembroRepository membroRepository, LivroRepository livroRepository, EmpRepository emprestimoRepository) {
        this.membroRepository = membroRepository;
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }




    @Transactional
    public EmpDTO toLoan(Long idMember, Long idBook) {
        Livro livro = livroRepository.findById(idBook).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );
        Membro membro = membroRepository.findById(idMember).orElseThrow(
                () -> new ResourceNotFoundException("Membro não encontrado")
        );

        registLoan(livro, membro);
        return new EmpDTO(membro.getId(), livro.getId());
    }

    public boolean maxLoans(Long idMember) {
        if(!membroRepository.existsById(idMember)) {
            throw new ResourceNotFoundException("Membro não encontrado");
        }
        return emprestimoRepository.countEmprestimo(idMember, EmpStatus.EMPRESTADO, EmpStatus.ATRASADO) >= 5;
    }

    @Transactional
    public EmpDTO returnLoan(Long idBook, Long idMember) {
        Livro livro = livroRepository.findById(idBook).orElseThrow(
                () -> new ResourceNotFoundException("Livro com id: " + idBook + "não encontrado")
        );

        Membro membro = membroRepository.findById(idMember).orElseThrow(
                () -> new ResourceNotFoundException("Membro com id: " + idMember + "não encontrado")
        );

        Emprestimo emprestimo = emprestimoRepository.findByMembroAndLivroAndStatus(
                membro,
                livro,
                EmpStatus.EMPRESTADO
        ).orElseThrow(
                () -> new ResourceNotFoundException("Membro com id: " + idMember +
                        "não tem um emprestimo ativo do livro: " + livro.getTitulo())
        );

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus(EmpStatus.DEVOLVIDO);
        emprestimoRepository.save(emprestimo);

        livro.setEstoque(livro.getEstoque() + 1);
        livroRepository.save(livro);
        return new EmpDTO(membro.getId(), livro.getId());
    }

    public List<EmpRequestDTO> findAll() {
        return emprestimoRepository.findAll().stream()
                .map(emprestimo -> new EmpRequestDTO(
                        emprestimo.getMembro().getNome(),
                        emprestimo.getMembro().getId(),
                        emprestimo.getLivro().getTitulo(),
                        emprestimo.getStatus()
                ))
                .toList();
    }

    public EmpRequestDTO getMemberLoans(Long idMember) {
        Membro membro = membroRepository.findById(idMember).orElseThrow(
                () -> new ResourceNotFoundException("Membro com id: " + idMember + " não encontrado")
        );

        List<EmpProjection> livros = emprestimoRepository.findMemberLoans(
                idMember,
                List.of(EmpStatus.EMPRESTADO, EmpStatus.ATRASADO)
        );
        return new EmpRequestDTO(membro.getNome(), livros);
    }

    public int checkLateLoans() {
        int modified = 0;

        List<Emprestimo> emprestimosAtrasados = emprestimoRepository.findByStatus(EmpStatus.EMPRESTADO);

        for(Emprestimo emprestimo : emprestimosAtrasados) {
            if(LocalDate.now().isAfter(emprestimo.getDataEmprestimo().plusDays(7))) {
                emprestimo.setStatus(EmpStatus.ATRASADO);
                emprestimoRepository.save(emprestimo);
                modified++;
            }
        }

        return modified;
    }



    public boolean bookHaveLoan(Long idBook) {
        Livro livro = livroRepository.findById(idBook).orElseThrow(
                () -> new ResourceNotFoundException("Livro com id: " + idBook + "não encontrado")
        );

        Optional<Emprestimo> empOptional = emprestimoRepository.findByLivroAndStatus(
                livro,
                EmpStatus.EMPRESTADO
        );
        return empOptional.isPresent();
    }


    public boolean memberHaveLoan(Long idMember) {
        Membro membro = membroRepository.findById(idMember).orElseThrow(
                () -> new ResourceNotFoundException("Membro com id: " + idMember + " não encontrado")
        );
        return emprestimoRepository.findByMembroAndStatus(membro, EmpStatus.EMPRESTADO).isPresent();
    }

    private void registLoan(Livro livro, Membro membro) {
        if(emprestimoRepository.existsByLivroAndMembroAndStatus(
                livro,
                membro,
                EmpStatus.EMPRESTADO,
                EmpStatus.ATRASADO
        )) {
            throw new BookAlreadyBorrowed(livro.getId(), membro.getId());
        }
        if(maxLoans(membro.getId())) {
            throw new MaxLoanLimitReachedException(membro.getId());
        }
        if(livro.getEstoque() < 1) {
            throw new BookStockIsEmptyException(livro.getId());
        }
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setMembro(membro);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setStatus(EmpStatus.EMPRESTADO);
        emprestimoRepository.save(emprestimo);
        livro.setEstoque(livro.getEstoque() - 1);
        livroRepository.save(livro);
    }




}
