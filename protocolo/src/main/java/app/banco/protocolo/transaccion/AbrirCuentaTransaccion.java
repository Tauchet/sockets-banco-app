package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AbrirCuentaTransaccion extends Transaccion {

    private String nombreUsuario;

    public AbrirCuentaTransaccion() {
        super(TipoTransaccion.ABRIR_CUENTA);
    }

    public AbrirCuentaTransaccion(String nombreUsuario) {
        super(TipoTransaccion.ABRIR_CUENTA);
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

}
