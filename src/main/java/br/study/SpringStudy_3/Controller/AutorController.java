package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.DTO.AutorDTO;
import br.study.SpringStudy_3.Service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }


    @GetMapping
    public ResponseEntity<List<AutorDTO>> findAll(){
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorDTO> showAuthor(@PathVariable Long id){
        return ResponseEntity.ok(autorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AutorDTO> createAuthor(@RequestBody AutorDTO autorCreateDTO){
        AutorDTO dto = autorService.create(autorCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/autor/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<AutorDTO> updateAuthor(@RequestBody AutorDTO autorCreateDTO, @PathVariable Long id){
        return ResponseEntity.ok(autorService.update(autorCreateDTO, id));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
