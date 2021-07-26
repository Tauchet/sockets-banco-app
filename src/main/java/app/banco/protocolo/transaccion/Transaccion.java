package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public abstract class Transaccion {

    private final TipoTransaccion id;

    public Transaccion(TipoTransaccion id) {
        this.id = id;
    }

    public TipoTransaccion getId() {
        return id;
    }

    public void leer(PaqueteLector entrada) throws IOException {
    }

    public void escribir(PaqueteEscritor salida) throws IOException {

    }

}
