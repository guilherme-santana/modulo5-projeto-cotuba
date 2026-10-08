package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new Main().executar(args);
    }

    public int executar(String[] args) {
        Path diretorioDosMD;
        FormatoEbook formato;
        Path arquivoDeSaida;
        boolean modoVerboso = false;

        try {
            var opcoesCLI = new LeitorOpcoesCLI(args);

            diretorioDosMD = opcoesCLI.getDiretorioDosMD();
            formato = opcoesCLI.getFormato();
            arquivoDeSaida = opcoesCLI.getArquivoDeSaida();
            modoVerboso = opcoesCLI.isModoVerboso();

            var renderizadorMD = new RenderizadorMD();
            List<Capitulo> capitulos = renderizadorMD.renderizar(diretorioDosMD);

            if (FormatoEbook.PDF.equals(formato)) {
                var geradorPDF = new GeradorPDF();
                geradorPDF.gera(capitulos, arquivoDeSaida);
            } else if (FormatoEbook.EPUB.equals(formato)) {
                var geradorEPUB = new GeradorEPUB();
                geradorEPUB.gera(capitulos, arquivoDeSaida);
            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
            return 0;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            return 1;
        }
    }
}