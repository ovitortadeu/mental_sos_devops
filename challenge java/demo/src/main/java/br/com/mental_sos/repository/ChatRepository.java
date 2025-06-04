package br.com.mental_sos.repository;

import br.com.mental_sos.model.Chat;
import br.com.mental_sos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByCriadorOrderByCriadoEmDesc(Usuario criador);
    List<Chat> findByCriadorUsername(String username);
}