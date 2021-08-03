package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class TrasladarDineroTransaccion extends Transaccion{

    private int cuentaAhorros;
    private int valor;

    public TrasladarDineroTransaccion() {
        super(TipoTransaccion.TRASLADAR);
    }

    public TrasladarDineroTransaccion(int cuentaAhorros, int valor) {
        super(TipoTransaccion.TRASLADAR);
        this.cuentaAhorros = cuentaAhorros;
        this.valor = valor;
    }

    public int getCuentaAhorros() {
        return cuentaAhorros;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.cuentaAhorros = entrada.leerEntero();
        this.valor = entrada.leerEntero();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirEntero(this.cuentaAhorros);
        salida.escribirEntero(this.valor);
    }

    @Override
    public String toString() {
        return "TrasladarDineroTransaccion{" +
                "cuentaAhorros=" + cuentaAhorros +
                ", valor=" + valor +
                '}';
    }

}
