# Proposta de Novos Testes Unitários

Abaixo estão os testes **novos**.

---

## 1. Novos Formatos Adicionados (HTML e XML)

> [!NOTE]
> Os testes existentes já cobrem PDF, CSV e JSON. Os formatos **HTML** e **XML** foram adicionados no PR do Factory Method e não possuem testes dedicados que validem a estrutura da sua saída.

### Arquivo: `HtmlRelatorioGeneratorTest.java`

| # | Nome do Teste | O que verifica | Por que é interessante |
|---|--------------|----------------|----------------------|
| 1 | `deveGerarHtmlComDoctype` | Que o resultado começa com `<!DOCTYPE html>` | Garante que o gerador produz HTML válido; um DOCTYPE ausente faria o navegador entrar em *quirks mode*. |
| 2 | `deveGerarHtmlComTituloNaTag` | Que o HTML contém `<title>` com o título do relatório | Valida que o título do relatório é usado corretamente dentro da tag `<title>`, essencial para exibição no browser. |
| 3 | `deveGerarHtmlComConteudoNaTagPre` | Que o HTML contém `<pre>` com o conteúdo do relatório | Confirma que o conteúdo bruto está encapsulado em `<pre>`, preservando formatação. |
| 4 | `deveGerarHtmlComTipoDoRelatorio` | Que a saída contém o tipo (e.g. `VENDAS`) | Garante que a informação de tipo não é perdida na geração HTML. |
| 5 | `deveGerarHtmlComDataGeracao` | Que a saída contém a data de geração | Assegura rastreabilidade temporal no documento gerado. |

### Arquivo sugerido: `XmlRelatorioGeneratorTest.java`

| # | Nome do Teste | O que verifica | Por que é interessante |
|---|--------------|----------------|----------------------|
| 6 | `deveGerarXmlComTagRaizRelatorio` | Que o resultado contém `<relatorio>` e `</relatorio>` | Valida a estrutura básica do XML; sem a tag raiz, o XML é inválido. |
| 7 | `deveGerarXmlComTagTitulo` | Que contém `<titulo>` com o título correto | Verifica se os dados estão nos elementos XML corretos. |
| 8 | `deveGerarXmlComTagTipo` | Que contém `<tipo>` com o tipo do relatório | Garante que a informação de tipo é serializada. |
| 9 | `deveGerarXmlComTagConteudo` | Que contém `<conteudo>` com o conteúdo | Verifica integridade do campo principal do relatório. |
| 10 | `deveGerarXmlComTagDataGeracao` | Que contém `<dataGeracao>` com a data | Garante que o timestamp de geração está presente no XML. |

---

## 2. Comportamento das Fábricas (Factory Method + Registry)

> [!NOTE]
> Estes testes validam o padrão **Factory Method** em si: se as fábricas concretas retornam o tipo correto, se o Registry resolve corretamente cada formato, e se o contrato polimórfico via `IRelatorioGenerator` é respeitado.

### Arquivo sugerido: `GeradorRegistryTest.java`

| # | Nome do Teste | O que verifica | Por que é interessante |
|---|--------------|----------------|----------------------|
| 11 | `deveRetornarFactoryParaPdf` | `GeradorRegistry.getFactory(PDF)` retorna `PdfGeneratorFactory` | Confirma que o registro mapeia PDF para a fábrica correta. |
| 12 | `deveRetornarFactoryParaCsv` | `GeradorRegistry.getFactory(CSV)` retorna `CsvGeneratorFactory` | Idem para CSV. |
| 13 | `deveRetornarFactoryParaJson` | `GeradorRegistry.getFactory(JSON)` retorna `JsonGeneratorFactory` | Idem para JSON. |
| 14 | `deveRetornarFactoryParaXml` | `GeradorRegistry.getFactory(XML)` retorna `XmlGeneratorFactory` | Idem para XML (formato novo). |
| 15 | `deveRetornarFactoryParaHtml` | `GeradorRegistry.getFactory(HTML)` retorna `HtmlGeneratorFactory` | Idem para HTML (formato novo). |
| 16 | `todosFormatosDevemTerFactoryRegistrada` | Itera sobre `FormatoRelatorio.values()` e verifica que nenhum lança exceção | Protege contra o cenário em que alguém adiciona um novo formato ao enum mas esquece de registrar a fábrica. É um "safety net". |
| 17 | `deveRetornarNaoNuloParaTodosFormatos` | Itera e verifica que `getFactory()` nunca retorna `null` | Complementa o anterior: pode haver formato registrado com valor `null`. |

### Arquivo sugerido: `RelatorioGeneratorFactoryTest.java`

