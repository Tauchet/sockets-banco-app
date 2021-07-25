package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class DepositarDineroTransaccion extends Transaccion{

    private int cuentaAhorros;
    private int valor;

    public DepositarDineroTransaccion() {
        super(TipoTransaccion.DEPOSITAR);
    }

    public DepositarDineroTransaccion(int cuentaAhorros, int valor) {
        super(TipoTransaccion.DEPOSITAR);
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

}
