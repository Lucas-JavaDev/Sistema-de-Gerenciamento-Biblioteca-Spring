package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.MembroDTO;
import br.study.SpringStudy_3.Service.MembroService;
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
    public ResponseEntity<MembroDTO> create(@RequestBody MembroDTO membroDTO) {
        MembroDTO dto = membroService.create(membroDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<MembroDTO>> findAll() {
        return ResponseEntity.ok(membroService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MembroDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(membroService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MembroDTO> update(@PathVariable Long id, @RequestBody MembroDTO membroDTO) {
        return ResponseEntity.ok(membroService.update(membroDTO, id));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        membroService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
