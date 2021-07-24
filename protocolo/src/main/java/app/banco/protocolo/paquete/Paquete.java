package app.banco.protocolo.paquete;

import app.banco.protocolo.TipoPaquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Paquete {

    TipoPaquete getId();

    default void leer(DataInputStream entrada) throws IOException {
    }

    default void escribir(DataOutputStream salida) throws IOException {

    }

}
