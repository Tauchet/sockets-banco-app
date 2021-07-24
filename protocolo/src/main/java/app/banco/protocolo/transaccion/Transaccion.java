package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Transaccion {

    private final TipoTransaccion id;
    private long tiempo;

    public Transaccion(TipoTransaccion id) {
        this.id = id;
        this.tiempo = System.currentTimeMillis();
    }

    public TipoTransaccion getId() {
        return id;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public void leer(DataInputStream entrada) throws IOException {
    }

    public void escribir(DataOutputStream salida) throws IOException {

    }

}
