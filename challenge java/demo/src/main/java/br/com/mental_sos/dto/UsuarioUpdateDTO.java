package br.com.mental_sos.dto;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String username;
    private String email;
    private String senha;
    private Integer numeroCrp;
}