package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.JsonRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public class JsonGeneratorFactory implements IRelatorioGeneratorFactory {
    @Override
    public IRelatorioGenerator criarGerador() {
        return new JsonRelatorioGenerator();
    }
}