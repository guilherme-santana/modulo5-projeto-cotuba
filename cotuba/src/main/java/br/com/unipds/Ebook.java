package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private String titulo;
    private String autor;
    private FormatoEbook formato;
    private List<Capitulo> capitulos;
    private Path arquivoSaida;
}
