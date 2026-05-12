package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public class HtmlRelatorioGenerator implements IRelatorioGenerator {

    @Override
    public String gerar(Relatorio relatorio) {
        return "<!DOCTYPE html>\n<html>\n<head><title>" + relatorio.getTitulo() + "</title></head>\n" +
                "<body>\n" +
                "  <h1>" + relatorio.getTitulo() + "</h1>\n" +
                "  <p><strong>Tipo:</strong> " + relatorio.getTipo() + "</p>\n" +
                "  <p><strong>Gerado em:</strong> " + relatorio.getDataGeracao() + "</p>\n" +
                "  <hr/>\n" +
                "  <pre>" + relatorio.getConteudo() + "</pre>\n" +
                "</body>\n</html>";
    }
}