| # | Nome do Teste | O que verifica | Por que é interessante |
|---|--------------|----------------|----------------------|
| 18 | `pdfFactoryDeveCriarPdfGenerator` | `new PdfGeneratorFactory().criarGerador()` é `instanceof PdfRelatorioGenerator` | Verifica que o Factory Method concreto retorna o tipo correto — essência do padrão. |
| 19 | `csvFactoryDeveCriarCsvGenerator` | Idem para CSV | Mesma justificativa. |
| 20 | `jsonFactoryDeveCriarJsonGenerator` | Idem para JSON | Mesma justificativa. |
| 21 | `xmlFactoryDeveCriarXmlGenerator` | Idem para XML | Valida a fábrica do formato novo. |
| 22 | `htmlFactoryDeveCriarHtmlGenerator` | Idem para HTML | Valida a fábrica do formato novo. |
| 23 | `cadaChamadaDeveCriarNovaInstancia` | Duas chamadas a `criarGerador()` retornam objetos `!=` (não são same) | Verifica que a fábrica cria instâncias novas, não reutiliza — comportamento esperado do Factory Method (cada chamada é uma criação). |
| 24 | `todosGeradorDevemImplementarInterface` | `criarGerador()` retorna `instanceof IRelatorioGenerator` para todas as fábricas | Garante o contrato polimórfico; se alguém esquecer de `implements`, esse teste pega. |

---

## 3. Gerenciamento Centralizado de Configurações (Singleton)

> [!NOTE]
> Os testes existentes em `ConfiguracaoSistemaTest` já cobrem: instância única, alteração de campos, e propagação entre referências. Os testes abaixo cobrem **aspectos adicionais** do Singleton e da integração com os Services.

### Arquivo sugerido: `ConfiguracaoSistemaSingletonTest.java`

| # | Nome do Teste | O que verifica | Por que é interessante |
|---|--------------|----------------|----------------------|
| 25 | `construtorDeveSerPrivado` | Via reflection, verifica que o construtor de `ConfiguracaoSistema` é `private` | Valida a implementação correta do Singleton — se o construtor for público, qualquer um pode criar novas instâncias, quebrando o padrão. |
| 26 | `instanciaDeveSerInicializadaComValoresPadrao` | `getNomeEmpresa()` retorna `"Empresa XPTO Ltda."`, `getAmbiente()` retorna `"DEV"`, etc. | Garante que os defaults de produção estão corretos. Evita regressões se alguém alterar os valores da inicialização estática. |
| 27 | `instanciaNuncaDeveSerNull` | `getInstancia()` nunca retorna `null` | Teste defensivo — garante que o campo `static final` é sempre inicializado. |
| 28 | `alteracaoDeConfiguracaoDeveSerVisivelEmNovoService` | Altera `nomeEmpresa` via `config`, cria novo `ConfiguracaoService`, verifica que o novo service vê a alteração | Comprova que o Singleton funciona como ponto centralizado: mudanças são globais e imediatamente visíveis por novos consumidores. |
| 29 | `exportacaoServiceDeveUsarMesmaConfiguracao` | `ExportacaoService` usa a mesma instância de `ConfiguracaoSistema` que `getInstancia()` | Valida a integração: o serviço de exportação realmente consome a configuração centralizada. |
| 30 | `relatorioServiceDeveUsarMesmaConfiguracao` | `RelatorioService` (debug ativo) reflete a configuração do Singleton | Valida que o `RelatorioService` também usa o Singleton, garantindo consistência nos serviços. |
| 31 | `alteracaoDeDiretorioDeveRefletirNaExportacao` | Altera `diretorioExportacao`, exporta relatório, verifica que a saída contém o novo diretório | Testa o fluxo end-to-end: configuração centralizada → serviço de exportação → saída correta. |

---

## Resumo

| Categoria | Qtd. Testes | Arquivos de Teste |
|-----------|:-----------:|-------------------|
| Novos Formatos (HTML + XML) | 10 | `HtmlRelatorioGeneratorTest.java`, `XmlRelatorioGeneratorTest.java` |
| Comportamento das Fábricas | 14 | `GeradorRegistryTest.java`, `RelatorioGeneratorFactoryTest.java` |
| Gerenciamento Centralizado (Singleton) | 7 | `ConfiguracaoSistemaSingletonTest.java` |
| **Total** | **31** | **5 novos arquivos** |

> [!TIP]
> Todos os testes utilizam apenas `JUnit 5` e asserções padrão, sem dependências adicionais. Lembre-se de restaurar os valores originais da `ConfiguracaoSistema` nos testes que alteram o Singleton (usando `@AfterEach`), pois como é compartilhado entre testes, alterações podem causar efeitos colaterais.
