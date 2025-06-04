package br.com.mental_sos.control;

import br.com.mental_sos.dto.UsuarioCreateDTO;
import br.com.mental_sos.dto.UsuarioDTO;
import br.com.mental_sos.dto.UsuarioUpdateDTO;
import br.com.mental_sos.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody @Valid UsuarioCreateDTO createDTO) {
        UsuarioDTO usuarioCriado = usuarioService.criarUsuario(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (isAuthenticated() and #userDetails.username == @usuarioService.findUsuarioById(#id).username)")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id,
                                                     @RequestBody @Valid UsuarioUpdateDTO updateDTO,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, updateDTO, userDetails.getUsername());
        return ResponseEntity.ok(usuarioAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}