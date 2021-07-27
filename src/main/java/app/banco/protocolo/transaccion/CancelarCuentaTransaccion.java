package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class CancelarCuentaTransaccion extends Transaccion {

    private int cuentaAhorros;

    public CancelarCuentaTransaccion() {
        super(TipoTransaccion.CANCELAR_CUENTA);
    }

    public CancelarCuentaTransaccion(int cuentaAhorros) {
        super(TipoTransaccion.CANCELAR_CUENTA);
        this.cuentaAhorros = cuentaAhorros;
    }

    public int getCuentaAhorros() {
        return cuentaAhorros;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.cuentaAhorros = entrada.leerEntero();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirEntero(this.cuentaAhorros);
    }

    @Override
    public String toString() {
        return "CancelarCuentaTransaccion{" +
                "cuentaAhorros=" + cuentaAhorros +
                '}';
    }

}
