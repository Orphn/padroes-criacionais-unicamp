package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.HtmlRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HtmlRelatorioGeneratorTest {

    private IRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new HtmlRelatorioGenerator();
        relatorio = new Relatorio(
                "Relatorio de Vendas",
                "Produto A: 150 unidades",
                TipoRelatorio.VENDAS,
                LocalDateTime.of(2025, 5, 15, 10, 30)
        );
    }

    @Test
    void deveGerarHtmlComDoctype() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.startsWith("<!DOCTYPE html>"),
                "HTML gerado deve comecar com <!DOCTYPE html>");
    }

    @Test
    void deveGerarHtmlComTituloNaTag() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<title>Relatorio de Vendas</title>"),
                "HTML deve conter o titulo do relatorio dentro da tag <title>");
    }

    @Test
    void deveGerarHtmlComConteudoNaTagPre() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("<pre>"),
                "HTML deve conter a tag <pre>");
        assertTrue(resultado.contains("Produto A: 150 unidades"),
                "HTML deve conter o conteudo do relatorio");
        assertTrue(resultado.contains("</pre>"),
                "HTML deve fechar a tag </pre>");
    }

    @Test
    void deveGerarHtmlComTipoDoRelatorio() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("VENDAS"),
                "HTML deve conter o tipo do relatorio");
    }

    @Test
    void deveGerarHtmlComDataGeracao() {
        String resultado = generator.gerar(relatorio);

        assertTrue(resultado.contains("2025-05-15"),
                "HTML deve conter a data de geracao do relatorio");
    }
}
