package app.banco.protocolo;

import java.util.ArrayList;
import java.util.List;

public class PaqueteLector {

    private final String[] parametros;
    private int parametroIndice;

    public PaqueteLector(String valor) {
        this.parametros = valor.split(", ");
        this.parametroIndice = 0;
    }

    public String leerCadena() {
        int indice = this.parametroIndice;
        this.parametroIndice++;
        return parametros[indice];
    }

    public int leerEntero() {
        int indice = this.parametroIndice;
        this.parametroIndice++;
        return Integer.parseInt(parametros[indice]);
    }

    public List<String> leerLista() {
        List<String> resultado = new ArrayList<>();
        int tamanio = leerEntero();
        for (int i = 0; i < tamanio; i++) {
            resultado.add(leerCadena());
        }
        return resultado;
    }

}
