package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoSistemaTest {

    @Test
    void deveRetornarSempreAMesmaInstancia() {
        ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstancia();

        assertSame(config1, config2);
    }

    @Test
    void devePermitirAlteracaoDeAmbiente() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        String ambienteOriginal = config.getAmbiente();

        config.setAmbiente("PROD");

        assertEquals("PROD", config.getAmbiente());
        config.setAmbiente(ambienteOriginal);
    }

    @Test
    void devePermitirAlteracaoDeDebug() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        boolean debugOriginal = config.isDebugAtivo();

        config.setDebugAtivo(true);

        assertTrue(config.isDebugAtivo());
        config.setDebugAtivo(debugOriginal);
    }

    @Test
    void devePermitirAlteracaoDeDiretorio() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();
        String diretorioOriginal = config.getDiretorioExportacao();

        config.setDiretorioExportacao("/novo/diretorio");

        assertEquals("/novo/diretorio", config.getDiretorioExportacao());
        config.setDiretorioExportacao(diretorioOriginal);
    }

    @Test
    void alteracaoEmUmaReferenciaAfetaTodas() {
        ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstancia();
        String ambienteOriginal = config1.getAmbiente();

        config1.setAmbiente("PROD");

        assertEquals("PROD", config1.getAmbiente());
        assertEquals("PROD", config2.getAmbiente());
        config1.setAmbiente(ambienteOriginal);
    }

    @Test
    void configuracaoServiceDeveRetornarConfiguracaoNaoNula() {
        ConfiguracaoService service = new ConfiguracaoService();
        assertNotNull(service.getConfiguracao());
        assertFalse(service.getConfiguracao().getNomeEmpresa().isBlank());
        assertSame(ConfiguracaoSistema.getInstancia(), service.getConfiguracao());
    }
}
