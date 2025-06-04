package br.com.mental_sos.dto;

import br.com.mental_sos.model.enuns.TipoGrupoUsuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private Integer numeroCrp;
    private TipoGrupoUsuario tipoGrupo;
}