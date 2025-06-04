package br.com.mental_sos.model.enuns;

public enum StatusChat {
    ARQUIVADO(0),
    ATIVO(1),
    CONCLUIDO(2);

    private final int valor;

    StatusChat(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static StatusChat fromValor(Short valor) {
        if (valor == null) return null;
        for (StatusChat status : StatusChat.values()) {
            if (status.valor == valor) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de status do chat inválido: " + valor);
    }
}