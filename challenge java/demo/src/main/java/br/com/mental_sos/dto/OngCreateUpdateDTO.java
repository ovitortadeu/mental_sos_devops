package br.com.mental_sos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OngCreateUpdateDTO {
    @NotBlank
    @Size(max = 100)
    private String nome;
    private Long contato;
    @NotNull
    private Long administradorOngId;
}