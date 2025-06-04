package br.com.mental_sos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatCreateDTO {
    @NotNull
    private Long criadorId;
}