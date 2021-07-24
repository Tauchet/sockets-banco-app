package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AbrirBolsilloTransaccion extends Transaccion {

    private int cuentaAhorros;

    public AbrirBolsilloTransaccion() {
        super(TipoTransaccion.ABRIR_BOLSILLO);
    }

    public AbrirBolsilloTransaccion(int cuentaAhorros) {
        super(TipoTransaccion.ABRIR_BOLSILLO);
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
