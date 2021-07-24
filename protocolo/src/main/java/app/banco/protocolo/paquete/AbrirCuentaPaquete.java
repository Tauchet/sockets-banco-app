package app.banco.protocolo.paquete;

import app.banco.protocolo.TipoPaquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AbrirCuentaPaquete implements Paquete {

    private String nombreUsuario;

    public AbrirCuentaPaquete() {}

    public AbrirCuentaPaquete(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public void leer(DataInputStream entrada) throws IOException {
        this.nombreUsuario = entrada.readUTF();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeUTF(this.nombreUsuario);
    }

    @Override
    public TipoPaquete getId() {
        return TipoPaquete.ABRIR_CUENTA;
    }

}
