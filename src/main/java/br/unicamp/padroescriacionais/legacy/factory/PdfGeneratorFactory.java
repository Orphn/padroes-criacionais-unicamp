package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.generator.PdfRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;

public class PdfGeneratorFactory implements IRelatorioGeneratorFactory {
    @Override
    public IRelatorioGenerator criarGerador() {
        return new PdfRelatorioGenerator();
    }
}