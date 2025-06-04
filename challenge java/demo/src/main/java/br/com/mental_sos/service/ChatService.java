package br.com.mental_sos.service;

import br.com.mental_sos.dto.ChatCreateDTO;
import br.com.mental_sos.dto.ChatDTO;
import br.com.mental_sos.dto.ChatStatusUpdateDTO;
import br.com.mental_sos.exception.BusinessException;
import br.com.mental_sos.exception.ResourceNotFoundException;
import br.com.mental_sos.mapper.ChatMapperInterface;
import br.com.mental_sos.model.Chat;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.model.enuns.StatusChat;
import br.com.mental_sos.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UsuarioService usuarioService;
    private final ChatMapperInterface chatMapper;

    @Transactional
    public ChatDTO criarChat(Usuario criador, ChatCreateDTO createDTO) {
        Chat chat = new Chat();
        chat.setCriador(criador);
        chat.setStatus(StatusChat.ATIVO);
        Chat chatSalvo = chatRepository.save(chat);
        return chatMapper.toChatDTO(chatSalvo);
    }

    @Transactional(readOnly = true)
    public List<ChatDTO> listarMeusChats(String username) {
        Usuario usuario = usuarioService.findByUsername(username)
               .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + username));
        return chatRepository.findByCriadorOrderByCriadoEmDesc(usuario).stream()
                .map(chatMapper::toChatDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatDTO buscarChatPorIdDTO(Long id, String usernameAutenticado) {
        Chat chat = findChatById(id);
        return chatMapper.toChatDTO(chat);
    }

    @Transactional(readOnly = true)
    public Chat findChatById(Long id) {
        return chatRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Chat não encontrado com ID: " + id));
    }

    @Transactional
    public ChatDTO atualizarStatusChat(Long chatId, ChatStatusUpdateDTO statusUpdateDTO, String usernameAutenticado) {
        Chat chat = findChatById(chatId);
        Usuario usuarioAutenticado = usuarioService.findByUsername(usernameAutenticado)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado: " + usernameAutenticado));

        if (!chat.getCriador().equals(usuarioAutenticado) ) {
             throw new BusinessException("Usuário não autorizado a alterar o status deste chat.");
        }
        chat.setStatus(statusUpdateDTO.getStatus());
        Chat chatAtualizado = chatRepository.save(chat);
        return chatMapper.toChatDTO(chatAtualizado);
    }
}