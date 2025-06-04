package br.com.mental_sos.repository;

import br.com.mental_sos.model.Chat;
import br.com.mental_sos.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByChatOrderByEnviadaEmAsc(Chat chat);
}