package br.com.mental_sos.model;

import br.com.mental_sos.model.enuns.TipoGrupoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_SOS_GRUPO_USUARIO",
        uniqueConstraints = @UniqueConstraint(name = "TB_SOS_GRUPO_USUARIO_UK_USUARIO", columnNames = "TB_SOS_USUARIO_ID")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(exclude = "usuario")
public class GrupoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ID_GRUPO", nullable = false)
    private TipoGrupoUsuario tipoGrupo;

    @OneToOne(mappedBy = "grupoUsuario", fetch = FetchType.LAZY, optional = false)
    private Usuario usuario;
}