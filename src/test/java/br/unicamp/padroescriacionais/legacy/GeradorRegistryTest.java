package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.factory.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeradorRegistryTest {

    @Test
    void deveRetornarFactoryParaPdf() {
        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(FormatoRelatorio.PDF);

        assertInstanceOf(PdfGeneratorFactory.class, factory,
                "Factory para PDF deve ser PdfGeneratorFactory");
    }

    @Test
    void deveRetornarFactoryParaCsv() {
        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(FormatoRelatorio.CSV);

        assertInstanceOf(CsvGeneratorFactory.class, factory,
                "Factory para CSV deve ser CsvGeneratorFactory");
    }

    @Test
    void deveRetornarFactoryParaJson() {
        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(FormatoRelatorio.JSON);

        assertInstanceOf(JsonGeneratorFactory.class, factory,
                "Factory para JSON deve ser JsonGeneratorFactory");
    }

    @Test
    void deveRetornarFactoryParaXml() {
        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(FormatoRelatorio.XML);

        assertInstanceOf(XmlGeneratorFactory.class, factory,
                "Factory para XML deve ser XmlGeneratorFactory");
    }

    @Test
    void deveRetornarFactoryParaHtml() {
        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(FormatoRelatorio.HTML);

        assertInstanceOf(HtmlGeneratorFactory.class, factory,
                "Factory para HTML deve ser HtmlGeneratorFactory");
    }

    @Test
    void todosFormatosDevemTerFactoryRegistrada() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            assertDoesNotThrow(
                    () -> GeradorRegistry.getFactory(formato),
                    "Formato " + formato + " deveria ter uma factory registrada no GeradorRegistry"
            );
        }
    }

    @Test
    void deveRetornarNaoNuloParaTodosFormatos() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(formato);

            assertNotNull(factory,
                    "Factory para formato " + formato + " nao deve ser null");
        }
    }
}
