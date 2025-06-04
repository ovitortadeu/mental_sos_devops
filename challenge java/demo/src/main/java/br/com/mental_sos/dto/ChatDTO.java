package br.com.mental_sos.dto;

import br.com.mental_sos.model.enuns.StatusChat;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ChatDTO {
    private Long id;
    private StatusChat status;
    private OffsetDateTime criadoEm;
    private Long criadorId;
    private String criadorUsername;
}