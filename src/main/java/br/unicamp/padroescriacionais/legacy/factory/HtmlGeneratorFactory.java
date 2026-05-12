package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.HtmlRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public class HtmlGeneratorFactory implements IRelatorioGeneratorFactory {
    @Override
    public IRelatorioGenerator criarGerador() {
        return new HtmlRelatorioGenerator();
    }
}