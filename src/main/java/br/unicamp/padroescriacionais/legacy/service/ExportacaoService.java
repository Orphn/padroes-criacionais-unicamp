package br.unicamp.padroescriacionais.legacy.service;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.generator.IRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.factory.GeradorRegistry;
import br.unicamp.padroescriacionais.legacy.factory.IRelatorioGeneratorFactory;

public class ExportacaoService {

    private final ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();

    public void exportar(Relatorio relatorio, FormatoRelatorio formato) {

        IRelatorioGeneratorFactory factory = GeradorRegistry.getFactory(formato);
        IRelatorioGenerator generator = factory.criarGerador();

        String conteudoFormatado = generator.gerar(relatorio);

        String nomeArquivo = relatorio.getTitulo()
                .replace(" ", "_")
                .toLowerCase()
                + "." + formato.name().toLowerCase();

        String caminhoCompleto = configuracao.getDiretorioExportacao() + "/" + nomeArquivo;

        System.out.println("[EXPORTACAO] Empresa  : " + configuracao.getNomeEmpresa());
        System.out.println("[EXPORTACAO] Ambiente : " + configuracao.getAmbiente());
        System.out.println("[EXPORTACAO] Arquivo  : " + caminhoCompleto);
        System.out.println("[EXPORTACAO] Conteudo :");
        System.out.println(conteudoFormatado);
    }
}
