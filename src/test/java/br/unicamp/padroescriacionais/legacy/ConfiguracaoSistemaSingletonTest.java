package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import br.unicamp.padroescriacionais.legacy.service.ExportacaoService;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoSistemaSingletonTest {

    private String nomeEmpresaOriginal;
    private String ambienteOriginal;
    private String diretorioOriginal;
    private boolean debugOriginal;

    private final PrintStream saidaOriginal = System.out;

    @BeforeEach
    void salvarEstadoOriginal() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        nomeEmpresaOriginal = config.getNomeEmpresa();
        ambienteOriginal = config.getAmbiente();
        diretorioOriginal = config.getDiretorioExportacao();
        debugOriginal = config.isDebugAtivo();
    }

    @AfterEach
    void restaurarEstadoOriginal() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        config.setNomeEmpresa(nomeEmpresaOriginal);
        config.setAmbiente(ambienteOriginal);
        config.setDiretorioExportacao(diretorioOriginal);
        config.setDebugAtivo(debugOriginal);
        System.setOut(saidaOriginal);
    }

    @Test
    void construtorDeveSerPrivado() throws NoSuchMethodException {
        Constructor<ConfiguracaoSistema> construtor = ConfiguracaoSistema.class
                .getDeclaredConstructor(String.class, String.class, String.class, boolean.class);

        assertFalse(construtor.canAccess(null),
                "O construtor de ConfiguracaoSistema deve ser privado para garantir o padrao Singleton");
    }

    @Test
    void instanciaDeveSerInicializadaComValoresPadrao() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();

        assertEquals("Empresa XPTO Ltda.", config.getNomeEmpresa(),
                "Nome da empresa padrao deve ser 'Empresa XPTO Ltda.'");
        assertEquals("DEV", config.getAmbiente(),
                "Ambiente padrao deve ser 'DEV'");
        assertEquals("/tmp/relatorios", config.getDiretorioExportacao(),
                "Diretorio de exportacao padrao deve ser '/tmp/relatorios'");
        assertFalse(config.isDebugAtivo(),
                "Debug deve estar desativado por padrao");
    }

    @Test
    void instanciaNuncaDeveSerNull() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();

        assertNotNull(config,
                "getInstancia() nunca deve retornar null");
    }

    @Test
    void alteracaoDeConfiguracaoDeveSerVisivelEmNovoService() {
        ConfiguracaoSistema.getInstancia().setNomeEmpresa("Nova Empresa Teste");

        ConfiguracaoService service = new ConfiguracaoService();

        assertEquals("Nova Empresa Teste", service.getConfiguracao().getNomeEmpresa(),
                "Um novo ConfiguracaoService deve enxergar a alteracao feita no Singleton");
    }

    @Test
    void exportacaoServiceDeveUsarMesmaConfiguracao() {
        ConfiguracaoSistema configDireta = ConfiguracaoSistema.getInstancia();
        configDireta.setNomeEmpresa("Empresa Teste Exportacao");

        ExportacaoService exportacaoService = new ExportacaoService();

        ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));

        Relatorio relatorio = new Relatorio(
                "Teste", "Conteudo", TipoRelatorio.VENDAS, LocalDateTime.now());
        exportacaoService.exportar(relatorio, FormatoRelatorio.PDF);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("Empresa Teste Exportacao"),
                "ExportacaoService deve usar a configuracao do Singleton, exibindo o nome da empresa alterado");
    }

    @Test
    void relatorioServiceDeveUsarMesmaConfiguracao() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        config.setDebugAtivo(true);

        RelatorioService relatorioService = new RelatorioService();

        ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));

        relatorioService.gerarRelatorio(TipoRelatorio.VENDAS, FormatoRelatorio.PDF);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("[DEBUG-RelatorioService]"),
                "RelatorioService deve usar a configuracao do Singleton; com debug ativo, deve exibir mensagem de debug");
    }

    @Test
    void alteracaoDeDiretorioDeveRefletirNaExportacao() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        config.setDiretorioExportacao("/novo/diretorio/exportacao");

        ExportacaoService exportacaoService = new ExportacaoService();

        ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));

        Relatorio relatorio = new Relatorio(
                "Teste Dir", "Conteudo", TipoRelatorio.VENDAS, LocalDateTime.now());
        exportacaoService.exportar(relatorio, FormatoRelatorio.CSV);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("/novo/diretorio/exportacao"),
                "ExportacaoService deve exibir o novo diretorio de exportacao configurado no Singleton");
    }
}
