package app.banco.protocolo;

import java.util.ArrayList;
import java.util.List;

public class PaqueteEscritor {

    private final List<String> parametros;

    public PaqueteEscritor() {
        this.parametros = new ArrayList<>();
    }

    public void escribirCadena(String cadena) {
        this.parametros.add(cadena);
    }

    public void escribirEntero(int numero) {
        this.parametros.add("" + numero);
    }

    public void escribirLista(List<String> lista) {
        escribirEntero(lista.size());
        for (String linea: lista) {
            escribirCadena(linea);
        }
    }

    public String resultado() {
        return String.join(", ", parametros);
    }



}
