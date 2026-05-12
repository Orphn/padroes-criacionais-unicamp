package br.unicamp.padroescriacionais.legacy.factory;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import java.util.EnumMap;
import java.util.Map;

public class GeradorRegistry {

    private static final Map<FormatoRelatorio, IRelatorioGeneratorFactory> factories = new EnumMap<>(
            FormatoRelatorio.class);

    static {
        factories.put(FormatoRelatorio.PDF, new PdfGeneratorFactory());
        factories.put(FormatoRelatorio.CSV, new CsvGeneratorFactory());
        factories.put(FormatoRelatorio.JSON, new JsonGeneratorFactory());
        factories.put(FormatoRelatorio.XML, new XmlGeneratorFactory());
        factories.put(FormatoRelatorio.HTML, new HtmlGeneratorFactory());
    }

    public static IRelatorioGeneratorFactory getFactory(FormatoRelatorio formato) {
        IRelatorioGeneratorFactory factory = factories.get(formato);
        if (factory == null) {
            throw new IllegalArgumentException("Formato não suportado: " + formato);
        }
        return factory;
    }
}