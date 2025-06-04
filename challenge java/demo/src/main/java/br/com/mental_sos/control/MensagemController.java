package br.com.mental_sos.control;

import br.com.mental_sos.dto.MensagemCreateDTO;
import br.com.mental_sos.dto.MensagemDTO;
import br.com.mental_sos.service.MensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {
    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemDTO> enviarMensagem(@RequestBody @Valid MensagemCreateDTO createDTO,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        MensagemDTO mensagemEnviada = mensagemService.enviarMensagem(createDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemEnviada);
    }
}