package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.LivroDTO;
import br.study.SpringStudy_3.DTO.LivroPutDTO;
import br.study.SpringStudy_3.DTO.LivroUpdateDTO;
import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Exception.BookHasLoansException;
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

    @Transactional
    public LivroDTO create(LivroDTO livroDTO){
        Livro livro = new Livro();

        setValues(livro, livroDTO);

        return new LivroDTO(livro);
    }

    @Transactional
    public LivroDTO update(LivroUpdateDTO livroUpdateDTO, Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );

        update(livro, livroUpdateDTO);

        return new LivroDTO(livro);
    }

    @Transactional
    public LivroDTO update(LivroPutDTO livroPutDTO, Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );
        update(livro, livroPutDTO);

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


    private void update(Livro livro, LivroUpdateDTO livroUpdateDTO) {
        if(livroUpdateDTO.getTitulo() != null) {
            livro.setTitulo(livroUpdateDTO.getTitulo());
        }
        if(livroUpdateDTO.getEstoque() != null) {
            livro.setEstoque(livroUpdateDTO.getEstoque());
        }

        livroRepository.save(livro);
    }

    private void update(Livro livro, LivroPutDTO dto) {
        livro.setTitulo(dto.getTitulo());
        livro.setEstoque(dto.getEstoque());
        livroRepository.save(livro);
    }

    public void deleteById(Long id) {
        if(!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membro não encontrado");
        }
        if(livroRepository.existsEmprestimoByLivroId(id)) {
            throw new BookHasLoansException(id);
        }
        livroRepository.deleteById(id);
    }
}
