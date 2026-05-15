package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.factory.*;
import br.unicamp.padroescriacionais.legacy.generator.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioGeneratorFactoryTest {

    @Test
    void pdfFactoryDeveCriarPdfGenerator() {
        IRelatorioGeneratorFactory factory = new PdfGeneratorFactory();
        IRelatorioGenerator generator = factory.criarGerador();

        assertInstanceOf(PdfRelatorioGenerator.class, generator,
                "PdfGeneratorFactory deve criar instancia de PdfRelatorioGenerator");
    }

    @Test
    void csvFactoryDeveCriarCsvGenerator() {
        IRelatorioGeneratorFactory factory = new CsvGeneratorFactory();
        IRelatorioGenerator generator = factory.criarGerador();

        assertInstanceOf(CsvRelatorioGenerator.class, generator,
                "CsvGeneratorFactory deve criar instancia de CsvRelatorioGenerator");
    }

    @Test
    void jsonFactoryDeveCriarJsonGenerator() {
        IRelatorioGeneratorFactory factory = new JsonGeneratorFactory();
        IRelatorioGenerator generator = factory.criarGerador();

        assertInstanceOf(JsonRelatorioGenerator.class, generator,
                "JsonGeneratorFactory deve criar instancia de JsonRelatorioGenerator");
    }

    @Test
    void xmlFactoryDeveCriarXmlGenerator() {
        IRelatorioGeneratorFactory factory = new XmlGeneratorFactory();
        IRelatorioGenerator generator = factory.criarGerador();

        assertInstanceOf(XmlRelatorioGenerator.class, generator,
                "XmlGeneratorFactory deve criar instancia de XmlRelatorioGenerator");
    }

    @Test
    void htmlFactoryDeveCriarHtmlGenerator() {
        IRelatorioGeneratorFactory factory = new HtmlGeneratorFactory();
        IRelatorioGenerator generator = factory.criarGerador();

        assertInstanceOf(HtmlRelatorioGenerator.class, generator,
                "HtmlGeneratorFactory deve criar instancia de HtmlRelatorioGenerator");
    }

    @Test
    void cadaChamadaDeveCriarNovaInstancia() {
        IRelatorioGeneratorFactory factory = new PdfGeneratorFactory();

        IRelatorioGenerator generator1 = factory.criarGerador();
        IRelatorioGenerator generator2 = factory.criarGerador();

        assertNotSame(generator1, generator2,
                "Cada chamada a criarGerador() deve retornar uma nova instancia");
    }

    @Test
    void todosGeradorDevemImplementarInterface() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(formato);
            IRelatorioGenerator generator = factory.criarGerador();

            assertInstanceOf(IRelatorioGenerator.class, generator,
                    "Gerador para formato " + formato + " deve implementar IRelatorioGenerator");
        }
    }
}
