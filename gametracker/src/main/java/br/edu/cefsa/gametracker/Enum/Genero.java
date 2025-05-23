package br.edu.cefsa.gametracker.Enum;

import lombok.Getter;

@Getter
public enum Genero {
    ACAO("Ação"),
    AVENTURA("Aventura"),
    RPG("RPG"),
    ESTRATEGIA("Estratégia"),
    TIRO("Tiro"),
    CORRIDA("Corrida"),
    ESPORTE("Esporte"),
    SIMULACAO("Simulação"),
    PUZZLE("Puzzle"),
    TERROR("Terror"),
    PLATAFORMA("Plataforma"),
    MULTIJOGADOR_ONLINE("Multijogador Online"),
    OUTRO("Outro");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

}