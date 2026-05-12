package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public interface IRelatorioGeneratorFactory {
    IRelatorioGenerator criarGerador();
}