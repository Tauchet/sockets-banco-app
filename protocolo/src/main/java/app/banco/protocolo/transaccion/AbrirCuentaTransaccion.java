package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

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
    public void leer(PaqueteLector entrada) throws IOException {
        this.nombreUsuario = entrada.leerCadena();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirCadena(this.nombreUsuario);
    }

}
