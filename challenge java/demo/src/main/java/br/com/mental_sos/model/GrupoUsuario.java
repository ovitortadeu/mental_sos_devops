package br.com.mental_sos.model;

import br.com.mental_sos.model.enuns.TipoGrupoUsuario; // Certifique-se que este enum existe
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_SOS_GRUPO_USUARIO"
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

    @Enumerated(EnumType.ORDINAL) 
    @Column(name = "ID_GRUPO", nullable = false)
    private TipoGrupoUsuario tipoGrupo; 

    @OneToOne(mappedBy = "grupoUsuario", fetch = FetchType.LAZY)
    private Usuario usuario;

    public void setUsuarioAssociado(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && usuario.getGrupoUsuario() != this) {
            usuario.setGrupoUsuario(this);
        }
    }
}