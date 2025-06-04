package br.com.mental_sos.repository;

import br.com.mental_sos.model.Ong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngRepository extends JpaRepository<Ong, Long> {
}