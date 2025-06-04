package br.com.mental_sos.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class MensagemDTO {
    private Long id;
    private String conteudo;
    private OffsetDateTime enviadaEm;
    private Long chatId;
    private Long remetenteId;
    private String remetenteUsername;
    private Long pacienteId;
    private Long psicologoId;
}