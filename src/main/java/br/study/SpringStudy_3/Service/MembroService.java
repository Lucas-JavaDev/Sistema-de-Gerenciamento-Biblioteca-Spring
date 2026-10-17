package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.LivroUpdateDTO;
import br.study.SpringStudy_3.DTO.MembroDTO;
import br.study.SpringStudy_3.DTO.MembroUpdateDTO;
import br.study.SpringStudy_3.Entity.Livro;
import br.study.SpringStudy_3.Entity.Membro;
import br.study.SpringStudy_3.Exception.ResourceNotFoundException;
import br.study.SpringStudy_3.Repository.MembroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class MembroService {

    private final MembroRepository membroRepository;

    public MembroService(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }



    public MembroDTO create(MembroDTO membroDTO) {
        Membro membro = new Membro();

        setValues(membro, membroDTO);

        return new MembroDTO(membro);
    }

    public MembroDTO update(MembroDTO membroDTO, Long id) {
        Membro membro = membroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Membro não encontrado")
        );

        update(membro, membroDTO);

        return new MembroDTO(membro);

    }

    public MembroUpdateDTO update(MembroUpdateDTO membroUpdDTO, Long id) {
        Membro membro = membroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Membro não encontrado")
        );

        update(membro, membroUpdDTO);
        return new MembroUpdateDTO(membro);
    }

    public MembroDTO findById(Long id) {

        Membro membro = membroRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("membro não encontrado")
        );


        return new MembroDTO(membro);

    }



    public List<MembroDTO> findAll() {
        List<Membro> membros = membroRepository.findAll();

        return membros.stream().map(membro -> new MembroDTO(membro)).toList();
    }


    public void delete(Long id) {
        if(!membroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membro não encontrado");
        }

        membroRepository.deleteById(id);
    }

    // Setar valores
    private void setValues(Membro membro, MembroDTO membroDTO) {
        membro.setNome(membroDTO.getNome());
        membro.setCpf(membroDTO.getCpf());
        membro.setEmail(membroDTO.getEmail());

        membroRepository.save(membro);
    }


    // PUT
    private void update(Membro membro, MembroDTO membroDTO) {

        membro.setNome(membroDTO.getNome());
        membro.setCpf(membroDTO.getCpf());
        membro.setEmail(membroDTO.getEmail());
        membroRepository.save(membro);
    }

    // Atualizar valores (se nulo, deixar como esta) -> PATCH
    private void update(Membro membro, MembroUpdateDTO membroUpdateDTO) {
        if(membroUpdateDTO.getNome() != null) {
            membro.setNome(membroUpdateDTO.getNome());
        }
        if(membroUpdateDTO.getCpf() != null) {
            membro.setCpf(membroUpdateDTO.getCpf());
        }
        if(membroUpdateDTO.getEmail() != null) {
            membro.setEmail(membroUpdateDTO.getEmail());
        }
        membroRepository.save(membro);
    }

}
