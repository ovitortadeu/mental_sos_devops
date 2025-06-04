package br.com.mental_sos.control;

import br.com.mental_sos.dto.ChatCreateDTO;
import br.com.mental_sos.dto.ChatDTO;
import br.com.mental_sos.exception.ResourceNotFoundException;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.repository.UsuarioRepository;
import br.com.mental_sos.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List; 

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<ChatDTO> criarChat(@RequestBody @Valid ChatCreateDTO createDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Usuario criador = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário criador não autenticado ou não encontrado"));
        ChatDTO chatCriado = chatService.criarChat(criador, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatCriado);
    }

    @GetMapping("/meus")
    public ResponseEntity<List<ChatDTO>> listarMeusChats(@AuthenticationPrincipal UserDetails userDetails) {
        List<ChatDTO> chats = chatService.listarMeusChats(userDetails.getUsername());
        return ResponseEntity.ok(chats);
    }
}