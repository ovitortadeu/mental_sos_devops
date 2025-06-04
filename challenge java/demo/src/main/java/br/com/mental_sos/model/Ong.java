package br.com.mental_sos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_SOS_ONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    private Long contato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TB_SOS_USUARIO_ID", nullable = false,
                foreignKey = @ForeignKey(name = "TB_SOS_ONG_USUARIO_FK"))
    private Usuario administradorOng;
}