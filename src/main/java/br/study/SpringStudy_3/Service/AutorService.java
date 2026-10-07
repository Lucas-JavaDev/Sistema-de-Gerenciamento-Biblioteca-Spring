package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.AutorDTO;
import br.study.SpringStudy_3.Entity.Autor;
import br.study.SpringStudy_3.Exception.ResourceNotFoundException;
import br.study.SpringStudy_3.Repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
            this.autorRepository = autorRepository;
    }


    public AutorDTO create(AutorDTO autorDTO) {
        Autor autor = new Autor();

        autor.setNome(autorDTO.getNome());

        return new AutorDTO(autorRepository.save(autor));

    }

    public AutorDTO update(AutorDTO autorDTO, Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Autor não encontrado")
        );

        autor.setNome(autorDTO.getNome());
        return new AutorDTO(autorRepository.save(autor));
    }

    public void delete(Long id) {
        if(autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AutorDTO findById(Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Autor não encontrado")
        );

        return new AutorDTO(autor);
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> findAll() {
        List<Autor> autors = autorRepository.findAll();

        return autors.stream().map(autor -> new AutorDTO(autor)).toList();
    }




}
