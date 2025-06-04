package br.com.mental_sos.dto;

import lombok.Data;

@Data
public class OngDTO {
    private Long id;
    private String nome;
    private Long contato;
    private Long administradorOngId;
    private String administradorOngUsername;
}