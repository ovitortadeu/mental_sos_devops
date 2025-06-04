package br.com.mental_sos.service;

import br.com.mental_sos.dto.UsuarioCreateDTO;
import br.com.mental_sos.dto.UsuarioDTO;
import br.com.mental_sos.dto.UsuarioUpdateDTO;
import br.com.mental_sos.exception.BusinessException;
import br.com.mental_sos.exception.ResourceNotFoundException;
import br.com.mental_sos.mapper.UsuarioMapperInterface;
import br.com.mental_sos.model.GrupoUsuario;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.repository.GrupoUsuarioRepository;
import br.com.mental_sos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final GrupoUsuarioRepository grupoUsuarioRepository; 
    private final UsuarioMapperInterface usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioCreateDTO createDTO) {
        if (usuarioRepository.findByUsername(createDTO.getUsername()).isPresent()) {
            throw new BusinessException("Username já existe.");
        }
        if (usuarioRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new BusinessException("Email já existe.");
        }

        GrupoUsuario grupo = new GrupoUsuario();
        if (createDTO.getTipoGrupo() == null) {
            throw new BusinessException("Tipo de grupo do usuário é obrigatório.");
        }

        grupo.setTipoGrupo(createDTO.getTipoGrupo());
        
        GrupoUsuario grupoSalvo = grupoUsuarioRepository.save(grupo);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(createDTO.getUsername());
        novoUsuario.setEmail(createDTO.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(createDTO.getSenha()));
        novoUsuario.setNumeroCrp(createDTO.getNumeroCrp());
        
        novoUsuario.setGrupoUsuario(grupoSalvo); 

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
  
        return usuarioMapper.toUsuarioDTO(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, UsuarioUpdateDTO updateDTO, String authenticatedUsername) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        if (updateDTO.getUsername() != null && !updateDTO.getUsername().isBlank() &&
            !usuario.getUsername().equals(updateDTO.getUsername()) && 
            usuarioRepository.findByUsername(updateDTO.getUsername()).isPresent()) {
            throw new BusinessException("Novo username já existe.");
        }
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().isBlank() &&
            !usuario.getEmail().equals(updateDTO.getEmail()) &&
            usuarioRepository.findByEmail(updateDTO.getEmail()).isPresent()) {
            throw new BusinessException("Novo email já existe.");
        }
        
        usuarioMapper.updateUsuarioFromDto(updateDTO, usuario);

        if (updateDTO.getSenha() != null && !updateDTO.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(updateDTO.getSenha()));
        }


        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioDTO(usuarioAtualizado);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }
    
    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id + " para deleção.");
        }
        usuarioRepository.deleteById(id);
    }
}