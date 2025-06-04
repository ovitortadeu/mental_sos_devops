package br.com.mental_sos.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TB_SOS_MENSAGEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    @Column(nullable = false)
    private String conteudo;

    @CreationTimestamp
    @Column(name = "ENVIADA_EM", updatable = false, nullable = false)
    private OffsetDateTime enviadaEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TB_SOS_CHAT_ID", nullable = false,
                foreignKey = @ForeignKey(name = "TB_SOS_MENSAGEM_CHAT_FK"))
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REMETENTE_USUARIO_ID", nullable = false,
                foreignKey = @ForeignKey(name = "TB_SOS_MENSAGEM_REMETENTE_FK"))
    private Usuario remetente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_SOS_USUARIO_ID_PACIENTE",
                foreignKey = @ForeignKey(name = "TB_SOS_MENSAGEM_PACIENTE_FK"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TB_SOS_USUARIO_ID_PSICOLOGO",
                foreignKey = @ForeignKey(name = "TB_SOS_MENSAGEM_PSICOLOGO_FK"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario psicologo;
}