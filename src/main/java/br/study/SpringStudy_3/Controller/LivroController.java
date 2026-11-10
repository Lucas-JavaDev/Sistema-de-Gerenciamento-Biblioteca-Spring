package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.LivroDTO;
import br.study.SpringStudy_3.DTO.LivroPutDTO;
import br.study.SpringStudy_3.DTO.LivroUpdateDTO;
import br.study.SpringStudy_3.Service.EmprestimoService;
import br.study.SpringStudy_3.Service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    private final LivroService livroService;


    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LivroDTO> create(@Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO createdLivro = livroService.create(livroDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/livro/{id}")
                .buildAndExpand(createdLivro.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdLivro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> showBook(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.findById(id));
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<LivroDTO> updateBook(@Valid @RequestBody LivroPutDTO livroPutDTO, @PathVariable Long id) {
        return ResponseEntity.ok(livroService.update(livroPutDTO, id));
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        livroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<LivroDTO> updateBook(@PathVariable Long id, @Valid @RequestBody LivroUpdateDTO livroUpdateDTO) {
        return ResponseEntity.ok(livroService.update(livroUpdateDTO, id));
    }
}
