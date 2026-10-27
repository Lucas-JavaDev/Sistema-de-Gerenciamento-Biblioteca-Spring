package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.MembroRequestDTO;
import br.study.SpringStudy_3.DTO.MembroResponseDTO;
import br.study.SpringStudy_3.DTO.MembroUpdateDTO;
import br.study.SpringStudy_3.Service.MembroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/membro")
public class MembroController {

    private final MembroService membroService;

    public MembroController(MembroService membroService) {
        this.membroService = membroService;
    }

    @PostMapping
    public ResponseEntity<MembroResponseDTO> create(@Valid @RequestBody MembroRequestDTO membroRequestDTO) {
        MembroResponseDTO dto = membroService.create(membroRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<MembroResponseDTO>> findAll() {
        return ResponseEntity.ok(membroService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MembroResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(membroService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MembroResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MembroRequestDTO membroRequestDTO) {
        return ResponseEntity.ok(membroService.update(membroRequestDTO, id));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        membroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MembroResponseDTO> update(@Valid @RequestBody MembroUpdateDTO membroUpdateDTO, @PathVariable Long id) {
        return ResponseEntity.ok(membroService.update(membroUpdateDTO, id));
    }


}
