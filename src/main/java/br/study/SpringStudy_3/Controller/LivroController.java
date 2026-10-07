package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.LivroDTO;
import br.study.SpringStudy_3.Service.EmprestimoService;
import br.study.SpringStudy_3.Service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    private final LivroService livroService;
    private final EmprestimoService emprestimoService;

    public LivroController(LivroService livroService, EmprestimoService emprestimoService) {
        this.livroService = livroService;
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @PostMapping
    public ResponseEntity<LivroDTO> create(@RequestBody LivroDTO livroDTO) {
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<LivroDTO> updateBook(@RequestBody LivroDTO livroDTO, @PathVariable Long id) {
        return ResponseEntity.ok(livroService.update(livroDTO, id));
    }


}
