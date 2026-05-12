package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public class XmlRelatorioGenerator implements IRelatorioGenerator {

    @Override
    public String gerar(Relatorio relatorio) {
        return "<relatorio>\n" +
                "  <titulo>" + relatorio.getTitulo() + "</titulo>\n" +
                "  <tipo>" + relatorio.getTipo() + "</tipo>\n" +
                "  <dataGeracao>" + relatorio.getDataGeracao() + "</dataGeracao>\n" +
                "  <conteudo>" + relatorio.getConteudo() + "</conteudo>\n" +
                "</relatorio>";
    }
}