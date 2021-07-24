package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CancelarBolsilloTransaccion extends Transaccion {

    private String bolsilloId;

    public CancelarBolsilloTransaccion() {
        super(TipoTransaccion.CANCELAR_BOLSILLO);
    }

    public CancelarBolsilloTransaccion(String bolsilloId) {
        super(TipoTransaccion.CANCELAR_BOLSILLO);
        this.bolsilloId = bolsilloId;
    }

    public String getBolsilloId() {
        return bolsilloId;
    }

    @Override
    public void leer(DataInputStream entrada) throws IOException {
        this.bolsilloId = entrada.readUTF();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeUTF(this.bolsilloId);
    }

}
