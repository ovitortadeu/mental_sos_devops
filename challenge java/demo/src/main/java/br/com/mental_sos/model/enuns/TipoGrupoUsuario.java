package br.com.mental_sos.model.enuns;

public enum TipoGrupoUsuario {
    PACIENTE(1),
    PSICOLOGO(2),
    ADMIN(3);

    private final int valor;

    TipoGrupoUsuario(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static TipoGrupoUsuario fromValor(Short valor) {
        if (valor == null) return null;
        for (TipoGrupoUsuario tipo : TipoGrupoUsuario.values()) {
            if (tipo.valor == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor de id_grupo inválido: " + valor);
    }
}