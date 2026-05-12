package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.CsvRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public class CsvGeneratorFactory implements IRelatorioGeneratorFactory {
    @Override
    public IRelatorioGenerator criarGerador() {
        return new CsvRelatorioGenerator();
    }
}