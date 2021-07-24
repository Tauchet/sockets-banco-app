package app.banco.protocolo.paquete;

import app.banco.protocolo.TipoPaquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CancelarBolsilloPaquete implements Paquete {

    private String bolsilloId;

    public CancelarBolsilloPaquete() {}

    public CancelarBolsilloPaquete(String bolsilloId) {
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

    @Override
    public TipoPaquete getId() {
        return TipoPaquete.CANCELAR_BOLSILLO;
    }


}
