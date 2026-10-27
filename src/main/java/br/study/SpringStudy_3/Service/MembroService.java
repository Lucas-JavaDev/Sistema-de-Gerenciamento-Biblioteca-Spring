package br.study.SpringStudy_3.Service;


import br.study.SpringStudy_3.DTO.MembroRequestDTO;
import br.study.SpringStudy_3.DTO.MembroResponseDTO;
import br.study.SpringStudy_3.DTO.MembroUpdateDTO;
import br.study.SpringStudy_3.Entity.Membro;
import br.study.SpringStudy_3.Exception.ResourceNotFoundException;
import br.study.SpringStudy_3.Repository.MembroRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembroService {

    private final MembroRepository membroRepository;
    private final PasswordEncoder passwordEncoder;

    public MembroService(MembroRepository membroRepository, PasswordEncoder passwordEncoder) {
        this.membroRepository = membroRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public MembroResponseDTO create(MembroRequestDTO membroRequestDTO) {
        Membro membro = new Membro();

        setValues(membro, membroRequestDTO);

        return new MembroResponseDTO(membro);
    }

    public MembroResponseDTO update(MembroRequestDTO membroRequestDTO, Long id) {
        Membro membro = membroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Membro não encontrado")
        );

        update(membro, membroRequestDTO);

        return new MembroResponseDTO(membro);

    }

    public MembroResponseDTO update(MembroUpdateDTO membroUpdDTO, Long id) {
        Membro membro = membroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Membro não encontrado")
        );

        update(membro, membroUpdDTO);
        return new MembroResponseDTO(membro);
    }

    public MembroResponseDTO findById(Long id) {

        Membro membro = membroRepository.findById(id).orElseThrow(
                () ->  new ResourceNotFoundException("membro não encontrado")
        );


        return new MembroResponseDTO(membro);

    }



    public List<MembroResponseDTO> findAll() {
        List<Membro> membros = membroRepository.findAll();

        return membros.stream().map(MembroResponseDTO::new).toList();
    }


    public void delete(Long id) {
        if(!membroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membro não encontrado");
        }

        membroRepository.deleteById(id);
    }

    // Setar valores
    private void setValues(Membro membro, MembroRequestDTO membroRequestDTO) {
        membro.setNome(membroRequestDTO.getNome());
        membro.setCpf(membroRequestDTO.getCpf());
        membro.setEmail(membroRequestDTO.getEmail());
        membro.setPassword(passwordEncoder.encode(membroRequestDTO.getPassword()));
        membroRepository.save(membro);
    }


    // PUT
    private void update(Membro membro, MembroRequestDTO membroRequestDTO) {

        membro.setNome(membroRequestDTO.getNome());
        membro.setCpf(membroRequestDTO.getCpf());
        membro.setEmail(membroRequestDTO.getEmail());
        membro.setPassword(passwordEncoder.encode(membroRequestDTO.getPassword()));
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
