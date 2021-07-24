package app.banco.protocolo.paquete;

import app.banco.protocolo.TipoPaquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConsultarPaquete implements Paquete {

    private String codigo;

    public ConsultarPaquete() {}

    public ConsultarPaquete(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public void leer(DataInputStream entrada) throws IOException {
        this.codigo = entrada.readUTF();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeUTF(this.codigo);
    }

    @Override
    public TipoPaquete getId() {
        return TipoPaquete.CONSULTAR;
    }


}
