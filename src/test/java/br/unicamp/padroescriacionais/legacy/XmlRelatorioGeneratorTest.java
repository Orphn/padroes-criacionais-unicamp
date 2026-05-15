package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.XmlRelatorioGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class XmlRelatorioGeneratorTest {

    private IRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new XmlRelatorioGenerator();
        relatorio = new Relatorio(
                "Relatorio de Estoque",
                "Item X: 500 unidades",
                TipoRelatorio.ESTOQUE,
                LocalDateTime.of(2025, 5, 15, 10, 30)
        );
    }

    @Test
    void deveGerarXmlComTagRaizRelatorio() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<relatorio>"),
                "XML deve conter a tag de abertura <relatorio>");
        assertTrue(resultado.contains("</relatorio>"),
                "XML deve conter a tag de fechamento </relatorio>");
    }

    @Test
    void deveGerarXmlComTagTitulo() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<titulo>Relatorio de Estoque</titulo>"),
                "XML deve conter o titulo dentro da tag <titulo>");
    }

    @Test
    void deveGerarXmlComTagTipo() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<tipo>ESTOQUE</tipo>"),
                "XML deve conter o tipo do relatorio dentro da tag <tipo>");
    }

    @Test
    void deveGerarXmlComTagConteudo() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<conteudo>"),
                "XML deve conter a tag <conteudo>");
        assertTrue(resultado.contains("Item X: 500 unidades"),
                "XML deve conter o conteudo do relatorio");
        assertTrue(resultado.contains("</conteudo>"),
                "XML deve fechar a tag </conteudo>");
    }

    @Test
    void deveGerarXmlComTagDataGeracao() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<dataGeracao>"),
                "XML deve conter a tag <dataGeracao>");
        assertTrue(resultado.contains("2025-05-15"),
                "XML deve conter a data de geracao");
        assertTrue(resultado.contains("</dataGeracao>"),
                "XML deve fechar a tag </dataGeracao>");
    }
}
