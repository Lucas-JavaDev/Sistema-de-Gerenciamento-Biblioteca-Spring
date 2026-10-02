package br.study.SpringStudy_3.Controller;

import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Exception.AutorNotFoundException;
import br.study.SpringStudy_3.Repository.AutorRepository;
import br.study.SpringStudy_3.DTO.AutorCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;


    @GetMapping
    public List<Autor> findAll(){
        return autorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Autor showAuthor(@PathVariable Long id){
        return autorRepository.findById(id).orElseThrow(
                () -> new AutorNotFoundException(id)
        );
    }

    @PostMapping("/cadastro")
    public Autor createAuthor(@RequestBody AutorCreateDTO autorCreateDTO){
        Autor autor = new Autor();
        autor.setNome(autorCreateDTO.getNome());
        autorRepository.save(autor);
        return autor;
    }

    @PutMapping("/atualizar/{id}")
    public Autor updateAuthor(@RequestBody AutorCreateDTO autorCreateDTO, @PathVariable Long id){
        Autor autor = autorRepository.findById(id).orElseThrow(
                () -> new AutorNotFoundException(id)
        );
        autor.setNome(autorCreateDTO.getNome());
        autorRepository.save(autor);

        return autor;
    }

    @DeleteMapping("/excluir/{id}")
    public void delete(@PathVariable Long id) {
        autorRepository.findById(id).orElseThrow(
                () -> new AutorNotFoundException(id)
        );

        autorRepository.deleteById(id);
    }
}
