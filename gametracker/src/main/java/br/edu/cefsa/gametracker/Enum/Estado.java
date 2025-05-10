package br.edu.cefsa.gametracker.Enum;

public enum Estado {
    COMPLETO("Completo"),
    EM_ANDAMENTO("Em andamento"),
    ABANDONADO("Abandonado"),
    REVISITANDO("Revisitando"),
    DESEJO("Desejo");

    private final String descricao;

    Estado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}