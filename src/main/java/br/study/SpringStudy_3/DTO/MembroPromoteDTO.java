package br.study.SpringStudy_3.DTO;


import br.study.SpringStudy_3.Entity.Membro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroPromoteDTO {


    private Long id;

    @NotBlank(message = "Role para promoção de membro exigida")
    private String roleName;


}
