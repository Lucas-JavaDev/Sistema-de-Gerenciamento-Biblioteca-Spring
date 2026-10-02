package br.study.SpringStudy_3.Controller;


import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Exception.AutorNotFoundException;
import br.study.SpringStudy_3.Exception.BookAlreadyBorrowed;
import br.study.SpringStudy_3.Exception.BookNotFoundException;
import br.study.SpringStudy_3.Repository.AutorRepository;
import br.study.SpringStudy_3.Repository.LivroRepository;
import br.study.SpringStudy_3.DTO.LivroCreateDTO;
import br.study.SpringStudy_3.DTO.LivroRequestDTO;
import br.study.SpringStudy_3.Service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private EmprestimoService emprestimoService;
    @Autowired
    private AutorRepository autorRepository;


    @GetMapping
    public List<Livro> listAll() {
        return livroRepository.findAll();
    }

    @PostMapping("/cadastro")
    public LivroRequestDTO registerBook(@RequestBody LivroCreateDTO livroCreateDTO) {
        Autor autor = autorRepository.findById(livroCreateDTO.getAutorId()).orElseThrow(
                () -> new AutorNotFoundException(livroCreateDTO.getAutorId())
        );
        Livro livro = new Livro();
        livro.setTitulo(livroCreateDTO.getTitulo());
        livro.setAutor(autor);
        livro.setEstoque(livroCreateDTO.getEstoque());
        livroRepository.save(livro);
        return new LivroRequestDTO(
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getEstoque(),
                livro.getId()
        );
    }

    @GetMapping("/{id}") // Atualizar Depois Utilizando DTOs
    public LivroRequestDTO showBook(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );

        return new LivroRequestDTO(
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getEstoque(),
                livro.getId()
        );
    }

    @PutMapping("/atualizar/{id}")
    public LivroRequestDTO updateBook(@RequestBody LivroCreateDTO livroCreateDTO, @PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );


        if(livroCreateDTO.getAutorId() != null) {
            Autor autor = autorRepository.findById(livroCreateDTO.getAutorId()).orElseThrow(
                    () -> new AutorNotFoundException(livroCreateDTO.getAutorId())
            );
            livro.setAutor(autor);
        }
        if(livroCreateDTO.getTitulo() != null) {
            livro.setTitulo(livroCreateDTO.getTitulo());
        }
        if(livroCreateDTO.getEstoque() != null) {
            livro.setEstoque(livroCreateDTO.getEstoque());
        }
        livroRepository.save(livro);

        return new LivroRequestDTO(
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getEstoque(),
                livro.getId()
        );
    }


    @DeleteMapping("/excluir/{id}")
    public void delete(@PathVariable Long id) {
        livroRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );

        if(emprestimoService.bookHaveLoan(id)) {
            throw new BookAlreadyBorrowed("Book with id: " + id + " already borrowed");
        } else {
            livroRepository.deleteById(id);
        }
    }

}
