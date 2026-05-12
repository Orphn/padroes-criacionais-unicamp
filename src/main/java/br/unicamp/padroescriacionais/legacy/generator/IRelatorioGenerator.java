package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public interface IRelatorioGenerator {
    String gerar(Relatorio relatorio);
}