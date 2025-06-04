package br.com.mental_sos.control;

import br.com.mental_sos.dto.OngCreateUpdateDTO;
import br.com.mental_sos.dto.OngDTO;
import br.com.mental_sos.service.OngService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ongs")
@RequiredArgsConstructor
public class OngController {
    private final OngService ongService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OngDTO> criarOng(@RequestBody @Valid OngCreateUpdateDTO createDTO) {
        OngDTO ongCriada = ongService.criarOng(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ongCriada);
    }

    @GetMapping
    public ResponseEntity<List<OngDTO>> listarTodasOngs() {
        List<OngDTO> ongs = ongService.listarTodasOngs();
        return ResponseEntity.ok(ongs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngDTO> buscarOngPorId(@PathVariable Long id) {
        OngDTO ong = ongService.buscarOngPorId(id);
        return ResponseEntity.ok(ong);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OngDTO> atualizarOng(@PathVariable Long id, @RequestBody @Valid OngCreateUpdateDTO updateDTO) {
        OngDTO ongAtualizada = ongService.atualizarOng(id, updateDTO);
        return ResponseEntity.ok(ongAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletarOng(@PathVariable Long id) {
        ongService.deletarOng(id);
        return ResponseEntity.noContent().build();
    }
}