package br.com.unipds;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeitorOpcoesCLITest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Deve ler opções padrão quando nenhum argumento é passado")
    void deveLerOpcoesPadrao() {
        String[] args = {};
        LeitorOpcoesCLI leitor = new LeitorOpcoesCLI(args);

        assertThat(leitor.getDiretorioDosMD()).isEqualTo(Paths.get(""));
        assertThat(leitor.getFormato()).isEqualTo(FormatoEbook.PDF);
        assertThat(leitor.getArquivoDeSaida()).isEqualTo(Paths.get("book.pdf"));
        assertThat(leitor.isModoVerboso()).isFalse();
    }

    @Test
    @DisplayName("Deve ler diretório customizado")
    void deveLerDiretorioCustomizado() {
        String[] args = {"-d", tempDir.toString()};
        LeitorOpcoesCLI leitor = new LeitorOpcoesCLI(args);

        assertThat(leitor.getDiretorioDosMD()).isEqualTo(tempDir);
    }

    @Test
    @DisplayName("Deve ler formato e saída customizados")
    void deveLerFormatoESaidaCustomizados() {
        Path arquivoSaida = tempDir.resolve("meulivro.epub");
        String[] args = {"-f", "epub", "-o", arquivoSaida.toString()};
        LeitorOpcoesCLI leitor = new LeitorOpcoesCLI(args);

        assertThat(leitor.getFormato()).isEqualTo(FormatoEbook.EPUB);
        assertThat(leitor.getArquivoDeSaida()).isEqualTo(arquivoSaida);
    }

    @Test
    @DisplayName("Deve ler modo verboso")
    void deveLerModoVerboso() {
        String[] args = {"-v"};
        LeitorOpcoesCLI leitor = new LeitorOpcoesCLI(args);

        assertThat(leitor.isModoVerboso()).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exceção para formato inválido")
    void deveLancarExcecaoParaFormatoInvalido() {
        String[] args = {"-f", "invalido"};
        assertThatThrownBy(() -> new LeitorOpcoesCLI(args))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Formato do ebook inválido");
    }

    @Test
    @DisplayName("Deve lançar exceção para diretório inexistente")
    void deveLancarExcecaoParaDiretorioInexistente() {
        String pathInexistente = tempDir.resolve("nao-existe").toString();
        String[] args = {"-d", pathInexistente};
        assertThatThrownBy(() -> new LeitorOpcoesCLI(args))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("não é um diretório");
    }
}
