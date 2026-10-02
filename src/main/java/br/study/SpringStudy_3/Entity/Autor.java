package br.study.SpringStudy_3.Entity;

import br.study.SpringStudy_3.DTO.AutorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;


    @OneToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();

}
