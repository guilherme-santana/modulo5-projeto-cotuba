package br.com.unipds;

import java.nio.file.Path;

public class Main {

    void main(String[] args) {

        Path diretorioDosMD;
        String formato;
        Path arquivoDeSaida;
        boolean modoVerboso = false;

        try {
            var opcoesCLI = new LeitorOpcoesCLI(args);

            diretorioDosMD = opcoesCLI.getDiretorioDosMD();
            formato = opcoesCLI.getFormato();
            arquivoDeSaida = opcoesCLI.getArquivoDeSaida();
            modoVerboso = opcoesCLI.isModoVerboso();

            if ("pdf".equals(formato)) {
                var geradorPDF = new GeradorPDF();
                geradorPDF.gera(diretorioDosMD, arquivoDeSaida);
            } else if ("epub".equals(formato)) {
                var geradorEPUB = new GeradorEPUB();
                geradorEPUB.gera(diretorioDosMD, arquivoDeSaida);
            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
            System.exit(0);


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }
}