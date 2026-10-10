package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.MembroDTO;
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
        if(membroRepository.existsById(id)) {
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


    // Atualizar valores (se nulo, deixar como esta)
    private void update(Membro membro, MembroDTO membroDTO) {
        if (membroDTO.getNome() != null) {
            membro.setNome(membroDTO.getNome());
        }
        if (membroDTO.getCpf() != null) {
            membro.setCpf(membroDTO.getCpf());
        }
        if (membroDTO.getEmail() != null) {
            membro.setEmail(membroDTO.getEmail());
        }
        membroRepository.save(membro);
    }

}
