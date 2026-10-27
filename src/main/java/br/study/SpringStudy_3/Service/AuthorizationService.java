package br.study.SpringStudy_3.Service;



import br.study.SpringStudy_3.Entity.Membro;

import br.study.SpringStudy_3.Entity.Role;
import br.study.SpringStudy_3.Repository.MembroRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {


    private final MembroRepository membroRepository;
    public AuthorizationService(MembroRepository membroRepository, PasswordEncoder passwordEncoder) {
        this.membroRepository = membroRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Membro membro = membroRepository.findMembroByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Membro  não encontrado")
        );


        for(Role role : membro.getRoles()){
            membro.getRoles().add(role);
        }
        return membro;
    }



}
