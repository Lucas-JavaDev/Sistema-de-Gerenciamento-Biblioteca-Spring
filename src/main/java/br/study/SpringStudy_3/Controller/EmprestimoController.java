package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.EmpDTO;
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
    public ResponseEntity<EmpDTO> registerLoan(@RequestBody EmpRequestDTO empRequestDTO) {
        EmpDTO createdLoan = emprestimoService.toLoan(
                empRequestDTO.getMembroId(),
                empRequestDTO.getLivroId()
        );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{membroId}/{livroId}")
                .buildAndExpand(createdLoan.getMembroId(), createdLoan.getLivroId())
                .toUri();

        return ResponseEntity.created(uri).body(createdLoan);
    }

    @GetMapping("/membro/{id}")
    public ResponseEntity<EmpRequestDTO> showById(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.getMemberLoans(id));
    }

    @PutMapping("/atualizar-status")
    public ResponseEntity<EmpDTO> checkLateLoans() {
        EmpDTO result = new EmpDTO();
        result.setLoansUpdated(emprestimoService.checkLateLoans());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/devolver/{livroId}/{membroId}")
    public ResponseEntity<EmpDTO> returnLoan(
            @PathVariable Long livroId,
            @PathVariable Long membroId
    ) {
        return ResponseEntity.ok(emprestimoService.returnLoan(livroId, membroId));
    }

    @GetMapping("/membro/{id}/ativo")
    public ResponseEntity<EmpDTO> memberHaveLoan(@PathVariable Long id) {
        EmpDTO result = new EmpDTO();
        result.setMembroId(id);
        result.setHasActiveLoan(emprestimoService.memberHaveLoan(id));
        return ResponseEntity.ok(result);
    }
}
