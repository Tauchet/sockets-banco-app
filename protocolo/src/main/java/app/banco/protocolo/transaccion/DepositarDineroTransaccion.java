package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    public void leer(DataInputStream entrada) throws IOException {
        this.cuentaAhorros = entrada.readInt();
        this.valor = entrada.readInt();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeInt(this.cuentaAhorros);
        salida.writeInt(this.valor);
    }

}
