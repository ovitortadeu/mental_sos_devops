// Arquivo: br/com/mental_sos/dto/ChatStatusUpdateDTO.java
package br.com.mental_sos.dto;

import br.com.mental_sos.model.enuns.StatusChat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatStatusUpdateDTO {
    @NotNull
    private StatusChat status;
}