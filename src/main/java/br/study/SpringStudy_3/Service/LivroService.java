package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.LivroDTO;
import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Exception.ResourceNotFoundException;
import br.study.SpringStudy_3.Repository.AutorRepository;
import br.study.SpringStudy_3.Repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }


    public LivroDTO create(LivroDTO livroDTO){
        Livro livro = new Livro();

        setValues(livro, livroDTO);

        return new LivroDTO(livro);
    }

    public LivroDTO update(LivroDTO livroDTO, Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );

        update(livro, livroDTO);

        return new LivroDTO(livro);
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> findAll() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream().map(livro -> new LivroDTO(livro)).toList();
    }

    @Transactional(readOnly = true)
    public LivroDTO findById(Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );

        return new LivroDTO(livro);
    }



    private void setValues(Livro livro, LivroDTO livroDTO){
        Autor autor = autorRepository.findById(livroDTO.getAutor().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Autor não encontrado")
                );

        livro.setTitulo(livroDTO.getTitulo());
        livro.setEstoque(livroDTO.getEstoque());
        livro.setAutor(autor);
        livroRepository.save(livro);
    }


    private void update(Livro livro, LivroDTO livroDTO) {
        if(livroDTO.getTitulo() != null) {
            livro.setTitulo(livroDTO.getTitulo());
        }
        if(livroDTO.getEstoque() != null) {
            livro.setEstoque(livroDTO.getEstoque());
        }

        livroRepository.save(livro);
    }
}
