package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.EmpDTO;
import br.study.SpringStudy_3.DTO.EmpLateStatusUpdateDTO;
import br.study.SpringStudy_3.DTO.EmpMemberLoanStatusDTO;
import br.study.SpringStudy_3.DTO.EmpMemberSummaryDTO;
import br.study.SpringStudy_3.DTO.EmpRequestDTO;
import br.study.SpringStudy_3.Service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpRequestDTO>> listAll() {
        return ResponseEntity.ok(emprestimoService.findAll());
    }

    @PostMapping
    public ResponseEntity<EmpDTO> registerLoan(@RequestBody EmpDTO empDTO) {
        EmpDTO createdLoan = emprestimoService.toLoan(empDTO.getMembroId(), empDTO.getLivroId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{membroId}/{livroId}")
                .buildAndExpand(createdLoan.getMembroId(), createdLoan.getLivroId())
                .toUri();

        return ResponseEntity.created(uri).body(createdLoan);
    }

    @GetMapping("/membro/{id}")
    public ResponseEntity<EmpMemberSummaryDTO> showById(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.getMemberLoanSummary(id));
    }

    @PutMapping("/atualizar-status")
    public ResponseEntity<EmpLateStatusUpdateDTO> checkLateLoans() {
        return ResponseEntity.ok(new EmpLateStatusUpdateDTO(emprestimoService.checkLateLoans()));
    }

    @PutMapping("/devolver/{livroId}/{membroId}")
    public ResponseEntity<EmpDTO> returnLoan(
            @PathVariable Long livroId,
            @PathVariable Long membroId
    ) {
        return ResponseEntity.ok(emprestimoService.returnLoan(livroId, membroId));
    }

    @GetMapping("/membro/{id}/ativo")
    public ResponseEntity<EmpMemberLoanStatusDTO> memberHaveLoan(@PathVariable Long id) {
        return ResponseEntity.ok(new EmpMemberLoanStatusDTO(id, emprestimoService.memberHaveLoan(id)));
    }
}
