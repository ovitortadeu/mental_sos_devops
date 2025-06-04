package br.com.mental_sos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MensagemCreateDTO {
    @NotBlank
    private String conteudo;
    @NotNull
    private Long chatId;
    private Long pacienteId;
    private Long psicologoId;
}