package br.com.mental_sos.dto;

import br.com.mental_sos.model.enuns.TipoGrupoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioCreateDTO {
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    @NotBlank
    @Size(min = 6, max = 100)
    private String senha;
    private Integer numeroCrp;
    @NotNull
    private TipoGrupoUsuario tipoGrupo;
}