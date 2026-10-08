package br.com.unipds;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RenderizadorMDTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Deve renderizar arquivos markdown para capítulos na ordem alfabética")
    void deveRenderizarArquivosMD() throws IOException {
        Path cap1 = tempDir.resolve("01-cap1.md");
        Files.writeString(cap1, "# Título 1\nConteúdo 1");
        
        Path cap2 = tempDir.resolve("02-cap2.md");
        Files.writeString(cap2, "# Título 2\nConteúdo 2");

        RenderizadorMD renderizador = new RenderizadorMD();
        List<Capitulo> capitulos = renderizador.renderizar(tempDir);

        assertThat(capitulos).hasSize(2);
        assertThat(capitulos.get(0).getTitulo()).isEqualTo("Título 1");
        assertThat(capitulos.get(0).getHtml()).contains("<h1>Título 1</h1>").contains("<p>Conteúdo 1</p>");
        assertThat(capitulos.get(1).getTitulo()).isEqualTo("Título 2");
        assertThat(capitulos.get(1).getHtml()).contains("<h1>Título 2</h1>").contains("<p>Conteúdo 2</p>");
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver arquivos markdown no diretório")
    void deveLancarExcecaoQuandoNaoHouverArquivosMD() {
        RenderizadorMD renderizador = new RenderizadorMD();
        
        assertThatThrownBy(() -> renderizador.renderizar(tempDir))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Não foram encontrados capítulos");
    }
}
