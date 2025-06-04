package br.com.mental_sos.service;

import br.com.mental_sos.dto.UsuarioCreateDTO;
import br.com.mental_sos.dto.UsuarioDTO;
import br.com.mental_sos.dto.UsuarioUpdateDTO;
import br.com.mental_sos.exception.BusinessException;
import br.com.mental_sos.exception.ResourceNotFoundException; 
import br.com.mental_sos.mapper.UsuarioMapperInterface;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
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
    private final UsuarioMapperInterface usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioCreateDTO createDTO) {
        if (usuarioRepository.findByUsername(createDTO.getUsername()).isPresent()) {
            throw new BusinessException("Username já existe.");
        }
        if (usuarioRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new BusinessException("Email já existe.");
        }

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("proc_criar_usuario_com_grupo");

        query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_senha", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numero_crp", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id_grupo_conceitual", Short.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_out_usuario_id", Long.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_out_grupo_usuario_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_username", createDTO.getUsername());
        query.setParameter("p_email", createDTO.getEmail());
        query.setParameter("p_senha", passwordEncoder.encode(createDTO.getSenha()));
        query.setParameter("p_numero_crp", createDTO.getNumeroCrp());
        query.setParameter("p_id_grupo_conceitual", (short) createDTO.getTipoGrupo().getValor());

        query.execute();

        Number outUsuarioIdNum = (Number) query.getOutputParameterValue("p_out_usuario_id");

        if (outUsuarioIdNum == null || outUsuarioIdNum.longValue() == -1) {
            throw new BusinessException("Falha ao criar usuário via procedure.");
        }
        Long novoUsuarioId = outUsuarioIdNum.longValue();

        Usuario usuarioCriado = usuarioRepository.findById(novoUsuarioId)
                .orElseThrow(() -> new BusinessException("Usuário criado pela procedure não encontrado. ID: " + novoUsuarioId));

        return usuarioMapper.toUsuarioDTO(usuarioCriado);
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
            .orElseThrow(() -> new BusinessException("Usuário não encontrado com ID: " + id));
    }
    
    
    
    
    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        usuarioRepository.delete(usuario);
    }
    
    
    
}