package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.Entity.Emprestimo;
import br.study.SpringStudy_3.Entity.Membro;
import br.study.SpringStudy_3.Repository.EmpRepository;
import br.study.SpringStudy_3.Repository.MembroRepository;
import br.study.SpringStudy_3.DTO.EmpCreateDTO;
import br.study.SpringStudy_3.DTO.EmpDTO;
import br.study.SpringStudy_3.DTO.EmpRequestDTO;
import br.study.SpringStudy_3.Service.EmprestimoService;
import br.study.SpringStudy_3.Service.Enum.EmpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {
    @Autowired
    EmpRepository empRepository;
    @Autowired
    MembroRepository membroRepository;
    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping
    public List<EmpRequestDTO> listAll() {
        List<Emprestimo> emprestimos = empRepository.findAll();
        List<EmpRequestDTO> empRequestDTOS = new ArrayList<>();

        for(Emprestimo emp : emprestimos) {
            empRequestDTOS.add(new EmpRequestDTO(
               emp.getMembro().getNome(),
               emp.getMembro().getId(),
               emp.getLivro().getTitulo(),
                    emp.getStatus()
            ));
        }
        return empRequestDTOS;
    }

    @PostMapping("/registrar")
    public Emprestimo registerLoan(@RequestBody EmpCreateDTO empCreateDTO) {
        return emprestimoService.toLoan(empCreateDTO.getMembroId(), empCreateDTO.getLivroId());
    }

    @GetMapping("/membro/{id}")
    public EmpDTO showById(@PathVariable("id") Long idMember) {
        Membro membro = membroRepository.findById(idMember).orElseThrow(
                () -> new MemberNotFoundException(idMember)
        );
        Long emprestimos = empRepository.countEmprestimoByMembro_IdAndStatus(idMember, EmpStatus.EMPRESTADO, EmpStatus.ATRASADO);

        return new EmpDTO(membro.getNome(), emprestimos);
    }


    @PutMapping("/atualizar-status")
    public ResponseEntity<String> checkLateLoans() {
        int modified = emprestimoService.checkLateLoans();
        if(modified == 0) {
            return ResponseEntity.ok("No loans are Late");
        }
        return ResponseEntity.ok(modified + " Loans updated to ATRASADO");
    }

    @PutMapping("/livro/devolver/{id}/membro/{idMember}")
    public ResponseEntity<String> returnLoan(@PathVariable("id") Long idBook, @PathVariable("idMember") Long idMember) {
        emprestimoService.returnLoan(idBook, idMember);
        return ResponseEntity.ok("Book with id: " + idBook + " has been returned");
    }
    

}

