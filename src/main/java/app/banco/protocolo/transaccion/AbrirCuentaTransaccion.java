package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class AbrirCuentaTransaccion extends Transaccion {

    private String nombreCompleto;

    public AbrirCuentaTransaccion() {
        super(TipoTransaccion.ABRIR_CUENTA);
    }

    public AbrirCuentaTransaccion(String nombreCompleto) {
        super(TipoTransaccion.ABRIR_CUENTA);
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.nombreCompleto = entrada.leerCadena();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirCadena(this.nombreCompleto);
    }

    @Override
    public String toString() {
        return "AbrirCuentaTransaccion{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                '}';
    }

}
