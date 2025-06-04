package br.com.mental_sos.service;

import br.com.mental_sos.dto.MensagemCreateDTO;
import br.com.mental_sos.dto.MensagemDTO;
import br.com.mental_sos.exception.ResourceNotFoundException;
import br.com.mental_sos.mapper.MensagemMapperInterface;
import br.com.mental_sos.model.Chat;
import br.com.mental_sos.model.Mensagem;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.repository.MensagemRepository;
import br.com.mental_sos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensagemService {
    private final MensagemRepository mensagemRepository;
    private final ChatService chatService;
    private final UsuarioRepository usuarioRepository;
    private final MensagemMapperInterface mensagemMapper;

    @Transactional
    public MensagemDTO enviarMensagem(MensagemCreateDTO createDTO, String remetenteUsername) { 
        Usuario remetente = usuarioRepository.findByUsername(remetenteUsername)
            .orElseThrow(() -> new ResourceNotFoundException("Remetente não encontrado: " + remetenteUsername));
        Chat chat = chatService.findChatById(createDTO.getChatId()); 

        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(createDTO.getConteudo());
        mensagem.setChat(chat);
        mensagem.setRemetente(remetente);

        if (createDTO.getPacienteId() != null) {
            Usuario paciente = usuarioRepository.findById(createDTO.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + createDTO.getPacienteId()));
            mensagem.setPaciente(paciente);
        }
        if (createDTO.getPsicologoId() != null) {
            Usuario psicologo = usuarioRepository.findById(createDTO.getPsicologoId())
                .orElseThrow(() -> new ResourceNotFoundException("Psicólogo não encontrado com ID: " + createDTO.getPsicologoId()));
            mensagem.setPsicologo(psicologo);
        }
        Mensagem mensagemSalva = mensagemRepository.save(mensagem);
        return mensagemMapper.toMensagemDTO(mensagemSalva);
    }

    @Transactional(readOnly = true)
    public List<MensagemDTO> listarMensagensDoChat(Long chatId, String usernameAutenticado) {
        Chat chat = chatService.findChatById(chatId);
        return mensagemRepository.findByChatOrderByEnviadaEmAsc(chat).stream()
                .map(mensagemMapper::toMensagemDTO)
                .collect(Collectors.toList());
    }
}