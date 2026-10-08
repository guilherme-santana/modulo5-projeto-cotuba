package br.com.unipds;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeradorPDFTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Deve gerar um arquivo PDF a partir de uma lista de capítulos")
    void deveGerarPDF() {
        Path arquivoSaida = tempDir.resolve("teste.pdf");
        Capitulo capitulo = new Capitulo();
        capitulo.setTitulo("Capítulo 1");
        capitulo.setHtml("<h1>Capítulo 1</h1><p>Conteúdo</p>");
        List<Capitulo> capitulos = List.of(capitulo);
        
        GeradorPDF gerador = new GeradorPDF();
        gerador.gera(capitulos, arquivoSaida);

        assertThat(arquivoSaida).exists().isRegularFile();
    }
}
