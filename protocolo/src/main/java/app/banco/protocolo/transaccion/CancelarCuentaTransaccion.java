package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    public void leer(DataInputStream entrada) throws IOException {
        this.cuentaAhorros = entrada.readInt();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeInt(this.cuentaAhorros);
    }

}
