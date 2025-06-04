package br.com.mental_sos.service;

import br.com.mental_sos.dto.OngCreateUpdateDTO;
import br.com.mental_sos.dto.OngDTO;
import br.com.mental_sos.exception.ResourceNotFoundException;
import br.com.mental_sos.mapper.OngMapperInterface;
import br.com.mental_sos.model.Ong;
import br.com.mental_sos.model.Usuario;
import br.com.mental_sos.repository.OngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OngService {
    private final OngRepository ongRepository;
    private final UsuarioService usuarioService;
    private final OngMapperInterface ongMapper;

    @Transactional
    public OngDTO criarOng(OngCreateUpdateDTO createDTO) {
        Usuario admin = usuarioService.findUsuarioById(createDTO.getAdministradorOngId());
        Ong ong = ongMapper.toOng(createDTO, admin);
        Ong ongSalva = ongRepository.save(ong);
        return ongMapper.toOngDTO(ongSalva);
    }

    @Transactional(readOnly = true)
    public List<OngDTO> listarTodasOngs() {
        return ongRepository.findAll().stream()
                .map(ongMapper::toOngDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OngDTO buscarOngPorId(Long id) {
        Ong ong = ongRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ONG não encontrada com ID: " + id));
        return ongMapper.toOngDTO(ong);
    }

    @Transactional
    public OngDTO atualizarOng(Long id, OngCreateUpdateDTO updateDTO) {
        Ong ongExistente = ongRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ONG não encontrada com ID: " + id));
        Usuario admin = usuarioService.findUsuarioById(updateDTO.getAdministradorOngId());

        ongMapper.updateOngFromDto(updateDTO, admin, ongExistente);
        Ong ongAtualizada = ongRepository.save(ongExistente);
        return ongMapper.toOngDTO(ongAtualizada);
    }

    @Transactional
    public void deletarOng(Long id) {
        if (!ongRepository.existsById(id)) {
            throw new ResourceNotFoundException("ONG não encontrada com ID: " + id);
        }
        ongRepository.deleteById(id);
    }
}