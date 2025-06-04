package br.com.mental_sos.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TB_SOS_USUARIO",
       uniqueConstraints = {
           @UniqueConstraint(name = "TB_SOS_USUARIO_UK_USERNAME", columnNames = "username"),
           @UniqueConstraint(name = "TB_SOS_USUARIO_UK_EMAIL", columnNames = "email")
       }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(exclude = {"grupoUsuario", "chatsCriados", "mensagensEnviadas", "mensagensComoPaciente", "mensagensComoPsicologo", "ongsAdministradas"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "TB_SOS_GRUPO_USUARIO_ID", referencedColumnName = "id", unique = true, nullable = false)
    private GrupoUsuario grupoUsuario;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String senha;

    @Column(name = "NUMERO_CRP", precision = 7)
    private Integer numeroCrp;

    @OneToMany(mappedBy = "criador", cascade = CascadeType.REMOVE)
    private List<Chat> chatsCriados;

    @OneToMany(mappedBy = "remetente", cascade = CascadeType.REMOVE)
    private List<Mensagem> mensagensEnviadas;

    @OneToMany(mappedBy = "paciente")
    private List<Mensagem> mensagensComoPaciente;

    @OneToMany(mappedBy = "psicologo")
    private List<Mensagem> mensagensComoPsicologo;

    @OneToMany(mappedBy = "administradorOng", cascade = CascadeType.REMOVE)
    private List<Ong> ongsAdministradas;

    // Método helper para facilitar a associação bidirecional
    public void setGrupoUsuarioAssociado(GrupoUsuario grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
        if (grupoUsuario != null && grupoUsuario.getUsuario() != this) {
            grupoUsuario.setUsuario(this);
        }
    }
}