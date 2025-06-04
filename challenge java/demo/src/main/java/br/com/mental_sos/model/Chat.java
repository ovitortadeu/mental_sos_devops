package br.com.mental_sos.model;

import br.com.mental_sos.model.enuns.StatusChat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "TB_SOS_CHAT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private StatusChat status;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", updatable = false, nullable = false)
    private OffsetDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TB_SOS_USUARIO_ID_CRIADOR", nullable = false,
                foreignKey = @ForeignKey(name = "TB_SOS_CHAT_USUARIO_CRIADOR_FK"))
    private Usuario criador;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens;
}