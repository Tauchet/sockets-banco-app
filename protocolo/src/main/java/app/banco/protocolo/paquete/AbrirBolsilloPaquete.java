package app.banco.protocolo.paquete;

import app.banco.protocolo.TipoPaquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AbrirBolsilloPaquete implements Paquete {

    private int cuentaAhorros;

    public AbrirBolsilloPaquete() {}

    public AbrirBolsilloPaquete(int cuentaAhorros) {
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

    @Override
    public TipoPaquete getId() {
        return TipoPaquete.ABRIR_BOLSILLO;
    }


}
