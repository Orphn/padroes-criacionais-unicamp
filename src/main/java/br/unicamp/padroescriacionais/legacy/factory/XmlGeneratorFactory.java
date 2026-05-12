package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.XmlRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public class XmlGeneratorFactory implements IRelatorioGeneratorFactory {
    @Override
    public IRelatorioGenerator criarGerador() {
        return new XmlRelatorioGenerator();
    }
}